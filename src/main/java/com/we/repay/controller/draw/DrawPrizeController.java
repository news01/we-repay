/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.controller.draw;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.we.repay.common.Constants;
import com.we.repay.common.ControlHandler;
import com.we.repay.common.HandlerProxy;
import com.we.repay.dto.QueryRespDTO;
import com.we.repay.dto.draw.ModifyRandomDrawReqDTO;
import com.we.repay.dto.draw.QueryRandomDrawReqDTO;
import com.we.repay.dto.draw.RandomDrawReqDTO;
import com.we.repay.dto.user.QueryWxAccountRespDTO;
import com.we.repay.dto.user.WxAccountRespDTO;
import com.we.repay.po.draw.RandomDraw;
import com.we.repay.po.repay.YearPayRecord;
import com.we.repay.po.user.WxAccount;
import com.we.repay.service.draw.RandomDrawService;
import com.we.repay.service.repay.YearPayRecordService;
import com.we.repay.service.user.WxAccountService;
import com.we.repay.tps.common.TPSConstants;
import com.we.repay.tps.dto.TemplateDataDTO;
import com.we.repay.tps.dto.TemplateMessageDTO;
import com.we.repay.tps.service.WXOAuthWebPage;
import com.we.repay.tps.service.WXTemplateMsgSend;
import com.we.repay.util.DateUtil;
import com.we.repay.util.StringUtil;
import com.we.repay.util.WebParamUtils;
import com.we.repay.util.model.ResultVo;

/**
 * @ClassName: DrawPrizeController
 * @version 2.0 
 * @Desc: 抽奖活动
 * @author Jack
 * @date 2017年12月15日下午2:56:50
 * @history v2.0
 *
 */
@Controller
public class DrawPrizeController {
	
	//日志
	private Logger logger = Logger.getLogger(DrawPrizeController.class);
	
	@Autowired
	private WxAccountService wxAccountService;
	
	@Autowired
	private RandomDrawService randomDrawService;
	
	@Autowired
	private YearPayRecordService yearPayRecordService;
	
	/**
	 * 
	 * 描述：进入抽奖页面
	 * @author Jack 
	 * @date 2017年12月18日上午11:24:02
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/draw/drawInit")
	public ModelAndView drawInit(HttpServletRequest request, HttpServletResponse response) {
		
		return HandlerProxy.assemble(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				//返回页面
				ModelAndView modelAndView = new ModelAndView("pc/jsp/module/draw/draw_page.jsp");
				return modelAndView;
			}
		}, request, response);
	}
	
	
	
	
	/**
	 * 
	 * 描述：同步会员客户
	 * @author Jack 
	 * @date 2017年12月15日下午3:07:39
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/draw/synchAccount")
	public void synchAccount(HttpServletRequest request, HttpServletResponse response){
		
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				logger.info(">>>>>>>>>>>>>>>>>>>>同步会员客户："+wxAccount.getRealName());
				long start = System.currentTimeMillis();
				//添加结果
				int result = 0;
				//查询当不在抽奖表里的客户(有资格抽奖的会员客户)
				List<WxAccountRespDTO> wxAccounts = wxAccountService.queryRandomDrawByYear(Constants.YEAR_CONFIG_NUMBER);
				if(null!=wxAccounts&&wxAccounts.size()>0){
					for(WxAccountRespDTO wax:wxAccounts){
						RandomDraw randomDraw = new RandomDraw();
						randomDraw.setUserId(wax.getWaId());
						randomDraw.setName(wax.getRealName());
						randomDraw.setNumberCode(wax.getAttribute3());
						if(null!=wax.getCcUserId()){
							randomDraw.setNoPrefixCode(wax.getCcUserId().intValue());
						}
						randomDraw.setPrizeLevel((short)-1);
						randomDraw.setCrtDtm(new Date());
						randomDraw.setAttribute1((short)0);
						randomDraw.setMemberType((short)Integer.parseInt(wax.getAttribute1()));
						randomDraw.setAttribute2(Constants.YEAR_CONFIG_NUMBER);
						result += randomDrawService.addRandomDraw(randomDraw);
					}
					
					if(result<=0){
						resultVo.setCustomError(null, "同步失败");
					}
				}else{
					logger.info(">>>>>>>>>>>>>>>>>未查询到同步的数据！");
				}
				long end = System.currentTimeMillis();
				logger.info(">>>>>>>>>>>>>>>>>>>>>>同步会员客户耗时："+(end-start)+"毫秒");
				return null;
			}
		}, request, response);
	}
	
	/**
	 * 
	 * 描述：抽奖
	 * @author Jack 
	 * @date 2017年12月15日下午5:33:11
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/draw/drawPrize")
	public void drawPrize(HttpServletRequest request, HttpServletResponse response){
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				
				int drawLevel = WebParamUtils.getInteger("prizlevel", request);//中奖等级
				int drawNo = WebParamUtils.getInteger("prizeNum", request);//设置中奖人数
				
				//中奖人数
				List<Long> drawlist = new ArrayList<Long>();
				//有资格抽奖的人
				List<Long> list = new ArrayList<Long>();
				//查询符合抽奖的人的名单
				RandomDraw randomDraw = new RandomDraw();
				randomDraw.setPrizeLevel((short)-1);//未抽奖
				randomDraw.setStatus((short)1);//正常
				randomDraw.setAttribute2(Constants.YEAR_CONFIG_NUMBER);//交年费的年份
				List<RandomDraw> randomDrawList = randomDrawService.queryRandomDrawList(randomDraw);
				//判断抽奖人数 是是否足够
				if(drawNo>randomDrawList.size()){
					resultVo.setCustomError(null, "参与抽奖人数不足"+drawNo+"个~~~");
					return null;
				}
				
				//查询库里的最大批次
				int drawBatch = randomDrawService.queryAttribute1Max();
				
				// 开始抽奖
				if(null!=randomDrawList&&randomDrawList.size()>0){
					for (RandomDraw rdd: randomDrawList) {
						list.add(rdd.getRdId());
					}
					
					//将集合里的Id打乱
					if(null!=list&&list.size()>0){
						Collections.shuffle(list);
					}
					//抽奖人数
					drawlist = list.subList(0, drawNo);
					ModifyRandomDrawReqDTO modifyRandomDrawReqDTO = new ModifyRandomDrawReqDTO();
					modifyRandomDrawReqDTO.setRdIds(drawlist);
					modifyRandomDrawReqDTO.setPrizeLevel((short)drawLevel);
					modifyRandomDrawReqDTO.setPrizeDtm(new Date());
					modifyRandomDrawReqDTO.setAttribute1((short)(drawBatch+1));
					int result = randomDrawService.modifyRandomDrawByIdList(modifyRandomDrawReqDTO);
					if(result<=0){
						resultVo.setCustomError(null, "抽奖失败~~~");
					}
					resultVo.setResult(drawlist);
					logger.info(">>>>>>>>>>>>>>>>>中奖人的id："+drawlist.toString()+"；中奖的等级："+drawLevel+";抽奖人："+wxAccount.getRealName());
				}else{
					resultVo.setCustomError(null, "未查询到符合条件的会员客户");
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>未查询到有符合抽奖资格的会员客户~~~~"+wxAccount.getWaId()+";年份："+Constants.YEAR_CONFIG_NUMBER);
				}
				return null;
			}
		}, request, response);
	}
	
	
	/**
	 * 
	 * 描述：查询抽奖记录详情
	 * @author Jack 
	 * @date 2017年12月19日上午9:08:03
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/draw/drawRecodePage")
	public ModelAndView drawRecodePage(HttpServletRequest request, HttpServletResponse response) {
		return HandlerProxy.assemble(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				//返回页面
				ModelAndView mav =  new ModelAndView("pc/jsp/module/draw/draw_recode_list.jsp");
				return mav;
			}
		}, request, response);
	}
	
	
	/**
	 * 
	 * 描述：加载数据列表
	 * @author Jack 
	 * @date 2017年12月19日上午9:23:38
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/draw/queryDrawRecodeList")
	public void queryDrawRecodeList(HttpServletRequest request, HttpServletResponse response) {
		
		final RandomDrawReqDTO randomDrawReqDTO = new RandomDrawReqDTO();
		
		HandlerProxy.assembleAjaxGrid(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				QueryRespDTO<RandomDraw> queryRandomDrawPage = randomDrawService.queryRandomDrawPage(randomDrawReqDTO);
				resultVo.setGridData(queryRandomDrawPage);
				return null;
			}
		}, request, response, randomDrawReqDTO);
	}
	
	/**
	 * 
	 * 描述：冻结抽奖资格
	 * @author Jack 
	 * @date 2017年12月19日上午10:16:26
	 * @param rdIds
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/draw/frozenUser")
	public void frozenUser(final String rdIds,HttpServletRequest request, HttpServletResponse response){
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				List<Long> rdIdlist = new ArrayList<Long>();
				String[] idArr = rdIds.split(",");
				for (int i = 0; i < idArr.length; i++) {
					rdIdlist.add(Long.parseLong(idArr[i]));
				}
				//批量修改抽奖记录状态
				ModifyRandomDrawReqDTO	modifyRandomDrawReqDTO = new ModifyRandomDrawReqDTO();
				modifyRandomDrawReqDTO.setStatus((short)0);
				modifyRandomDrawReqDTO.setRdIds(rdIdlist);
				int result  = randomDrawService.modifyRandomDrawByIdList(modifyRandomDrawReqDTO);
				if(result<=0){
					resultVo.setCustomError(null, "冻结失败！");
				}
				return null;
			}
		}, request, response);
		
	}
	
	/**
	 * 
	 * 描述：按等级查询中奖记录
	 * @author Jack 
	 * @date 2017年12月19日下午5:08:20
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/draw/drawPrizeList")
	public void drawPrizeList(HttpServletRequest request, HttpServletResponse response){
		
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				//中奖等级
				int drawLevel = WebParamUtils.getInteger("prizlevel", request);//中奖等级
				
				RandomDraw randomDraw = new RandomDraw();
				randomDraw.setPrizeLevel((short)drawLevel);
				randomDraw.setAttribute2(Constants.YEAR_CONFIG_NUMBER);
				List<RandomDraw> randomDrawList = randomDrawService.queryRandomDrawList(randomDraw);
				resultVo.setResult(randomDrawList);
				return null;
			}
		}, request, response);
		
	} 
	
	/**
	 * 
	 * 描述：根据抽检记录表的id集合查询所有的中奖记录
	 * @author Jack 
	 * @date 2017年12月20日上午9:15:20
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/draw/drawPrizeListByIds")
	public void drawPrizeListByIds(HttpServletRequest request, HttpServletResponse response){
		
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				//中奖等级
				int drawLevel = WebParamUtils.getInteger("prizlevel", request);//中奖等级
				//中奖记录的ids集合
				String rdIds = WebParamUtils.getString("rdIds", request);
				List<Long> list = new ArrayList<Long>();
				if(StringUtils.isNotBlank(rdIds)){
					String[] idArr = rdIds.split(",");
					if(idArr!=null&&idArr.length>0){
						for (int i = 0; i < idArr.length; i++) {
							list.add(Long.parseLong(idArr[i]));
						}
					}
				}
				QueryRandomDrawReqDTO queryRandomDrawReqDTO = new QueryRandomDrawReqDTO();
				queryRandomDrawReqDTO.setPrizeLevel((short)drawLevel);
				queryRandomDrawReqDTO.setAttribute2(Constants.YEAR_CONFIG_NUMBER);
				queryRandomDrawReqDTO.setRdIds(list);
				List<RandomDraw> randomDrawByRdIdList = randomDrawService.queryRandomDrawByRdIdList(queryRandomDrawReqDTO);
				resultVo.setResult(randomDrawByRdIdList);
				return null;
			}
		}, request, response);
		
	}
	
	
	/**
	 * 
	 * 描述：推送消息
	 * @author Jack 
	 * @date 2017年12月20日上午10:26:46
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/draw/sendDrawMsgByIds")
	public void sendDrawMsgByIds(HttpServletRequest request, HttpServletResponse response){
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				
				int drawLevel = WebParamUtils.getInteger("prizlevel", request);//中奖等级
				//中奖记录的ids集合
				String rdIds = WebParamUtils.getString("rdIds", request);
				List<Long> list = new ArrayList<Long>();
				if(StringUtils.isNotBlank(rdIds)){
					String[] idArr = rdIds.split(",");
					if(idArr!=null&&idArr.length>0){
						for (int i = 0; i < idArr.length; i++) {
							list.add(Long.parseLong(idArr[i]));
						}
					}
				}
				QueryRandomDrawReqDTO queryRandomDrawReqDTO  = new QueryRandomDrawReqDTO();
				queryRandomDrawReqDTO.setPrizeLevel((short)drawLevel);
				queryRandomDrawReqDTO.setAttribute2(Constants.YEAR_CONFIG_NUMBER);
				queryRandomDrawReqDTO.setRdIds(list);
				 List<QueryWxAccountRespDTO> wxAccounts = wxAccountService.queryWxAccountRandomDrawByRdId(queryRandomDrawReqDTO);
				if(null!=wxAccounts&&wxAccounts.size()>0){
					
					for(QueryWxAccountRespDTO wax:wxAccounts){
						
						TemplateDataDTO item1 = new TemplateDataDTO();
						item1.setValue("恭喜您，中奖啦！");
						item1.setColor("#FF0000");
						
						TemplateDataDTO item2 = new TemplateDataDTO();
						item2.setValue(wax.getAttribute3());
						
						TemplateDataDTO item3 = new TemplateDataDTO();
						String prizeStr = "未中奖";
						if(drawLevel==0){
							prizeStr = "特等奖";
						}else if(drawLevel==1){
							prizeStr = "一等奖";
						}else if(drawLevel==2){
							prizeStr = "二等奖";
						}else if(drawLevel==3){
							prizeStr = "三等奖";
						}else if(drawLevel==4){
							prizeStr = "四等奖";
						}else if(drawLevel==5){
							prizeStr = "五等奖";
						}else if(drawLevel==6){
							prizeStr = "六等奖";
						}
						item3.setValue(prizeStr);
						
						TemplateDataDTO item4 = new TemplateDataDTO();
						item4.setValue(DateUtil.formatDate(wax.getPrizeDtm(), DateUtil.YYYYMMDDHHMMSS));
						
						TemplateDataDTO item5 = new TemplateDataDTO();
						item5.setValue("请关注平台消息");
						
						Map<String, TemplateDataDTO> map = new HashMap<String, TemplateDataDTO>();
						map.put("first", item1);
						map.put("keyword1", item2);
						map.put("keyword2", item3);
						map.put("keyword3", item4);
						map.put("remark", item5);
						
						TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO();
						templateMessageDTO.setTouser(wax.getMpOpenId());
						templateMessageDTO.setTemplate_id(TPSConstants.draw_prize_successfully);
						templateMessageDTO.setData(map);
						JSONObject json = WXTemplateMsgSend.sendTemplateMsg(templateMessageDTO);
						logger.info(">>>>>>>>>>>>>>>>发送消息结果："+ json.toString()+"；中奖记录Id："+ wax.getWaId());
					}
				}
				return null;
			}
		}, request, response);
		
	}
	
	/**
	 * 
	 * 描述：签到领取礼品
	 * @author Jack 
	 * @date 2017年12月26日下午5:40:42
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/draw/wxScavengPrize")
	public void getUrl(HttpServletRequest request, HttpServletResponse response){
		//请求微信端获取openid实现自动登录
		try {
			String redirectUri = Constants.BASE_PATH+"/draw/wxAutoCallback.do";
			//微信端网页授权code值
	    	String requestUrl = MessageFormat.format(TPSConstants.GETOAUTHCODE,
					TPSConstants.APPID,
					URLEncoder.encode(redirectUri,"UTF-8"),
					"code",
					"snsapi_userinfo",
					"STATE");
		   ((HttpServletResponse)response).sendRedirect(requestUrl);
		} catch (Exception e) {
			logger.error(">>>>>>>>>>扫码发生异常：",e);
		}
		
	}
	
	/**
	 * 
	 * 描述：微信回调地址
	 * @author Jack 
	 * @date 2017年12月27日上午9:07:30
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/draw/wxAutoCallback")
	public ModelAndView wxAutoCallback(@RequestParam("code") String code,HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/app/jsp/module/success/prizeSuccess.jsp");
		mav.addObject("successMsg", "领取成功！");
		if(code!=null){
				WXOAuthWebPage wxOAuthWebPage = new WXOAuthWebPage();//获取微信授权对象
				//获取用户AccessToken
				JSONObject jsonAccessToken = wxOAuthWebPage.GetOAuthAccessToken(code);
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>扫码返回信息："+ jsonAccessToken.toString());
				Object errcodeAccessToken = jsonAccessToken.get("errcode");
				if(errcodeAccessToken==null){//错误errcode为空表示访问成功
					String openid = jsonAccessToken.getString("openid");
					WxAccount currWxAccount = wxAccountService.queryByMpOpenId(openid);
					if(null!=currWxAccount){
						String userNumber = currWxAccount.getAttribute3();
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>会员领取奖品的编号："+userNumber);
						YearPayRecord yearPayRecord = new YearPayRecord();
						yearPayRecord.setUserNumber(userNumber);
						List<YearPayRecord> yearPayRecordList = yearPayRecordService.queryYearPayRecordList(yearPayRecord);
						if(yearPayRecordList!=null&&yearPayRecordList.size()>0){
							//支付状态 -1 终身会员 1 已支付 2 未支付 3 支付未完成
							int payStatus = yearPayRecordList.get(0).getPayStatus().intValue();
							if(payStatus==-1||payStatus==1){
								//更新
								RandomDraw randomDraw = new RandomDraw();
								randomDraw.setUserId(currWxAccount.getWaId());
								randomDraw.setAttribute2(Constants.YEAR_CONFIG_NUMBER);
								List<RandomDraw> randomDrawList = randomDrawService.queryRandomDrawList(randomDraw);
								if(randomDrawList!=null&&randomDrawList.size()>0){
									long rdId = randomDrawList.get(0).getRdId();
									
									String attribute3 = randomDrawList.get(0).getAttribute3();
									if(StringUtil.isEmpty(attribute3)){
										RandomDraw updateRandomDraw = new RandomDraw();
										updateRandomDraw.setRdId(rdId);
										updateRandomDraw.setAttribute3(openid);
										int result = randomDrawService.modifyRandomDrawById(updateRandomDraw);
										if(result<=0){
											mav.addObject("successMsg", "领取失败！");
											logger.info(">>>>>>>>>>>>>>>扫码失败！"+openid+";编号："+userNumber);
										}
									}else{
										mav.addObject("successMsg", "领取失败！请勿重复领取~~~");
										logger.info(">>>>>>>>>>>>>>>领取失败！重复领取！"+openid+";编号："+userNumber);
									}
								}else{
									mav.addObject("successMsg", "领取失败！");
									logger.info(">>>>>>>>>>>>>>>>>>抽奖信息表中未查询到数据："+userNumber);
								}
							}else{
								mav.addObject("successMsg", "领取失败！未支付或者未支付完成年费");
								logger.info(">>>>>>>>>>>>>>>>会员年会未支付或者未支付完成，payStatus="+payStatus+";编号："+userNumber);
							}
						}else{
							mav.addObject("successMsg", "领取失败！未支付或者未支付完成年费");
  							logger.info(">>>>>>>>>>>>>>>>>>会员年费统计信息表中未查询到数据："+userNumber);
						}
					}else{//没有注册的
						mav.addObject("successMsg", "领取失败！未注册会员客户");
						logger.info(">>>>>>>>>>>>>>>>>>>>>>>扫码失败！你还未注册成会员："+code);
					}
				}else{
					mav.addObject("successMsg", "领取失败！");
					logger.info(">>>>>>>>>>>>>>>>>>>>>>>扫码失败！返回错误信息："+errcodeAccessToken);
				}
			}else{
				mav.addObject("successMsg", "领取失败！网络超时");
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>扫码失败！"+code);
			}
			return mav;
	}
	
	

}
