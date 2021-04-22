/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.mc.entity.McTextfiledata;
import com.jeesite.modules.mc.service.McTextfiledataService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据浏览Controller
 * @author Mxbt
 * @version 2020-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/mc/mcTextfiledata")
public class McTextfiledataController extends BaseController {

	@Autowired
	private McTextfiledataService mcTextfiledataService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McTextfiledata get(String id, boolean isNewRecord) {
		return mcTextfiledataService.get(id, isNewRecord);
	}
	 
	 

	
	@RequiresUser
	@RequestMapping(value = {"queryData"})
	@ResponseBody
	public List<Map<String, Object>> queryData(String areacode,String rowtitle,String rowtime1,String rowtime2){
	
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		McTextfiledata entity = new McTextfiledata();
		entity.setAreacode(areacode);
		entity.setRowtitle(rowtitle);
		list = mcTextfiledataService.findMap(entity,new String[] {"rowtitle","value","areacode","date_format(rowtime ,'%Y-%m-%d %H:%i:%S') as rowtime"});
 
 
		return list;
	}
	 
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("mc:mcTextfiledata:view")
	@RequestMapping(value = {"list", ""})
	public String list(McTextfiledata mcTextfiledata, Model model) {
		model.addAttribute("mcTextfiledata", mcTextfiledata);
		return "modules/mc/mcTextfiledataList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("mc:mcTextfiledata:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<McTextfiledata> listData(McTextfiledata mcTextfiledata, HttpServletRequest request, HttpServletResponse response) {
		mcTextfiledata.setPage(new Page<>(request, response));
		Page<McTextfiledata> page = mcTextfiledataService.findPage(mcTextfiledata);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("mc:mcTextfiledata:view")
	@RequestMapping(value = "form")
	public String form(McTextfiledata mcTextfiledata, Model model) {
		model.addAttribute("mcTextfiledata", mcTextfiledata);
		return "modules/mc/mcTextfiledataForm";
	}

	/**
	 * 保存数据浏览
	 */
	@RequiresPermissions("mc:mcTextfiledata:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McTextfiledata mcTextfiledata) {
		mcTextfiledataService.save(mcTextfiledata);
		return renderResult(Global.TRUE, text("保存数据浏览成功！"));
	}
	
	/**
	 * 删除数据浏览
	 */
	@RequiresPermissions("mc:mcTextfiledata:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McTextfiledata mcTextfiledata) {
		mcTextfiledataService.delete(mcTextfiledata);
		return renderResult(Global.TRUE, text("删除数据浏览成功！"));
	}
	
}