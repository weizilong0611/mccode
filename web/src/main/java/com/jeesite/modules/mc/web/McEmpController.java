/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.web;

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
import com.jeesite.modules.mc.entity.McEmp;
import com.jeesite.modules.mc.service.McEmpService;

/**
 * 水电站流量Controller
 * @author mxbt
 * @version 2020-07-07
 */
@Controller
@RequestMapping(value = "${adminPath}/mc/mcEmp")
public class McEmpController extends BaseController {

	@Autowired
	private McEmpService mcEmpService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McEmp get(String id, boolean isNewRecord) {
		return mcEmpService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("mc:mcEmp:view")
	@RequestMapping(value = {"list", ""})
	public String list(McEmp mcEmp, Model model) {
		model.addAttribute("mcEmp", mcEmp);
		return "modules/mc/mcEmpList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("mc:mcEmp:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<McEmp> listData(McEmp mcEmp, HttpServletRequest request, HttpServletResponse response) {
		Page<McEmp> page = mcEmpService.findPage(new Page<McEmp>(request, response), mcEmp); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("mc:mcEmp:view")
	@RequestMapping(value = "form")
	public String form(McEmp mcEmp, Model model) {
		model.addAttribute("mcEmp", mcEmp);
		return "modules/mc/mcEmpForm";
	}

	/**
	 * 保存水电站流量
	 */
	@RequiresPermissions("mc:mcEmp:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McEmp mcEmp) {
		mcEmpService.save(mcEmp);
		return renderResult(Global.TRUE, text("保存水电站流量成功！"));
	}
	
	/**
	 * 删除水电站流量
	 */
	@RequiresPermissions("mc:mcEmp:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McEmp mcEmp) {
		mcEmpService.delete(mcEmp);
		return renderResult(Global.TRUE, text("删除水电站流量成功！"));
	}
	
}