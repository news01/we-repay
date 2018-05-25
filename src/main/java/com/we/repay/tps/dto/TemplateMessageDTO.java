/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.dto;

import java.util.Map;

/**
 * @ClassName: TemplateMessageDTO
 * @version 2.0 
 * @Desc: 模板消息
 * @author tianzhongshan
 * @date 2017年7月11日下午6:12:42
 * @history v2.0
 *
 */
public class TemplateMessageDTO {

	// 接收方帐号（收到的OpenID）
    private String touser;
    //发送模板消息id
    private String template_id;
    //模板链接
    private String url;
    //模板消息内容
    private Map<String,TemplateDataDTO> data;
    
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, TemplateDataDTO> getData() {
		return data;
	}
	public void setData(Map<String, TemplateDataDTO> data) {
		this.data = data;
	}
    
    
    
    
}
