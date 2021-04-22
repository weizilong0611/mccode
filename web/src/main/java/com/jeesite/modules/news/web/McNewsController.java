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
import com.jeesite.modules.news.entity.McNews;
import com.jeesite.modules.news.service.McNewsService;

/**
 * 新闻Controller
 * @author zq
 * @version 2020-10-20
 */
@Controller
@RequestMapping(value = "${adminPath}/news/mcNews")
public class McNewsController extends BaseController {

	@Autowired
	private McNewsService mcNewsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McNews get(String id, boolean isNewRecord) {
		return mcNewsService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("news:mcNews:view")
	@RequestMapping(value = {"list", ""})
	public String list(McNews mcNews, Model model) {
		model.addAttribute("mcNews", mcNews);
		return "modules/news/mcNewsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("news:mcNews:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<McNews> listData(McNews mcNews, HttpServletRequest request, HttpServletResponse response) {
		Page<McNews> page = mcNewsService.findPage(new Page<McNews>(request, response), mcNews); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("news:mcNews:view")
	@RequestMapping(value = "form")
	public String form(McNews mcNews, Model model) {
		model.addAttribute("mcNews", mcNews);
		return "modules/news/mcNewsForm";
	}

	/**
	 * 保存新闻
	 */
	@RequiresPermissions("news:mcNews:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McNews mcNews) {
		mcNewsService.save(mcNews);
		return renderResult(Global.TRUE, text("保存新闻成功！"));
	}
	
	/**
	 * 停用新闻
	 */
	@RequiresPermissions("news:mcNews:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(McNews mcNews) {
		mcNews.setStatus(McNews.STATUS_DISABLE);
		mcNewsService.updateStatus(mcNews);
		return renderResult(Global.TRUE, text("停用新闻成功"));
	}
	
	/**
	 * 启用新闻
	 */
	@RequiresPermissions("news:mcNews:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(McNews mcNews) {
		mcNews.setStatus(McNews.STATUS_NORMAL);
		mcNewsService.updateStatus(mcNews);
		return renderResult(Global.TRUE, text("启用新闻成功"));
	}
	
	/**
	 * 删除新闻
	 */
	@RequiresPermissions("news:mcNews:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McNews mcNews) {
		mcNewsService.delete(mcNews);
		return renderResult(Global.TRUE, text("删除新闻成功！"));
	}
	
}