/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.app.web;

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
import com.jeesite.modules.app.entity.AppEdition;
import com.jeesite.modules.app.service.AppEditionService;

/**
 * app_editionController
 * 
 * @author ZQ
 * @version 2020-09-09
 */
@Controller
@RequestMapping(value = "${adminPath}/app/appEdition")
public class AppEditionController extends BaseController {

	@Autowired
	private AppEditionService appEditionService;

	//  查询最新版本
	@RequestMapping(value = "getNewEdition")
	@ResponseBody
	public String getNewEdition(AppEdition appEdition) {
		appEdition.setAppType("10");
		List<AppEdition> list = appEditionService.findList(appEdition);
		return renderResult(Global.TRUE, text("成功"), list.get(0));
	}

	// 更新下载次数
	@RequestMapping(value = "setTotalDownloads")
	@ResponseBody
	public String setTotalDownloads(AppEdition appEdition) {
		List<AppEdition> list = appEditionService.findList(appEdition);
		appEdition = list.get(0);
		appEdition.setTotalDownloads(appEdition.getTotalDownloads() + 1);
		appEditionService.save(appEdition);
		return renderResult(Global.TRUE, text("成功"));
	}

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public AppEdition get(String id, boolean isNewRecord) {
		return appEditionService.get(id, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("app:appEdition:view")
	@RequestMapping(value = { "list", "" })
	public String list(AppEdition appEdition, Model model) {
		model.addAttribute("appEdition", appEdition);
		return "modules/app/appEditionList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("app:appEdition:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<AppEdition> listData(AppEdition appEdition, HttpServletRequest request, HttpServletResponse response) {
		Page<AppEdition> page = appEditionService.findPage(new Page<AppEdition>(request, response), appEdition);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("app:appEdition:view")
	@RequestMapping(value = "form")
	public String form(AppEdition appEdition, Model model) {
		model.addAttribute("appEdition", appEdition);
		return "modules/app/appEditionForm";
	}

	/**
	 * 保存app_edition
	 */
	@RequiresPermissions("app:appEdition:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated AppEdition appEdition) {
		appEditionService.save(appEdition);
		return renderResult(Global.TRUE, text("保存app_edition成功！"));
	}

	/**
	 * 删除app_edition
	 */
	@RequiresPermissions("app:appEdition:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(AppEdition appEdition) {
		appEditionService.delete(appEdition);
		return renderResult(Global.TRUE, text("删除app_edition成功！"));
	}

	/**
	 * 停用app版本
	 */
	@RequiresPermissions("app:appEdition:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(AppEdition appEdition) {
		appEdition.setStatus(AppEdition.STATUS_DISABLE);
		appEditionService.updateStatus(appEdition);
		return renderResult(Global.TRUE, text("停用app版本成功"));
	}

	/**
	 * 启用app版本
	 */
	@RequiresPermissions("app:appEdition:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(AppEdition appEdition) {
		appEdition.setStatus(AppEdition.STATUS_NORMAL);
		appEditionService.updateStatus(appEdition);
		return renderResult(Global.TRUE, text("启用app版本成功"));
	}
}