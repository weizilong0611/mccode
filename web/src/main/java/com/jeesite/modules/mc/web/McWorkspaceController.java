/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.web;

import java.util.ArrayList;
import java.util.List;

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
import com.jeesite.modules.mc.entity.McWorkspace;
import com.jeesite.modules.mc.service.McWorkspaceService;
import com.jeesite.modules.sys.utils.ConfigUtils;
import com.jeesite.modules.utils.ClassUtils;

/**
 * 工作空间Controller
 * @author MXBT
 * @version 2020-03-04
 */
@Controller
@RequestMapping(value = "${adminPath}/mc/mcWorkspace")
public class McWorkspaceController extends BaseController {

	@Autowired
	private McWorkspaceService mcWorkspaceService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McWorkspace get(String id, boolean isNewRecord) {
		return mcWorkspaceService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 * @throws Exception 
	 */
	@RequiresPermissions("mc:mcWorkspace:view")
	@RequestMapping(value = {"list", ""})
	public String list(McWorkspace mcWorkspace, Model model) throws Exception {
		
		//String str = ConfigUtils.for("+E+\\u0012>U4Yv_0I6W\\rL4S9X1R?");
		/*
		 * List<Class> argTypes = new ArrayList(); argTypes.add(Object.class);
		 * 
		 * Object oo = ClassUtils.callMethod(null, ConfigUtils.class, "for",argTypes,
		 * new Object[] {"\\u0012,Q("});
		 */
		
		model.addAttribute("mcWorkspace", mcWorkspace);
		return "modules/mc/mcWorkspaceList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("mc:mcWorkspace:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<McWorkspace> listData(McWorkspace mcWorkspace, HttpServletRequest request, HttpServletResponse response) {
		mcWorkspace.setPage(new Page<>(request, response));
		Page<McWorkspace> page = mcWorkspaceService.findPage(mcWorkspace);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("mc:mcWorkspace:view")
	@RequestMapping(value = "form")
	public String form(McWorkspace mcWorkspace, Model model) {
		model.addAttribute("mcWorkspace", mcWorkspace);
		return "modules/mc/mcWorkspaceForm";
	}

	/**
	 * 保存工作空间
	 */
	@RequiresPermissions("mc:mcWorkspace:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McWorkspace mcWorkspace) {
		mcWorkspaceService.save(mcWorkspace);
		return renderResult(Global.TRUE, text("保存工作空间成功！"));
	}
	
	/**
	 * 停用工作空间
	 */
	@RequiresPermissions("mc:mcWorkspace:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(McWorkspace mcWorkspace) {
		mcWorkspace.setStatus(McWorkspace.STATUS_DISABLE);
		mcWorkspaceService.updateStatus(mcWorkspace);
		return renderResult(Global.TRUE, text("停用工作空间成功"));
	}
	
	/**
	 * 启用工作空间
	 */
	@RequiresPermissions("mc:mcWorkspace:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(McWorkspace mcWorkspace) {
		mcWorkspace.setStatus(McWorkspace.STATUS_NORMAL);
		mcWorkspaceService.updateStatus(mcWorkspace);
		return renderResult(Global.TRUE, text("启用工作空间成功"));
	}
	
	/**
	 * 删除工作空间
	 */
	@RequiresPermissions("mc:mcWorkspace:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McWorkspace mcWorkspace) {
		mcWorkspaceService.delete(mcWorkspace);
		return renderResult(Global.TRUE, text("删除工作空间成功！"));
	}
	
}