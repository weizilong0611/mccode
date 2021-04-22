/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.dao;

import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.mc.entity.McAppReport;

/**
 * app报告下载DAO接口
 * @author fidelzhang
 * @version 2020-08-21
 */
@MyBatisDao
public interface McAppReportDao extends QCrudDao<McAppReport> {
	
}