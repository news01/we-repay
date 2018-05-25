/******************************************************************************
 * Copyright (C) 2016  ShenZhen InnoPro Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳市精华隆安防设备有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.util.json;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.util.JSONUtils;

/**
 * @ClassName: JSONUtil
 * @version 1.0
 * @Desc: JSON格式化工具类
 * @author huaping hu
 * @date 2016年5月12日下午6:16:04
 * @history v1.0
 *
 */
public class JSONUtil {

	// 静态代码块
	static {

		// 注册时间格式
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd", "yyyy-MM-dd HH",
						"yyyy-MM-dd HH:mm" }));
	}

	/**
	 * 描述：返回时间格式Config
	 * 
	 * @author huaping hu
	 * @date 2016年5月13日上午8:58:47
	 * @return
	 */
	public static JsonConfig getDateConfig1111() {

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		jsonConfig.registerDefaultValueProcessor(String.class,
				new DefaultValueProcessor() {
					@SuppressWarnings("rawtypes")
					@Override
					public Object getDefaultValue(Class arg0) {
						return null;
					}
				});
		return jsonConfig;
	}

	/**
	 * 描述：返回时间格式Config
	 * 
	 * @author huaping hu
	 * @date 2016年5月13日上午8:58:47
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static JsonConfig formatNullConfig() {

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		jsonConfig.registerDefaultValueProcessor(String.class,
				new DefaultValueProcessor() {
					@Override
					public Object getDefaultValue(Class arg0) {
						return null;
					}
				});
		jsonConfig.registerDefaultValueProcessor(Integer.class,
				new DefaultValueProcessor() {
					@Override
					public Object getDefaultValue(Class arg0) {
						return null;
					}
				});
		jsonConfig.registerDefaultValueProcessor(Short.class,
				new DefaultValueProcessor() {
					@Override
					public Object getDefaultValue(Class arg0) {
						return null;
					}
				});
		jsonConfig.registerDefaultValueProcessor(Long.class,
				new DefaultValueProcessor() {
					@Override
					public Object getDefaultValue(Class arg0) {
						return null;
					}
				});
		jsonConfig.registerDefaultValueProcessor(Double.class,
				new DefaultValueProcessor() {
					@Override
					public Object getDefaultValue(Class arg0) {
						return null;
					}
				});
		jsonConfig.registerDefaultValueProcessor(BigDecimal.class,
				new DefaultValueProcessor() {
					@Override
					public Object getDefaultValue(Class arg0) {
						return null;
					}
				});

		return jsonConfig;
	}

	/**
	 * 
	 * 描述： 对象转JSON数据
	 * 
	 * @author huaping hu
	 * @date 2016年5月12日下午6:22:27
	 * @param object
	 * @return
	 */
	public static String toJSON(Object object) {
		return JSONObject.fromObject(object).toString();
	}

	/**
	 * 
	 * 描述： 对象转JSON数据 带CONFIG
	 * 
	 * @author huaping hu
	 * @date 2016年5月12日下午6:22:27
	 * @param object
	 * @return
	 */
	public static String toJSON(Object object, JsonConfig config) {
		return JSONObject.fromObject(object, config).toString();
	}
	
	/**
	 * 
	 * 描述：格式化详情（""-null,date-yyyyMMdd hh:mm:ss）
	 * @author John Gu 
	 * @date 2016年5月19日上午8:22:29
	 * @param object
	 * @return
	 */
	public static String toFormatJSON(Object object) {
		return JSONObject.fromObject(object, JSONUtil.formatNullConfig()).toString();
	}

	/**
	 * 描述：数组转JOSN数据
	 * 
	 * @author huaping hu
	 * @date 2016年5月13日上午10:05:23
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String toFormatJSON(List list) {
		return JSONArray.fromObject(list, JSONUtil.formatNullConfig()).toString();
	}
	
	/**
	 * 描述：数组转JOSN数据
	 * 
	 * @author huaping hu
	 * @date 2016年5月13日上午10:05:23
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String toJSON(List list) {
		return JSONArray.fromObject(list).toString();
	}

	/**
	 * 描述：数组转JOSN数据
	 * 
	 * @author huaping hu
	 * @date 2016年5月13日上午10:05:23
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String toJSON(List list, JsonConfig config) {
		return JSONArray.fromObject(list, config).toString();
	}

	/**
	 * 
	 * 描述： JSON数据转对象
	 * 
	 * @author huaping hu
	 * @date 2016年5月12日下午6:22:27
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String data, Class<T> cla) {
		return (T) JSONObject.toBean(JSONObject.fromObject(data), cla);
	}

	/**
	 * 
	 * 描述： JSON数据转对象
	 * 
	 * @author huaping hu
	 * @date 2016年5月12日下午6:22:27
	 * @param object
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T toBean(String data, Class<T> cla,
			Map<String, Class> classMap) {
		return (T) JSONObject
				.toBean(JSONObject.fromObject(data), cla, classMap);
	}

	/**
	 * 
	 * 描述： JSON数据List对象
	 * 
	 * @author huaping hu
	 * @date 2016年5月12日下午6:22:27
	 * @param object
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static <T> List toList(String data, Class<T> cla) {
		return (List) JSONArray.toCollection(JSONArray.fromObject(data), cla);
	}

	/**
	 * 
	 * 描述：生成JSONObject
	 * @author huaping hu 
	 * @date 2016年10月20日下午5:23:21
	 * @param data
	 * @return
	 */
	public static JSONObject toJSONObject(Object data) {
		
		return JSONObject.fromObject(data);
	}
	public static void main(String[] args) {


	}
}
