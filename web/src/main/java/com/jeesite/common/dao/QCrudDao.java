package com.jeesite.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.jeesite.common.mybatis.mapper.provider.QSelectSqlProvider;
import com.jeesite.common.mybatis.mapper.provider.SelectSqlProvider;

public abstract interface QCrudDao<T>
extends CrudDao<T> {
	
	  @SelectProvider(type=QSelectSqlProvider.class, method="findAll")
	  public abstract List<T> findAll(T entity);
	  
	  @SelectProvider(type=QSelectSqlProvider.class, method="findOneBySql")
	  public abstract T findOneBySql(String sql);

	  @ResultType(java.util.HashMap.class)
	  @SelectProvider(type=QSelectSqlProvider.class, method="findMap")
	  public abstract List<Map<String, Object>> findMap(Map<String, Object> entity);

	  @ResultType(java.util.HashMap.class)
	  @SelectProvider(type=QSelectSqlProvider.class, method="findMapBySql")
	public abstract List<Map<String, Object>> findMapBySql(String sql);
	   
}
