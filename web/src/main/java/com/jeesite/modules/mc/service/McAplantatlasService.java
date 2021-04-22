/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.mc.entity.McAplantatlas;
import com.jeesite.modules.mc.dao.McAplantatlasDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 珍贵动植物Service
 * @author mxbt
 * @version 2020-07-17
 */
@Service
@Transactional(readOnly=true)
public class McAplantatlasService extends QCrudService<McAplantatlasDao, McAplantatlas> {
	
	/**
	 * 获取单条数据
	 * @param mcAplantatlas
	 * @return
	 */
	@Override
	public McAplantatlas get(McAplantatlas mcAplantatlas) {
		return super.get(mcAplantatlas);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param mcAplantatlas
	 * @return
	 */
	@Override
	public Page<McAplantatlas> findPage(Page<McAplantatlas> page, McAplantatlas mcAplantatlas) {
		return super.findPage(page, mcAplantatlas);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcAplantatlas
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McAplantatlas mcAplantatlas) {
		super.save(mcAplantatlas);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(mcAplantatlas.getId(), "mcAplantatlas_image");
	}
	
	/**
	 * 更新状态
	 * @param mcAplantatlas
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McAplantatlas mcAplantatlas) {
		super.updateStatus(mcAplantatlas);
	}
	
	/**
	 * 删除数据
	 * @param mcAplantatlas
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McAplantatlas mcAplantatlas) {
		super.delete(mcAplantatlas);
	}
	
}