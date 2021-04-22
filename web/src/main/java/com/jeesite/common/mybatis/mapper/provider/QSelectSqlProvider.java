package com.jeesite.common.mybatis.mapper.provider;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mybatis.mapper.MapperException;
import com.jeesite.common.mybatis.mapper.SqlMap;
import com.jeesite.common.mybatis.mapper.query.QueryOrder;
import com.jeesite.common.mybatis.mapper.query.QueryTable;
import com.jeesite.common.mybatis.mapper.query.QueryWhere;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Who;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jeesite.common.entity.BaseEntity;

public class QSelectSqlProvider extends com.jeesite.common.mybatis.mapper.provider.SelectSqlProvider{

	
	public String findAll(BaseEntity<?> entity) { 
		
		return "SELECT * FROM " + entity.getSqlMap().getTable().toSql();
		
	}
	
	public String findOneBySql(String sql) {  
		return sql; 
	}
	public String findBySql(String sql) {  
		return sql; 
	}
	public String findMap(Map<String,Object> entity) {
		
		BaseEntity entityT = (BaseEntity) entity.get("entity");
		String []field = (String[]) entity.get("field");
		 
        StringBuilder sb = new StringBuilder();  
        
        sb.append("SELECT ");
        if(field != null && field.length >0) 
        	sb .append(String.join(",", field));
        else
        	sb.append(entityT.getSqlMap().getColumn().toSql());
        sb.append(" FROM ");
        sb.append(entityT.getSqlMap().getTable().toSql());
        
        String where = entityT.getSqlMap().getWhere().toSql();
        String order = entityT.getSqlMap().getOrder().toSql();
        if (StringUtils.isNotBlank(where)) {
            sb.append(" WHERE ");
            sb.append(where);
        }
        if (StringUtils.isNotBlank( order)){
            sb.append(" ORDER BY ");
            sb.append(order);
        }
       String ret = sb.toString();
       
       return ret;
	}
	
	public String findMapBySql(String sql) {
		return sql;
	}
	
	
}
