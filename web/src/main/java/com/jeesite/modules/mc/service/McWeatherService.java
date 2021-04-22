/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.service;

import java.util.List;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.mc.dao.McWeatherDao;
import com.jeesite.modules.mc.entity.McWeather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 天气数据Service
 * @author MXBT
 * @version 2020-07-28
 */
@Service
@Transactional(readOnly=true)
public class McWeatherService extends QCrudService<McWeatherDao, McWeather> {
	
	@Autowired
	private McWeatherDao mcWeatherDao;

	/**
	 * 获取单条数据
	 * @param mcWeather
	 * @return
	 */
	@Override
	public McWeather get(McWeather mcWeather) {
		return super.get(mcWeather);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param mcWeather
	 * @return
	 */
	@Override
	public Page<McWeather> findPage(Page<McWeather> page, McWeather mcWeather) {
		return super.findPage(page, mcWeather);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param mcWeather
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(McWeather mcWeather) {
		super.save(mcWeather);
	}
	
	/**
	 * 更新状态
	 * @param mcWeather
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(McWeather mcWeather) {
		super.updateStatus(mcWeather);
	}
	
	/**
	 * 删除数据
	 * @param mcWeather
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(McWeather mcWeather) {
		super.delete(mcWeather);
	}
	
	public List<McWeather> getWeatherHistory(String cityX,String cityY,String startTime,String endTime){
		return  mcWeatherDao.getWeatherHistory(cityX, cityY, startTime, endTime);
	}
	
}