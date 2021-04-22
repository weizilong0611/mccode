/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.geo.dao;

import com.jeesite.common.dao.QTreeDao;
import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.geo.entity.McGeofolder;

/**
 * 数据管理DAO接口
 * @author Mxbt
 * @version 2020-05-12
 */
@MyBatisDao
public interface McGeofolderDao extends QTreeDao<McGeofolder> {

}