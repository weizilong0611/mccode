/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.news.dao;

import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.news.entity.McNews;

/**
 * 新闻DAO接口
 * @author zq
 * @version 2020-10-20
 */
@MyBatisDao
public interface McNewsDao extends QCrudDao<McNews> {
	
}