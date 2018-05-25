/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.service;

import org.apache.log4j.Logger;

import com.we.repay.common.Constants;
import com.we.repay.pay.service.WXPay;
import com.we.repay.pay.to.WXPayConfig;
import com.we.repay.tps.dto.WXPayConfigImpl;


/**
 * @ClassName: WXTemplateMsgSend
 * @version 2.0 
 * @Desc: 初始化微信公众号支付配置
 * @author tianzhongshan
 * @date 2017年7月11日下午5:22:02
 * @history v2.0
 *
 */
public class WXTPSPay {
	
	// 日志
	public static Logger logger = Logger.getLogger(WXTPSPay.class);
	private static WXPayConfig config;
	private static WXPay wxPay;
	
	static{
		try {
			config = WXPayConfigImpl.getInstance();
			wxPay = new WXPay(config, Constants.BASE_PATH+Constants.WXPAY_NOTIFY_URL,true,config.getUseSandbox());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("初始化微信公众号支付配置出现错误....",e);
		}
		
	}
	
	public static WXPay getWXPay(){
		return wxPay;
	}
	
	public static WXPayConfig getWXPayConfig(){
		return config;
	}
	
}
