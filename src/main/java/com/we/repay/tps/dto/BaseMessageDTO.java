/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.dto;

/**
 * @ClassName: BaseMessageDTO
 * @version 2.0 
 * @Desc: 消息基类（公众帐号 -> 普通用户）
 * @author tianzhongshan
 * @date 2017年7月11日下午3:25:06
 * @history v2.0
 *
 */
public class BaseMessageDTO {

	 // 接收方帐号（收到的OpenID）
    private String ToUserName;
    
    // 开发者微信号
    private String FromUserName;
    
    // 消息创建时间 （整型）
    private long CreateTime;
    
    // 消息类型
    private String MsgType;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
    
}
