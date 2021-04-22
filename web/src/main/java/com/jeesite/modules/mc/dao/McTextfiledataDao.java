/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.mc.entity.McTextfiledata;

/**
 * 数据浏览DAO接口
 * @author Mxbt
 * @version 2020-05-13
 */
@MyBatisDao
public interface McTextfiledataDao extends QCrudDao<McTextfiledata> {
	String findMd5ByPath(String path);

	void deleteByPath(String path);
}