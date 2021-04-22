package com.jeesite.modules.kjfw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.QCrudService;
import com.jeesite.modules.kjfw.dao.KjfwExpertDatabaseDao;
import com.jeesite.modules.kjfw.entity.KjfwExpertDatabaseTab;
import com.jeesite.modules.mc.entity.McAplantatlas;

/**
* @Author Tony
*  2021年4月9日 上午10:25:28
*  Never give up
*  生态专家库服务类
*/
@Transactional(readOnly=true)
@Service
public class KjfwExpertDatabaseService extends QCrudService<KjfwExpertDatabaseDao,KjfwExpertDatabaseTab>{
    
	/*
     * 查询分页数据 
     */
	public Page<KjfwExpertDatabaseTab> findPage(Page<KjfwExpertDatabaseTab> page, KjfwExpertDatabaseTab kjfwExpertDatabaseTab) {
		return super.findPage(page, kjfwExpertDatabaseTab);
	}
	
}
