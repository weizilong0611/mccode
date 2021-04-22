/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.cas.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.cas.entity.CasUser;

/**
 * js_sys_userDAO接口
 * @author 梦溪笔檀
 * @version 2019-03-04
 */
@MyBatisDao
public interface CasUserDao extends CrudDao<CasUser> {
	
}