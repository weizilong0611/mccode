/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.dao;

import com.jeesite.common.dao.QTreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.mc.entity.McMapinfo;

/**
 * 地图信息表DAO接口
 * @author mxbt
 * @version 2020-07-27
 */
@MyBatisDao
public interface McMapinfoDao extends QTreeDao<McMapinfo> {
	
}