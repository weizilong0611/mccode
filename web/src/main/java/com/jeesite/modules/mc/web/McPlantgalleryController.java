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
import com.jeesite.modules.mc.entity.McPlantgallery;
import com.jeesite.modules.mc.service.McPlantgalleryService;

/**
 * 植物图册Controller
 * @author 阿
 * @version 2020-09-16
 */
@Controller
@RequestMapping(value = "${adminPath}/mc/mcPlantgallery")
public class McPlantgalleryController extends BaseController {

	@Autowired
	private McPlantgalleryService mcPlantgalleryService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McPlantgallery get(String id, boolean isNewRecord) {
		return mcPlantgalleryService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("mc:mcPlantgallery:view")
	@RequestMapping(value = {"list", ""})
	public String list(McPlantgallery mcPlantgallery, Model model) {
		model.addAttribute("mcPlantgallery", mcPlantgallery);
		return "modules/mc/mcPlantgalleryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("mc:mcPlantgallery:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<McPlantgallery> listData(McPlantgallery mcPlantgallery, HttpServletRequest request, HttpServletResponse response) {
		Page<McPlantgallery> page = mcPlantgalleryService.findPage(new Page<McPlantgallery>(request, response), mcPlantgallery); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("mc:mcPlantgallery:view")
	@RequestMapping(value = "form")
	public String form(McPlantgallery mcPlantgallery, Model model) {
		model.addAttribute("mcPlantgallery", mcPlantgallery);
		return "modules/mc/mcPlantgalleryForm";
	}

	/**
	 * 保存植物图册
	 */
	@RequiresPermissions("mc:mcPlantgallery:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McPlantgallery mcPlantgallery) {
		mcPlantgalleryService.save(mcPlantgallery);
		return renderResult(Global.TRUE, text("保存植物图册成功！"));
	}
	
	/**
	 * 删除植物图册
	 */
	@RequiresPermissions("mc:mcPlantgallery:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McPlantgallery mcPlantgallery) {
		mcPlantgalleryService.delete(mcPlantgallery);
		return renderResult(Global.TRUE, text("删除植物图册成功！"));
	}
	
}