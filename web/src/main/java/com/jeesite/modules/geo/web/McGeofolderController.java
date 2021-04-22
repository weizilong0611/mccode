/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.geo.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.codec.Md5Utils;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.utils.SpringUtils;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.geo.dataParse.DataParse;
import com.jeesite.modules.geo.entity.McGeofolder;
import com.jeesite.modules.geo.service.McGeofolderService;
import com.jeesite.modules.mc.entity.McTextfiledata;
import com.jeesite.modules.mc.service.McTextfiledataService;
import com.jeesite.modules.msg.entity.MsgPush;
import com.jeesite.modules.msg.utils.MsgPushUtils;

/**
 * 数据管理Controller
 * @author Mxbt
 * @version 2020-05-12
 */
@Controller
@RequestMapping(value = "${adminPath}/geo/mcGeofolder")
public class McGeofolderController extends BaseController {

	@Autowired
	private McGeofolderService mcGeofolderService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private McTextfiledataService mcTextfiledataService;
	
	
	/**
	 * 删除数据浏览
	 */ 
	@RequestMapping(value = "runTask")
	@ResponseBody
	public String runTask(String id) {
		
		McTextfiledata mcTextfiledata = new McTextfiledata();
		mcTextfiledata.getSqlMap().getWhere().and("filemd5", QueryType.EQ,"");
		mcTextfiledataService.delete(mcTextfiledata);
		
		McGeofolder	folder =	mcGeofolderService.get(id);
		
		List<String> path = DataParse.getPathList(folder);
		List<String> fialdList = new ArrayList();
		path.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
			
				String md5 = Md5Utils.md5File(new File(t));
				String md5Old = mcTextfiledataService.findMd5ByPath(t);
				
			
				if(true) {
					
					if(!StringUtils.isEmpty(md5Old)) {
						System.out.println("文件发生修改");
						mcTextfiledataService.deleteByPath(t); 
					}else {
						mcTextfiledataService.deleteByPath(t); 
						System.out.println("新数据，准备push到数据库");	
					}
					try {
						t = t.toLowerCase();
						if(t.endsWith(".xls") || t.endsWith(".xlsx")) {		//判断是否为excel，还需判断csv等等等
							List<McTextfiledata> list = DataParse.parseExcel(t, folder);							
							mcTextfiledataService.insertBatch(list);	
						}
					} catch (Exception e) {
						System.out.println("异常！" + t);
						e.printStackTrace();
						fialdList.add( renderResult(Global.TRUE, "解析失败！" + t));
					}
				}else if(md5.equals(md5Old)) {
					System.out.println("文件未修改");
				}
			}
		});
		
		if(fialdList.size()>0)
			return StringUtils.join(fialdList,"</br>");
		
		return renderResult(Global.TRUE, text("解析成功！"));
	}
	
	
	
	
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McGeofolder get(String treeCode, boolean isNewRecord) {
		return mcGeofolderService.get(treeCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("geo:mcGeofolder:view")
	@RequestMapping(value = {"list", ""})
	public String list(McGeofolder mcGeofolder, Model model) { 
		model.addAttribute("mcGeofolder", mcGeofolder);
		return "modules/geo/mcGeofolderList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("geo:mcGeofolder:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<McGeofolder> listData(McGeofolder mcGeofolder) {
		if (StringUtils.isBlank(mcGeofolder.getParentCode())) {
			mcGeofolder.setParentCode(McGeofolder.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(mcGeofolder.getTreeName())){
			mcGeofolder.setParentCode(null);
		}
		if (StringUtils.isNotBlank(mcGeofolder.getFileType())){
			mcGeofolder.setParentCode(null);
		}
		if (StringUtils.isNotBlank(mcGeofolder.getFileRegex())){
			mcGeofolder.setParentCode(null);
		}
		if (StringUtils.isNotBlank(mcGeofolder.getFileRule())){
			mcGeofolder.setParentCode(null);
		}
		if (StringUtils.isNotBlank(mcGeofolder.getRemarks())){
			mcGeofolder.setParentCode(null);
		}
		List<McGeofolder> list = mcGeofolderService.findList(mcGeofolder);
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("geo:mcGeofolder:view")
	@RequestMapping(value = "form")
	public String form(McGeofolder mcGeofolder, Model model) {
		// 创建并初始化下一个节点信息
		mcGeofolder = createNextNode(mcGeofolder);
		model.addAttribute("mcGeofolder", mcGeofolder);
		return "modules/geo/mcGeofolderForm";
	}
	
	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("geo:mcGeofolder:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public McGeofolder createNextNode(McGeofolder mcGeofolder) {
		if (StringUtils.isNotBlank(mcGeofolder.getParentCode())){
			mcGeofolder.setParent(mcGeofolderService.get(mcGeofolder.getParentCode()));
		}
		if (mcGeofolder.getIsNewRecord()) {
			McGeofolder where = new McGeofolder();
			where.setParentCode(mcGeofolder.getParentCode());
			McGeofolder last = mcGeofolderService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				mcGeofolder.setTreeSort(last.getTreeSort() + 30);
				mcGeofolder.setTreeCode(IdGen.nextCode(last.getTreeCode()));
			}else if (mcGeofolder.getParent() != null){
				mcGeofolder.setTreeCode(mcGeofolder.getParent().getTreeCode() + "001");
			}
		}
		// 以下设置表单默认数据
		if (mcGeofolder.getTreeSort() == null){
			mcGeofolder.setTreeSort(McGeofolder.DEFAULT_TREE_SORT);
		}
		return mcGeofolder;
	}

	/**
	 * 保存数据管理
	 */
	@RequiresPermissions("geo:mcGeofolder:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McGeofolder mcGeofolder) {
		mcGeofolderService.save(mcGeofolder);
		if(mcGeofolder.getFileType().equals("folder")) {
			String folder = mcGeofolder.getTreeNames();
			folder = DataParse.pathBuilder(folder);
			try {
				FileUtils.forceMkdir(new File(folder));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(String.format("创建目录%s成功",folder));
		}
		return renderResult(Global.TRUE, text("保存数据管理成功！"));
	}
	
	/**
	 * 删除数据管理
	 */
	@RequiresPermissions("geo:mcGeofolder:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McGeofolder mcGeofolder) {
		mcGeofolderService.delete(mcGeofolder);
		return renderResult(Global.TRUE, text("删除数据管理成功！"));
	}
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("geo:mcGeofolder:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<McGeofolder> list = mcGeofolderService.findList(new McGeofolder());
		for (int i=0; i<list.size(); i++){
			McGeofolder e = list.get(i);
			// 过滤非正常的数据
			if (!McGeofolder.STATUS_NORMAL.equals(e.getStatus())){
				continue;
			}
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotBlank(excludeCode)){
				if (e.getId().equals(excludeCode)){
					continue;
				}
				if (e.getParentCodes().contains("," + excludeCode + ",")){
					continue;
				}
			}
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentCode());
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getTreeCode(), e.getTreeName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("geo:mcGeofolder:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(McGeofolder mcGeofolder){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		mcGeofolderService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}
	
}