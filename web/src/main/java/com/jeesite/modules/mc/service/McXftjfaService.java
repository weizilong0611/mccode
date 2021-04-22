/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.mc.entity.McXftjfa;
import com.jeesite.modules.mc.dao.McXftjfaDao;

/**
 * 生态修复推荐Service
 * @author MXBT
 * @version 2020-09-17
 */
@Service
@Transactional(readOnly=true)
public class McXftjfaService extends QCrudService<McXftjfaDao, McXftjfa> {
	
	/**
	 * 获取单条数据
	 * @param mcXftjfa
	 * @return
	 */
	@Override
	public McXftjfa get(McXftjfa mcXftjfa) {
		return super.get(mcXftjfa);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param mcXftjfa
	 * @return
	 */
	@Override
	public Page<McXftjfa> findPage(Page<McXftjfa> page, McXftjfa mcXftjfa) {
		return super.findPage(page, mcXftjfa);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcXftjfa
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McXftjfa mcXftjfa) {
		super.save(mcXftjfa);
	}
	
	/**
	 * 更新状态
	 * @param mcXftjfa
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McXftjfa mcXftjfa) {
		super.updateStatus(mcXftjfa);
	}
	
	/**
	 * 删除数据
	 * @param mcXftjfa
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McXftjfa mcXftjfa) {
		super.delete(mcXftjfa);
	}
	
}