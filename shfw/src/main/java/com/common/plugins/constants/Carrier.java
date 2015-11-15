package com.common.plugins.constants;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.util.CollectionUtils;

/**
 * 运营商
 * @author Administrator
 *
 */
public enum Carrier {
	YIDONG(1,"移动"),
	LIANTONG(2,"联通"),
	DIANXI(3,"电信"),
	TIETONG(4,"铁通"),
	QITA(5,"其他");
	
	
	private int value;
	private String comment;
	private Carrier(int value,String comment){
		this.value = value;
		this.comment =comment;
	}
	
	public int getValue(){
		return value;
		
	}
	public String getComment(){
		return comment;
	}
	
	@Override
	public String toString() {
		
		return comment;
	}

	public static Carrier get(int value){
		for (Carrier o:Carrier.values()) {
			if(o.value ==value){
				return o;
			}
		}
		return null;
	}
	
	public static Map<Integer,Carrier> getMap(){
		Carrier[] adTypes=Carrier.values();
		Map<Integer, Carrier> map =new TreeMap<Integer, Carrier>();
		for(Carrier o :adTypes){
			map.put(o.getValue(), o);
		}
		return map;
	}
	
	public static List<Carrier> getList(){
		Carrier[] adTypes=Carrier.values();
		
		return CollectionUtils.arrayToList(adTypes);
	}
	
}
