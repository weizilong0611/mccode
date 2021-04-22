/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.news.web;

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
import com.jeesite.modules.news.entity.McOpinion;
import com.jeesite.modules.news.service.McOpinionService;

/**
 * 意见举报Controller
 * @author zq
 * @version 2020-11-02
 */
@Controller
@RequestMapping(value = "${adminPath}/news/mcOpinion")
public class McOpinionController extends BaseController {

	@Autowired
	private McOpinionService mcOpinionService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McOpinion get(String id, boolean isNewRecord) {
		return mcOpinionService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("news:mcOpinion:view")
	@RequestMapping(value = {"list", ""})
	public String list(McOpinion mcOpinion, Model model) {
		model.addAttribute("mcOpinion", mcOpinion);
		return "modules/news/mcOpinionList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("news:mcOpinion:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<McOpinion> listData(McOpinion mcOpinion, HttpServletRequest request, HttpServletResponse response) {
		Page<McOpinion> page = mcOpinionService.findPage(new Page<McOpinion>(request, response), mcOpinion); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("news:mcOpinion:view")
	@RequestMapping(value = "form")
	public String form(McOpinion mcOpinion, Model model) {
		model.addAttribute("mcOpinion", mcOpinion);
		return "modules/news/mcOpinionForm";
	}

	/**
	 * 保存意见举报
	 */
	@RequiresPermissions("news:mcOpinion:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McOpinion mcOpinion) {
		mcOpinionService.save(mcOpinion);
		return renderResult(Global.TRUE, text("保存意见举报成功！"));
	}
	
	/**
	 * 删除意见举报
	 */
	@RequiresPermissions("news:mcOpinion:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McOpinion mcOpinion) {
		mcOpinionService.delete(mcOpinion);
		return renderResult(Global.TRUE, text("删除意见举报成功！"));
	}
	
}