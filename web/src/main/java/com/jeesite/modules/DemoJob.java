package com.jeesite.modules;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DemoJob{
	
	public static void test(int a,int b) {
		 
		System.out.println("Job Called...");
	}
	
}