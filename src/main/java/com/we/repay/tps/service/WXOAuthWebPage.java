/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Locale;

import net.sf.json.JSONObject;

import com.we.repay.tps.common.TPSConstants;
import com.we.repay.tps.task.AccessTokenTask;
import com.we.repay.tps.util.HttpsClientUtil;


/**
 * @ClassName: WXOAuthWebPage
 * @version 2.0 
 * @Desc: 微信用户信息获取
 * @author tianzhongshan
 * @date 2017年7月11日下午5:03:48
 * @history v2.0
 *
 */
public class WXOAuthWebPage {
	
	
	/**
	 * 微信端OPENID拉取用户信息
	 * @param oAuthAccessToken 接口调用凭证
	 * @param openid 用户的唯一标识
	 * @return
	 */
	public  JSONObject GetUserInfo(String openid){
		String requestUrl = MessageFormat.format(TPSConstants.GETUSERINFO,
				AccessTokenTask.accessToken,
				openid,
				Locale.CHINA.toString());
		JSONObject jsonObject = HttpsClientUtil.getInstance().sendGetRequestJson(requestUrl);
		return jsonObject;
	}
	
	/**
	 * 描述：获取微信端网页授权code值 (不能调用必须用微信浏览器调用)
	 * @author tianzhongshan 
	 * @date 2017年7月11日下午5:17:10
	 * @param redirectUri 授权回调地址
	 * @throws UnsupportedEncodingException
	 */
	public static void getOAuthCode(String redirectUri) throws UnsupportedEncodingException{
		//微信端网页授权code值
		
		String requestUrl = MessageFormat.format(TPSConstants.GETOAUTHCODE,
													TPSConstants.APPID,
													URLEncoder.encode(redirectUri,"UTF-8"),
													"code",
													"snsapi_base",
													"code#wechat_redirect");
    	HttpsClientUtil.getInstance().sendGetRequestJson(requestUrl);
	}

	/**
	 * 描述：获取微信端网页授权access_token
	 * @author tianzhongshan 
	 * @date 2017年7月11日下午5:19:28
	 * @param code 用户同意授权返回code 值,code只能使用一次，5分钟未被使用自动过期
	 * @return JSONObect 网页授权后数据
	 */
	public JSONObject GetOAuthAccessToken(String code){
		String requestUrl = MessageFormat.format(TPSConstants.GETOAUTHACCESSTOKEN,
													TPSConstants.APPID,
													TPSConstants.APPSECRET,
													code,
													"authorization_code");
    	JSONObject jsonObject = HttpsClientUtil.getInstance().sendGetRequestJson(requestUrl);
		return jsonObject;
	}
	
	
	/**
	 * 微信端网页授权后拉取用户信息
	 * @param oAuthAccessToken 网页授权接口调用凭证
	 * @param openid 用户的唯一标识
	 * @return
	 */
	public JSONObject GetOAuthUserInfo(String oAuthAccessToken,String openid){
		String requestUrl = MessageFormat.format(TPSConstants.GETOAUTHUSERINFO,
				oAuthAccessToken,
				openid,
				Locale.CHINA.toString());
		JSONObject jsonObject = HttpsClientUtil.getInstance().sendGetRequestJson(requestUrl);
		return jsonObject;
	}
	
}
