/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.face.web;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.shiro.authc.FormToken;
import com.jeesite.common.shiro.realm.LoginInfo;
import com.jeesite.common.web.BaseController;
import com.jeesite.common.web.http.UserAgentUtils;
import com.jeesite.modules.face.entity.Image;
import com.jeesite.modules.face.shiro.token.FaceToken;
import com.jeesite.modules.face.util.FaceAPI;
import com.jeesite.modules.mc.entity.McAnim;
import com.jeesite.modules.mc.service.McAnimService;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.PwdUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.sys.web.LoginController;
import com.jeesite.modules.sys.web.user.UserController;


@Controller
@RequestMapping(value = "mc/face")
public class McFaceController extends BaseController {

	@Autowired
	UserController userController;

	@Autowired
	UserService userService;
	
	@RequestMapping(value = {""})
	public String index(McAnim mcAnim, Model model) {
		return "modules/mc/mcFace";
	}
	 
	@ResponseBody
	@RequestMapping(value = "faceLogin")
    public  JSONObject faceLogin(@RequestBody Map map,HttpServletRequest request) throws Exception{
		JSONObject ret = new JSONObject(); 
		Subject currentUser = SecurityUtils.getSubject();
	 
		Object l = com.jeesite.modules.sys.utils.UserUtils.getAuthInfo();
		 
		try {   
			ret = JSONObject.parseObject( FaceAPI.getUserLoginCode(map.get("img").toString()) );
	
			Map mapA = new HashMap();
			//FormToken token = new FormToken(ret.getString("user_id"), ret.getJSONObject("user_info").getString("pwd"), false,request.getRemoteHost(),"",mapA);
			FaceToken token = new FaceToken();
			token.setUsername(ret.getString("user_id"));
			UserUtils.getSubject().login(token);
			ret.put("succ", true);
		}catch(Exception err) {
			err.printStackTrace();
			ret.put("succ", false);
		}
	 
		return ret;
	}
	

	@RequestMapping(value = "infoSaveBase")
	@ResponseBody
	protected String LoginServlet(HttpServletRequest request,User user,String checkMyPassword) {
		
 		 User tmpUser = UserUtils.getUser();
		try {
			 String secretKey = Global.getProperty("shiro.loginSubmit.secretKey"); 
			 
			 if(PwdUtils.validatePassword(checkMyPassword, tmpUser.getPassword())) {			 
				if(!StringUtils.isEmpty(user.getAvatarBase64())) {
					FaceAPI.save(user.getAvatarBase64(), UserUtils.getUser().getLoginCode(),checkMyPassword);
					System.out.println(user.getAvatarBase64());
				}
				userController.infoSaveBase(user,request); 
			 }else {
				 return renderResult("false", "密码错误！"); 
			 }
			 
		}catch(Exception err) {
			return renderResult("false", "更新失败！");
		}
		return renderResult("true", "更新成功！");
	}
  
	
}