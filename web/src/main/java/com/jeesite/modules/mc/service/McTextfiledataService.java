/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.mc.entity.McTextfiledata;
import com.jeesite.modules.mc.dao.McTextfiledataDao;

/**
 * 数据浏览Service
 * @author Mxbt
 * @version 2020-05-13
 */
@Service
@Transactional(readOnly=true)
public class McTextfiledataService extends QCrudService<McTextfiledataDao, McTextfiledata> {
	
	public String findMd5ByPath(String path) {
		return dao.findMd5ByPath(path);
	}
	
	@Transactional(readOnly=false)
	public void deleteByPath(String path) {
		dao.deleteByPath(path);
	}	
	
	/**
	 * 获取单条数据
	 * @param mcTextfiledata
	 * @return
	 */
	@Override
	public McTextfiledata get(McTextfiledata mcTextfiledata) {
		return super.get(mcTextfiledata);
	}
	
	/**
	 * 查询分页数据
	 * @param mcTextfiledata 查询条件
	 * @param mcTextfiledata.page 分页对象
	 * @return
	 */
	@Override
	public Page<McTextfiledata> findPage(McTextfiledata mcTextfiledata) {
		return super.findPage(mcTextfiledata);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcTextfiledata
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McTextfiledata mcTextfiledata) {
		super.save(mcTextfiledata);
	}
	
	/**
	 * 更新状态
	 * @param mcTextfiledata
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McTextfiledata mcTextfiledata) {
		super.updateStatus(mcTextfiledata);
	}
	
	/**
	 * 删除数据
	 * @param mcTextfiledata
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McTextfiledata mcTextfiledata) {
		super.delete(mcTextfiledata);
	}
	
}