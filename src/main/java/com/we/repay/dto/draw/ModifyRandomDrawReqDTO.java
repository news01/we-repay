package com.we.repay.dto.draw;

import java.util.Date;
import java.util.List;

public class ModifyRandomDrawReqDTO {
    /**
     * 主键ID
     */
    private List<Long> rdIds;

    /**
     * 中奖级别 -1 未中奖 0 未抽奖 1 是一等奖 2 是二等奖 3 三等奖 4 四等奖 5 五等奖 6 六等奖
     */
    private Short prizeLevel;

    /**
     * 中奖时间
     */
    private Date prizeDtm;

    /**
     * 创建时间
     */
    private Date modifyDtm;
    
    /**
     * 抽奖资格状态  1 正常 0 异常（没有资格抽奖）
     */
    private Short status;
    
    
    private Short attribute1;

	public Short getPrizeLevel() {
		return prizeLevel;
	}

	public void setPrizeLevel(Short prizeLevel) {
		this.prizeLevel = prizeLevel;
	}

	public Date getPrizeDtm() {
		return prizeDtm;
	}

	public void setPrizeDtm(Date prizeDtm) {
		this.prizeDtm = prizeDtm;
	}

	public Date getModifyDtm() {
		return modifyDtm;
	}

	public void setModifyDtm(Date modifyDtm) {
		this.modifyDtm = modifyDtm;
	}

	public List<Long> getRdIds() {
		return rdIds;
	}

	public void setRdIds(List<Long> rdIds) {
		this.rdIds = rdIds;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(Short attribute1) {
		this.attribute1 = attribute1;
	}

}