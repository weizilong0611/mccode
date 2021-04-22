package com.jeesite.modules.kjfw.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.kjfw.entity.KjfwExpertDatabaseTab;
import com.jeesite.modules.kjfw.service.KjfwExpertDatabaseService;
import com.jeesite.modules.mc.entity.McAnim;

/**
* @Author Tony
*  2021年4月9日 上午10:37:24
*  Never give up
*/
@Controller
@RequestMapping(value="${adminPath}/kjfw/kjfwExpertDb")
public class KjfwExpertDatabaseController {

	@Autowired
	private KjfwExpertDatabaseService kjfwExpertDatabaseService ;
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("kjfw:kjfwExpertDb:view")
	@RequestMapping(value = {"list", ""})
	public String list(KjfwExpertDatabaseTab kjfwExpertDb, Model model) {
		model.addAttribute("kjfwExpertDb", kjfwExpertDb);
		return "modules/kjfw/kjfwExpertDbList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("kjfw:kjfwExpertDb:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<KjfwExpertDatabaseTab> listData(KjfwExpertDatabaseTab kjfwExpertDb, HttpServletRequest request, HttpServletResponse response) {
		Page<KjfwExpertDatabaseTab> page = kjfwExpertDatabaseService.findPage(new Page<KjfwExpertDatabaseTab>(request, response), kjfwExpertDb); 
		return page;
	}
	
	//科技服务类查询接口
	@RequestMapping("/list")
	@ResponseBody
	public JSONObject  kjfwList(KjfwExpertDatabaseTab kjfwExpertDatabaseTab, HttpServletRequest request, HttpServletResponse response) {
		
		//数据格式以json返回
		JSONObject ret = new JSONObject();
		JSONObject params = new JSONObject();
		List list = null;
		try {
			list =  (List) kjfwExpertDatabaseService.findAll(kjfwExpertDatabaseTab);
			params.put("code","10000");
		}catch(Exception e) {//数据返回异常
			params.put("code","10001");
		}
		params.put("list",list);
		ret.put("data",params);
		return ret;
	}
	
}
