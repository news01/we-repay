
package com.we.repay.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * @ClassName: SpringUtil
 * @version 1.0
 * @Desc: 获取Spring Bean
 * @author John Gu
 * @date 2016年4月21日下午6:13:00
 * @history v1.0 
 */
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext(){
		return context;
	}
	
	public static Object getBean(String id){
		return context.getBean(id);
	}
}
