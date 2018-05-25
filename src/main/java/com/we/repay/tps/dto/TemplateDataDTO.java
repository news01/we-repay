/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.dto;

/**
 * @ClassName: TemplateDataDTO
 * @version 2.0 
 * @Desc:  模板数据封装
 * @author tianzhongshan
 * @date 2017年7月11日下午6:11:29
 * @history v2.0
 *
 */
public class TemplateDataDTO {

	// 数据类型值
	private String value;
	// 数据类型颜色
	private String color;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
}
