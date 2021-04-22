/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.mc.entity.McWorkImg;
import com.jeesite.modules.mc.dao.McWorkImgDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 采样工作照Service
 * @author MXBT
 * @version 2020-08-28
 */
@Service
@Transactional(readOnly=true)
public class McWorkImgService extends QCrudService<McWorkImgDao, McWorkImg> {
	
	/**
	 * 获取单条数据
	 * @param mcWorkImg
	 * @return
	 */
	@Override
	public McWorkImg get(McWorkImg mcWorkImg) {
		return super.get(mcWorkImg);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param mcWorkImg
	 * @return
	 */
	@Override
	public Page<McWorkImg> findPage(Page<McWorkImg> page, McWorkImg mcWorkImg) {
		return super.findPage(page, mcWorkImg);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcWorkImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McWorkImg mcWorkImg) {
		super.save(mcWorkImg);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(mcWorkImg.getId(), "mcWorkImg_image");
	}
	
	/**
	 * 更新状态
	 * @param mcWorkImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McWorkImg mcWorkImg) {
		super.updateStatus(mcWorkImg);
	}
	
	/**
	 * 删除数据
	 * @param mcWorkImg
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McWorkImg mcWorkImg) {
		super.delete(mcWorkImg);
	}
	
}