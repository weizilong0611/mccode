package com.jeesite.modules.kjfw.constant;
/**
* @Author Tony
*  2021年4月13日 上午10:51:37
*  Never give up
*/
public interface GlobConstant {

	//返回状态码 成功：10000 失败：10001
	int sucCode=10000;
	int errorCode=10001;
	public String sucMessage = "数据执行成功！";
	public String errorMessage = "数据执行异常！";
	
	//缓存数据标识
	public String KEY_AREA_JSON = "KEY_AREA_JSON";
	
}
