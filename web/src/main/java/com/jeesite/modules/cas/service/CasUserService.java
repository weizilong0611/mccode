/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.cas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.cas.entity.CasUser;
import com.jeesite.modules.cas.dao.CasUserDao;
import com.jeesite.modules.sys.dao.UserRoleDao;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.entity.UserRole;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.service.UserService;

/**
 * js_sys_userService
 * @author 梦溪笔檀
 * @version 2019-03-04
 */
@Service
@Transactional(readOnly=true)
public class CasUserService extends CrudService<CasUserDao, CasUser> {
 

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 获取单条数据
	 * @param casUser
	 * @return
	 */
	@Override
	public CasUser get(CasUser casUser) {
		return super.get(casUser);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param casUser
	 * @return
	 */
	@Override
	public Page<CasUser> findPage(Page<CasUser> page, CasUser casUser) {
		return super.findPage(page, casUser);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param casUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(CasUser casUser) {
		super.save(casUser);
	}
	
	
	public void saveUserWithRole(User casUser,String roleName){

	
		Role role = new Role();
		role.setRoleName(roleName);
		role = roleService.getByRoleName(role);
		if(null == role){
			role = new Role();
			role.setRoleCode(roleName);
			role = roleService.get(role);
		}
		
		casUser.setPassword("123456");
		
		userService.save(casUser); 
		 
		UserRole ur = new UserRole();
		ur.setUserCode(casUser.getUserCode());
		ur.setRoleCode(role.getRoleCode());
		userRoleDao.insert(ur);
		
	}
	
	/**
	 * 更新状态
	 * @param casUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(CasUser casUser) {
		super.updateStatus(casUser);
	}
	
	/**
	 * 删除数据
	 * @param casUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(CasUser casUser) {
		super.delete(casUser);
	}
	
}