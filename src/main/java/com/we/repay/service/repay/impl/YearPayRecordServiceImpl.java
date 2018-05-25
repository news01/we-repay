/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.service.repay.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.we.repay.dao.repay.YearPayRecordMapper;
import com.we.repay.dto.QueryRespDTO;
import com.we.repay.po.repay.YearPayRecord;
import com.we.repay.service.repay.YearPayRecordService;

/**
 * @ClassName: YearPayRecordServiceImpl
 * @version 2.0 
 * @Desc: TODO
 * @author lisha
 * @date 2017年11月16日下午4:51:33
 * @history v2.0
 *
 */
@Service("yearPayRecordService")
public class YearPayRecordServiceImpl implements YearPayRecordService {

	@Autowired
	private YearPayRecordMapper yearPayRecordMapper;

	@Override
	public int addYearPayRecord(YearPayRecord yearPayRecord) {
		return yearPayRecordMapper.insertSelective(yearPayRecord);
	}

	@Override
	public int modifyYearPayRecordById(YearPayRecord yearPayRecord) {
		return yearPayRecordMapper.updateByPrimaryKeySelective(yearPayRecord);
	}

	@Override
	public int deleteYearPayRecordById(Long id) {
		return yearPayRecordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public YearPayRecord queryYearPayRecordById(Long id) {
		return yearPayRecordMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<YearPayRecord> queryYearPayRecordList(
			YearPayRecord yearPayRecord) {
		return yearPayRecordMapper.selectListSelective(yearPayRecord);
	}
	
	@Override
	public QueryRespDTO<YearPayRecord> queryAmountSum(YearPayRecord record) {
		QueryRespDTO<YearPayRecord> result = new QueryRespDTO<YearPayRecord>();
		int total = yearPayRecordMapper.selectAmountSumCount(record);
		if(total > 0){
			List<YearPayRecord> list = yearPayRecordMapper.selectAmountSum(record);
			result.setRows(list);
			result.setTotal(total);
		}
		return result;
	}

	@Override
	public List<YearPayRecord> queryYearPayRecordByIdList(List<Long> ids) {
		return yearPayRecordMapper.selectByIdList(ids);
	}
	
	
}
