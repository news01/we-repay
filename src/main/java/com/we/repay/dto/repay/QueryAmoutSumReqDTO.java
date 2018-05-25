/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.dto.repay;

import com.we.repay.dto.QueryReqDTO;

/**
 * @ClassName: WxAccountReqDTO
 * @version 2.0 
 * @Desc: TODO
 * @author lisha
 * @date 2017年9月20日上午9:48:10
 * @history v2.0
 *
 */
@SuppressWarnings("serial")
public class QueryAmoutSumReqDTO extends QueryReqDTO {
	
	/**
	 * 年份
	 */
	private Integer year;
	
	/**
	 * 支付标准值
	 */
	private Double paystandard;
	
	/**
	 * 支付状态 0--未完全支付   1-- 已完全支付
	 */
	private Integer status;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getPaystandard() {
		return paystandard;
	}

	public void setPaystandard(Double paystandard) {
		this.paystandard = paystandard;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
