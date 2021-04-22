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
import com.jeesite.modules.mc.entity.McXftjfa;
import com.jeesite.modules.mc.service.McXftjfaService;

/**
 * 生态修复推荐Controller
 * @author MXBT
 * @version 2020-09-17
 */
@Controller
@RequestMapping(value = "${adminPath}/mc/mcXftjfa")
public class McXftjfaController extends BaseController {

	@Autowired
	private McXftjfaService mcXftjfaService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McXftjfa get(String id, boolean isNewRecord) {
		return mcXftjfaService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("mc:mcXftjfa:view")
	@RequestMapping(value = {"list", ""})
	public String list(McXftjfa mcXftjfa, Model model) {
		model.addAttribute("mcXftjfa", mcXftjfa);
		
		/*
		 * String[] area = {"玛曲县","碌曲县","夏河县","合作市","卓尼县","临潭县","迭部县"};
		 * 
		 * String[] cate = {"草地退化","草地沙化","草地退化","鼠害治理","毒杂草治理","矿山修复","林地生态","湿地生态"};
		 * 
		 * for (String ctateName : cate) { for (String areaname : area) {
		 * 
		 * McXftjfa mc = new McXftjfa(); mc.setCate(ctateName);
		 * mc.setTitle(""+areaname); mc.setTxt(ctateName); mcXftjfaService.save(mc); } }
		 */
		
		return "modules/mc/mcXftjfaList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("mc:mcXftjfa:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<McXftjfa> listData(McXftjfa mcXftjfa, HttpServletRequest request, HttpServletResponse response) {
		Page<McXftjfa> page = mcXftjfaService.findPage(new Page<McXftjfa>(request, response), mcXftjfa); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("mc:mcXftjfa:view")
	@RequestMapping(value = "form")
	public String form(McXftjfa mcXftjfa, Model model) {
		model.addAttribute("mcXftjfa", mcXftjfa);
		return "modules/mc/mcXftjfaForm";
	}

	/**
	 * 保存生态修复推荐
	 */
	@RequiresPermissions("mc:mcXftjfa:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McXftjfa mcXftjfa) {
		mcXftjfaService.save(mcXftjfa);
		return renderResult(Global.TRUE, text("保存生态修复推荐成功！"));
	}
	
	/**
	 * 删除生态修复推荐
	 */
	@RequiresPermissions("mc:mcXftjfa:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McXftjfa mcXftjfa) {
		mcXftjfaService.delete(mcXftjfa);
		return renderResult(Global.TRUE, text("删除生态修复推荐成功！"));
	}
	
}