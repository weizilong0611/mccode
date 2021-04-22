package com.jeesite.modules.kjfw.dao;

import java.util.List;

import com.jeesite.common.dao.QCrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.kjfw.entity.KjfwExpertDatabaseTab;
import com.jeesite.modules.mc.entity.McAplantatlas;
/**
 * 
 * @author Tony
 *  2021-04-09
 */
@MyBatisDao
public interface KjfwExpertDatabaseDao extends QCrudDao<KjfwExpertDatabaseTab>{
	public List<McAplantatlas> findList(McAplantatlas kjfwExpertDatabaseTab);
}
