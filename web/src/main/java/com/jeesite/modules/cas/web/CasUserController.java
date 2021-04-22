/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.cas.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.cas.entity.CasUser;
import com.jeesite.modules.cas.service.CasUserService;
import com.jeesite.modules.sys.entity.User;

/**
 * js_sys_userController
 * @author 梦溪笔檀
 * @version 2019-03-04
 */
@Controller
@RequestMapping(value = "${adminPath}/cas/casUser")
public class CasUserController extends BaseController {

	@Autowired
	private CasUserService casUserService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public CasUser get(String userCode, boolean isNewRecord) {
		return casUserService.get(userCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("cas:casUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(CasUser casUser, Model model) {
		model.addAttribute("casUser", casUser);
		return "modules/cas/casUserList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("cas:casUser:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<CasUser> listData(CasUser casUser, HttpServletRequest request, HttpServletResponse response) {
		Page<CasUser> page = casUserService.findPage(new Page<CasUser>(request, response), casUser); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("cas:casUser:view")
	@RequestMapping(value = "form")
	public String form(CasUser casUser, Model model) {
		model.addAttribute("casUser", casUser);
		return "modules/cas/casUserForm";
	}

	/**
	 * 保存用户管理
	 */
	@RequiresPermissions("cas:casUser:edit")
	@PostMapping(value = "saveUser")
	@ResponseBody
	public String save(@Validated User casUser,String roleName) {
		
		casUserService.saveUserWithRole(casUser,"proJL");
		return renderResult(Global.TRUE, text("保存用户管理成功！"));
	}
	
	/**
	 * 停用用户管理
	 */
	@RequiresPermissions("cas:casUser:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(CasUser casUser) {
		casUser.setStatus(CasUser.STATUS_DISABLE);
		casUserService.updateStatus(casUser);
		return renderResult(Global.TRUE, text("停用用户管理成功"));
	}
	
	/**
	 * 启用用户管理
	 */
	@RequiresPermissions("cas:casUser:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(CasUser casUser) {
		casUser.setStatus(CasUser.STATUS_NORMAL);
		casUserService.updateStatus(casUser);
		return renderResult(Global.TRUE, text("启用用户管理成功"));
	}
	
	/**
	 * 删除用户管理
	 */
	@RequiresPermissions("cas:casUser:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(CasUser casUser) {
		casUserService.delete(casUser);
		return renderResult(Global.TRUE, text("删除用户管理成功！"));
	}
	
}