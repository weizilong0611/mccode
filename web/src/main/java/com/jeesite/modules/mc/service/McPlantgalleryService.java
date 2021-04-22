/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.mc.entity.McPlantgallery;
import com.jeesite.modules.mc.dao.McPlantgalleryDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 植物图册Service
 * @author 阿
 * @version 2020-09-16
 */
@Service
@Transactional(readOnly=true)
public class McPlantgalleryService extends QCrudService<McPlantgalleryDao, McPlantgallery> {
	
	/**
	 * 获取单条数据
	 * @param mcPlantgallery
	 * @return
	 */
	@Override
	public McPlantgallery get(McPlantgallery mcPlantgallery) {
		return super.get(mcPlantgallery);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param mcPlantgallery
	 * @return
	 */
	@Override
	public Page<McPlantgallery> findPage(Page<McPlantgallery> page, McPlantgallery mcPlantgallery) {
		return super.findPage(page, mcPlantgallery);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcPlantgallery
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McPlantgallery mcPlantgallery) {
		super.save(mcPlantgallery);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(mcPlantgallery.getId(), "mcPlantgallery_image");
	}
	
	/**
	 * 更新状态
	 * @param mcPlantgallery
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McPlantgallery mcPlantgallery) {
		super.updateStatus(mcPlantgallery);
	}
	
	/**
	 * 删除数据
	 * @param mcPlantgallery
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McPlantgallery mcPlantgallery) {
		super.delete(mcPlantgallery);
	}
	
}