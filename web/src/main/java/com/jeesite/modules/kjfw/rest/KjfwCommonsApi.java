package com.jeesite.modules.kjfw.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.kjfw.cache.J2CacheUtils;
import com.jeesite.modules.kjfw.constant.GlobConstant;
import com.jeesite.modules.kjfw.entity.KjfwExpertDatabaseTab;
import com.jeesite.modules.kjfw.service.KjfwExpertDatabaseService;

/**
* @Author Tony
*  2021年4月12日 上午9:02:24
*  Never give up
*/
@RestController
@RequestMapping("/rest")
public class KjfwCommonsApi {
	
	@Autowired
	J2CacheUtils j2cache;
	
	//引入所依赖的服务类
	@Autowired
	private KjfwExpertDatabaseService kjfwExpertDatabaseService;
	
	//科技服务类查询接口
	@RequestMapping("/kjfw/list")
	@ResponseBody
	public JSONObject  kjfwExpertDbList(KjfwExpertDatabaseTab kjfwExpertDatabaseTab, HttpServletRequest request, HttpServletResponse response) {
		//数据格式以json返回
		JSONObject ret = new JSONObject();
		Page<KjfwExpertDatabaseTab> page = kjfwExpertDatabaseService.findPage(new Page<KjfwExpertDatabaseTab>(request, response), kjfwExpertDatabaseTab); 
		//List list = (List) kjfwExpertDatabaseService.findAll(kjfwExpertDatabaseTab);
		ret.put("list",page.getList());
		return ret;
	}
	
	/*
	 * //科技服务类表单录入接口
	 * 
	 * @PostMapping("/kjfw/save")
	 * 
	 * @ResponseBody //请求参数制动映射到实列对象中 public JSONObject
	 * kjfwExpertDbSave(@RequestBody KjfwExpertDatabaseTab kjfwExpertDatabaseTab) {
	 * JSONObject
	 *  ret = new JSONObject(); JSONObject params = new JSONObject(); try
	 * { params.put("code",GlobConstant.sucCode); }catch(Exception e) {
	 * params.put("code", GlobConstant.errorCode); }
	 * kjfwExpertDatabaseService.save(kjfwExpertDatabaseTab);
	 * ret.put("data",params); return ret; }
	 */
	
	
	  //科技服务类表单录入接口
	  @PostMapping("/kjfw/save")
	  @ResponseBody //请求参数制动映射到实列对象中 //用Aspect开启aop模式 
	  public JSONObject kjfwExpertDbSave(@Validated @RequestBody KjfwExpertDatabaseTab kjfwExpertDatabaseTab) {
	  JSONObject ret = new JSONObject();
	  kjfwExpertDatabaseService.save(kjfwExpertDatabaseTab); return ret; }
	  
	   //获取全国省市地区信息 已json格式返回数据
	  @RequestMapping("/area/json")
	  @ResponseBody
	  public JSONObject  provinceCityData(HttpServletRequest request) {
		  JSONObject ret = new JSONObject();
		  List<Map<String,Object>> maps = new ArrayList();
		  if(j2cache.getKey2JSONObject(GlobConstant.KEY_AREA_JSON)!=null) {
			  ret = j2cache.getKey2JSONObject(GlobConstant.KEY_AREA_JSON);
		  }else {
			  List<Map<String,Object>> listPro = kjfwExpertDatabaseService.findMapBySql("select area_code,area_name from province_city_tab t where (t.p_area_code='' || t.p_area_code is null) order by t.area_code asc ");
			  for(Map map:listPro) {
				  JSONObject provinceCityArr = new JSONObject();
				  List<Map<String,Object>> listCity = kjfwExpertDatabaseService.findMapBySql("select area_code,area_name from province_city_tab t where t.p_area_code="+map.get("area_code").toString()+" order by t.area_code asc");
				  provinceCityArr.put("province", map);
				  provinceCityArr.put("cityArr", listCity);
				  maps.add(provinceCityArr);
			  }
			  ret.put("provinceCityArr", maps);
			  j2cache.setKey(GlobConstant.KEY_AREA_JSON, ret.toJSONString());
		  }
		  return ret;
	  }
	  
		
	

}
