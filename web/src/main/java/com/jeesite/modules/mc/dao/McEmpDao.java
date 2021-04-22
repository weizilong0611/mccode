/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.dao;

import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.mc.entity.McEmp;

/**
 * 水电站流量DAO接口
 * @author mxbt
 * @version 2020-07-07
 */
@MyBatisDao
public interface McEmpDao extends QCrudDao<McEmp> {
	
}