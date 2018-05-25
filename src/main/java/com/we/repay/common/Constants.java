package com.we.repay.common;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.we.repay.tps.common.TPSConstants;
import com.we.repay.util.io.PropertyUtil;


/**
 * @ClassName: Constants
 * @version 2.0
 * @Desc: 数据常量
 * @author Jack
 * @date 2017年5月24日上午10:28:28
 * @history v2.0
 */
public class Constants {

	private static final Logger logger = Logger.getLogger(Constants.class);
	
	/* 网站根目录 */
	public static  String BASE_PATH;	
	
	//微信公众号应用ID
	public static String INNOAPPID;
	
	//微信公众号JSSDK使用权限签名jsapi_ticket
	public static String INNOJSAPI_TICKET;
	
	public static final String USER_AUTH_KEY = "USER_INFO";// 用户登录会话key值session key
	
	public static final String USER_LOGIN_URL = "/login/tologin.do";//用户登录页面地址
	
	public static final String USER_WXAUTHORIZE_LOGIN_URL = "/user/towxauthorize.do";//微信二次自动登录授权页面

	public static final String AUTOLOGIN_REMINDER_URL = "/user/topaystatus.do";//微信自动登陆失败提示页面
	
	public static final String WXPAY_NOTIFY_URL = "/repay/notify.do";//异步接收微信支付结果通知的回调地址
	
	// 初始化环境变量 配置文件 config.properties
	public static Properties properties;
	
	public static final Integer RESULT0 = 0;// 成功
	
	public static final String P_CHANGE_VALIDCODE = "P_CHANGE_VALIDCODE";// 更改手机号码短信验证码前缀

	/* 抽奖的年份 */
	public static  String YEAR_CONFIG_NUMBER;	

	
	/**
	 * 静态加载
	 */	
	static {
		init();
	}
	
	/**
	 * 
	 * 描述：初始化环境变量
	 * @author John Gu 
	 * @date 2016年4月6日上午9:14:24
	 */
	private static void init() {
		properties = PropertyUtil.getInstance().getProperties("properties/config.properties");
		if(properties!=null){
			BASE_PATH = properties.getProperty("BASE_PATH");
			YEAR_CONFIG_NUMBER = properties.getProperty("YEAR_CONFIG_NUMBER");
			INNOAPPID = TPSConstants.APPID;
//			INNOJSAPI_TICKET = TPSConstants.JSAPI_TICKET;获取不到，初始化时JSAPI_TICKET获取没执行完成
			logger.info("读取config.properties配置完成...");
		}
	}
	

	/**
	 * 
	 * 描述：获取资源值
	 * @author John Gu 
	 * @date 2016年4月6日上午9:15:02
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		return properties.getProperty(key);
	}

}
