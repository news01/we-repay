package com.we.repay.dto.draw;

import java.util.List;

public class QueryRandomDrawReqDTO {
    /**
     * 主键ID
     */
    private List<Long> rdIds;


    /**
     * 中奖级别 -1 未中奖 0 未抽奖 1 是一等奖 2 是二等奖 3 三等奖 4 四等奖 5 五等奖 6 六等奖 
     */
    private Short prizeLevel;


    /**
     * 批次号
     */
    private String attribute2;

	public Short getPrizeLevel() {
		return prizeLevel;
	}


	public void setPrizeLevel(Short prizeLevel) {
		this.prizeLevel = prizeLevel;
	}


	public String getAttribute2() {
		return attribute2;
	}


	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}


	public List<Long> getRdIds() {
		return rdIds;
	}


	public void setRdIds(List<Long> rdIds) {
		this.rdIds = rdIds;
	}

}