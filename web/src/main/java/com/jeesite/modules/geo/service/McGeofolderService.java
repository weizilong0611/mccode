/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.geo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.common.service.QTreeService;
import com.jeesite.common.service.TreeService;
import com.jeesite.modules.geo.entity.McGeofolder;
import com.jeesite.modules.mc.dao.McAplantatlasDao;
import com.jeesite.modules.mc.entity.McAplantatlas;
import com.jeesite.modules.geo.dao.McGeofolderDao;

/**
 * 数据管理Service
 * @author Mxbt
 * @version 2020-05-12
 */
@Service
@Transactional(readOnly=true)
public class McGeofolderService extends QTreeService<McGeofolderDao, McGeofolder> {
//public class McAplantatlasService extends QCrudService<McAplantatlasDao, McAplantatlas> {
	
	/**
	 * 获取单条数据
	 * @param mcGeofolder
	 * @return
	 */
	@Override
	public McGeofolder get(McGeofolder mcGeofolder) {
		return super.get(mcGeofolder);
	}
	
	/**
	 * 查询列表数据
	 * @param mcGeofolder
	 * @return
	 */
	@Override
	public List<McGeofolder> findList(McGeofolder mcGeofolder) {
		return super.findList(mcGeofolder);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcGeofolder
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McGeofolder mcGeofolder) {
		super.save(mcGeofolder);
	}
	
	/**
	 * 更新状态
	 * @param mcGeofolder
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McGeofolder mcGeofolder) {
		super.updateStatus(mcGeofolder);
	}
	
	/**
	 * 删除数据
	 * @param mcGeofolder
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McGeofolder mcGeofolder) {
		super.delete(mcGeofolder);
	}
	
}