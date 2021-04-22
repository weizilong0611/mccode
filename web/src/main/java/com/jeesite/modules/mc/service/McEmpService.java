/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.mc.entity.McEmp;
import com.jeesite.modules.mc.dao.McEmpDao;

/**
 * 水电站流量Service
 * @author mxbt
 * @version 2020-07-07
 */
@Service
@Transactional(readOnly=true)
public class McEmpService extends QCrudService<McEmpDao, McEmp> {
	
	/**
	 * 获取单条数据
	 * @param mcEmp
	 * @return
	 */
	@Override
	public McEmp get(McEmp mcEmp) {
		return super.get(mcEmp);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param mcEmp
	 * @return
	 */
	@Override
	public Page<McEmp> findPage(Page<McEmp> page, McEmp mcEmp) {
		return super.findPage(page, mcEmp);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcEmp
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McEmp mcEmp) {
		super.save(mcEmp);
	}
	
	/**
	 * 更新状态
	 * @param mcEmp
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McEmp mcEmp) {
		super.updateStatus(mcEmp);
	}
	
	/**
	 * 删除数据
	 * @param mcEmp
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McEmp mcEmp) {
		super.delete(mcEmp);
	}
	
}