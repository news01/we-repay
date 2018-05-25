/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.service.repay;

import java.util.List;

import com.we.repay.dto.QueryRespDTO;
import com.we.repay.dto.repay.QueryAmoutSumReqDTO;
import com.we.repay.dto.repay.RepayRecordReqDTO;
import com.we.repay.po.repay.RepayRecord;

/**
 * @ClassName: RepayRecordService
 * @version 2.0 
 * @Desc: TODO
 * @author lisha
 * @date 2017年9月20日下午3:11:55
 * @history v2.0
 *
 */
public interface RepayRecordService {

	public int addRepayRecord(RepayRecord repayRecord);
	
	public int modifyRepayRecordById(RepayRecord repayRecord);
	
	public int deleteRepayRecordById(Long wrId);
	
	public RepayRecord queryRepayRecordById(Long wrId);
	
	public List<RepayRecord> queryRepayRecordList(RepayRecord repayRecord);
	
	public QueryRespDTO<RepayRecord> queryRepayRecordPage(RepayRecordReqDTO repayRecordReqDTO);
	
	public int modifyRepayRecordByOrdId(RepayRecord repayRecord);
	
	public QueryRespDTO<RepayRecord> queryAmountSum(QueryAmoutSumReqDTO queryAmoutSumReqDTO);
}
