/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.dto;


/**
 * @ClassName: TextMessageDTO
 * @version 2.0 
 * @Desc: 文本消息
 * @author tianzhongshan
 * @date 2017年7月11日下午3:24:01
 * @history v2.0
 *
 */
public class TextMessageDTO extends BaseMessageDTO{
	
	// 回复的消息内容
    private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	
    
}
