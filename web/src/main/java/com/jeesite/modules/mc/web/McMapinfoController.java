/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.mc.web;

import java.util.List;
import java.util.Map;

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
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.mc.entity.McMapinfo;
import com.jeesite.modules.mc.service.McMapinfoService;

/**
 * 地图信息表Controller
 * @author mxbt
 * @version 2020-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/mc/mcMapinfo")
public class McMapinfoController extends BaseController {

	@Autowired
	private McMapinfoService mcMapinfoService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public McMapinfo get(String treeCode, boolean isNewRecord) {
		return mcMapinfoService.get(treeCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("mc:mcMapinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(McMapinfo mcMapinfo, Model model) {
		model.addAttribute("mcMapinfo", mcMapinfo);
		return "modules/mc/mcMapinfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("mc:mcMapinfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<McMapinfo> listData(McMapinfo mcMapinfo) {
		if (StringUtils.isBlank(mcMapinfo.getParentCode())) {
			mcMapinfo.setParentCode(McMapinfo.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(mcMapinfo.getTreeName())){
			mcMapinfo.setParentCode(null);
		}
		if (StringUtils.isNotBlank(mcMapinfo.getMenucode())){
			mcMapinfo.setParentCode(null);
		}
		if (StringUtils.isNotBlank(mcMapinfo.getUrl())){
			mcMapinfo.setParentCode(null);
		}
		if (StringUtils.isNotBlank(mcMapinfo.getStatus())){
			mcMapinfo.setParentCode(null);
		}
		if (StringUtils.isNotBlank(mcMapinfo.getRemarks())){
			mcMapinfo.setParentCode(null);
		}
		List<McMapinfo> list = mcMapinfoService.findList(mcMapinfo);
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("mc:mcMapinfo:view")
	@RequestMapping(value = "form")
	public String form(McMapinfo mcMapinfo, Model model) {
		// 创建并初始化下一个节点信息
		mcMapinfo = createNextNode(mcMapinfo);
		model.addAttribute("mcMapinfo", mcMapinfo);
		return "modules/mc/mcMapinfoForm";
	}
	
	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("mc:mcMapinfo:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public McMapinfo createNextNode(McMapinfo mcMapinfo) {
		if (StringUtils.isNotBlank(mcMapinfo.getParentCode())){
			mcMapinfo.setParent(mcMapinfoService.get(mcMapinfo.getParentCode()));
		}
		if (mcMapinfo.getIsNewRecord()) {
			McMapinfo where = new McMapinfo();
			where.setParentCode(mcMapinfo.getParentCode());
			McMapinfo last = mcMapinfoService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				mcMapinfo.setTreeSort(last.getTreeSort() + 30);
				mcMapinfo.setTreeCode(IdGen.nextCode(last.getTreeCode()));
			}else if (mcMapinfo.getParent() != null){
				mcMapinfo.setTreeCode(mcMapinfo.getParent().getTreeCode() + "001");
			}
		}
		// 以下设置表单默认数据
		if (mcMapinfo.getTreeSort() == null){
			mcMapinfo.setTreeSort(McMapinfo.DEFAULT_TREE_SORT);
		}
		return mcMapinfo;
	}

	/**
	 * 保存地图信息
	 */
	@RequiresPermissions("mc:mcMapinfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated McMapinfo mcMapinfo) {
		mcMapinfoService.save(mcMapinfo);
		return renderResult(Global.TRUE, text("保存地图信息成功！"));
	}
	
	/**
	 * 停用地图信息
	 */
	@RequiresPermissions("mc:mcMapinfo:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(McMapinfo mcMapinfo) {
		McMapinfo where = new McMapinfo();
		where.setStatus(McMapinfo.STATUS_NORMAL);
		where.setParentCodes("," + mcMapinfo.getId() + ",");
		long count = mcMapinfoService.findCount(where);
		if (count > 0) {
			return renderResult(Global.FALSE, text("该地图信息包含未停用的子地图信息！"));
		}
		mcMapinfo.setStatus(McMapinfo.STATUS_DISABLE);
		mcMapinfoService.updateStatus(mcMapinfo);
		return renderResult(Global.TRUE, text("停用地图信息成功"));
	}
	
	/**
	 * 启用地图信息
	 */
	@RequiresPermissions("mc:mcMapinfo:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(McMapinfo mcMapinfo) {
		mcMapinfo.setStatus(McMapinfo.STATUS_NORMAL);
		mcMapinfoService.updateStatus(mcMapinfo);
		return renderResult(Global.TRUE, text("启用地图信息成功"));
	}
	
	/**
	 * 删除地图信息
	 */
	@RequiresPermissions("mc:mcMapinfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(McMapinfo mcMapinfo) {
		mcMapinfoService.delete(mcMapinfo);
		return renderResult(Global.TRUE, text("删除地图信息成功！"));
	}
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("mc:mcMapinfo:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<McMapinfo> list = mcMapinfoService.findList(new McMapinfo());
		for (int i=0; i<list.size(); i++){
			McMapinfo e = list.get(i);
			// 过滤非正常的数据
			if (!McMapinfo.STATUS_NORMAL.equals(e.getStatus())){
				continue;
			}
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotBlank(excludeCode)){
				if (e.getId().equals(excludeCode)){
					continue;
				}
				if (e.getParentCodes().contains("," + excludeCode + ",")){
					continue;
				}
			}
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentCode());
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getTreeCode(), e.getTreeName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("mc:mcMapinfo:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(McMapinfo mcMapinfo){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		mcMapinfoService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}
	
}