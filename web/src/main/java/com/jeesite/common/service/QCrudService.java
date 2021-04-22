package com.jeesite.common.service;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.mapper.query.QueryColumn;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly=true)
public abstract class QCrudService<D extends QCrudDao<T>, T extends DataEntity<?>>
  extends CrudService<D, T>{
	
	public List<T> findAll(T entity){ 
		return this.dao.findAll(entity); 
	}
	
	public List<T> findByAreaCode(String areacode){
		T entity = null;
		try {
			entity = this.entityClass.newInstance();
			if(entity instanceof DataEntity && entity.getClass().getDeclaredField("areacode")!= null) {
				DataEntity e = (DataEntity)entity;
				e.getSqlMap().add("areacode", areacode);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("can not find key 'areacode' ");
		}
		return this.dao.findAll(entity); 
	}
	
	public T findOneBySql(String sql){
		return this.dao.findOneBySql(sql);
	}
	
	public List<Map<String,Object>> findMap(T entity,String []field) {
		try {

			//Map<String, Object> map = new HashMap<String,Object>();
	        Class<?> clazz = entity.getClass();
	        Map<String,Object> map = new HashMap<String, Object>();
	        for (Field fd : clazz.getDeclaredFields()) {
	        	fd.setAccessible(true);
	            String fieldName = fd.getName();
	            Object value = fd.get(entity); 
	            map.put(fieldName, value);
	        }
	        for (Field fd : clazz.getSuperclass().getSuperclass().getDeclaredFields()) {
	        	fd.setAccessible(true);
	            String fieldName = fd.getName();
	            Object value = fd.get(entity); 
	            map.put(fieldName, value);
	        }
	        
	        map.put("entity", entity);
	        map.put("field",field);
				 
	        return this.dao.findMap(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String,Object>> findMapBySql(String sql){
		return this.dao.findMapBySql(sql);
	}
	
	@Transactional(readOnly=false)
	public void insertBatch(List<T> list) {
		List<T> tmp = new ArrayList();
		for(int i=0;i<list.size();i++) {
			tmp.add(list.get(i));
			if(tmp.size() > 200) {
				dao.insertBatch(tmp);
				tmp.clear();
			}
		}
		if(tmp.size() > 0) {
			dao.insertBatch(tmp);
		}	
		
	}

}
