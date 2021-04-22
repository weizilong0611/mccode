package com.jeesite.modules.kjfw.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
* @Author Tony
*  2021年4月22日 上午8:55:04
*  Never give up
*/
@Component
@ConfigurationProperties(prefix="redis")
@PropertySource(value="classpath:/config/j2cache.properties")
public class J2CacheProperties {

	public static String channel;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
