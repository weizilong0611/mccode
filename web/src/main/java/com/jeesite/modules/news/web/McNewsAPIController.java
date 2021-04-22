/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.news.web;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.io.FileUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.app.entity.AppEdition;
import com.jeesite.modules.app.service.AppEditionService;
import com.jeesite.modules.file.entity.FileEntity;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.entity.FileUploadParams;
import com.jeesite.modules.file.service.FileEntityService;
import com.jeesite.modules.file.service.FileUploadService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.news.entity.McNews;
import com.jeesite.modules.news.entity.McOpinion;
import com.jeesite.modules.news.service.McNewsService;
import com.jeesite.modules.news.service.McOpinionService;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.entity.DictType;
import com.jeesite.modules.sys.service.DictDataService;
import com.jeesite.modules.sys.service.DictTypeService;

/**
 * 新闻Controller
 * 
 * @author zq
 * @version 2020-10-20
 */
@Controller
@RequestMapping(value = "/news/mcNewsAPI")
public class McNewsAPIController extends BaseController {

	@Autowired
	private McNewsService mcNewsService;

	@Autowired
	private AppEditionService appEditionService;

	@Autowired
	DictDataService dictDataService;

	@Autowired
	private McOpinionService mcOpinionService;
	@Autowired
	private FileEntityService fileEntityService;
	@Autowired
	private FileUploadService fileUploadService;

	// 获取轮播
	@RequestMapping(value = { "getColumnList_lb", "" })
	@ResponseBody
	public String getColumnList_lb(McNews mcNews, Model model) {
		mcNews.setColumnPlay("1");
		mcNews.setColumnApp("1");
		List<McNews> list = mcNewsService.findList(mcNews);
		return renderResult(Global.TRUE, text("成功！"), list);
	}

	@RequestMapping(value = { "getcolumnPagelist", "" })
	@ResponseBody
	public Page<McNews> getcolumnPagelist(McNews mcNews, String pageindex, String pagesize) {
		if (pagesize == null) {
			pagesize = "15";
		}
		Page<McNews> page = new Page<McNews>();
		page.setPageNo(Integer.parseInt(pageindex));
		page.setPageSize(Integer.parseInt(pagesize));
		page.setOrderBy("create_date desc");
		mcNews.setStatus("0");
		mcNews.setColumnApp("1");
		/* sddtyColumn.setColumnName("xwzx"); */

		return mcNewsService.findPage(page, mcNews);
	}

	/**
	 * 获取数据
	 */
	/*
	 * @ModelAttribute public TestUser get(String id, boolean isNewRecord) { return
	 * testUserService.get(id, isNewRecord); }
	 */

	@RequestMapping(value = { "getColumnById", "" })
	@ResponseBody
	public McNews getColumnById(McNews mcNews) {
		// SddtyColumn news = appNewsService.get(id, false);// .getColumnById(id);
		mcNews = mcNewsService.get(mcNews);

		/* 点击量累加 */
		mcNews.setColumnCount((mcNews.getColumnCount() == null ? 0 : mcNews.getColumnCount()) + 1);
		mcNewsService.update(mcNews);
		return mcNews;
	}

	//  查询最新版本
	@RequestMapping(value = "getNewEdition")
	@ResponseBody
	public String getNewEdition(AppEdition appEdition) {
		appEdition.setAppType("20");
		List<AppEdition> list = appEditionService.findList(appEdition);

		return renderResult(Global.TRUE, text("成功"), list.get(0));
	}

	//  获取字典信息
	@RequestMapping(value = "getdictData")
	@ResponseBody
	public String getdictData(DictData dictData) {
		List<DictData> list = dictDataService.findList(dictData);

		return renderResult(Global.TRUE, text("成功"), list);
	}

	/**
	 * 保存意见举报
	 */
	@PostMapping(value = "saveMcOpinion")
	@ResponseBody
	public String saveMcOpinion(@Validated McOpinion mcOpinion, String imgIds) {
		mcOpinion.setOpinionState("10");
		mcOpinionService.save(mcOpinion);
		FileUploadUtils.saveFileUpload(mcOpinion.getId(), "mcOpinion_image", imgIds, "");
		return renderResult(Global.TRUE, text("保存意见举报成功！"));
	}

	// 上传文件
	@RequestMapping(value = { "uploadfile", "post" })
	@ResponseBody
	public String uploadfile(FileUploadParams params, HttpServletRequest request) throws IOException {
		// 转型为MultipartHttpRequest
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		List fileUploads = new ArrayList();
		if (multipartRequest != null) {
			Iterator<String> files = multipartRequest.getFileNames();

			while (files.hasNext()) {
				MultipartFile multipartFile = multipartRequest.getFile(files.next());
				// 获得文件
				byte[] bytes = multipartFile.getBytes();
				String tmpfileName = MessageFormat.format("{0,date,yyyyMM}",
						new Object[] { new java.sql.Date(System.currentTimeMillis()) });
				FileEntity fileEntity = new FileEntity();
				fileEntity.setFilePath(tmpfileName + "/");
				fileEntity.setFileContentType(multipartFile.getContentType());
				int index = multipartFile.getOriginalFilename().lastIndexOf(".") + 1;
				String extension = multipartFile.getOriginalFilename().substring(index);
				fileEntity.setFileExtension(extension);
				fileEntity.setFileSize((long) bytes.length);
				fileEntity.setFileMd5("");
				fileEntityService.save(fileEntity);
				// 创建目录
				File direFile = new File(Global.getUserfilesBaseDir("fileupload" + File.separator + tmpfileName));
				if (!direFile.exists()) {
					FileUtils.createDirectory(Global.getUserfilesBaseDir("fileupload" + File.separator + tmpfileName));
				}
				File file = new File(Global.getUserfilesBaseDir("fileupload" + File.separator + tmpfileName)
						+ File.separator + fileEntity.getId() + "." + fileEntity.getFileExtension());
				FileUtils.writeByteArrayToFile(file, bytes);

				FileUpload fileUpload = new FileUpload();
				fileUpload.setFileEntity(fileEntity);
				fileUpload.setFileName(multipartFile.getOriginalFilename());
				fileUpload.setFileType(multipartFile.getContentType().indexOf("image") > -1 ? "image" : "file");
				// fileUpload.setBizKey(tempDelegationTestItem.getId());
				fileUpload.setBizType("infTestData_file");
				fileUpload.setStatus("0");
				fileUpload.preInsert();
				fileUpload.setIsNewRecord(true);
				fileUploadService.save(fileUpload);

				fileUploads.add(fileUpload.getId());
			}
		}

		return StringUtils.join(fileUploads.toArray(), ",");
	}
}