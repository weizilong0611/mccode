package com.jeesite.modules.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List; 

public class ClassUtils {

	public static Object callMethod(Object target,Class cls,String methodName,List<Class> argsType,Object[] args) throws Exception {
		List<Class> ls = new ArrayList();
		for (Object class1 : args) {
			ls.add(class1.getClass());
		}
		Class[] c = new Class[argsType.size()]; 
		argsType.toArray(c); 
		Method md = null;
		try {
			try {
				md = cls.getDeclaredMethod(methodName,c );
			}catch(Exception err){
				err.addSuppressed(err);
				md = cls.getMethod(methodName, c);
			}
			md.setAccessible(true);
		}catch(Exception err) {
			err.addSuppressed(err);
		}
		return md.invoke(target, args);
	}
	
	public static Object callMethod(Object target,String className,String methodName,List<Class> argsType,Object[] args) throws Exception {
		
		return callMethod(target,Class.forName(className), methodName,argsType,args);
		
		
	}
	
}
