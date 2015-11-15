package com.common.plugins.tools;

import org.springframework.util.StringUtils;

public class Tools {
	/**
	 * 去除掉字符串中的中文
	 * @param str
	 * @return
	 */
	public static String clearChinese(String str){
		if(!StringUtils.hasText(str))
			return str;
		try {
			str = str.replaceAll("[\\u4e00-\\u9fa5]", "");
		} catch (Exception e) {
			 
		}
		return str;
	}
}
