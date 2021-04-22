package com.jeesite.test;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jeesite.modules.utils.HttpUtils;

import eu.bitwalker.useragentutils.Application;


/**
* @Author Tony
*  2021年4月12日 上午11:23:26
*  Never give up
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@EnableAutoConfiguration  
@ComponentScan(basePackages= {"com.jeesite.*"})
@WebAppConfiguration   
public class KjfwApplicationTest {

	@Autowired
	private WebApplicationContext wac; //注入web环境，但是并不会启动Tomcat，所以很快
	private MockMvc mockMvc; //模拟web环境
	@Before //@Before会在每个@Test之前执行，在里面初始化模拟的web环境
	public void setup() {
	mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	
	/*
	 * @Test public void queryExpertDbOK() throws Exception { ResultActions result =
	 * mockMvc.perform(MockMvcRequestBuilders.get("/rest/kjfw/list")
	 * .contentType(MediaType.APPLICATION_JSON_UTF8))
	 * .andExpect(MockMvcResultMatchers.status().isOk());
	 * System.out.println(result.andReturn().getResponse().getContentAsString()); }
	 */
	
	
	
	
	/*
	 * @Test public void saveExpertDbOk(){ String jsonstr
	 * ="{'workingPlace':'01ferffwewferfergree','name':'测试字段'}"; ResultActions
	 * result; try { result =
	 * mockMvc.perform(MockMvcRequestBuilders.post("/rest/kjfw/save").contentType(
	 * MediaType.APPLICATION_JSON_UTF8).content(jsonstr));
	 * System.out.println(result.andReturn().getResponse().getContentAsString()); }
	 * catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
	 * } }
	 */
	 
	
	
	  //获取全国省市地区数据 http://10.40.11.213:9999/js/rest/area/json
	  @Test public void queryAreaDataOK() throws Exception { ResultActions result =
	  mockMvc.perform(MockMvcRequestBuilders.get("/rest/area/json")
	  .contentType(MediaType.APPLICATION_JSON_UTF8))
	  .andExpect(MockMvcResultMatchers.status().isOk());
	  System.out.println(result.andReturn().getResponse().getContentAsString()); }
	 
	 
	
	/*
	 * //获取全国省市地区数据 http://10.40.11.213:9999/js/rest/area/json postman进行测试
	 * 或者httpclient
	 * @Test public void queryAreaDataByHttpClient() throws Exception { HttpUtils
	 * utils = new HttpUtils(); String str =
	 * utils.sendGet("http://10.40.11.213:9999/js/rest/area/json",new HashMap());
	 * System.out.println(str); }
	 */
	 
	 
	
	
}
