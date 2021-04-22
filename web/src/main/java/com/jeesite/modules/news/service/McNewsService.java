/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.news.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.news.entity.McNews;
import com.jeesite.modules.news.dao.McNewsDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 新闻Service
 * @author zq
 * @version 2020-10-20
 */
@Service
@Transactional(readOnly=true)
public class McNewsService extends QCrudService<McNewsDao, McNews> {
	
	/**
	 * 获取单条数据
	 * @param mcNews
	 * @return
	 */
	@Override
	public McNews get(McNews mcNews) {
		return super.get(mcNews);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param mcNews
	 * @return
	 */
	@Override
	public Page<McNews> findPage(Page<McNews> page, McNews mcNews) {
		return super.findPage(page, mcNews);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcNews
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McNews mcNews) {
		super.save(mcNews);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(mcNews.getId(), "mcNews_image");
	}
	
	/**
	 * 更新状态
	 * @param mcNews
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McNews mcNews) {
		super.updateStatus(mcNews);
	}
	
	/**
	 * 删除数据
	 * @param mcNews
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McNews mcNews) {
		super.delete(mcNews);
	}
	
}