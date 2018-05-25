package com.we.repay.tps.common;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.we.repay.util.io.PropertyUtil;

/**
 * @ClassName: Constants
 * @version 2.0
 * @Desc: 数据常量
 * @author Jack
 * @date 2017年5月24日上午10:28:28
 * @history v2.0
 */
public class TPSConstants {

	private static final Logger logger = Logger.getLogger(TPSConstants.class);
	
	//微信公众号应用ID
	public static String APPID;
	//微信公众号应用密匙
	public static String APPSECRET;
	
	///微信公众号开发者中心配置服务器令牌标识token
	public static String TOKEN;
	
	//微信公众号JSSDK使用权限签名jsapi_ticket
	public static String JSAPI_TICKET;
	
	// 初始化环境变量 配置文件 wxconfig.properties
	public static Properties properties;
	
	//微信公众账号OPENID 拉取用户信息
	public static final String GETUSERINFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}&lang={2}";
	//获取ACCESS_TOKEN接口 	grant_type填写client_credential
	public static final String GETACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
	//获取JSSDK使用权限JSAPI_TICKET接口 	type填写jsapi 
	public static final String GETJSAPITICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";
	//自定义菜单创建接口
	public static final String CREATECUSTOMMENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={0}";
	//自定义菜单查询接口
	public static final String GETCUSTOMMENU = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token={0}";
	//发送模板消息接口
	public static final String SENDTEMPLATEMSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
	
	//OAuth微信公众账号网页授权
	public static final String GETOAUTHCODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type={2}&scope={3}&state={4}#wechat_redirect";
	//OAuth微信公众账号网页授权 通过code换取网页授权access_token
	public static final String GETOAUTHACCESSTOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
	//OAuth微信公众账号网页授权 拉取用户信息
	public static final String GETOAUTHUSERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang={2}";
	
	//关注模板
	public static final String attention = "JgejMU6iYvwMqtTupbHJV4Gy27MSjgAthbNuAgTVZF0";
	
	//注册成功模板
	public static final String registered_successfully = "oL8SrjU-2g53eVtFXyLvWKfpHiB527BquFPXTPz4HOA";
	
	//============素材文件管理===================
	public static final String GETTMPEMATERIAL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token={0}&media_id={1}";
	
	
	//抽奖获奖的模板
    public static final String draw_prize_successfully = "Wyybt9OZ8182Or-ienrOTzvq9rxw_UlwNy0GZZUrwuI";
	
	
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
		properties = PropertyUtil.getInstance().getProperties("properties/wxconfig.properties");
		if(properties!=null){
			APPID = properties.getProperty("APPID");
			APPSECRET = properties.getProperty("APPSECRET");
			TOKEN = properties.getProperty("TOKEN");
			logger.info("读取wxconfig.properties配置完成...");
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
