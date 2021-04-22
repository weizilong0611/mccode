package com.jeesite.modules.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.codec.Md5Utils;
import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.mc.entity.McEmp;
import com.jeesite.modules.mc.entity.McWeather;
import com.jeesite.modules.mc.service.McEmpService;
import com.jeesite.modules.mc.service.McTextfiledataService;
import com.jeesite.modules.mc.service.McWeatherService;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.utils.DataUtils;
import com.jeesite.modules.utils.HttpUtils;

public class WeatherJob {

	
	
	
	public static void doJob() throws Exception {
		HttpUtils http = new HttpUtils();
		Map map = new HashMap();
		String txt = http.sendGet("http://flash.weather.com.cn/wmaps/xml/gannan.xml", map);
		String timeStr = new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(Calendar.getInstance().getTime());
		Document doc= DocumentHelper.parseText(txt);
        JSONObject json=DataUtils.dom4j2Json(doc.getRootElement());
        
        List<McWeather> mcWeather =JSONArray.parseArray(json.getJSONArray("city").toJSONString(), McWeather.class);
        
        McWeatherService mcEmpService = SpringUtils.getBean("mcWeatherService");
	 
        McWeather mcWeatherA = new McWeather();
        mcWeatherA.setTime(timeStr);
        mcEmpService.delete(mcWeatherA);
        
		for (McWeather mcEmp : mcWeather) {
			mcEmp.setTime(timeStr);
			mcEmp.setId(Md5Utils.md5(mcEmp.getTime()+ mcEmp.getCityname()) );
			mcEmp.setIsNewRecord(true);
		} 
		try {
			mcEmpService.insertBatch(mcWeather);
		}catch(Exception err) {
			err.printStackTrace();
		}
		
	}
	
}
