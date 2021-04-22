/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.news.dao;

import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.news.entity.McOpinion;

/**
 * 意见举报DAO接口
 * @author zq
 * @version 2020-11-02
 */
@MyBatisDao
public interface McOpinionDao extends QCrudDao<McOpinion> {
	
}