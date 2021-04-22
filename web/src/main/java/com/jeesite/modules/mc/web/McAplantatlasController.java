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
import com.jeesite.modules.mc.entity.McAplantatlas;
import com.jeesite.modules.mc.service.McAplantatlasService;

/**
 * 珍贵动植物Controller
 * @author mxbt
 * @version 2020-07-17
 */
@Controller
@RequestMapping(value = "${adminPath}/mc/mcAplantatlas")
public class McAplantatlasController extends BaseController {

	@Autowired
	private McAplantatlasService mcAplantatlasService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McAplantatlas get(String aid, boolean isNewRecord) {
		return mcAplantatlasService.get(aid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("mc:mcAplantatlas:view")
	@RequestMapping(value = {"list", ""})
	public String list(McAplantatlas mcAplantatlas, Model model) {
		model.addAttribute("mcAplantatlas", mcAplantatlas);
		return "modules/mc/mcAplantatlasList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("mc:mcAplantatlas:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<McAplantatlas> listData(McAplantatlas mcAplantatlas, HttpServletRequest request, HttpServletResponse response) {
		Page<McAplantatlas> page = mcAplantatlasService.findPage(new Page<McAplantatlas>(request, response), mcAplantatlas); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("mc:mcAplantatlas:view")
	@RequestMapping(value = "form")
	public String form(McAplantatlas mcAplantatlas, Model model) {
		model.addAttribute("mcAplantatlas", mcAplantatlas);
		return "modules/mc/mcAplantatlasForm";
	}

	/**
	 * 保存珍贵动植物
	 */
	@RequiresPermissions("mc:mcAplantatlas:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McAplantatlas mcAplantatlas) {
		mcAplantatlasService.save(mcAplantatlas);
		return renderResult(Global.TRUE, text("保存珍贵动植物成功！"));
	}
	
	/**
	 * 删除珍贵动植物
	 */
	@RequiresPermissions("mc:mcAplantatlas:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McAplantatlas mcAplantatlas) {
		mcAplantatlasService.delete(mcAplantatlas);
		return renderResult(Global.TRUE, text("删除珍贵动植物成功！"));
	}
	
}