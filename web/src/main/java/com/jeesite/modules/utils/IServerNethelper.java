package com.jeesite.modules.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair; 
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class IServerNethelper extends HttpUtils {

	private String _url;
	private String uid;
	private String pwd;
	private String[] cooikeCache ;
	public IServerNethelper(String url,String uid,String pwd) {
		_url = url;
		this.uid = uid;
		this.pwd = pwd;
	}
	
	@Override
	public String getCookie() {
		//SimpleDateFormat sd = new SimpleDateFormat(DATE_FORMAT_FULL);
		if(null!=cooikeCache && cooikeCache.length==3) {
			
			//rememberMe="deleteMe", version:0, domain:212.64.11.103, path:/iserver, expiry:Wed Feb 26 11:28:23 CST 2020
			String expiry = cooikeCache[2].substring(cooikeCache[2].lastIndexOf("Expires")+8);
			
			try {
				Date dt = new Date(expiry);
				if(dt.getTime() > System.currentTimeMillis()) {
					return null;
				} 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String value = cooikeCache[1];
			value = value.substring(0, value.indexOf(";") + 1); 
			 return value;
		}
		
		return null;
	}
	
	public void login() {
		//http://212.64.11.103:8090/iserver/services/security/login.json?_t=1582771603660
		String url = _url + "/services/security/login.json?_t=" + System.currentTimeMillis();
		
		JSONObject json = new JSONObject();
		json.put("password",pwd);
		json.put("rememberme", false);
		json.put("username",uid);
		 
		if(null == getCookie()) {
			cooikeCache = GetLoginCookie(url,json.toJSONString()); 
		}
		getCookie();
		System.out.println(); 
	}
	
	public JSONArray reportWorkspace(String smwu) {
		String url = _url + "/manager/workspaces.rjson?_t=" + System.currentTimeMillis();
		smwu = smwu.replace("\\", "/");
		String json = "{\r\n" + 
				"	\"workspaceConnectionInfo\": \""+smwu+"\",\r\n" + 
				"	\"servicesTypes\": [\"RESTMAP\", \"RESTDATA\"],\r\n" + 
				"	\"isDataEditable\": true,\r\n" + 
				"	\"mapEditable\": true,\r\n" + 
				"	\"isMultiInstance\": false,\r\n" + 
				"	\"instanceCount\": 0,\r\n" + 
				"	\"dataProviderDelayCommitSetting\": {\r\n" + 
				"		\"enabled\": false\r\n" + 
				"	}\r\n" + 
				"}";
		
		String ret = sendPost(url,json);
		System.out.println(ret );
		try {
			return JSONArray.parseArray(ret);
		}catch(Exception err){
			return null;
		}
		 
	}
	
	public JSONObject deleteWorkspace(String smwu) {
				          
		String url = _url + "/manager/workspaces.rjson?_t=" + System.currentTimeMillis();
		smwu = smwu.replace("\\", "/");
		String json = "{\"workspaceConnectionInfo\":\""+smwu+"\"}";
		
		String ret = sendPost(url, json ,"PUT" );
		System.out.println(ret );
		try {
			return JSONObject.parseObject(ret);
		}catch(Exception err){
			return null;
		}
	}
	
	public static void main(String[]args) {
		
		IServerNethelper s = new IServerNethelper("http://212.64.11.103:8090/iserver","admin","123456");
		s.login();
		s.login();
		s.reportWorkspace("D:/reportTest/test.smwu");
		s.deleteWorkspace("D:/reportTest/test.smwu");
	}
	
	public String[] GetLoginCookie(String url,String json ) {
		List<String> ret = new ArrayList<String>();
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    HttpPost httpget = new HttpPost(url);
	    //httpget.setHeader("Cookie", cookieStr);
	    httpget.setHeader(
	            HttpHeaders.USER_AGENT,
	            "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
	    //httpget.setc
	    ///application/x-www-form-urlencoded; charset=UTF-8
	    try { 
            if (json != null && json.length() > 0) { 
            	httpget.setEntity(new StringEntity(json,ContentType.APPLICATION_FORM_URLENCODED));
            }
	    	CloseableHttpResponse response = httpclient.execute(httpget); 
	    	ret.add(
	    			EntityUtils.toString(response.getEntity())
            ); 
	        Header[] headers = response.getAllHeaders();
	        for (Header h : headers) {
	            String name = h.getName();
	            String value = h.getValue();
	            if ("Set-Cookie".equalsIgnoreCase(name)) { 
	            	//value = value.substring(0, value.indexOf(";") + 1);
	            	ret.add(value); 		
	            } 
	        }
	    } catch (Exception e) {
	        System.err.println(String.format("HTTP GET error %s",
	                e.getMessage()));
	    } finally {
	        try {
	            httpclient.close();
	        } catch (IOException e) { 
	        }
	    }
	    return ArrayUtils.toStringArray(ret.toArray());
	}
}
