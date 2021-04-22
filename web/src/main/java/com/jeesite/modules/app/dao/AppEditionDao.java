/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.app.dao;

import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.app.entity.AppEdition;

/**
 * app_editionDAO接口
 * @author ZQ
 * @version 2020-09-09
 */
@MyBatisDao
public interface AppEditionDao extends QCrudDao<AppEdition> {
	
}