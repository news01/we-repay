/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.service.repay;

import java.util.List;

import com.we.repay.dto.QueryRespDTO;
import com.we.repay.po.repay.YearPayRecord;

/**
 * @ClassName: YearPayRecordService
 * @version 2.0 
 * @Desc: TODO
 * @author lisha
 * @date 2017年11月16日下午4:51:25
 * @history v2.0
 *
 */
public interface YearPayRecordService {

	public int addYearPayRecord(YearPayRecord yearPayRecord);
	
	public int modifyYearPayRecordById(YearPayRecord yearPayRecord);
	
	public int deleteYearPayRecordById(Long id);
	
	public YearPayRecord queryYearPayRecordById(Long id);
	
	public List<YearPayRecord> queryYearPayRecordList(YearPayRecord yearPayRecord);

	QueryRespDTO<YearPayRecord> queryAmountSum(YearPayRecord record);
	
	public List<YearPayRecord> queryYearPayRecordByIdList(List<Long> ids);
}
