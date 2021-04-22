package com.jeesite.common.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter; 
import com.jeesite.modules.sys.dao.UserRoleDao;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;

public class QBaseController extends com.jeesite.common.web.BaseController{
	 
	
	protected String getRoleCode(){
		
		List<Role> rl = UserUtils.getUser().getRoleList();
		
		if(rl.size()>0)
			return rl.get(0	).getRoleCode();
		 
		
		return null;
		
	}
	
 
}
