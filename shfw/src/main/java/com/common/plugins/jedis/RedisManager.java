package com.common.plugins.jedis;

import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {
	Logger logger = Logger.getLogger(RedisManager.class);
	private String host = "127.0.0.1";
	
	private int port = 6379;
	private int dbindex =0;
	// 0 - never expire
	private int expire = 30;
	private int timeout = 2000;
	
	private  JedisPool jedisPool = null;
	
	public RedisManager(){
		
	}
	
	public int getDbindex() {
		return dbindex;
	}

	public void setDbindex(int dbindex) {
		this.dbindex = dbindex;
	}

	/**
	 * 初始化方法
	 */
	public void init(){
		if(jedisPool == null){
			jedisPool = new JedisPool(new JedisPoolConfig(), host, port,timeout,"",dbindex);
		}
	}
	
	/**
	 * get value from redis
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key){
		logger.debug("getkey:"+new String(key));
		byte[] value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.get(key);
			
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 返回指定hash的field数量
	 * @param key
	 * @return
	 */
	public Long hlen(String key){
		Long value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.hlen(key);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 获取指定的hash field  
	 * @param key
	 * @return
	 */
	public String hget(String key,String value){
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.hget(key,value);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 设置hash field为指定值，如果key不存在，则先创建  
	 * @param key
	 * @return
	 */
	public Long hset(String key,String value1,String value2){
		Long value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.hset(key,value1,value2);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 添加一个string元素到,key对应的set集合中，成功返回1,如果元素以及在集合中返回0,key对应的set不存在返回错误  
	 * @param key
	 * @return
	 */
	public Long sadd(String key,String value1){
		Long value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.sadd(key,value1);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 为key指定过期时间，单位是秒。返回1成功，0表示key已经设置过过期时间或者不存在
	 * @param key
	 * @return
	 */
	public Long expire(String key,int value1){
		Long value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.expire(key,value1);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 判断member是否在set中
	 * @param key
	 * @return
	 */
	public Boolean sismember(String key,String value1){
		Boolean value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.sismember(key,value1);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 从key对应set中移除给定元素，成功返回1，如果member在集合中不
	 * @param key
	 * @return
	 */
	public Long srem(String key,String value1){
		Long value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.srem(key,value1);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value){
		
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.set(key,value);
			if(this.expire != 0){
				jedis.expire(key, this.expire);
		 	}
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value,int expire){
		
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.set(key,value);
			if(expire != 0){
				jedis.expire(key, expire);
		 	}
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * del
	 * @param key
	 */
	public void del(byte[] key){
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.del(key);
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * flush
	 */
	public void flushDB(){
		Jedis jedis = jedisPool.getResource();
		try{
			jedis.flushDB();
		}finally{
			jedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * size
	 */
	public Long dbSize(){
		Long dbSize = 0L;
		Jedis jedis = jedisPool.getResource();
		try{
			dbSize = jedis.dbSize();
		}finally{
			jedisPool.returnResource(jedis);
		}
		return dbSize;
	}

	/**
	 * keys
	 * @param regex
	 * @return
	 */
	public Set<byte[]> keys(String pattern){
		Set<byte[]> keys = null;
		Jedis jedis = jedisPool.getResource();
		try{
			keys = jedis.keys(pattern.getBytes());
			
			
		}finally{
			jedisPool.returnResource(jedis);
		}
		return keys;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}
	
	
	
	public static void main(String[] args) {
		RedisManager manager =new RedisManager();
		manager.setHost("192.168.1.166");
		manager.init();
//		for(int i=0;i<100000;i++){
//			//BoardItem item = new BoardItem(i+"", "clientId"+i, i, 8, 0);
//			String item = i +"|"+"clientId"+i;
//			manager.zadd("test", i, item);
//		}
		
		
		System.out.println(manager.zrevrange("test",0,100));
		System.out.println(manager.zrange("test",0,100));
		
	}
	
	/**
	 * 有序SET 添加
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public Long zadd(String key,double score,String member){
		Long value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.zadd(key, score, member);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	
	public Long zrem(String key,String member){
		Long value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.zrem(key, member);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	public Set<String> zrevrange(String key,long start,long  end){
		Set<String> value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.zrevrange(key, start, end);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	
	public Set<String> zrange(String key,long start,long  end){
		Set<String> value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.zrange(key, start, end);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	public Long zrank(String key,String member){
		Long value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.zrank(key, member);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	
	public Long zrevrank(String key,String member){
		Long value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.zrevrank(key, member);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	public Long zcard(String key){
		Long value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.zcard(key);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	
	public Set<redis.clients.jedis.Tuple> zrangeWithScores(String key,long start,long  end){
		Set<redis.clients.jedis.Tuple> value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.zrangeWithScores(key, start, end);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	public Set<redis.clients.jedis.Tuple> zrevrangeWithScores(String key,long start,long  end){
		Set<redis.clients.jedis.Tuple> value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.zrevrangeWithScores(key, start, end);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
	
	
	public Set<String> zrevrangeByScore(String key,double max,double min,int offset ,int limit){
		Set<String> value = null;
		Jedis jedis = jedisPool.getResource();
		try{
			value = jedis.zrevrangeByScore(key, max, min, offset, limit);
		}finally{
			jedisPool.returnResource(jedis);
		}
		return value;
	}
}
