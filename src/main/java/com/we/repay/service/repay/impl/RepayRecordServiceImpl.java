/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.service.repay.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.we.repay.dao.repay.RepayRecordMapper;
import com.we.repay.dto.QueryRespDTO;
import com.we.repay.dto.repay.QueryAmoutSumReqDTO;
import com.we.repay.dto.repay.RepayRecordReqDTO;
import com.we.repay.po.repay.RepayRecord;
import com.we.repay.service.repay.RepayRecordService;

/**
 * @ClassName: RepayRecordServiceImpl
 * @version 2.0 
 * @Desc: TODO
 * @author lisha
 * @date 2017年9月20日下午3:12:00
 * @history v2.0
 *
 */
@Service("repayRecordService")
public class RepayRecordServiceImpl implements RepayRecordService{

	@Autowired
	private RepayRecordMapper repayRecordMapper;

	@Override
	public int addRepayRecord(RepayRecord repayRecord) {
		repayRecord.setCreateDtm(new Date());
		// 添加支付账单流水
		return repayRecordMapper.insertSelective(repayRecord);
	}

	@Override
	public int modifyRepayRecordById(RepayRecord repayRecord) {
		repayRecord.setUpdateDtm(new Date());
		// 根据主键ID修改支付账单流水
		return repayRecordMapper.updateByPrimaryKeySelective(repayRecord);
	}

	@Override
	public int deleteRepayRecordById(Long wrId) {
		// 根据主键ID删除支付账单流水
		return repayRecordMapper.deleteByPrimaryKey(wrId);
	}

	@Override
	public RepayRecord queryRepayRecordById(Long wrId) {
		// 根据主键ID查询支付账单流水
		return repayRecordMapper.selectByPrimaryKey(wrId);
	}

	@Override
	public List<RepayRecord> queryRepayRecordList(RepayRecord repayRecord) {
		// 查询支付账单流水List
		return repayRecordMapper.selectListSelective(repayRecord);
	}

	@Override
	public QueryRespDTO<RepayRecord> queryRepayRecordPage(
			RepayRecordReqDTO repayRecordReqDTO) {
		QueryRespDTO<RepayRecord> result = new QueryRespDTO<RepayRecord>();
		// 查询支付账单流水总数
		int total = repayRecordMapper.selectPageCountSelective(repayRecordReqDTO);
		if(total > 0){
			// 分页查询支付账单流水
			List<RepayRecord> list = repayRecordMapper.selectPageSelective(repayRecordReqDTO);
			result.setTotal(total);
			result.setRows(list);
		}
		return result;
	}

	@Override
	public int modifyRepayRecordByOrdId(RepayRecord repayRecord) {
		return repayRecordMapper.updateByOrdIdSelective(repayRecord);
	}

	@Override
	public QueryRespDTO<RepayRecord> queryAmountSum(QueryAmoutSumReqDTO queryAmoutSumReqDTO) {
		QueryRespDTO<RepayRecord> result = new QueryRespDTO<RepayRecord>();
		int total = repayRecordMapper.selectAmountSumCount(queryAmoutSumReqDTO);
		if(total > 0){
			List<RepayRecord> list = repayRecordMapper.selectAmountSum(queryAmoutSumReqDTO);
			result.setRows(list);
			result.setTotal(total);
		}
		return result;
	}
	
	
}
