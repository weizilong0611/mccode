/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.news.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.news.entity.McOpinion;
import com.jeesite.modules.news.dao.McOpinionDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 意见举报Service
 * @author zq
 * @version 2020-11-02
 */
@Service
@Transactional(readOnly=true)
public class McOpinionService extends QCrudService<McOpinionDao, McOpinion> {
	
	/**
	 * 获取单条数据
	 * @param mcOpinion
	 * @return
	 */
	@Override
	public McOpinion get(McOpinion mcOpinion) {
		return super.get(mcOpinion);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param mcOpinion
	 * @return
	 */
	@Override
	public Page<McOpinion> findPage(Page<McOpinion> page, McOpinion mcOpinion) {
		return super.findPage(page, mcOpinion);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcOpinion
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McOpinion mcOpinion) {
		super.save(mcOpinion);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(mcOpinion.getId(), "mcOpinion_image");
	}
	
	/**
	 * 更新状态
	 * @param mcOpinion
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McOpinion mcOpinion) {
		super.updateStatus(mcOpinion);
	}
	
	/**
	 * 删除数据
	 * @param mcOpinion
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McOpinion mcOpinion) {
		super.delete(mcOpinion);
	}
	
}