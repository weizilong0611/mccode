package com.jeesite.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.jeesite.common.tests.BaseInitDataTests;
import com.jeesite.modules.Application;
import com.jeesite.modules.HackFiles;

@ActiveProfiles("test")
@SpringBootTest(classes=Application.class) 
public class HackTest extends BaseInitDataTests  {
	@Test
	public void initStep01() throws Exception{
		HackFiles.main(null);
	}
}
