/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.mc.entity.McMedicine;
import com.jeesite.modules.mc.dao.McMedicineDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 藏药产业导航Service
 * @author MXBT
 * @version 2020-08-12
 */
@Service
@Transactional(readOnly=true)
public class McMedicineService extends QCrudService<McMedicineDao, McMedicine> {
	
	/**
	 * 获取单条数据
	 * @param mcMedicine
	 * @return
	 */
	@Override
	public McMedicine get(McMedicine mcMedicine) {
		return super.get(mcMedicine);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param mcMedicine
	 * @return
	 */
	@Override
	public Page<McMedicine> findPage(Page<McMedicine> page, McMedicine mcMedicine) {
		return super.findPage(page, mcMedicine);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcMedicine
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McMedicine mcMedicine) {
		super.save(mcMedicine);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(mcMedicine.getId(), "mcMedicine_image");
	}
	
	/**
	 * 更新状态
	 * @param mcMedicine
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McMedicine mcMedicine) {
		super.updateStatus(mcMedicine);
	}
	
	/**
	 * 删除数据
	 * @param mcMedicine
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McMedicine mcMedicine) {
		super.delete(mcMedicine);
	}
	
}