/******************************************************************************
 * Copyright (C) 2016  ShenZhen InnoPro Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳市精华隆安防设备有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: WebParamUtils
 * @version 1.0 
 * @Desc: web参数获取工具类
 * @author huaping hu
 * @date 2016年5月4日下午5:39:01
 * @history v1.0
 *
 */
public class WebParamUtils {

	/**
	 * 描述：获取Short数据
	 * @author huaping hu 
	 * @date 2016年5月4日下午5:43:09
	 * @param proptery
	 * @param request
	 * @return
	 */
	public static Short getShort(String proptery,HttpServletRequest request,Short defaultValue){
		
		String temp = request.getParameter(proptery);
		if(StringUtils.isNotEmpty(temp))
			return Short.valueOf(temp);
		return defaultValue;
	}
	
	public static Short getShort(String proptery,HttpServletRequest request){
		return getShort(proptery,request,null);
	}
	
	/**
	 * 描述：获取Integer数据
	 * @author huaping hu 
	 * @date 2016年5月4日下午5:43:09
	 * @param proptery
	 * @param request
	 * @return
	 */
	public static Integer getInteger(String proptery,HttpServletRequest request,Integer defaultValue){
		
		String temp = request.getParameter(proptery);
		if(StringUtils.isNotEmpty(temp))
			return Integer.valueOf(temp);
		return defaultValue;
	}
	
	public static Integer getInteger(String proptery,HttpServletRequest request){
		return getInteger(proptery,request,null);
	}
	
	/**
	 * 描述：获取long数据
	 * @author huaping hu 
	 * @date 2016年5月4日下午5:43:09
	 * @param proptery
	 * @param request
	 * @return
	 */
	public static Long getLong(String proptery,HttpServletRequest request,Long defaultValue){
		
		String temp = request.getParameter(proptery);
		if(StringUtils.isNotEmpty(temp))
			return Long.parseLong(temp);
		return defaultValue;
	}
	
	public static Long getLong(String proptery,HttpServletRequest request){
		return getLong(proptery,request,null);
	}
	
	/**
	 * 获取double数据
	 * @author huaping hu 
	 * @date 2016年8月15日下午5:09:18
	 * @param proptery
	 * @param request
	 * @param defaultValue
	 * @return
	 */
	public static Double getDouble(String proptery,HttpServletRequest request,Double defaultValue){
		
		String temp = request.getParameter(proptery);
		if(StringUtils.isNotEmpty(temp))
			return Double.parseDouble(temp);
		return defaultValue;
	}
	
	public static Double getDouble(String proptery,HttpServletRequest request){
		return getDouble(proptery,request,null);
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
	public static List<Long> toLongList(String proptery,HttpServletRequest request){
		
		List<Long> longList = new ArrayList<Long>();
		
		 String updateEventIds = request.getParameter(proptery);
		if(StringUtils.isNotEmpty(updateEventIds)){
			
			String[] split = updateEventIds.split(",");
			for (String lo : split) {
				if(StringUtils.isNotEmpty(lo)){
					longList.add(new Long(lo));
				}
			}
		}
		return longList;
	}
	
	/**
	 * 描述：获取String型数据
	 * @author huaping hu 
	 * @date 2016年5月4日下午5:43:09
	 * @param proptery
	 * @param request
	 * @return
	 */
	public static String getString(String proptery,HttpServletRequest request,String defaultValue){
		
		String temp = request.getParameter(proptery);
		if(StringUtils.isNotEmpty(temp))
			return temp;
		return defaultValue;
	}
	public static String getString(String proptery,HttpServletRequest request){
		return getString(proptery,request,null);
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
	public static List<String> toStringList(String proptery,HttpServletRequest request){
		
		return toStringList(proptery, request,false);
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
	public static List<String> toStringList(String proptery,HttpServletRequest request,boolean emptyJoin){
		
		List<String> strList = new ArrayList<String>();
		
		 String updateEventIds = request.getParameter(proptery);
		if(StringUtils.isNotEmpty(updateEventIds)){
			
			String[] split = updateEventIds.split(",");
			for (String lo : split) {
				
				if(emptyJoin || StringUtils.isNotEmpty(lo)){
					strList.add(lo);
				}
			}
		}
		return strList;
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
	public static List<Long> toLongIdList(String proptery,HttpServletRequest request){
		
		List<Long> strList = new ArrayList<Long>();
		
		 String updateEventIds = request.getParameter(proptery);
		if(StringUtils.isNotEmpty(updateEventIds)){
			
			String[] split = updateEventIds.split(",");
			for (String lo : split) {
				
				if( StringUtils.isNotEmpty(lo)){
					strList.add(Long.valueOf(lo));
				}
			}
		}
		return strList;
	}
}
