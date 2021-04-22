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
import com.jeesite.modules.mc.entity.McMedicine;
import com.jeesite.modules.mc.service.McMedicineService;

/**
 * 藏药产业导航Controller
 * @author MXBT
 * @version 2020-08-12
 */
@Controller
@RequestMapping(value = "${adminPath}/mc/mcMedicine")
public class McMedicineController extends BaseController {

	@Autowired
	private McMedicineService mcMedicineService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McMedicine get(String aid, boolean isNewRecord) {
		return mcMedicineService.get(aid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("mc:mcMedicine:view")
	@RequestMapping(value = {"list", ""})
	public String list(McMedicine mcMedicine, Model model) {
		model.addAttribute("mcMedicine", mcMedicine);
		return "modules/mc/mcMedicineList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("mc:mcMedicine:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<McMedicine> listData(McMedicine mcMedicine, HttpServletRequest request, HttpServletResponse response) {
		Page<McMedicine> page = mcMedicineService.findPage(new Page<McMedicine>(request, response), mcMedicine); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("mc:mcMedicine:view")
	@RequestMapping(value = "form")
	public String form(McMedicine mcMedicine, Model model) {
		model.addAttribute("mcMedicine", mcMedicine);
		return "modules/mc/mcMedicineForm";
	}

	/**
	 * 保存藏药产业导航
	 */
	@RequiresPermissions("mc:mcMedicine:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McMedicine mcMedicine) {
		mcMedicineService.save(mcMedicine);
		return renderResult(Global.TRUE, text("保存藏药产业导航成功！"));
	}
	
	/**
	 * 删除藏药产业导航
	 */
	@RequiresPermissions("mc:mcMedicine:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McMedicine mcMedicine) {
		mcMedicineService.delete(mcMedicine);
		return renderResult(Global.TRUE, text("删除藏药产业导航成功！"));
	}
	
}