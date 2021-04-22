/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.app.entity.AppEdition;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.app.dao.AppEditionDao;

/**
 * app_editionService
 * @author ZQ
 * @version 2020-09-09
 */
@Service
@Transactional(readOnly=true)
public class AppEditionService extends QCrudService<AppEditionDao, AppEdition> {
	
	/**
	 * 获取单条数据
	 * @param appEdition
	 * @return
	 */
	@Override
	public AppEdition get(AppEdition appEdition) {
		return super.get(appEdition);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param appEdition
	 * @return
	 */
	@Override
	public Page<AppEdition> findPage(Page<AppEdition> page, AppEdition appEdition) {
		return super.findPage(page, appEdition);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param appEdition
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AppEdition appEdition) {
		super.save(appEdition);
		// 保存上传附件
		FileUploadUtils.saveFileUpload(appEdition.getId(),"appEdition_file");
	}
	
	/**
	 * 更新状态
	 * @param appEdition
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AppEdition appEdition) {
		super.updateStatus(appEdition);
	}
	
	/**
	 * 删除数据
	 * @param appEdition
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AppEdition appEdition) {
		super.delete(appEdition);
	}
	
}