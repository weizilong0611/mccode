/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.mc.entity.McAnim;
import com.jeesite.modules.mc.dao.McAnimDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 畜牧品种Service
 * @author MXBT
 * @version 2020-08-28
 */
@Service
@Transactional(readOnly=true)
public class McAnimService extends QCrudService<McAnimDao, McAnim> {
	
	/**
	 * 获取单条数据
	 * @param mcAnim
	 * @return
	 */
	@Override
	public McAnim get(McAnim mcAnim) {
		return super.get(mcAnim);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param mcAnim
	 * @return
	 */
	@Override
	public Page<McAnim> findPage(Page<McAnim> page, McAnim mcAnim) {
		return super.findPage(page, mcAnim);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcAnim
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McAnim mcAnim) {
		super.save(mcAnim);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(mcAnim.getId(), "mcAnim_image");
	}
	
	/**
	 * 更新状态
	 * @param mcAnim
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McAnim mcAnim) {
		super.updateStatus(mcAnim);
	}
	
	/**
	 * 删除数据
	 * @param mcAnim
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McAnim mcAnim) {
		super.delete(mcAnim);
	}
	
}