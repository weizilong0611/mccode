package com.jeesite.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.jeesite.common.mybatis.mapper.provider.QSelectSqlProvider;

public abstract  interface QTreeDao<T> extends TreeDao<T>{
	
	  @SelectProvider(type=QSelectSqlProvider.class, method="findOneBySql")
	  public abstract T findOneBySql(String sql);
	  @SelectProvider(type=QSelectSqlProvider.class, method="findBySql")
	  public abstract List<T> findBySql(String sql);
 
}


//public abstract interface QCrudDao<T> extends CrudDao<T> {