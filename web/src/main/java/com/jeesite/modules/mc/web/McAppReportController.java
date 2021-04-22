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
import com.jeesite.modules.mc.entity.McAppReport;
import com.jeesite.modules.mc.service.McAppReportService;

/**
 * app报告下载Controller
 * @author fidelzhang
 * @version 2020-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/mc/mcAppReport")
public class McAppReportController extends BaseController {

	@Autowired
	private McAppReportService mcAppReportService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McAppReport get(String id, boolean isNewRecord) {
		return mcAppReportService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("mc:mcAppReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(McAppReport mcAppReport, Model model) {
		model.addAttribute("mcAppReport", mcAppReport);
		return "modules/mc/mcAppReportList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("mc:mcAppReport:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<McAppReport> listData(McAppReport mcAppReport, HttpServletRequest request, HttpServletResponse response) {
		Page<McAppReport> page = mcAppReportService.findPage(new Page<McAppReport>(request, response), mcAppReport); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("mc:mcAppReport:view")
	@RequestMapping(value = "form")
	public String form(McAppReport mcAppReport, Model model) {
		model.addAttribute("mcAppReport", mcAppReport);
		return "modules/mc/mcAppReportForm";
	}

	/**
	 * 保存app报告下载
	 */
	@RequiresPermissions("mc:mcAppReport:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McAppReport mcAppReport) {
		mcAppReportService.save(mcAppReport);
		return renderResult(Global.TRUE, text("保存app报告成功！"));
	}
	
	/**
	 * 停用app报告下载
	 */
	@RequiresPermissions("mc:mcAppReport:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(McAppReport mcAppReport) {
		mcAppReport.setStatus(McAppReport.STATUS_DISABLE);
		mcAppReportService.updateStatus(mcAppReport);
		return renderResult(Global.TRUE, text("停用app报告成功"));
	}
	
	/**
	 * 启用app报告下载
	 */
	@RequiresPermissions("mc:mcAppReport:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(McAppReport mcAppReport) {
		mcAppReport.setStatus(McAppReport.STATUS_NORMAL);
		mcAppReportService.updateStatus(mcAppReport);
		return renderResult(Global.TRUE, text("启用app报告成功"));
	}
	
	/**
	 * 删除app报告下载
	 */
	@RequiresPermissions("mc:mcAppReport:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McAppReport mcAppReport) {
		mcAppReportService.delete(mcAppReport);
		return renderResult(Global.TRUE, text("删除app报告成功！"));
	}
	
}