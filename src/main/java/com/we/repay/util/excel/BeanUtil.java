package com.we.repay.util.excel;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;



/**
 * bean工具类
 */
public class BeanUtil {
	
	private static Logger logger =  Logger.getLogger(BeanUtil.class);
	
	/**
	 * 将bean的属性值转为bean2的
	 * @param bean
	 * @param bean2
	 */
	public static void beanTOBean(Object bean,Object bean2){
		if(bean == null || bean2 == null) {
			return;
		}
		try {
			PropertyDescriptor pds[] = Introspector.getBeanInfo(bean.getClass(),Object.class).getPropertyDescriptors();
			if(pds != null && pds.length > 0){
				PropertyDescriptor pd = null;
				String field = null;
				Class<?> type = null;
				Method rM = null;
				Method wM = null;
				Object value = null;
				Object[] args = new Object[0];
				for(int i=0;i<pds.length;i++){
					field = pds[i].getName();
					type = pds[i].getPropertyType();
					rM = pds[i].getReadMethod();
					value = rM.invoke(bean, args);
					if(value == null){
						continue;
					}
					try {
						pd = new PropertyDescriptor(field, bean2.getClass());
						if(type == pd.getPropertyType()){
							wM = pd.getWriteMethod();
							wM.invoke(bean2, value);
						}
					} catch (Exception e) {
						logger.error("bean2没有[" + field + "]属性");
					}
				}
			}
		} catch (Exception e) {
			logger.error("将bean的属性值转为bean2异常！" + e);
		}
	}
	
	/**
	 * 将Map的属性值转为bean的
	 * @param map
	 * @param bean
	 */
	public static void mapTOBean(Map<String, String> map,Object bean){
		if(map == null || bean == null) {
			return;
		}
		try {
			PropertyDescriptor pds[] = Introspector.getBeanInfo(bean.getClass(),Object.class).getPropertyDescriptors();
			if(pds != null && pds.length > 0){
				String field = null;
				Class<?> type = null;
				Method wM = null;
				String value = null;
				for(int i=0;i<pds.length;i++){
					field = pds[i].getName();
					type = pds[i].getPropertyType();
					value = map.get(field);
					wM = pds[i].getWriteMethod();
					if(value == null) {
						continue;
					}
					if(type == Integer.class || type == int.class) {
						wM.invoke(bean, ConvertUtil.strToInteger(value, -1));
					}else if(type == Double.class || type == double.class){
						wM.invoke(bean, ConvertUtil.strToDouble(value, -1));
					}else if(type == Long.class || type == long.class) {
						wM.invoke(bean, ConvertUtil.strToLong(value, -1));
					}else if(type == BigDecimal.class) {
						wM.invoke(bean, AmountUtils.objToBigDecimal(value));
					}else if(type == String.class) {
						wM.invoke(bean, String.valueOf(value));
					}else if(type == Date.class) {
						wM.invoke(bean, DateUtil.strToDate(value));
					}
				}
			}
		} catch (Exception e) {
			logger.error("将Map的属性值转为bean的异常！" + e);
		}
	}
	
	/**
	 * 将Map的属性值转为bean的
	 * @param map
	 * @param bean
	 */
	public static void mapTOBean2(Map<String, Object> map,Object bean){
		if(map == null || bean == null) {
			return;
		}
		try {
			PropertyDescriptor pds[] = Introspector.getBeanInfo(bean.getClass(),Object.class).getPropertyDescriptors();
			if(pds != null && pds.length > 0){
				String field = null;
				Method wM = null;
				Object value = null;
				for(int i=0;i<pds.length;i++){
					field = pds[i].getName();
					value = map.get(field);
					wM = pds[i].getWriteMethod();
					if(value == null) {
						continue;
					}
					wM.invoke(bean, value);
				}
			}
		} catch (Exception e) {
			logger.error("将Map的属性值转为bean的异常！" + e);
		}
	}
	
	/**
	 * 将bean的属性值转为map的
	 * @param bean
	 * @param bean2
	 */
	public static void beanTOMap(Object bean,Map<String, String> map){
		if(bean == null || map == null) {
			return;
		}
		try {
			PropertyDescriptor pds[] = Introspector.getBeanInfo(bean.getClass(),Object.class).getPropertyDescriptors();
			if(pds != null && pds.length > 0){
				String field = null;
				Class<?> type = null;
				Method rM = null;
				Object value = null;
				Object[] args = new Object[0];
				for(int i=0;i<pds.length;i++){
					field = pds[i].getName();
					type = pds[i].getPropertyType();
					rM = pds[i].getReadMethod();
					value = rM.invoke(bean, args);
					if(value == null){
						map.put(field, "");
						continue;
					}
					if(type == Date.class) {
						map.put(field, DateUtil.getDateTimeStr1((Date)value));
					} else {
						map.put(field, String.valueOf(value));
					}
				}
			}
		} catch (Exception e) {
			logger.error("将bean的属性值转为map异常！", e);
		}
	}
	
	/**
	 * 将bean的属性值转为map的
	 * @param bean
	 * @param bean2
	 */
	public static void beanTOMap2(Object bean,Map<String, Object> map){
		if(bean == null || map == null) {
			return;
		}
		try {
			PropertyDescriptor pds[] = Introspector.getBeanInfo(bean.getClass(),Object.class).getPropertyDescriptors();
			if(pds != null && pds.length > 0){
				String field = null;
				Class<?> type = null;
				Method rM = null;
				Object value = null;
				Object[] args = new Object[0];
				for(int i=0;i<pds.length;i++){
					field = pds[i].getName();
					type = pds[i].getPropertyType();
					rM = pds[i].getReadMethod();
					value = rM.invoke(bean, args);
					if(value == null){
						map.put(field, "");
						continue;
					}
					if(type == Date.class) {
						map.put(field, DateUtil.getDateTimeStr1((Date)value));
					} else {
						map.put(field, String.valueOf(value));
					}
				}
			}
		} catch (Exception e) {
			logger.error("将bean的属性值转为map异常！" + e);
		}
	}
	
	public static List<Map<String, String>> getListMap(List<? extends Object> list) {
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		if(list != null && list.size() > 0) {
			for(Object bean : list) {
				Map<String, String> map = new HashMap<String, String>();
				beanTOMap(bean, map);
				resultList.add(map);
			}
		}
		return resultList;
	}
	
	/**
	 * 根据属性获取值
	 * @param bean
	 * @param field
	 * @return
	 */
	public static Object getBeanValue(Object bean,String field){
		try {
			PropertyDescriptor pd = new PropertyDescriptor(field,
					bean.getClass());
			Method rM = pd.getReadMethod();
			Object[] args = new Object[0];
			return rM.invoke(bean, args);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取属性信息出错
	 * @param cls
	 * @return
	 */
	public static PropertyDescriptor[] getPropertyDescriptor(Class<?> cls){
		try {
			return Introspector.getBeanInfo(cls,Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			logger.error("获取属性信息异常！" + e);
		}
		return null;
	}
}
