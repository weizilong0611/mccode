package com.jeesite.modules.mc.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JWindow;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.io.ResourceUtils;
import com.jeesite.common.jwt.JwtUtils;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.mybatis.mapper.query.QueryWhere;
import com.jeesite.common.shiro.authc.FormToken;
import com.jeesite.common.shiro.filter.FormAuthenticationFilter;
import com.jeesite.common.shiro.realm.AuthorizingRealmImpl;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.FileEntityService;
import com.jeesite.modules.file.service.FileUploadService;
import com.jeesite.modules.file.web.FileUploadController;
import com.jeesite.modules.geo.service.McGeofolderService;
import com.jeesite.modules.mc.entity.McMapinfo;
import com.jeesite.modules.mc.entity.McPlantgallery;
import com.jeesite.modules.mc.entity.McTextfiledata;
import com.jeesite.modules.mc.entity.McXftjfa;
import com.jeesite.modules.mc.service.McAplantatlasService;
import com.jeesite.modules.mc.service.McAppReportService;
import com.jeesite.modules.mc.service.McEmpService;
import com.jeesite.modules.mc.service.McMapinfoService;
import com.jeesite.modules.mc.service.McPlantgalleryService;
import com.jeesite.modules.mc.service.McTextfiledataService;
import com.jeesite.modules.mc.service.McWeatherService;
import com.jeesite.modules.mc.service.McXftjfaService;
import com.jeesite.modules.mc.utils.Utils;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.Menu;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.AreaService;
import com.jeesite.modules.sys.service.MenuService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.modules.sys.web.LoginController;
import com.jeesite.modules.utils.DataUtils;
import com.jeesite.modules.utils.HttpUtils;

import io.jsonwebtoken.Claims;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.listener.ProcessListener;


@RestController
@RequestMapping("/rest")
public class CommonsApi {
	
	@Autowired
	private McEmpService mcEmpService;

	@Autowired
	private McAplantatlasService mcAplantatlasService;
	@Autowired
	private McTextfiledataService mcTextfiledataService;
	@Autowired
	private McGeofolderService mcGeofolderService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private McMapinfoService mcMapinfoService;
	
	@Autowired
	private McWeatherService mcWeatherService;
	@Autowired
	FileEntityService fileEntityService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	FileUploadController fileUploadController;
	
	@Autowired
	LoginController loginController;
	
	@Autowired
	AreaService areaService;

	@Autowired
	McAppReportService mcAppReportService;

	@Autowired
	McPlantgalleryService mcPlantgalleryService;
	

	@Autowired
	private McXftjfaService mcXftjfaService;
	
	
	static JSONArray areajsonArray = null;
	static {
		try {
			areajsonArray = JSONObject.parseObject(com.alibaba.druid.util.Utils.readFromResource("AllIn.json")).getJSONArray("features");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * @RequestMapping(value = "aarea") public void area() throws Exception{
	 * 
	 * 
	 * 
	 * DataParse.insertArea(areaService); }
	 */
	
	/*
	 * @RequestMapping(value = "getPoll") public JSONArray getPoll(Model
	 * model,String city) throws Exception{ JSONArray ret = null;
	 * 
	 * Map map = new HashMap(); map.put("token", "5j1znBVAsnSf5xQyNQyq");
	 * map.put("city", city);
	 * 
	 * String str = new
	 * HttpUtils().sendGet("http://www.pm25.in/api/querys/aqi_details.json",map);
	 * ret = JSONObject.parseArray( str ); return ret; }
	 */
	
	
	@RequestMapping(value = "login") 
	public Map login(String username,String password) {
		String secretKey = Global.getProperty("shiro.loginSubmit.secretKey");
		username = DesUtils.decode(username, secretKey);
		password = DesUtils.decode(password, secretKey);

		FormToken upToken = new FormToken();
        upToken.setUsername(username);
        upToken.setPassword(password);
         
		Map<String,Object> map =new HashMap();
 
		try {
		        UserUtils.getSubject().login(upToken);
				map.put("result","true");
				map.put("message","登录成功！");
				String token = JwtUtils.geneJsonWebToken(UserUtils.getUser());
				map.put("token", token);
		}catch(Exception err) {
			err.printStackTrace();
			map.put("result","false");
			map.put("message",err.getMessage());
		}
		return map;
	} 
	
	@RequestMapping(value = { "generate/docx" })
    public void toubleMaker(String d,HttpServletResponse res,HttpServletRequest req) throws Exception {
		
			res.setContentType("application/force-download");// 设置强制下载不打开            
			res.addHeader("Content-Disposition", "attachment;fileName="+d+".docx");
        
        
			String path = Global.getConfig("path.generate"); 
			
			JSONObject json = new JSONObject();
			for (Enumeration<String> e = req.getParameterNames(); e.hasMoreElements();) {
			       //System.out.println(e.nextElement());
					String name = e.nextElement();
					Object value = req.getParameter(name);
					try {
						value = Integer.parseInt(value+"");
					}catch(Exception err) {
					//	err.printStackTrace();
					}
					json.put(name, value);
			}
			ArrayList<String> cmd = new ArrayList();
			cmd.add("java -jar " + path);
			cmd.add(/* ResourceUtils.getFile("classpath:").getPath() */Global.getConfig("path.generateTmp") + d+ ".docx");
			String out = System.getProperty("java.io.tmpdir")+""+System.currentTimeMillis() + ".docx";
			cmd.add(out);
			cmd.add(json.toString().replaceAll("\"", "'"));
			ProcessExecutor a = new ProcessExecutor();  
			a.commandSplit(StringUtils.join(cmd," ")); 
	//		a.redirectOutput(System.out);
			a.readOutput(true);
			a.timeout(60, TimeUnit.SECONDS);
		System.out.println(
				a.execute().outputString("GBK")
				);
			
			OutputStream outputStream = res.getOutputStream();
	        byte[] buff = new byte[1024];
	        BufferedInputStream bis = null;
	        // 读取filename
	        bis = new BufferedInputStream(new FileInputStream(new File(out)));
	        int i = bis.read(buff);
	        while (i != -1) {
	            outputStream.write(buff, 0, buff.length);
	            outputStream.flush();
	            i = bis.read(buff);
	        }
	        bis.close();
    }
	
	
	@RequestMapping(value = "getCaiYangImgList")
    public  List<String> getCaiYangImgList(String type,String bh) throws Exception{
		List<String> imgsList = new ArrayList();
		
		String path = Global.getConfig("path.caiyang.path") + "\\" + type;
		
		if(new File(path).exists()) {
			String [] files = new File(path).list(new java.io.FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.contains(bh);
				}
			});
			for (String string : files) {
				imgsList.add(type + "/" + string);
			}
		}
		return imgsList;
	}
	@RequestMapping(value = { "downloadcaiyang/{type}/{bh}" }, method = { RequestMethod.GET })
    public void downloadcaiyang(@PathVariable("bh") final String bh, @PathVariable("type") String type,HttpServletResponse res) throws Exception { 
			String path = Global.getConfig("path.caiyang.path") + "/" + type + "/" + bh + ".jpg"; 
			OutputStream outputStream = res.getOutputStream();
	        byte[] buff = new byte[1024];
	        BufferedInputStream bis = null;
	        // 读取filename
	        bis = new BufferedInputStream(new FileInputStream(new File(path)));
	        int i = bis.read(buff);
	        while (i != -1) {
	            outputStream.write(buff, 0, buff.length);
	            outputStream.flush();
	            i = bis.read(buff);
	        }
    }
	
	@RequestMapping(value = { "downloadimg/{bh}" }, method = { RequestMethod.GET })
    public String downloadimg( @PathVariable("bh") final String bh, final String preview, final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		FileUpload arg0 = new FileUpload();
		arg0.getSqlMap().getWhere().and("biz_key", QueryType.EQ, bh);
		List<FileUpload> list = fileUploadService.findList(arg0);
		
		return fileUploadController.download(list.get(0).getId(), preview, request, response);
    }
	
	
	@RequestMapping(value = { "getXftj" })
    public List getXftj(String cate,String title,final HttpServletRequest request) throws ServletException, IOException {
		
		String url = request.getRequestURL().toString();
		String ctx = Global.getConfig("server.servlet.context-path");
		url = url.substring(0,url.indexOf(ctx)+ctx.length());
		
		McXftjfa mcXftjfa = new McXftjfa();
		//mcXftjfa.setTitle(title);
		mcXftjfa.setCate(cate);
		mcXftjfa.getSqlMap().getWhere().and("title", QueryType.EQ, title);
		
		
		List<McXftjfa> list = mcXftjfaService.findList(mcXftjfa);
		
		for(int i=0;i<list.size();i++) {
			
			String txt = list.get(i).getTxt(); 
			txt = txt.replaceAll(ctx+ "/userfiles", url+"/userfiles");
			txt = txt.replaceAll(ctx+ "/static", url+"/static");
			list.get(i).setTxt(txt);
		}
		
		return list;
	}
 
	@RequestMapping(value = { "getKe" }, method = { RequestMethod.GET })
    public List getKe( ) throws ServletException, IOException {
		return mcPlantgalleryService.findMapBySql("select  distinct keCn from mc_plantgallery");
    }
	@RequestMapping(value = { "getShu" }, method = { RequestMethod.GET })
    public List getShu( String ke) throws ServletException, IOException {
		return mcPlantgalleryService.findMapBySql("select  distinct shuCN from mc_plantgallery where keCn like '%"+ke+"%'");
    }
 
	@RequestMapping(value = "checkLogin")
    public  JSONObject checkLogin(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		JSONObject ret = new JSONObject();
		String token = request.getHeader("token");


		if(StringUtils.isEmpty(token)) {
			ret.put("isLogin", false);
		}else {
			ret.put("isLogin", true);
			 
			Claims jwtUser = JwtUtils.checkJWT(token);
			if(jwtUser == null ) {
				ret.put("isLogin", false);
			}else {
				User user =UserUtils.get(jwtUser.get("userName") +"") ;
				if(user == null) {
					user =UserUtils.getByLoginCode(jwtUser.get("userName") +"") ;
				}
				if(null != user && !StringUtils.isEmpty(user.getUserName())) {
					try {
						Employee obj = (Employee)user.getRefObj();
						String areacode = obj.getCompany().getArea().getAreaCode();
			
						for(int i=0;i<areajsonArray.size();i++) {
							JSONObject item = areajsonArray.getJSONObject(i);
							String a = item.getJSONObject("properties").getString("行政区");
							
							if(a.trim().contentEquals(areacode)) {
								ret.put("region",item.getJSONObject("geometry"));
							}	
						}
					}catch(Exception err) {
						
					}
					ret.put("user", user);
				}
			}
		}
		
		return ret;
	}

	@RequestMapping(value = "area")
    public  JSONArray area(HttpServletRequest request, HttpServletResponse response, Model model,Boolean withSelf) throws Exception{
 
		
			String areacode = "";
			List<Area> ret = null;
			try {
				User user = UserUtils.getUser();
				Employee obj = (Employee)user.getRefObj();
				areacode = obj.getCompany().getArea().getAreaCode();
			 
				Area par = new Area();
				
				Area curr = areaService.get(areacode);
				
				par.getSqlMap().getWhere().and("area_code",QueryType.IN, curr.getParentCodes().split(",")).or("parent_codes",QueryType.LEFT_LIKE,areacode + ",");
				ret = areaService.findList(par);
				ret.add(1,curr);
			}catch(Exception err) {
			}
			
			if(null == ret) {
				Area ar = new Area();
				ret = areaService.findList(ar);
			}
			
			List r = new ArrayList();
			areaService.convertChildList(ret, r, ret.get(0).getParentCode());
			ret = null;
			ret = r;
			SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
			filter.getIncludes().add("childList");
			filter.getIncludes().add("areaCode"); 
			filter.getIncludes().add("areaName"); 
			String retStr = JSONArray.toJSONString(ret,filter); 
			JSONArray jsonArr = (JSONArray) JSONArray.parse(retStr);
			
			//withSelf
			 class WithSelfFormat{
				public void format(JSONArray json) {
					for(int i=0;i<json.size();i++) {
						if(json.getJSONObject(i).containsKey("childList")) {
							JSONArray childList = json.getJSONObject(i).getJSONArray("childList");
							JSONObject jsonObj = new JSONObject();
							jsonObj.put("areaCode", json.getJSONObject(i).getString("areaCode"));
							jsonObj.put("areaName", json.getJSONObject(i).getString("areaName"));
							childList.add(0, jsonObj);
							format(childList);
						}
					}
				} 
			 };
		 
			 if(null!=withSelf && withSelf) {
				 new WithSelfFormat().format(jsonArr);
			 }
			 
			return jsonArr;
	}
 
	
	
	@RequestMapping(value = "getMenu")
    public  Menu getWeather(HttpServletRequest request) throws Exception{
		Menu m = new Menu();
		
		m.setMenuNameOrig("stsz");
		m.setMenuType("1");
		
		List<Menu> ret = menuService.findList(m);
		String parentCode = ret.get(0).getMenuCode();
		m = new Menu();
		m.setMenuCode(parentCode);
		User user = null;
		 
		String token = request.getHeader("token"); 

		if(StringUtils.isEmpty(token)) {
			 
		}else {  
			Claims jwtUser = JwtUtils.checkJWT(token);
			
			user =UserUtils.get(jwtUser.get("userName") +"") ;
			if(user == null) {
				user =UserUtils.getByLoginCode(jwtUser.get("userName") +"") ;
			}
			m.setUserCode(user.getUserCode());
		}
		ret = menuService.findByUserCode(m);
		
		
		List<Menu> rRet = new ArrayList();
		for(int i=0;i<ret.size();i++) {
			if(
					ret.get(i).getParentCodes().contains(parentCode) || 
					ret.get(i).getMenuCode().equals(parentCode)		
			) {
				rRet.add(ret.get(i));
			}
		}
		
		Utils.makeTree(m,rRet);
		
		//com.jeesite.modules.page.utils.Utils.makeTree(m,rRet);
		
		return m;
	}
		
	@RequestMapping(value = "getWeather")
    public  Object getWeather(String areacode) throws Exception{
		
		HttpUtils http = new HttpUtils();
		Map map = new HashMap();
		String txt = http.sendGet("http://flash.weather.com.cn/wmaps/xml/gannan.xml", map);
		
		Document doc= DocumentHelper.parseText(txt);
        JSONObject json=DataUtils.dom4j2Json(doc.getRootElement());

        return json;
	}
	
	@RequestMapping(value = "getWeatherHistory")
	public JSONObject getWeatherHistory(String cityX,String cityY,String time) throws Exception{
		JSONObject ret = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date currentDate=new Date();
		if (!time.equals("")) {
			try{
				currentDate=df.parse(time);
			}
			catch(Exception ex){}
		}	
		cal.setTime(currentDate);
		cal.add(Calendar.MONTH, -1);
		String startTime=df.format(cal.getTime());
		cal.add(Calendar.MONTH, 2);
		String endTime=df.format(cal.getTime());
		ret.put("data", mcWeatherService.getWeatherHistory(cityX, cityY, startTime, endTime));
		return ret;
    }
	
	/**
	 * 获取珍惜动物图册列表
	 * */
	@RequestMapping("getImgList")
	public List<Map<String, Object>> getImgList(String areacode,String tablename) {
		return mcAplantatlasService.findMapBySql("select * from ("
				+ "select a.*,b.id from "+
				(
						!StringUtils.isEmpty(tablename) ? tablename :  "mc_aplantatlas"
				)+ 
			" a LEFT JOIN js_sys_file_upload b on a.aid = b.biz_key ) c " + 
					(StringUtils.isEmpty(areacode) ? "" : " where c.areacode = " + areacode)
			);
	}
	
	@RequestMapping("getImgPlantList")
	public Page<McPlantgallery> getImgPlantList(String ke,String shu,String zhong,String pageNo,String pageSize) {
		//return mcPlantgalleryService.findMapBySql("select a.*,b.id from mc_plantgallery a LEFT JOIN js_sys_file_upload b on a.aid = b.biz_key");
		McPlantgallery mc = new McPlantgallery();
		mc.getSqlMap().getWhere().and("kecn", QueryType.LIKE, ke).and("shucn", QueryType.LIKE, shu).and("zhongcn", QueryType.LIKE, zhong);
		mc.setPage(new Page<McPlantgallery>());
		mc.setPageNo(Integer.parseInt(pageNo));
		mc.setPageSize(Integer.parseInt(pageSize));
		return mcPlantgalleryService.findPage(mc);
	}
	
		
	@RequestMapping("getReportList")
	@ResponseBody
	public List<Map<String, Object>> getReportList(String areacode,String type,String reportDate){
		String sql="	select a.*,(select id from js_sys_file_upload where status=0 and biz_key = a.id and biz_type = 'mcAppReport_file' limit 0,1) as fileX, "+
		"			(select id from js_sys_file_upload where status=0 and biz_key = a.id and biz_type='mcAppReport_image' limit 0,1) as imgX from mc_app_report a where 1=1 ";
		if(!StringUtils.isEmpty(areacode)){
			sql+=" and areacode like '"+areacode+"%' ";
		}
		  if(!StringUtils.isEmpty(type)){
			sql+=" and type ='"+type+"' ";
		}
		  if(!StringUtils.isEmpty(reportDate)){
			sql+=" and reportDate >='"+reportDate+"-01-01' and reportDate <= '"+reportDate+"-12-31' ";
		}
		return mcAppReportService.findMapBySql(sql);
	}
	
	@RequestMapping(value = { "download/{fileUploadId}" }, method = { RequestMethod.GET })
    public String download(@PathVariable("fileUploadId") final String fileUploadId, final String preview, final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		return fileUploadController.download(fileUploadId, preview, request, response);
    }
	
	
	 
	@RequestMapping(value = {"queryData"})
	@ResponseBody
	public List<Map<String, Object>> queryData(String areacode,String areaname,String parentcode,String rowtitle,String rowtime1,String rowtime2,String category,String displayName){
	
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		McTextfiledata entity = new McTextfiledata();
		QueryWhere where = entity.getSqlMap().getWhere();
		if(!StringUtils.isEmpty(rowtime1) && !StringUtils.isEmpty(rowtime2)) {
			where.andBracket("rowtime", QueryType.GTE, rowtime1).and("rowtime", QueryType.LTE, rowtime2).endBracket(1);			
		}else if(!StringUtils.isEmpty(rowtime1)) {
			where.and("rowtime", QueryType.GTE, rowtime1);
		}else if(!StringUtils.isEmpty(rowtime2)) {
			where.and("rowtime", QueryType.LTE, rowtime2);
		}
		
		if(!StringUtils.isEmpty(rowtitle)) {
			where.and("rowtitle", QueryType.IN, rowtitle.split(","));
		}
		if(!StringUtils.isEmpty(areaname)) {
			//entity.setAreaname(areaname);
			where.and("areaname", QueryType.IN, areaname.split(",") );
		}else if(!StringUtils.isEmpty(areacode)){
			if(!areacode.equals("null")) {
				entity.setAreacode(areacode);
			}
		}else if(!StringUtils.isEmpty(parentcode)) {
			where.andBracket("areacode", QueryType.LEFT_LIKE, parentcode + "%" ).endBracket(2);
		}else {
			where.and("CHAR_LENGTH(areacode)", QueryType.EQ,6);
		}

		
		if(!StringUtils.isEmpty(category)) {
			where.and("ruleid", QueryType.IN,category.split(","));		}
		
		entity.getSqlMap().getOrder().setOrderBy("rowtime asc");
		
		
		
		if(!StringUtils.isEmpty(displayName)) {

			list = mcTextfiledataService.findMap(entity,new String[] {"concat(rowtitle, ruleId) AS rowtitle","value","areaname","areacode","date_format(rowtime ,'%Y-%m-%d %H:%i:%S') as rowtime"});

			Map map = new HashMap();
			for(int i=0;i<rowtitle.split(",").length;i++) {
				map.put(rowtitle.split(",")[i] + (
						category.split(",").length > 1 ? category.split(",")[i] : category
								)
								, displayName.split(",")[i]);
			}
			for(Map<String, Object> mc : list) {
				mc.put("rowtitle",map.get(mc.get("rowtitle")));
			}
		}else {
			list = mcTextfiledataService.findMap(entity,new String[] {"rowtitle","value","areaname","areacode","date_format(rowtime ,'%Y-%m-%d %H:%i:%S') as rowtime","ruleid"});

		}
		
		
		return list;
	}
	
	@RequestMapping(value = {"queryData2"})
	@ResponseBody
	public JSONObject queryData2(String areacode,String areaname,String parentcode,String rowtitle,String rowtime1,String rowtime2,String category,String displayName){
		JSONObject ret = new JSONObject();
		List<Map<String,Object>> list= queryData(areacode,areaname,parentcode,rowtitle,rowtime1,rowtime2,category,displayName);
		JSONObject parm=new JSONObject();
		parm.put("areacode", areacode);
		parm.put("areaname", areaname); 
		parm.put("parentcode", parentcode);
		parm.put("rowtitle", rowtitle);
		parm.put("rowtime1", rowtime1);
		parm.put("rowtime2", rowtime2);
		parm.put("category", category);
		parm.put("displayName", displayName);
		ret.put("parm", parm);
		ret.put("list", list);
		return ret;
	}

	@RequestMapping(value = {"queryPieData"})
	@ResponseBody
	public List<Map<String, Object>> queryData(String areacode,String rowtime1,String rowtime2,String category){
	 
		return null;
	}
	
	
	
	@RequestMapping(value = {"getMapInfo"})
	@ResponseBody
	public JSONObject getMapInfo(String arg) {
		JSONObject ret = new JSONObject();
		McMapinfo mcMapinfo = mcMapinfoService.findOneBySql("select * from mc_mapinfo where tree_name = '"+arg+"' and status = '0' ");
		 
		McMapinfo mc = new McMapinfo();
		mc.setParentCode(mcMapinfo.getTreeCode());
		mc.setStatus("0");
		mc.getSqlMap().getOrder().setOrderBy("tree_sort asc");
		List<McMapinfo> mpList = mcMapinfoService.findList(mc);
		for (McMapinfo mcMapinfo2 : mpList) {
			JSONObject layers = new JSONObject(true);
	 
			mc.setParentCode(mcMapinfo2.getTreeCode());
			mc.setStatus("0");
			mc.getSqlMap().getOrder().setOrderBy("tree_sort asc");
			List<McMapinfo> mpList2 =mcMapinfoService.findList(mc);
			for (McMapinfo mcMapinfo3 : mpList2) { 
				JSONObject j = new JSONObject(true);
				try {
					j = JSONObject.parseObject(mcMapinfo3.getMapaction());
					if(null == j)
						j = new JSONObject();
					//}
				}catch(Exception e) {
					e.printStackTrace();
				}
				j.put("url", mcMapinfo3.getUrl());
				
				if(StringUtils.isEmpty(mcMapinfo3.getUrl())) {
					j.put("url", mcMapinfo2.getUrl());	
				}
				
				j.put("mark", mcMapinfo3.getRemarks());
				j.put("note", mcMapinfo3.getNote());
				 
				layers.put(mcMapinfo3.getTreeName(), j);
			}
			ret.put(mcMapinfo2.getTreeName()	+ (
					mcMapinfo2.getTreeName().equals(mcMapinfo.getMapaction()) ? "_selected" : ""
				), layers);
		}
		return ret;
	}
	
	
	@RequestMapping(value = {"getMobileMapInfo"})
	@ResponseBody
	public JSONObject getMobileMapInfo(String arg) {
		JSONObject ret = new JSONObject();
		McMapinfo mcMapinfo = mcMapinfoService.findOneBySql("select * from mc_mapinfo where tree_name = '"+arg+"' and status = '0' "); 
		McMapinfo mc = new McMapinfo();
		mc.setParentCode(mcMapinfo.getTreeCode());
		mc.setStatus("0");
		List<McMapinfo> mpList = mcMapinfoService.findList(mc);
		for (McMapinfo mcMapinfo2 : mpList) {
			JSONObject layers = new JSONObject(); 
			mc.setParentCode(mcMapinfo2.getTreeCode());
			mc.setStatus("0");
			mc.getSqlMap().getOrder().setOrderBy("tree_sort asc");
			List<McMapinfo> mpList2 =mcMapinfoService.findList(mc);
			for (McMapinfo mcMapinfo3 : mpList2) { 
				if(mcMapinfo3.getIsTreeLeaf()) {
					JSONObject j = new JSONObject();
					try {
						j = JSONObject.parseObject(mcMapinfo3.getMapaction());
						if(null == j)
							j = new JSONObject();
						//}
					}catch(Exception e) {
						e.printStackTrace();
					}
					j.put("url", mcMapinfo3.getUrl());
					
					if(StringUtils.isEmpty(mcMapinfo3.getUrl())) {
						j.put("url", mcMapinfo2.getUrl());	
					}
					
					j.put("mark", mcMapinfo3.getRemarks());
					j.put("note", mcMapinfo3.getNote());

					layers.put(mcMapinfo3.getTreeName(), j);
				}else { 
					JSONObject layersX = new JSONObject(); 
					layers.put(mcMapinfo3.getTreeName(), layersX);
					mc.setParentCode(mcMapinfo3.getTreeCode());
					mc.setStatus("0");
					mc.getSqlMap().getOrder().setOrderBy("tree_sort asc");
					List<McMapinfo> mpList2X =mcMapinfoService.findList(mc);
					for (McMapinfo mcMapinfo3X : mpList2X) { 
						JSONObject j = new JSONObject();
						try {
							j = JSONObject.parseObject(mcMapinfo3X.getMapaction());
								if(null == j)
									j = new JSONObject();
							//}
						}catch(Exception e) {
							e.printStackTrace();
						}
						j.put("url", mcMapinfo3X.getUrl());
						
						if(StringUtils.isEmpty(mcMapinfo3X.getUrl())) {
							j.put("url", mcMapinfo3.getUrl());	
						}
						
						j.put("mark", mcMapinfo3X.getRemarks());
						j.put("note", mcMapinfo3X.getNote());
	
						layersX.put(mcMapinfo3X.getTreeName(), j);
						
					}
				}

			}
			ret.put(mcMapinfo2.getTreeName()	+ (
					mcMapinfo2.getTreeName().equals(mcMapinfo.getMapaction()) ? "_selected" : ""
				), layers);
		}
		return ret;
	}
	
	 
	
	/**获取数据分类列表*/
	@RequestMapping(value = {"getDataTypeList"})
	@ResponseBody
	public List getDataTypeList() {  
		return mcGeofolderService.findBySql(
				"	select tree_code,tree_names from mc_Geofolder where file_type <> 'folder'\r\n" 
				); 
	}
	
	
	 
	@RequestMapping(value = {"getArea"})
	@ResponseBody
	public String getArea() {  
		List<Area> ret = areaService.findList(new Area());
		areaService.convertTreeList(ret,"0");
		
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		filter.getIncludes().add("childList");
		filter.getIncludes().add("areaCode");
		filter.getIncludes().add("treeNames");
		filter.getIncludes().add("areaName");
		//  System.out.println(JSONObject.toJSONString(u, filter));
		//return JSONObject.fo.toJSONString(ret,filter);
		String retStr = JSONArray.toJSONString(ret.get(0).getChildList(),filter);
		
		
		return  retStr;
	}
	
	@RequestMapping(value = {"getEmpData"})
	@ResponseBody
	public List getEmpData(String time1,String time2,String empName) {
		
		
		List<Map<String, Object>> ret = null;
		
		if(StringUtils.isEmpty(time1)) {
			ret = mcEmpService.findMapBySql("select * FROM mc_emp where COLLECTION_TIME = (\r\n" + 
					"	select COLLECTION_TIME FROM mc_emp order by COLLECTION_TIME desc limit 0,1\r\n" + 
					")");
		}else {
			ret = mcEmpService.findMapBySql(
					String.format(
							"select * FROM mc_emp where COLLECTION_TIME > '%s' and COLLECTION_TIME < '%s' and Hy_NAME = '%s' ",
							time1,
							time2,
							empName
							)
					);
		}
		
		String geo = "[{\"geometry\":{\"coordinates\":[102.8373102399756,34.57568652471338],\"type\":\"Point\"},\"properties\":{\"BSM\":1,\"JBQK\":\"未知\",\"JCSSLX\":\"11\",\"JCSSMC\":\"多宋多水库\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.6634526920881,34.67226030177159],\"type\":\"Point\"},\"properties\":{\"BSM\":2,\"JBQK\":\"未知\",\"JCSSLX\":\"11\",\"JCSSMC\":\"肖家沟水库\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.8479821961215,35.14406335214204],\"type\":\"Point\"},\"properties\":{\"BSM\":3,\"JBQK\":\"未知\",\"JCSSLX\":\"15\",\"JCSSMC\":\"工农团结桥\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.7935411126323,34.7613855833780],\"type\":\"Point\"},\"properties\":{\"BSM\":4,\"JBQK\":\"洮砚大桥横跨九甸峡水库库区淹没区,大桥全长345.64m,桥高50m,设计淹没深度41m.该桥基础为钻孔灌注桩,上设承台,下部为圆端型墩,肋板式桥台,上部构造为50m预应连续T梁,单梁净重110吨\",\"JCSSLX\":\"15\",\"JCSSMC\":\"洮砚大桥\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.4027663005244,34.00432125102498],\"type\":\"Point\"},\"properties\":{\"BSM\":5,\"JBQK\":\"位于迭部县的东约30km处嘉陵江一级支流—白龙江干流上.设计总装机容量为1.0万kW（2×5000kW）,初设工程总投资为3000万元.工程年发电量为4700~5000万kW/h,装机年利用小时数6970h,保证出力6560kW\",\"JCSSLX\":\"19\",\"JCSSMC\":\"尼什峡水电枢纽\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.7092934410395,35.21694582359095],\"type\":\"Point\"},\"properties\":{\"BSM\":6,\"JBQK\":\"未知\",\"JCSSLX\":\"11\",\"JCSSMC\":\"水库\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.7006988374814,33.39946006313147],\"type\":\"Point\"},\"properties\":{\"BSM\":7,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.6109209709261,33.40988503126926],\"type\":\"Point\"},\"properties\":{\"BSM\":8,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.4800561332840,33.53057186989594],\"type\":\"Point\"},\"properties\":{\"BSM\":9,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.3152624352133,33.53632135084973],\"type\":\"Point\"},\"properties\":{\"BSM\":10,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.8898810335829,33.79705927356667],\"type\":\"Point\"},\"properties\":{\"BSM\":11,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.7571049351447,33.89294393431516],\"type\":\"Point\"},\"properties\":{\"BSM\":12,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.4619573078835,33.91007662813394],\"type\":\"Point\"},\"properties\":{\"BSM\":13,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.4642235562722,33.91407771821218],\"type\":\"Point\"},\"properties\":{\"BSM\":14,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.4767952987641,33.99025039084290],\"type\":\"Point\"},\"properties\":{\"BSM\":15,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.9117171187501,34.03588634058218],\"type\":\"Point\"},\"properties\":{\"BSM\":16,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5908722246735,34.33644589636106],\"type\":\"Point\"},\"properties\":{\"BSM\":17,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.6002375603152,34.39446841368641],\"type\":\"Point\"},\"properties\":{\"BSM\":18,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5856480979814,34.47042368960166],\"type\":\"Point\"},\"properties\":{\"BSM\":19,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5772339824624,34.51465878967144],\"type\":\"Point\"},\"properties\":{\"BSM\":20,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5380174816215,34.56930761545414],\"type\":\"Point\"},\"properties\":{\"BSM\":21,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.6112859879580,34.9549091809140],\"type\":\"Point\"},\"properties\":{\"BSM\":22,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.1999434162557,34.09642635799008],\"type\":\"Point\"},\"properties\":{\"BSM\":23,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.6609717253649,34.51062488153309],\"type\":\"Point\"},\"properties\":{\"BSM\":24,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.7861878340014,34.54399549949278],\"type\":\"Point\"},\"properties\":{\"BSM\":25,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.4363414468004,34.58479763370389],\"type\":\"Point\"},\"properties\":{\"BSM\":26,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.4870811410818,34.58046486466185],\"type\":\"Point\"},\"properties\":{\"BSM\":27,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.2397993926279,34.64070708416466],\"type\":\"Point\"},\"properties\":{\"BSM\":28,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.3686028420476,34.60444064202204],\"type\":\"Point\"},\"properties\":{\"BSM\":29,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.3061476131219,34.62044924533643],\"type\":\"Point\"},\"properties\":{\"BSM\":30,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.4583480831976,35.14080723616507],\"type\":\"Point\"},\"properties\":{\"BSM\":31,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.4714103842819,35.15509853097245],\"type\":\"Point\"},\"properties\":{\"BSM\":32,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"水闸\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.7564387182723,33.89253311680994],\"type\":\"Point\"},\"properties\":{\"BSM\":33,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5890116199903,34.49648495701518],\"type\":\"Point\"},\"properties\":{\"BSM\":34,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5378552466224,34.56944892952453],\"type\":\"Point\"},\"properties\":{\"BSM\":35,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.6176940442448,34.57135992502060],\"type\":\"Point\"},\"properties\":{\"BSM\":36,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.6630837730977,34.67384264632113],\"type\":\"Point\"},\"properties\":{\"BSM\":37,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.2183516563040,34.05121857367828],\"type\":\"Point\"},\"properties\":{\"BSM\":38,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.6606842912471,34.51043778027940],\"type\":\"Point\"},\"properties\":{\"BSM\":39,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.6264428192807,34.54299940409925],\"type\":\"Point\"},\"properties\":{\"BSM\":40,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.4365542335910,34.58479489616758],\"type\":\"Point\"},\"properties\":{\"BSM\":41,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.8436377484834,34.57945297447526],\"type\":\"Point\"},\"properties\":{\"BSM\":42,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.9868989149530,34.67194023575331],\"type\":\"Point\"},\"properties\":{\"BSM\":43,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.0200624963871,34.68417241942166],\"type\":\"Point\"},\"properties\":{\"BSM\":44,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.4588669902195,35.14126549110961],\"type\":\"Point\"},\"properties\":{\"BSM\":45,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[101.1065034306889,34.24578958012506],\"type\":\"Point\"},\"properties\":{\"BSM\":46,\"JBQK\":\"无\",\"JCSSLX\":\"90\",\"JCSSMC\":\"拦水坝\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.4844741413686,34.58963665990399],\"type\":\"Point\"},\"properties\":{\"BSM\":47,\"JBQK\":\"未知\",\"JCSSLX\":\"90\",\"JCSSMC\":\"气象站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.3734153344502,33.77886662079794],\"type\":\"Point\"},\"properties\":{\"BSM\":48,\"JBQK\":\"未知\",\"JCSSLX\":\"90\",\"JCSSMC\":\"气象站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[101.6866222411236,33.76747011145653],\"type\":\"Point\"},\"properties\":{\"BSM\":49,\"JBQK\":\"位于甘南州玛曲县\",\"JCSSLX\":\"12\",\"JCSSMC\":\"阿万仓水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\" 玛曲\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.3902765950888,33.88689273441224],\"type\":\"Point\"},\"properties\":{\"BSM\":50,\"JBQK\":\"达拉电站位于甘肃省甘南州迭部县城东南30公里处,坝高76米,总库容量1506万立方米,装机容量5.25万千瓦,年发电量2.73亿千瓦,工程总投资4.4亿元\",\"JCSSLX\":\"12\",\"JCSSMC\":\"达拉电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.4446778833777,33.98902886958672],\"type\":\"Point\"},\"properties\":{\"BSM\":51,\"JBQK\":\"迭部县的东约30km处嘉陵江一级支流—白龙江干流上.设计总装机容量为1.0万kW（2×5000kW）,初设工程总投资为3000万元.工程年发电量为4700~5000万kW·h,装机年利用小时数6970h,保证出力6560kW.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"尼什峡水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.5620076650218,33.34486547815936],\"type\":\"Point\"},\"properties\":{\"BSM\":52,\"JBQK\":\"位于甘南藏族自治州迭部县西北方向的益哇乡境内\",\"JCSSLX\":\"12\",\"JCSSMC\":\"扎尕水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.5792837818211,33.35138725911475],\"type\":\"Point\"},\"properties\":{\"BSM\":53,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"岔坪水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.6064793506416,33.36706937255354],\"type\":\"Point\"},\"properties\":{\"BSM\":54,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"东益水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.6129429796349,33.37789648513473],\"type\":\"Point\"},\"properties\":{\"BSM\":55,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"铁坝一级电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.6814648057563,33.39100983623501],\"type\":\"Point\"},\"properties\":{\"BSM\":56,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"瓜子沟电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.6102646376030,33.40747625382613],\"type\":\"Point\"},\"properties\":{\"BSM\":57,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"铁坝二级电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.6748070697905,33.42123771394938],\"type\":\"Point\"},\"properties\":{\"BSM\":58,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"大年二级电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.6712753396852,33.94424124313889],\"type\":\"Point\"},\"properties\":{\"BSM\":59,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"曹世坝水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5865240916136,33.95833592186534],\"type\":\"Point\"},\"properties\":{\"BSM\":60,\"JBQK\":\"尼傲峡水电站位于卓尼县城以东约40公里的白龙江干流上,设计总装机容量为1.2万千瓦,年发电量为8600万度,年产值1376万元.1993年投建,1999年一期工程建成并试运行发电,2003年年底二期工程三号机组\",\"JCSSLX\":\"12\",\"JCSSMC\":\"尼傲峡水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5348974042918,33.97012149411171],\"type\":\"Point\"},\"properties\":{\"BSM\":61,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"小尼傲水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.2749217413453,33.46841463222955],\"type\":\"Point\"},\"properties\":{\"BSM\":62,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"曲玛电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.5704756760117,33.46994820474589],\"type\":\"Point\"},\"properties\":{\"BSM\":63,\"JBQK\":\"甘肃甘南舟曲县曲告纳乡力族坝\",\"JCSSLX\":\"12\",\"JCSSMC\":\"力族坝水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.5467359014407,33.48844226709701],\"type\":\"Point\"},\"properties\":{\"BSM\":64,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"缠坪坝电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.4958554238888,33.51769087303541],\"type\":\"Point\"},\"properties\":{\"BSM\":65,\"JBQK\":\"位于甘南州舟曲县拱坝乡脱落村\",\"JCSSLX\":\"12\",\"JCSSMC\":\"驼骆坝电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.4604949749632,33.53862927562062],\"type\":\"Point\"},\"properties\":{\"BSM\":66,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"阳庄坝电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.4426977253198,33.55493299506173],\"type\":\"Point\"},\"properties\":{\"BSM\":67,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"干间坝电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.3452029309706,33.56371812049491],\"type\":\"Point\"},\"properties\":{\"BSM\":68,\"JBQK\":\"嘎尔隆水电站是拱坝河流域综合开发的重要阶梯式电站,该电站由主厂房、控制室、高压室和升压站组成,装机容量为3600KW,设计年平均发电量为1539万kw.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"嘎尔隆一级水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.4130711702983,33.56863255215063],\"type\":\"Point\"},\"properties\":{\"BSM\":69,\"JBQK\":\"插岗二级电站位于甘南州舟曲县插岗乡拱坝河支流嘎尔隆沟内,属低坝径流引水式电站,由陇南顺通公司修建,装机容量２×１０００千瓦,投资１２６０万元；\",\"JCSSLX\":\"12\",\"JCSSMC\":\"插岗二级电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.3786083706798,33.57326816789168],\"type\":\"Point\"},\"properties\":{\"BSM\":70,\"JBQK\":\"嘎尔隆水电站位于甘南州舟曲县插岗乡拱坝河支流嘎尔隆沟内,属低坝径流引水式电站,由取水枢纽、引水渠道、前池、压力管道、升压站、发电厂房、尾水等组成.项目总投资2300万元,总装机容量\",\"JCSSLX\":\"12\",\"JCSSMC\":\"插岗乡水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.3508250454395,33.58118434947818],\"type\":\"Point\"},\"properties\":{\"BSM\":71,\"JBQK\":\"嘎尔隆水电站位于甘南州舟曲县插岗乡拱坝河支流嘎尔隆沟内,属低坝径流引水式电站,由取水枢纽、引水渠道、前池、压力管道、升压站、发电厂房、尾水等组成.项目总投资2300万元,总装机容量\",\"JCSSLX\":\"12\",\"JCSSMC\":\"嘎尔隆二级水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.5046632455124,33.67483559428729],\"type\":\"Point\"},\"properties\":{\"BSM\":72,\"JBQK\":\"石门坪水电站位于甘肃省舟曲县境内的白龙江干流上,距兰州市公路里程约390km；设计引水流量130 立方米/秒,装机容量15MW,工程概算静态投资额1.1亿元.该电站于2005年4月6日开工建设,10月25日顺利实\",\"JCSSLX\":\"12\",\"JCSSMC\":\"石门坪水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.4563452080650,33.70319712424794],\"type\":\"Point\"},\"properties\":{\"BSM\":73,\"JBQK\":\"两河口水电站位于舟曲县境内的白龙江干流上,为一低坝径流引水式电站,设计引水流量75.0m3／s,装机容量1500kw,属四等小(I型)工程.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"两河口电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.4035377028028,33.74094153022747],\"type\":\"Point\"},\"properties\":{\"BSM\":74,\"JBQK\":\"位于距舟曲县城7公里的江盘乡白龙江干流上,工程静态总投资达2.2亿元,设计装机容量2.8万千瓦,\",\"JCSSLX\":\"12\",\"JCSSMC\":\"虎家崖电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.3534971506730,33.78123004632965],\"type\":\"Point\"},\"properties\":{\"BSM\":75,\"JBQK\":\"锁儿头(沙川坝)水电站工程位于甘肃省舟曲县县城上游约500m的白龙江右岸,两河口~郎木寺公路(S313省道)从坝址左岸通过,该省道向西经迭部县与213国道相连,向东与212国道相连,距兰州市公路里程407\",\"JCSSLX\":\"12\",\"JCSSMC\":\"锁儿头发电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.7654504269956,33.83040199005984],\"type\":\"Point\"},\"properties\":{\"BSM\":76,\"JBQK\":\"位于甘肃省甘南藏族自治州迭部县\",\"JCSSLX\":\"12\",\"JCSSMC\":\"阿夏电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.9943586283481,33.91500910918308],\"type\":\"Point\"},\"properties\":{\"BSM\":77,\"JBQK\":\"位于甘肃省甘南州舟曲县曲瓦乡城马村\",\"JCSSLX\":\"12\",\"JCSSMC\":\"曲瓦乡发电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5059104102373,33.97529093303018],\"type\":\"Point\"},\"properties\":{\"BSM\":78,\"JBQK\":\"卡坝班九水电站位于迭部县白龙江干流,距卡坝乡政府1公里处,设计总装机容量3000KW,总投资2177万元,年平均发电量1662.7万千瓦时.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"卡坝班九水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.6491870290582,35.19216698222050],\"type\":\"Point\"},\"properties\":{\"BSM\":79,\"JBQK\":\"白土坡水电站位于夏河县达麦乡境内的大夏河上,于1968年10月建成发电,总装机容量4.6兆瓦.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"白土坡水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.7301140360340,35.21716164649286],\"type\":\"Point\"},\"properties\":{\"BSM\":80,\"JBQK\":\"位于甘肃省甘南州夏河县王格尔塘镇浦黄村,\",\"JCSSLX\":\"12\",\"JCSSMC\":\"浦黄电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.4417892852474,34.58716875743227],\"type\":\"Point\"},\"properties\":{\"BSM\":81,\"JBQK\":\"多架山水电站位于洮河中游的卓尼县大族乡多架山村,距卓尼县城约8km,现已建成运行.由池洪冲砂闸、溢流坝、副坝组成,最大坝高9m,最大流量61m3/s,总装机7.51NW.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"多架山电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.2416859094772,34.63837492446322],\"type\":\"Point\"},\"properties\":{\"BSM\":82,\"JBQK\":\"鹿儿台水电站位于甘南州临潭县术步乡立子滩村附近的洮河干流上 ,坝址位于洮河中上游 ,距上游洮河下巴沟水文站 4 1.5 ,距下游岷县水文站133 ,坝址以上控制流域面积 10 84 1 .\",\"JCSSLX\":\"12\",\"JCSSMC\":\"鹿儿台水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.2847539170597,34.61643684538416],\"type\":\"Point\"},\"properties\":{\"BSM\":83,\"JBQK\":\"术布水电站位于甘肃省甘南藏族自治州临潭县术布乡术布村附近的洮河干流上,为低坝径流(无调节)引水式电站.电站最大水头6.98m,加权平均水头6.49m,最小水头5.34m,设计水头6m;电站总装机容量3900kW,\",\"JCSSLX\":\"12\",\"JCSSMC\":\"术布水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.4087286734896,34.58239890168801],\"type\":\"Point\"},\"properties\":{\"BSM\":84,\"JBQK\":\"扭子水电站位于甘肃省甘南藏族自治州卓尼县城上游的洮河干流上,为一径流引水式电站.引水隧洞长4393.114m,设计引用流量138.6m3/s,电站装机容量30MW,为单一发电工程,无灌溉、防洪等要求.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"纽子水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.7892198632062,34.54589877251038],\"type\":\"Point\"},\"properties\":{\"BSM\":85,\"JBQK\":\"大庄水电站位于甘肃省甘南藏族自治州碌曲县境内的洮河干流上,为低坝无调节引水式电站,安装2台5.5MW轴流式发电机组,总装机容量11MW\",\"JCSSLX\":\"12\",\"JCSSMC\":\"大庄水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.6316328661639,34.54156469765275],\"type\":\"Point\"},\"properties\":{\"BSM\":86,\"JBQK\":\"阿拉山水电站位于甘肃甘南藏族自治州碌曲县西仓乡境内的洮河干流上,设计总装机容量９０００千瓦（3×3000千瓦）,年发电量４８１８万千瓦时.该工程是２００１年６月开工兴建的,是洮河水电\",\"JCSSLX\":\"12\",\"JCSSMC\":\"阿拉山水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.2451973366878,34.04756068737589],\"type\":\"Point\"},\"properties\":{\"BSM\":87,\"JBQK\":\"位于甘南藏族自治州迭部县\",\"JCSSLX\":\"12\",\"JCSSMC\":\"亚古电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.9884498290964,34.67058109034508],\"type\":\"Point\"},\"properties\":{\"BSM\":88,\"JBQK\":\"位于甘肃省甘南藏族自治州合作市勒秀乡峡村的洮河干流上,为低坝径流引水式水电站,技术改造后总装机容量为19MW,年发电量6512万kW.h,主要建设内容为将原有3×2000kW机组更换为3×3000kW机组,并新增\",\"JCSSLX\":\"12\",\"JCSSMC\":\"峡村水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.0576034244585,34.67870895255618],\"type\":\"Point\"},\"properties\":{\"BSM\":89,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"俄合水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.6359193154241,34.56453239255592],\"type\":\"Point\"},\"properties\":{\"BSM\":90,\"JBQK\":\"潭县青石山水电有限责任公司,2004年01月10日成立,经营范围包括水力发电、水产养殖、旅游资源开发等.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"青石山水电公司\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.6792423457577,34.49817545942995],\"type\":\"Point\"},\"properties\":{\"BSM\":91,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"贡去乎电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.4870256744953,34.58047531118672],\"type\":\"Point\"},\"properties\":{\"BSM\":92,\"JBQK\":\"位于甘南藏族自治州碌曲县\",\"JCSSLX\":\"12\",\"JCSSMC\":\"碌曲电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5852820082582,34.49176185792345],\"type\":\"Point\"},\"properties\":{\"BSM\":93,\"JBQK\":\"位于甘肃省甘南州卓尼县木耳镇多坝村委会,\",\"JCSSLX\":\"12\",\"JCSSMC\":\"扎那一级电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.6430460721186,34.98615494369574],\"type\":\"Point\"},\"properties\":{\"BSM\":94,\"JBQK\":\"位于甘南藏族自治州临潭县冶力关镇冶力关镇池沟村\",\"JCSSLX\":\"12\",\"JCSSMC\":\"池沟电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.2015782684224,33.77090632386779],\"type\":\"Point\"},\"properties\":{\"BSM\":95,\"JBQK\":\"舟曲县瓜子沟一级水电站位于甘肃省甘南藏族自治州舟曲县瓜子沟村\",\"JCSSLX\":\"12\",\"JCSSMC\":\"瓜咱沟一级水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.2142356974196,33.78126141288396],\"type\":\"Point\"},\"properties\":{\"BSM\":96,\"JBQK\":\"舟曲县瓜子沟二级水电站位于甘肃省甘南藏族自治州舟曲县瓜子沟村\",\"JCSSLX\":\"12\",\"JCSSMC\":\"瓜咱沟二级水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.2281989321351,33.80081367601036],\"type\":\"Point\"},\"properties\":{\"BSM\":97,\"JBQK\":\"舟曲县瓜子沟三级水电站位于甘肃省甘南藏族自治州舟曲县瓜子沟村\",\"JCSSLX\":\"12\",\"JCSSMC\":\"瓜咱沟三级水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5424905026346,35.04662977824171],\"type\":\"Point\"},\"properties\":{\"BSM\":98,\"JBQK\":\"位于甘南藏族自治州迭部县西北约28公里\",\"JCSSLX\":\"12\",\"JCSSMC\":\"扎日塘玛电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.9024772799026,33.98565838862379],\"type\":\"Point\"},\"properties\":{\"BSM\":99,\"JBQK\":\"水泊峡水电站位于甘肃省迭部县县城下游约84km处,是白龙江尼什峡至沙川坝河段梯级开发规划的第七个梯级水电站.水泊峡开发方式为混合开发,电站的主要任务是发电.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"水泊峡水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.0799701709867,33.95936557995759],\"type\":\"Point\"},\"properties\":{\"BSM\":100,\"JBQK\":\"未知\",\"JCSSLX\":\"11\",\"JCSSMC\":\"黄河大桥\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.8949666368009,34.07655912170617],\"type\":\"Point\"},\"properties\":{\"BSM\":101,\"JBQK\":\"腊子口二级水电站装机容量5100千瓦,采用隧道引流方式,水电站设计引水流量为每秒8.7立方米,把河道中的水引入长3.8公里的隧道,依靠76米的落差发电.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"腊子口二级电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.8919201572895,34.12392186690067],\"type\":\"Point\"},\"properties\":{\"BSM\":102,\"JBQK\":\"腊子口一级水电站采用隧道引流方式,水电站设计引水流量为每秒10.8立方米,把河道中的水引入隧道,依靠落差发电.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"腊子口一级电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.7257800536066,33.94242900579405],\"type\":\"Point\"},\"properties\":{\"BSM\":103,\"JBQK\":\"多儿水电站位于甘肃省迭部县白龙江一级支流多儿河上;距迭部县县城下游65公里处,总装机32(3*10+1*2)兆瓦\",\"JCSSLX\":\"12\",\"JCSSMC\":\"多儿水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.2045288084578,34.08032226645946],\"type\":\"Point\"},\"properties\":{\"BSM\":104,\"JBQK\":\"位于甘南迭部县电尕镇\",\"JCSSLX\":\"12\",\"JCSSMC\":\"电尕电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.3733796819269,34.01175600033918],\"type\":\"Point\"},\"properties\":{\"BSM\":105,\"JBQK\":\"位于甘南藏族自治州迭部县\",\"JCSSLX\":\"12\",\"JCSSMC\":\"白云水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.5787353522547,34.10351562464643],\"type\":\"Point\"},\"properties\":{\"BSM\":106,\"JBQK\":\"位于甘南州碌曲县郎木寺镇\",\"JCSSLX\":\"12\",\"JCSSMC\":\"郎木寺镇水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.0300314749727,34.68383523031167],\"type\":\"Point\"},\"properties\":{\"BSM\":107,\"JBQK\":\"电站设计为低坝式引水式水电站,主要由枢纽大坝,引水涵洞、电站厂房三部分组成\",\"JCSSLX\":\"12\",\"JCSSMC\":\"安果水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.8494264929383,34.72623131122606],\"type\":\"Point\"},\"properties\":{\"BSM\":108,\"JBQK\":\"未知\",\"JCSSLX\":\"12\",\"JCSSMC\":\"吉仓水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[104.6099355774650,33.43264018759464],\"type\":\"Point\"},\"properties\":{\"BSM\":109,\"JBQK\":\"2014年经甘肃省电力公司批复同意建设,为优化电网结构,提高电网抗事故能力,丁子河口电站将110千伏丁字河口原有2×31.5兆伏安主变增容改换为2×63兆伏安,它也是甘南公司110千伏单台容量最大的主\",\"JCSSLX\":\"12\",\"JCSSMC\":\"丁子河口电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5449365488827,33.96203458612729],\"type\":\"Point\"},\"properties\":{\"BSM\":110,\"JBQK\":\"尼傲加尕水电站为坝后式电站,属Ⅳ等小(1)型工程,枢纽建筑物由左向右依次为左岸挡水副坝、两孔泄洪底孔坝段、三孔电站进水口坝段和右岸挡水副坝组成.装机容量12.9MW.设计发电流量63.3m3/s,冬\",\"JCSSLX\":\"12\",\"JCSSMC\":\"尼傲加尕水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[102.6208458399636,34.82472037624444],\"type\":\"Point\"},\"properties\":{\"BSM\":111,\"JBQK\":\"甘南夏河机场位于甘肃省甘南州夏河县库塞塘村附近,距离夏河县和合作市分别为72公里和56公里,是我国民用机场布局规划以及民航西北地区、甘肃省民航发展规划的重点项目之一.总用地约2600亩\",\"JCSSLX\":\"13\",\"JCSSMC\":\"夏河机场\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"},{\"geometry\":{\"coordinates\":[103.5773414082794,34.53718603620666],\"type\":\"Point\"},\"properties\":{\"BSM\":112,\"JBQK\":\"于2005年8月开工建设,经过不断优化,设计装机容量为（2630KW+630KW）引数量11.8m3/s,年平均发电量1361万KW.h装机年利用小时数5175h,电站属1V小型径流式水力发电站.\",\"JCSSLX\":\"12\",\"JCSSMC\":\"云江峡水电站\",\"UserID\":null,\"XZQDM\":\"623000\",\"YSDM\":\"1001010100\",\"YYSJ\":\"2015\",\"ly\":\"\"},\"type\":\"Feature\"}]";
		
		JSONArray array = JSONObject.parseArray(geo);
		
		for(int i = 0;i<array.size();i++) {
			JSONObject obj = array.getJSONObject(i).getJSONObject("properties");
			String name = obj.getString("JCSSMC");
			for (Map<String, Object> object : ret) {
				if(object.get("HY_NAME").equals(name)) {
					object.put("geo", array.getJSONObject(i).getJSONObject("geometry"));
					break;
				}
			}	
		}
		return ret;
	}
	
}








