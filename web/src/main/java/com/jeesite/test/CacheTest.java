package com.jeesite.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jeesite.modules.kjfw.cache.J2CacheUtils;
import com.jeesite.modules.kjfw.properties.J2CacheProperties;

import eu.bitwalker.useragentutils.Application;

/**
 * @Author Tony 2021年4月21日 上午10:20:31 Never give up
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.jeesite.*" })
@WebAppConfiguration
public class CacheTest {

	@Autowired
	J2CacheProperties j2cachePro;
	
	@Test public void cacheParam() {
		System.out.println(j2cachePro.channel+"----");
	}
	
	/*
	 * //配置缓存服务 如果一级缓存中有数据就从一级缓存获取 如果没有就从二级缓存中获取
	 * 
	 * @Test public void cachetest() { CacheChannel cache = J2Cache.getChannel(); if
	 * (cache != null) { J2CacheUtils.setKey("test",null); String element = (String)
	 * J2CacheUtils.getKey2Object("test"); if (element != null) {
	 * System.out.println(element.toString()+"----"+element); } } }
	 */
	 

	/*
	 * @Test public void caffinecachetest() { Caffeine<Object, Object> caffeine =
	 * Caffeine.newBuilder(); caffeine =
	 * caffeine.maximumSize(10000).expireAfterWrite(10, TimeUnit.SECONDS);
	 * Cache<String, Object> theCache = caffeine.build(); theCache.put("test",
	 * "测试方法"); if(theCache!=null) {
	 * System.out.println(theCache.getIfPresent("test")); } }
	 */

	/*
	 * @Test public void autoCachetest() { Cache<String, Object> manualCache =
	 * Caffeine.newBuilder() .expireAfterWrite(10, TimeUnit.MINUTES)
	 * .maximumSize(10_000) .build(); String key = "name"; // 根据key查询一个缓存，如果没有返回NULL
	 * Object graph = manualCache.getIfPresent(key); System.out.println(graph); //
	 * 根据Key查询一个缓存，如果没有调用createExpensiveGraph方法，并将返回值保存到缓存。 //
	 * 如果该方法返回Null则manualCache.get返回null，如果该方法抛出异常则manualCache.get抛出异常 graph =
	 * manualCache.get(key, k -> createExpensiveGraph(k));
	 * System.out.println(graph); // 将一个值放入缓存，如果以前有值就覆盖以前的值 manualCache.put(key,
	 * graph); // 获取缓存Map ConcurrentMap<String, Object> map = manualCache.asMap();
	 * System.out.println(map); // 删除一个缓存 manualCache.invalidate(key); }
	 */

	// 自定义方法
	private Object createExpensiveGraph(String k) {
		return k;
	}

	/*
	 * //缓存同步加载
	 * 
	 * @Test public void syncCachetest() {
	 * 
	 * LoadingCache<String, Object> loadingCache = Caffeine.newBuilder()
	 * .maximumSize(10_000) .expireAfterWrite(10, TimeUnit.MINUTES) .build(key ->
	 * createExpensiveGraph(key));
	 * 
	 * String key = "nametest"; // 采用同步方式去获取一个缓存和上面的手动方式是一个原理。在build
	 * Cache的时候会提供一个createExpensiveGraph函数。 // 查询并在缺失的情况下使用同步的方式来构建一个缓存 Object graph
	 * = loadingCache.get(key);
	 * 
	 * // 获取组key的值返回一个Map List<String> keys = new ArrayList<>(); keys.add(key);
	 * Map<String, Object> graphs = loadingCache.getAll(keys);
	 * System.out.println(graphs);
	 * 
	 * }
	 */

	/*
	 * //异步加载缓存
	 * 
	 * @Test public void asyncCachetest() {
	 * 
	 * AsyncLoadingCache<String, Object> asyncLoadingCache = Caffeine.newBuilder()
	 * .maximumSize(10_000) .expireAfterWrite(10, TimeUnit.MINUTES) // Either: Build
	 * with a synchronous computation that is wrapped as asynchronous
	 * .buildAsync(key -> createExpensiveGraph(key)); // Or: Build with a
	 * asynchronous computation that returns a future // .buildAsync((key, executor)
	 * -> createExpensiveGraphAsync(key, executor)); String key = "nametest2"; //
	 * 查询并在缺失的情况下使用异步的方式来构建缓存 CompletableFuture<Object> graph =
	 * asyncLoadingCache.get(key); // 查询一组缓存并在缺失的情况下使用异步的方式来构建缓存 List<String> keys =
	 * new ArrayList<>(); keys.add(key); CompletableFuture<Map<String, Object>>
	 * graphs = asyncLoadingCache.getAll(keys);
	 * 
	 * // 异步转同步 loadingCache = asyncLoadingCache.synchronous();
	 * 
	 * try { System.out.println(graphs.get()); } catch (InterruptedException |
	 * ExecutionException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

}
