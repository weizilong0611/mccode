/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.dao;

import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.mc.entity.McPlantgallery;

/**
 * 植物图册DAO接口
 * @author 阿
 * @version 2020-09-16
 */
@MyBatisDao
public interface McPlantgalleryDao extends QCrudDao<McPlantgallery> {
	
}