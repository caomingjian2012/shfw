package com.common.plugins.tools;

import java.security.MessageDigest;

/**
 * 
 * MD5 加密算法
 *
 */
public class MD5Encrypt {
	/**
	 * 创建静态加密方法
	 * @param s
	 * @return
	 */
	public final static String md5Encrypt(String s) {
	    try {
	     byte[] btInput = s.getBytes();
	     MessageDigest mdInst = MessageDigest.getInstance("MD5");
	     mdInst.update(btInput);
	     byte[] md = mdInst.digest();
	     StringBuffer sb = new StringBuffer();
	     for (int i = 0; i < md.length; i++) {
	      int val = ((int) md[i]) & 0xff;
	      if (val < 16)
	       sb.append("0");
	      sb.append(Integer.toHexString(val));
	    
	     }
	     return sb.toString();
	    } catch (Exception e) {
	     return null;
	    }
	}
	
	public static String encode(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	
	public static void main(String args[]){
		
		//df5c504ff94804233f8e53c7d86c497d
		/*String json = "loginName 等我";
		String timestamp = "1375779509033";
		String md5Content = "123456";//json+timestamp+"aiqtao";
		String contentSign = MD5Encrypt.md5Encrypt(md5Content);
		String contentSign2 = MD5Encrypt.encode(md5Content);
		System.out.println(contentSign);
		System.out.println(contentSign2);*/
		
		String re = "1,很遗憾，您的账号经已获得过该应用的赠送";
		int index = re.indexOf(",");
		if(index>=0)
			System.out.println(re.substring(0, index));
		//
		
		
	}

}
