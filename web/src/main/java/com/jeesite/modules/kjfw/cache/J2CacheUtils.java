package com.jeesite.modules.kjfw.cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jeesite.modules.kjfw.properties.J2CacheProperties;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;

/**
* @Author Tony
*  2021年4月21日 下午2:58:28
*  Never give up
*/
@Component
public class J2CacheUtils {
	
	@Autowired
	J2CacheProperties j2cachePro;
	
	private static CacheChannel cacheChannel;
	
	static {
		cacheChannel = J2Cache.getChannel();
	}
	
	//设置key所对应的value的值
	public void setKey(String key,Object obj) {
		if(cacheChannel!=null) {
			cacheChannel.set(j2cachePro.channel, key, obj);
		}
	}
	
	//获取key所对应的值
	public JSONObject getKey2JSONObject(String key) {
		if(cacheChannel!=null) {
			return JSONObject.parseObject((String) cacheChannel.get(j2cachePro.channel, key).getValue());
		}
		return null;
	}
	
	//获取key所对应的值
	public Object getKey2Object(String key) {
		if(cacheChannel!=null) {
			return cacheChannel.get(j2cachePro.channel, key).getValue();
		}
		return null;
	}

}
