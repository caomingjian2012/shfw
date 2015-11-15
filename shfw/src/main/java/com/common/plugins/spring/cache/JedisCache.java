package com.common.plugins.spring.cache;

import org.apache.log4j.Logger;
import org.springframework.cache.Cache;

import com.common.plugins.serialize.SerializeUtils;
import com.common.plugins.shiro.RedisCache;
/**
 * SPRING cache 的实现
 * @author Administrator
 *
 */
public class JedisCache implements Cache{
	private Logger logger = Logger.getLogger(JedisCache.class);
	private  String name = "default";
	
	
	RedisCache<Object, Object> cache;
	
	public JedisCache(RedisCache<Object, Object> cache) {
		this(cache,"default");
	}
	
	public JedisCache(RedisCache<Object, Object> cache ,String name) {
		this.name = name;
		if (cache == null) {
	         throw new IllegalArgumentException("Cache argument cannot be null.");
	     }
	     this.cache = cache;
	}

	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public Object getNativeCache() {
		
		return this.cache;
	}

	@Override
	public ValueWrapper get(Object key) {
		Object value = this.cache.get(key);
		return value==null?null:new V(value);
	}

	@Override
	public void put(Object key, Object value) {
		this.cache.put(key, value);
	}

	@Override
	public void evict(Object key) {
		this.cache.remove(key);
		
	}

	@Override
	public void clear() {
		
		
	}

	
	/**
	 * 获得byte[]型的key
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(Object key){
		if(key instanceof String){
			String preKey = (String) key;
    		return preKey.getBytes();
    	}else{
    		return SerializeUtils.serialize(key);
    	}
	}
	
	public class V implements ValueWrapper{
		Object value;
		
		public V(Object obj){
			this.value = obj;
		}

		@Override
		public Object get() {
			
			return value;
		}
		
	}
}
