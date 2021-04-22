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
import com.jeesite.modules.mc.entity.McWorkImg;
import com.jeesite.modules.mc.service.McWorkImgService;

/**
 * 采样工作照Controller
 * @author MXBT
 * @version 2020-08-28
 */
@Controller
@RequestMapping(value = "${adminPath}/mc/mcWorkImg")
public class McWorkImgController extends BaseController {

	@Autowired
	private McWorkImgService mcWorkImgService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McWorkImg get(String aid, boolean isNewRecord) {
		return mcWorkImgService.get(aid, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("mc:mcWorkImg:view")
	@RequestMapping(value = {"list", ""})
	public String list(McWorkImg mcWorkImg, Model model) {
		model.addAttribute("mcWorkImg", mcWorkImg);
		return "modules/mc/mcWorkImgList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("mc:mcWorkImg:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<McWorkImg> listData(McWorkImg mcWorkImg, HttpServletRequest request, HttpServletResponse response) {
		Page<McWorkImg> page = mcWorkImgService.findPage(new Page<McWorkImg>(request, response), mcWorkImg); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("mc:mcWorkImg:view")
	@RequestMapping(value = "form")
	public String form(McWorkImg mcWorkImg, Model model) {
		model.addAttribute("mcWorkImg", mcWorkImg);
		return "modules/mc/mcWorkImgForm";
	}

	/**
	 * 保存采样工作照
	 */
	@RequiresPermissions("mc:mcWorkImg:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McWorkImg mcWorkImg) {
		mcWorkImgService.save(mcWorkImg);
		return renderResult(Global.TRUE, text("保存采样工作照成功！"));
	}
	
	/**
	 * 删除采样工作照
	 */
	@RequiresPermissions("mc:mcWorkImg:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McWorkImg mcWorkImg) {
		mcWorkImgService.delete(mcWorkImg);
		return renderResult(Global.TRUE, text("删除采样工作照成功！"));
	}
	
}