/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QTreeService; 
import com.jeesite.modules.mc.entity.McMapinfo;
import com.jeesite.modules.mc.dao.McMapinfoDao;

/**
 * 地图信息表Service
 * @author mxbt
 * @version 2020-07-27
 */
@Service
@Transactional(readOnly=true)
public class McMapinfoService extends QTreeService<McMapinfoDao, McMapinfo> {
	
	/**
	 * 获取单条数据
	 * @param mcMapinfo
	 * @return
	 */
	@Override
	public McMapinfo get(McMapinfo mcMapinfo) {
		return super.get(mcMapinfo);
	}
	
	/**
	 * 查询列表数据
	 * @param mcMapinfo
	 * @return
	 */
	@Override
	public List<McMapinfo> findList(McMapinfo mcMapinfo) {
		return super.findList(mcMapinfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcMapinfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McMapinfo mcMapinfo) {
		super.save(mcMapinfo);
	}
	
	/**
	 * 更新状态
	 * @param mcMapinfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McMapinfo mcMapinfo) {
		super.updateStatus(mcMapinfo);
	}
	
	/**
	 * 删除数据
	 * @param mcMapinfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McMapinfo mcMapinfo) {
		super.delete(mcMapinfo);
	}
	
}