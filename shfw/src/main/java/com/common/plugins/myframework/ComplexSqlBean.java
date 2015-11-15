package com.common.plugins.myframework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;
/**
 * 复杂SQL语句，容器
 * @author Administrator
 *
 */

public class ComplexSqlBean {
	
	
	
	private static Map<String,String> map = new HashMap<String,String>();
	
	private List<Map<String,String>> listMap;
	
	public void init(){
		for(Map<String,String> m:listMap){
			map.putAll(m);
		}
	}
	
	public static String getSql(String id){
		String sql =map.get(id);
		if(StringUtils.hasText(sql)){
			return sql.replace("\\s", " ");
		}else{
			throw new RuntimeException("未发现SQL语句："+id);
		}
		
	}


	
	public  Map<String, String> getMap() {
		return map;
	}


	public  void setMap(Map<String, String> map) {
		ComplexSqlBean.map = map;
	}



	public List<Map<String, String>> getListMap() {
		return listMap;
	}



	public void setListMap(List<Map<String, String>> listMap) {
		this.listMap = listMap;
	}
	
	
	

}
