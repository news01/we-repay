/******************************************************************************
 * Copyright (C) 2016  ShenZhen InnoPro Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳市精华隆安防设备有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: StringUtil
 * @version 1.0
 * @Desc: TODO
 * @author John Gu
 * @date 2016年4月21日下午5:28:18
 * @history v1.0
 *
 */
public class StringUtil {

	/**
	 * 
	 * 描述：字符串集合
	 * 
	 * @author John Gu
	 * @date 2016年4月21日下午5:29:36
	 * @param value
	 * @return true-为空,false-非空
	 */
	public static boolean isEmpty(String value) {
		if (value == null || "".equals(value)) {
			return true;
		}
		return false;
	}

	public static String formatIntNumber(int num, int len) {
		return String.format("%0" + len + "d", num);
	}

	/**
	 * 追加字符串
	 * @author huaping hu 
	 * @date 2016年9月19日下午3:50:53
	 * @param str
	 * @param len
	 * @param appendStr
	 * @return
	 */
	public static String appendBeforeString(String str, int len,String appendStr) {
		
		if(str ==null || str.length() >= len) return str;
		
		int count = len - str.length();
		for (int i = 0; i < count  ; i++) {
			str  = appendStr + str;
		}
		return str;
	}
	
	/**
	 * 
	 * 描述：将字符串id数组【“1,2,3,4”】转成List集合
	 * @author huaping hu 
	 * @date 2016年6月7日上午10:36:37
	 * @param proptery
	 * @param request
	 * @return
	 */
	public static List<String> toStringList(String strTemp){
		
		List<String> strList = new ArrayList<String>();
		
		if(StringUtils.isNotEmpty(strTemp)){
			
			String[] split = strTemp.split(",");
			for (String lo : split) {
				if(StringUtils.isNotEmpty(lo)){
					strList.add(lo);
				}
			}
		}
		return strList;
	}
	
	public static void main(String[] args) {
		
//		System.out.println(Integer.toHexString(13));
//		System.out.println(StringUtil.appendBeforeString(Integer.toHexString(13), 8,"0"));
	}
}
