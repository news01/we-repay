/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.dto.repay;

import java.util.Date;

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
public class RepayRecordReqDTO extends QueryReqDTO {
	/**
     * 微信账号ID
     */
    private Long waId;

    /**
     * 订单ID
     */
    private String ordId;

    /**
     * 微信账号标识ID
     */
    private String wxOpenId;

    /**
     * 状态:1-,2-
     */
    private Short status;
    
    /**
     * 多状态查询
     */
    private String instatus;

    /**
     * 金额
     */
    private Float amount;

    /**
     * 创建时间
     */
    private Date createDtm;

    /**
     * 更新时间
     */
    private Date updateDtm;

    /**
     * 支付人
     */
    private String payMan;

    /**
     * 预留字段1
     */
    private String attribute1;

    /**
     * 预留字段2
     */
    private String attribute2;

    /**
     * 预留字段3
     */
    private String attribute3;
    
    /**
     * 预留字段4
     */
    private String attribute4;

	public Long getWaId() {
		return waId;
	}

	public void setWaId(Long waId) {
		this.waId = waId;
	}

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getInstatus() {
		return instatus;
	}

	public void setInstatus(String instatus) {
		this.instatus = instatus;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Date getCreateDtm() {
		return createDtm;
	}

	public void setCreateDtm(Date createDtm) {
		this.createDtm = createDtm;
	}

	public Date getUpdateDtm() {
		return updateDtm;
	}

	public void setUpdateDtm(Date updateDtm) {
		this.updateDtm = updateDtm;
	}

	public String getPayMan() {
		return payMan;
	}

	public void setPayMan(String payMan) {
		this.payMan = payMan;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}
	
}
