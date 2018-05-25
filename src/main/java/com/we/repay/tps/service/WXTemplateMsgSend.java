/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.service;

import java.text.MessageFormat;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.we.repay.tps.common.TPSConstants;
import com.we.repay.tps.dto.TemplateMessageDTO;
import com.we.repay.tps.task.AccessTokenTask;
import com.we.repay.tps.util.HttpsClientUtil;


/**
 * @ClassName: WXTemplateMsgSend
 * @version 2.0 
 * @Desc: 发送模板消息
 * @author tianzhongshan
 * @date 2017年7月11日下午5:22:02
 * @history v2.0
 *
 */
public class WXTemplateMsgSend {
	
	// 日志
	public static Logger logger = Logger.getLogger(WXTemplateMsgSend.class);
	
	/**
	 * 描述：发送模板消息
	 * @author tianzhongshan 
	 * @date 2017年7月11日下午6:09:40
	 * @param TemplateMessageDTO
	 * @return
	 */
	public static JSONObject sendTemplateMsg(TemplateMessageDTO TemplateMessageDTO) {
		String requestUrl = MessageFormat.format(TPSConstants.SENDTEMPLATEMSG,
				AccessTokenTask.accessToken);
		// 需发送模板消息
		String outputStr = JSONObject.fromObject(TemplateMessageDTO).toString();
		JSONObject sendGetRequest = HttpsClientUtil.getInstance().sendPostRequestJson(requestUrl,outputStr);
		return sendGetRequest;
	}
}
