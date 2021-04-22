/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.dao;

import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.mc.entity.McMedicine;

/**
 * 藏药产业导航DAO接口
 * @author MXBT
 * @version 2020-08-12
 */
@MyBatisDao
public interface McMedicineDao extends QCrudDao<McMedicine> {
	
}