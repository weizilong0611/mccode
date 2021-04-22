/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.dao;

import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.mc.entity.McAnim;

/**
 * 畜牧品种DAO接口
 * @author MXBT
 * @version 2020-08-28
 */
@MyBatisDao
public interface McAnimDao extends QCrudDao<McAnim> {
	
}