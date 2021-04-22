package com.jeesite.modules.kjfw.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.kjfw.constant.GlobConstant;

/**
* @Author Tony
*  2021年4月13日 上午11:15:19
*  Never give up 自定义切面类
*/
@Aspect
@Component
public class KjfwAspect {
	
	private final Logger logger = LoggerFactory.getLogger(KjfwAspect.class);
	
	//定义切入点 满足包内所有的方法
	@Pointcut("execution(public * com.jeesite.modules.kjfw.rest..*.*(..))")
	public void controlBackResult() {}
	
	//定义要执行的方法
	//@AfterReturning(returning="ret"  
	//        , pointcut="controlBackResult()")
	@Around("controlBackResult()")
	public JSONObject dosomething(ProceedingJoinPoint pjp) {
		System.out.println("已经进入执行。。。");
		JSONObject ret = new JSONObject();
		JSONObject params = new JSONObject();
		try {
			params = (JSONObject) pjp.proceed();
			params.put("code",GlobConstant.sucCode);
			params.put("message", GlobConstant.sucMessage);
		}catch(Throwable e) {
			System.out.println(e.getStackTrace());
			params.put("code", GlobConstant.errorCode);
			params.put("message", GlobConstant.errorMessage);
		}
		ret.put("data",params);
		System.out.println("执行结束。。。");
		return ret;
	}

}
