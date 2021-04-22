/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.mc.entity.McWorkspace;

/**
 * 工作空间DAO接口
 * @author MXBT
 * @version 2020-03-04
 */
@MyBatisDao
public interface McWorkspaceDao extends CrudDao<McWorkspace> {
	
}