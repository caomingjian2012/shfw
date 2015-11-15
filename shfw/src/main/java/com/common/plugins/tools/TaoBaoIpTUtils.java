package com.common.plugins.tools;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class TaoBaoIpTUtils {
 public static 	Logger logger = Logger.getLogger(TaoBaoIpTUtils.class);
	public static String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
	/**
	 * 获取6位中国省份编码
	 * @param ip
	 * @return
	 */
	public static Integer getRegion(String ip){
		if(ip.startsWith("127.0.0")||ip.startsWith("192.168")){
			
		
			
			return 440000;
		}
		//return 440000;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urlStr+"?ip="+ip);
		try{
			HttpResponse response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity(),
						"utf-8");
				System.out.println(content);
				TaoBaoIpDto ipdto = JSON.parseObject(content,TaoBaoIpDto.class);
				if(ipdto.getCode()==0&&ipdto.getData().getCountry_id().equals("CN")){
					Integer region = Integer.parseInt(ipdto.getData().getRegion_id());
					return region;
				}
				return -1;
				
			}
		}catch(Exception e){
			logger.error("查询IP所在地区失败。IP："+ip+" ");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 获取IP信息
	 * @param ip
	 * @return
	 */
	public static TaoBaoIpInfo getIpInfo(String ip){
		if(ip.startsWith("127.0.0")||ip.startsWith("192.168")){
			TaoBaoIpInfo ipInfo = new TaoBaoIpInfo();
			ipInfo.setCountry("CN");
			ipInfo.setRegion_id("440000");
			ipInfo.setCity_id("440300");
			ipInfo.setCounty("440307");
			return ipInfo;
		}
		//return 440000;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(urlStr+"?ip="+ip);
		try{
			HttpResponse response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity(),
						"utf-8");
				System.out.println(content);
				TaoBaoIpDto ipdto = JSON.parseObject(content,TaoBaoIpDto.class);
				if(ipdto.getCode()==0){
					
					return ipdto.getData();
				}
				
				
			}
		}catch(Exception e){
			logger.error("查询IP所在地区失败。IP："+ip+" ");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		
		
		TaoBaoIpTUtils.getRegion("58.60.36.195");
	}

}



