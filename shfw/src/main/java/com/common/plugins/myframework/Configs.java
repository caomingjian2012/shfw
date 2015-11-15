package com.common.plugins.myframework;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置变量
 * @author Administrator
 *
 */
public class Configs {
	private static Map<String,String> map = new HashMap<String,String>();

	public  Map<String, String> getMap() {
		return map;
	}

	public  void setMap(Map<String, String> map) {
		Configs.map = map;
	}
	
	public static String get(String key){
		 return map.get(key);
	}
	

}
