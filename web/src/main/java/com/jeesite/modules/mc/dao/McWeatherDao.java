/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.dao;

import java.util.List;

import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.mc.entity.McWeather;

import org.apache.ibatis.annotations.Param;


/**
 * 天气数据DAO接口
 * @author MXBT
 * @version 2020-07-28
 */
@MyBatisDao
public interface McWeatherDao extends QCrudDao<McWeather> {
	List<McWeather> getWeatherHistory(@Param("cityX") String cityX,@Param("cityY") String cityY,@Param("startTime") String startTime,@Param("endTime") String endTime);
}