package com.common.plugins;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

public class ConvertTools {
	
	/**
	 * 把“1, c,d ,s” =>["1","c","d","s"]
	 * \s匹配任意的空白符，包括空格，制表符(Tab)，换行符，但不匹配中文全角空格
	 * @param commaStr
	 */
	public static  String commaString2JsonStr(String commaStr){
		if(null == commaStr){
			return "[]";
		}
		commaStr = commaStr.replaceAll("[\\s\\p{Zs}]+", ""); //去除空白
		commaStr =commaStr.replaceAll("，", ","); //替换中文逗号
		String[] arr = commaStr.split(",");
		List<String> list = new ArrayList<String>();
		for(int i=0;i<arr.length ;i++){
			String s = arr[i].toString();
			if(StringUtils.hasText(s)){
				list.add(s);
			}
		}
		
		
		return JSON.toJSONString(list);
		
		
	}
	/**
	 * ["1","c","d","s"]=>1,c,d,s
	 * @param jsonStr
	 * @return
	 */
	public static  String jsonStr2commaString(String jsonStr){
		List<String> list = JSON.parseArray(jsonStr, String.class);
		return StringUtils.arrayToCommaDelimitedString(list.toArray());
		
		
	}

	
	public static void main(String[] args) {
		String str1 = "1, c， d ,s,";
		
		String json = commaString2JsonStr(str1);
		System.out.println(json);
		
		System.out.println(jsonStr2commaString(json));
	}
}
