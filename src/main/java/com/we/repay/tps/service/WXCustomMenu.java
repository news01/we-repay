/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.service;

import java.text.MessageFormat;

import net.sf.json.JSONObject;

import com.we.repay.tps.common.TPSConstants;
import com.we.repay.tps.task.AccessTokenTask;
import com.we.repay.tps.util.HttpsClientUtil;
import com.we.repay.tps.util.WXMenuXmlToJSON;

/**
 * @ClassName: WXCustomMenu
 * @version 2.0 
 * @Desc: 自定义公众号菜单
 * @author tianzhongshan
 * @date 2017年7月11日下午4:47:02
 * @history v2.0
 *
 */
public class WXCustomMenu {
	
	//菜单配置文件地址
	public final String xmlFileClassesUrl = "other/wechat_service_menu.xml";
	
	/**
	 * 描述：自定义菜单创建
	 * @author tianzhongshan 
	 * @date 2017年7月11日下午4:47:44
	 * @return 创建是否成功
	 */
	public JSONObject createCustomMenu(){
		//读取服务号菜单配置文件
		JSONObject xmlToJson = WXMenuXmlToJSON.xmlToJson(xmlFileClassesUrl);
		String jsonMenu = xmlToJson.get("menu").toString();
	  	String requestUrl = MessageFormat.format(TPSConstants.CREATECUSTOMMENU, 
	  												AccessTokenTask.accessToken);
	  	JSONObject respJson = HttpsClientUtil.getInstance().sendPostRequestJson(requestUrl, jsonMenu);
		return respJson;
	}
	
	
}
