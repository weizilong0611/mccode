/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.mc.entity.McWorkspace;
import com.jeesite.modules.utils.AntZip;
import com.jeesite.modules.utils.IServerNethelper;
import com.jeesite.modules.file.entity.FileEntity;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.mc.dao.McWorkspaceDao; 

/**
 * 工作空间Service
 * @author MXBT
 * @version 2020-03-04
 */
@Service
@Transactional(readOnly=true)
public class McWorkspaceService extends CrudService<McWorkspaceDao, McWorkspace> {
	
	
	/**解压工作空间zip*/
	private List<String> unZipFile(String bizKey) {
		List<String> ret = new ArrayList();
		List<FileUpload> files = FileUploadUtils.findFileUpload(bizKey, "mcWorkspace_file");
		AntZip zip = new AntZip();
		for (FileUpload fileUpload : files) {
			FileEntity fileE = fileUpload.getFileEntity();
			String filePath = fileE.getFileRealPath();
			
			 String unZipPath =Global.getConfig("mysetting.unZipPath") +File.separatorChar+ fileE.getFileId();
			
			 AntZip.unZip(new File(filePath), unZipPath );
			 AntZip.deleteAllFile(new File(filePath));
			 
			 List<String> r = AntZip.searchFiles(new File(unZipPath),".smwu");
			 ret.addAll(r);
		}
		return ret;
	}
	private void deleteSmwu(String bizId) {
		IServerNethelper iserver = new IServerNethelper(Global.getConfig("mysetting.iserver.url"),
				Global.getConfig("mysetting.iserver.uid"),
				Global.getConfig("mysetting.iserver.pwd"));
		iserver.login();
		McWorkspace work = super.get(bizId);
		 
		String path = work.getFilepath();
		for(String p : path.split(";")) {
			iserver.deleteWorkspace(p);
			 
			AntZip.deleteAllFile(new File(p).getParentFile());
		}
	}
	/**重新发布工作空间*/
	private McWorkspace republishSmwu(String bizId,String newSmwu ,boolean force) {
		McWorkspace work = super.get(bizId);
		
		//如果内容相同，就不更新
		if(
				(
						null!=work.getFilepath() &&
						work.getFilepath().equals(newSmwu)
				) && 
				!force
		)
			return null;
		
		IServerNethelper iserver = new IServerNethelper(Global.getConfig("mysetting.iserver.url"),
				Global.getConfig("mysetting.iserver.uid"),
				Global.getConfig("mysetting.iserver.pwd"));
		iserver.login();
		
		if(null != work.getFilepath()) {
			String path = work.getFilepath();
		
			for(String sub : path.split(";")) { 
				iserver.deleteWorkspace(sub);  
			}
		}
		
		JSONArray jsonArray = new JSONArray();
		
		for(String sub : newSmwu.split(";")) {
			JSONArray re = iserver.reportWorkspace(sub);
			jsonArray.add(re);
		} 
		work.setIserverdat(jsonArray.toJSONString());
		work.setFilepath(newSmwu);
		return work;
	}
	
	/**
	 * 获取单条数据
	 * @param mcWorkspace
	 * @return
	 */
	@Override
	public McWorkspace get(McWorkspace mcWorkspace) {
		return super.get(mcWorkspace);
	}
	
	/**
	 * 查询分页数据
	 * @param mcWorkspace 查询条件
	 * @param mcWorkspace.page 分页对象
	 * @return
	 */
	@Override
	public Page<McWorkspace> findPage(McWorkspace mcWorkspace) {
		return super.findPage(mcWorkspace);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcWorkspace
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McWorkspace mcWorkspace) {
		
		boolean isNew = mcWorkspace.getIsNewRecord();
		
		super.save(mcWorkspace);
		
		// 保存上传附件
		FileUploadUtils.saveFileUpload(mcWorkspace.getId(), "mcWorkspace_file");
		
		List<String> expPath = unZipFile(mcWorkspace.getId());
		
		mcWorkspace.setFilepath(StringUtils.join(expPath,";"));
		
		//强制重新发布服务
		McWorkspace tmp = republishSmwu(mcWorkspace.getId(),mcWorkspace.getFilepath(),isNew);
		if(null != tmp) {
			mcWorkspace.setIserverdat(tmp.getIserverdat());
			mcWorkspace.setFilepath(tmp.getFilepath());
		}
		super.update(mcWorkspace);
	}
	
	/**
	 * 更新状态
	 * @param mcWorkspace
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McWorkspace mcWorkspace) {
		
		republishSmwu(mcWorkspace.getId(),mcWorkspace.getFilepath(),false);
		
		super.updateStatus(mcWorkspace);
	}
	
	/**
	 * 删除数据
	 * @param mcWorkspace
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McWorkspace mcWorkspace) {
		
		deleteSmwu(mcWorkspace.getId());
		
		super.delete(mcWorkspace);
	}
	
}