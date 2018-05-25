/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.service.user.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.we.repay.dao.user.WxAccountMapper;
import com.we.repay.dto.QueryRespDTO;
import com.we.repay.dto.draw.QueryRandomDrawReqDTO;
import com.we.repay.dto.user.QueryWxAccountRespDTO;
import com.we.repay.dto.user.WxAccountLoginReqDTO;
import com.we.repay.dto.user.WxAccountReqDTO;
import com.we.repay.dto.user.WxAccountRespDTO;
import com.we.repay.po.user.WxAccount;
import com.we.repay.service.user.WxAccountService;
import com.we.repay.tps.service.WXOAuthWebPage;
import com.we.repay.tps.util.TimedPush;

/**
 * @ClassName: WxAccountServiceImpl
 * @version 2.0 
 * @Desc: TODO
 * @author lisha
 * @date 2017年9月19日下午5:22:47
 * @history v2.0
 *
 */
@Service("wxAccountService")
public class WxAccountServiceImpl implements WxAccountService {
	//日志
	private Logger logger = Logger.getLogger(WxAccountServiceImpl.class);

	@Autowired 
	private WxAccountMapper wxAccountMapper;
	
	@Resource
	private PasswordEncoder passwordEncoder;
	

	@Override
	public int addWxAccount(WxAccount wxAccount) {
		WxAccount response = wxAccountMapper.selectByMpOpenId(wxAccount.getMpOpenId());
		if(response != null){
			return 0;
		}
		// 添加微信账号
		return wxAccountMapper.insertSelective(wxAccount);
	}

	@Override
	public int modifyWxAccountById(WxAccount wxAccount) {
		// 根据主键ID修改微信账号
		return wxAccountMapper.updateByPrimaryKeySelective(wxAccount);
	}

	@Override
	public int deleteWxAccountById(Long waId) {
		// 根据主键ID删除微信账号
		return wxAccountMapper.deleteByPrimaryKey(waId);
	}

	@Override
	public WxAccount queryWxAccountById(Long waId) {
		// 根据主键ID查询微信账号
		return wxAccountMapper.selectByPrimaryKey(waId);
	}

	@Override
	public List<WxAccount> queryWxAccountList(WxAccount wxAccount) {
		// 查询微信账号List
		return wxAccountMapper.selectListSelective(wxAccount);
	}

	@Override
	public QueryRespDTO<WxAccount> queryWxAccountPage(
			WxAccountReqDTO wxAccountReqDTO) {
		QueryRespDTO<WxAccount> result = new QueryRespDTO<WxAccount>();
		// 查询微信账号总数
		int total = wxAccountMapper.selectPageCountSelective(wxAccountReqDTO);
		if(total > 0){
			// 分页查询微信账号
			List<WxAccount> list = wxAccountMapper.selectPageSelective(wxAccountReqDTO);
			result.setTotal(total);
			result.setRows(list);
		}
		return result;
	}

	@Override
	public WxAccount queryByMpOpenId(String mpOpenId) {
		return wxAccountMapper.selectByMpOpenId(mpOpenId);
	}

	@Override
	public WxAccount login(WxAccountLoginReqDTO wxAccountLoginReqDTO) {
		WxAccount result = new WxAccount();
		if(StringUtils.isNotEmpty(wxAccountLoginReqDTO.getCcUserName()) || StringUtils.isNotEmpty(wxAccountLoginReqDTO.getCcUserPwd())){
			result = wxAccountMapper.login(wxAccountLoginReqDTO);
		}
		return result;
	}

	@Override
	public int queryMaxNumber() {
		return (int) wxAccountMapper.queryMaxCCid();
	}

	@Override
	public List<WxAccount> queryAllWxAccountList() {
		return wxAccountMapper.selectWxAccountList();
	}

	@Override
	public synchronized void registerAccount(String openid) {
		logger.info("执行关注方法");

		WXOAuthWebPage wxOAuthWebPage = new WXOAuthWebPage();//获取微信授权对象
		//获取用户信息
		JSONObject userInfo = wxOAuthWebPage.GetUserInfo(openid);
		Object errcodeUserInfo = userInfo.get("errcode");
		if(errcodeUserInfo==null){
			WxAccount wxAccount = new WxAccount();
			
			String opId = (String) userInfo.get("openid");
			wxAccount.setMpOpenId(opId);
			
			String nikeName = (String) userInfo.get("nickname");
			wxAccount.setNickname(nikeName);
			wxAccount.setSex((Integer) userInfo.get("sex"));
			wxAccount.setProvince((String) userInfo.get("province"));
			wxAccount.setCity((String) userInfo.get("city"));
			wxAccount.setCountry((String) userInfo.get("country"));
			wxAccount.setHeadimgurl((String) userInfo.get("headimgurl"));
			@SuppressWarnings("rawtypes")
			List privilege = (List) userInfo.get("privilege");
			if(privilege!=null&&privilege.size()>0){
				wxAccount.setPrivilege(JSONArray.fromObject(privilege).toString());
			}
			wxAccount.setUnionid((String) userInfo.get("unionid"));
			wxAccount.setLanguage(Locale.CHINA.toString());
			
			
//			WxAccount wxAccount2 = new WxAccount();
//			List<WxAccount> LW = wxAccountService.queryWxAccountList(wxAccount2);
//			String userNumber = LW.get(LW.size()-1).getAttribute3();//用户唯一编号
//			String newNumber = null;
			/*if(StringUtils.isBlank(userNumber)){//本字段不能为空，如果数据库最后一条记录为空，设为默认初始1000
				wxAccount.setAttribute3("1000");
			} else {
				for (int i = 0; i < userNumber.length(); i++) {
					if (Character.isDigit(userNumber.charAt(i))){
						newNumber = userNumber.substring(i);
//						System.out.println(userNumber.charAt(i));
						break;
					}
				}
//				newNumber = userNumber.substring(2);
				newNumber = (Integer.valueOf(newNumber) + 1)+"";
				wxAccount.setAttribute3(newNumber);
			}*/
			
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = dateFormat.format(date);
			wxAccount.setAttribute4(currentTime);
			wxAccount.setAttribute1("0");//关注但并非会员
			wxAccount.setRealName(nikeName);
			
			WxAccount WA = queryByMpOpenId(opId);//插入之前进行查询
			int result = 0;//插入结果
			String push_result = null;//推送结果
			if(WA == null){
				result = addWxAccount(wxAccount);
				logger.info("执行插入结果："+result);
				if(result == 1){
					push_result = TimedPush.push_news(wxAccount,1);
				}
				/*if(StringUtils.isNotBlank(opId)){
					String mobile = WA.getMobile();
					if(StringUtils.isBlank(mobile)){
						push_result = TimedPush.push_news(wxAccount,1);
					}
				}*/
				
			}
		
			logger.info("关注："+result);
			
//			String opId2 = (String) userInfo.get("openid");
			/*if(StringUtils.isNotBlank(opId)){
//				WxAccount WA = wxAccountService.queryByMpOpenId(opId);
				String mobile = WA.getMobile();
				if(StringUtils.isBlank(mobile)){
					push_result = TimedPush.push_news(wxAccount,1);
				}
				
			}*/
			
			/*if(result > 0){
//				wxAccount.setAttribute4(newNumber);//临时保存唯一标示
				push_result = TimedPush.push_news(wxAccount,1);
			}*/
			logger.info("订阅推送结果："+push_result);
			logger.info("保存微信关注用户信息成功 :::: "+openid);
		}
		
	
		
	}

	@Override
	public List<WxAccount> queryWxAccountRandomDraw() {
		return wxAccountMapper.selectRandomDraw();
	}
	

	@Override
	public List<WxAccountRespDTO> queryRandomDrawByYear(String year) {
		return wxAccountMapper.selectByYear(year);
	}

	@Override
	public List<QueryWxAccountRespDTO> queryWxAccountRandomDrawByRdId(
			QueryRandomDrawReqDTO queryRandomDrawReqDTO) {
		return wxAccountMapper.selectRandomDrawByRdId(queryRandomDrawReqDTO);
	}
}
