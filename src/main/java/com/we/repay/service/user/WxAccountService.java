/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.service.user;

import java.util.List;

import com.we.repay.dto.QueryRespDTO;
import com.we.repay.dto.draw.QueryRandomDrawReqDTO;
import com.we.repay.dto.user.QueryWxAccountRespDTO;
import com.we.repay.dto.user.WxAccountLoginReqDTO;
import com.we.repay.dto.user.WxAccountReqDTO;
import com.we.repay.dto.user.WxAccountRespDTO;
import com.we.repay.po.user.WxAccount;

/**
 * @ClassName: WxAccountService
 * @version 2.0 
 * @Desc: TODO
 * @author lisha
 * @date 2017年9月19日下午5:22:39
 * @history v2.0
 *
 */
public interface WxAccountService {

	public int addWxAccount(WxAccount wxAccount);
	
	public int modifyWxAccountById(WxAccount wxAccount);
	
	public int deleteWxAccountById(Long waId);
	
	public WxAccount queryWxAccountById(Long waId);
	
	public List<WxAccount> queryWxAccountList(WxAccount wxAccount);
	
	public QueryRespDTO<WxAccount> queryWxAccountPage(WxAccountReqDTO wxAccountReqDTO);
	
	public WxAccount queryByMpOpenId(String mpOpenId);
	
	public WxAccount login(WxAccountLoginReqDTO wxAccountLoginReqDTO);
	
	public int queryMaxNumber();
	
	public List<WxAccount> queryAllWxAccountList();
	
	public void registerAccount(String openId);
	
	public List<WxAccount> queryWxAccountRandomDraw();
	
	public List<WxAccountRespDTO> queryRandomDrawByYear(String year);
	
	public List<QueryWxAccountRespDTO> queryWxAccountRandomDrawByRdId(QueryRandomDrawReqDTO queryRandomDrawReqDTO);
}
