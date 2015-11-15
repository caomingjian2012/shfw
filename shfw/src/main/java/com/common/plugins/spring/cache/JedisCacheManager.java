package com.common.plugins.spring.cache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.common.plugins.jedis.RedisManager;
import com.common.plugins.shiro.RedisCache;

public class JedisCacheManager implements CacheManager {
	private static final Logger logger = Logger.getLogger(JedisCacheManager.class);
	
	// fast lookup by name map
	private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	
	private RedisManager redisManager;

	@Override
	public Cache getCache(String name) {
		logger.debug("获取名称为: " + name + " 的RedisCache实例");
		Cache c = caches.get(name);
		if(c==null){
			
			redisManager.init();
			RedisCache redisCache= new RedisCache(redisManager);
			c = new JedisCache(redisCache,name);
			caches.put(name, c);
		}
		return c;
	}

	@Override
	public Collection<String> getCacheNames() {
		return caches.keySet();
	}

	public RedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}

	public static void main(String[] args) {
		RedisManager manager =new RedisManager();
		manager.setHost("192.168.1.166");
		
		
		JedisCacheManager cacheManager =new JedisCacheManager();
		cacheManager.setRedisManager(manager);
	
		cacheManager.getCache("").put("a", "basdfa");
		
		System.out.println(cacheManager.getCache("").get("a").get());
		
	}
	
}
