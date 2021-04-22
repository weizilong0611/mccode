package com.jeesite.common.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.dao.QTreeDao;
import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.service.api.TreeQueryServiceApi;
 
//public abstract class QTreeService<D extends QTreeDao<T>, T extends TreeEntity<T>> extends TreeQueryService<D, T>  implements TreeQueryServiceApi<T> {
public abstract class QTreeService<D extends QTreeDao<T>, T extends TreeEntity<T>> extends TreeService<D,T > {
							 //QCrudService<D extends QCrudDao<T>, T extends DataEntity<?>>  extends CrudService<D, T>{	
	public T findOneBySql(String sql){
		return this.dao.findOneBySql(sql); 
	}
	
	public List<T> findBySql(String sql){
		return this.dao.findBySql(sql); 
	}
} 