package com.common.plugins.tools;

import com.common.plugins.constants.Carrier;
/**
 * 移动运营商判断根据
 * @author Administrator
 *
 */
public class CarrierUtils {
	public static String  YIDONG_1="46000";
	public static String  YIDONG_2="46002";
	public static String  YIDONG_3="46007";
	
	public static String  LIANTONG_1="46001";
	public static String  LIANTONG_2="46006";
	public static String  LIANTONG_3="46010";
	
	
	public static String  DIANXI_1="46003";
	public static String  DIANXI_2="46005";
	public static String  DIANXI_3="46011"; //电信4G
	
	public static String  TIETONG_1="46020";
	
	
	public static Carrier qufen(String imsi){
		if(imsi.startsWith(YIDONG_1)
				||imsi.startsWith(YIDONG_2)
				||imsi.startsWith(YIDONG_3)){
			return Carrier.YIDONG;
		}
		if(imsi.startsWith(LIANTONG_1)
				||imsi.startsWith(LIANTONG_2)
				){
			return Carrier.LIANTONG;
		}
		if(imsi.startsWith(DIANXI_1)
				||imsi.startsWith(DIANXI_2) || imsi.startsWith(DIANXI_3)
				){
			return Carrier.DIANXI;
		}
		if(imsi.startsWith(TIETONG_1)){
			return Carrier.TIETONG;
		}
		return Carrier.QITA;
	}

}
