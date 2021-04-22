/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.test;

import com.jeesite.common.tests.BaseInitDataTests;
import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.Application;
import com.jeesite.modules.HackFiles;
//import com.jeesite.modules.filemanager.db.InitFilemanagerData;
import com.jeesite.modules.sys.db.InitCoreData;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 初始化数据表
 * @author ThinkGem
 * @version 2019-12-30
 */
@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
public class InitData extends BaseInitDataTests {

	@Test
	public void initStep01() throws Exception{
		if(true)return;
		//initCoreData();			// 核心模块
	}
	
	@Test
	public void initStep02() throws Exception{
		if(true)return;
		//initFilemanagerData();	// 文件管理模块
	}
	
	/**
	 * 初始化核心模块表及数据
	 */
	public void initCoreData() throws Exception{
		
		HackFiles.main(null);
		
		if(true)return;
		// InitCoreData data = SpringUtils.getBean(InitCoreData.class);
		InitCoreData data = SpringUtils.getBean(InitCoreData.class);
		data.createTable();
		data.initLog();
		data.initArea("3700","3701","3702");
		data.initConfig();
		data.initModule();
		data.initDict();
		data.initRole();
		data.initMenu();
		data.initUser();
		data.initOffice();
		data.initCompany();
		data.initPost();
		data.initEmpUser();
		data.initMsgPushJob();
		data.initGenTestData();
		data.initGenTreeData();
	}
	
	/**
	 * 初始化文件管理模块表及数据
	 */
	/*
	 * public void initFilemanagerData() throws Exception{ if(true)return;
	 * InitFilemanagerData data = SpringUtils.getBean(InitFilemanagerData.class);
	 * data.createTable(); data.initFilemanagerFolder(); }
	 */

}
