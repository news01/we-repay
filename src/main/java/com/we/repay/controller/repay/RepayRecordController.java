/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.controller.repay;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.we.repay.common.BaseController;
import com.we.repay.common.Constants;
import com.we.repay.common.ControlHandler;
import com.we.repay.common.HandlerProxy;
import com.we.repay.dto.QueryRespDTO;
import com.we.repay.dto.repay.QueryAmoutSumReqDTO;
import com.we.repay.dto.repay.RepayRecordReqDTO;
import com.we.repay.pay.common.WXPayConstants.SignType;
import com.we.repay.pay.service.WXPay;
import com.we.repay.pay.to.WXPayConfig;
import com.we.repay.pay.util.WXPayUtil;
import com.we.repay.po.repay.RepayRecord;
import com.we.repay.po.repay.YearPayRecord;
import com.we.repay.po.user.WxAccount;
import com.we.repay.service.repay.RepayRecordService;
import com.we.repay.service.repay.YearPayRecordService;
import com.we.repay.service.user.WxAccountService;
import com.we.repay.tps.service.WXTPSPay;
import com.we.repay.util.DateUtil;
import com.we.repay.util.StringUtil;
import com.we.repay.util.WebParamUtils;
import com.we.repay.util.excel.BeanUtil;
import com.we.repay.util.excel.ExcelUtils;
import com.we.repay.util.model.ResultVo;

/**
 * @ClassName: RepayRecordController
 * @version 2.0
 * @Desc: 交易记录
 * @author tianzhongshan
 * @date 2017年7月12日下午4:59:35
 * @history v2.0
 *
 */
@Controller
public class RepayRecordController extends BaseController {
	// 日志
	private Logger logger = Logger.getLogger(RepayRecordController.class);

	@Autowired
	private RepayRecordService repayRecordService;

	@Autowired
	private WxAccountService wxAccountService;

	@Autowired
	private YearPayRecordService yearPayRecordService;

	/**
	 * 描述：进入交易记录列表页面
	 * 
	 * @author tianzhongshan
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/repay/torepayrecordlist")
	public ModelAndView showRepayRecordList(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("pc/jsp/module/repay/repay_record_list.jsp");
				return modelAndView;
			}
		}, request, response);
	}

	/**
	 * 描述：为他人支付后，进入交易记录列表页面
	 * 
	 * @author tianzhongshan
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/repay/tomyrepayrecord")
	public ModelAndView showMyRepayRecord(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/repay/my_repay_record.jsp");
				String user_key = WebParamUtils.getString("user_key", request);
				String param_total_fee = WebParamUtils.getString("param_total_fee", request);
				
				logger.info("user_key:" + user_key + ",---,param_total_fee:" + param_total_fee);

				logger.info("被支付的用户标示为：" + user_key);

				YearPayRecord yearPayRecord = new YearPayRecord();
				yearPayRecord.setUserNumber(user_key);

				Date time = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy");
				String year = format.format(time);
				yearPayRecord.setYear(year);

				List<YearPayRecord> LY = yearPayRecordService.queryYearPayRecordList(yearPayRecord);
				if (LY.size() > 0) {
					YearPayRecord payRecord = LY.get(0);
					Long id = payRecord.getId();
					payRecord.setId(id);
					Float oldF = payRecord.getYearAnnualFee();// 原有金额
					String nianfei = param_total_fee;
					Float f = Float.valueOf(nianfei);
					logger.info("替别人交付金额为：" + f);

					Float newF = f + oldF;// 累加的金额
					payRecord.setYearAnnualFee(newF);
					payRecord.setAttribute1(newF + "");// 年费缓存

					if (newF >= 300) {
						payRecord.setPayStatus((short) 1);
					}
					if (0 < newF && newF < 300) {
						payRecord.setPayStatus((short) 3);
					}
					if (newF <= 0) {
						payRecord.setPayStatus((short) 2);
					}
					payRecord.setPayTime(time);

					int resu = yearPayRecordService.modifyYearPayRecordById(payRecord);
					logger.info("为他人支付年费：" + resu);
					if (resu == 1) {
						logger.info("年费表维护成功");
						return modelAndView;
					}

				}

				return modelAndView;
			}
		}, request, response);
	}

	/**
	 * 描述：自己支付和热心赞助，进入记录页面
	 * @author news
	 * @date 下午2:31:09  2018年1月11日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/repay/tomyrepayrecord_myself")
	public ModelAndView tomyrepayrecord_myself(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/repay/my_repay_record.jsp");
				return modelAndView;
			}
		}, request, response);
	}
	
	
	/**
	 * 描述：进入交易记录统计列表页面
	 * 
	 * @author tianzhongshan
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/repay/torepaycountlist")
	public ModelAndView showRepayCountList(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("pc/jsp/module/repay/repay_count_list.jsp");
				// ModelAndView modelAndView = new
				// ModelAndView("pc/jsp/module/repay/repay_statusCount_list.jsp");
				return modelAndView;
			}
		}, request, response);
	}

	/**
	 * 描述：查询支付列表
	 * 
	 * @author tianzhongshan
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/repay/repayrecordlist")
	public void queryRepayRecordList(HttpServletRequest request, HttpServletResponse response) {

		final RepayRecordReqDTO repayRecordReqDTO = new RepayRecordReqDTO();
//		final YearPayRecord yearPayRecord = new YearPayRecord();

		HandlerProxy.assembleAjaxGrid(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				if (wxAccount.getWaId() != 1) {
					repayRecordReqDTO.setWxOpenId(wxAccount.getMpOpenId());
				}
				QueryRespDTO<RepayRecord> repayRecordPageResp = repayRecordService
						.queryRepayRecordPage(repayRecordReqDTO);
				resultVo.setGridData(repayRecordPageResp);

				return null;
			}
		}, request, response, repayRecordReqDTO);

	}

	/**
	 * 初始化支付记录
	 * 
	 * @author news
	 * @date 下午5:03:19 2017年11月16日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/repay/repay_statusRecordlist")
	public void repay_statusRecordlist(HttpServletRequest request, HttpServletResponse response) {

		final YearPayRecord yearPayRecord = new YearPayRecord();

		HandlerProxy.assembleAjaxGrid(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				QueryRespDTO<YearPayRecord> QY = yearPayRecordService.queryAmountSum(yearPayRecord);

				resultVo.setGridData(QY);

				return null;
			}
		}, request, response, yearPayRecord);

	}

	/**
	 * 描述：查询支付统计列表
	 * 
	 * @author tianzhongshan
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/repay/repaycountlist")
	public void queryRepayCountList(HttpServletRequest request, HttpServletResponse response) {

		final QueryAmoutSumReqDTO queryAmoutSumReqDTO = new QueryAmoutSumReqDTO();

		HandlerProxy.assembleAjaxGrid(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {

				QueryRespDTO<RepayRecord> repayRecordResp = repayRecordService.queryAmountSum(queryAmoutSumReqDTO);
				resultVo.setGridData(repayRecordResp);

				return null;
			}
		}, request, response, queryAmoutSumReqDTO);

	}

	/**
	 * 描述：进入缴费首页
	 * 
	 * @author news
	 * @date 下午2:34:26 2017年12月1日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/repay/torepayindex")
	public ModelAndView torepayindex(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/repay/repayIndex.jsp");
				return modelAndView;
			}
		}, request, response);
	}

	/**
	 * 描述：进入热心赞助页面
	 * 
	 * @author news
	 * @date 下午5:13:36 2017年12月7日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/repay/towarmsupport")
	public ModelAndView towarmsupport(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/repay/repayWarmSupport.jsp");
				return modelAndView;
			}
		}, request, response);
	}

	/**
	 * 描述：进入为自己支付界面
	 * 
	 * @author tianzhongshan
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/repay/torepay")
	public ModelAndView showRepay(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/repay/repay.jsp");
				return modelAndView;
			}
		}, request, response);
	}

	/**
	 * 描述：进入为他人支付界面
	 * 
	 * @author news
	 * @date 下午2:56:32 2017年12月1日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/repay/torepayforSomeone")
	public ModelAndView torepayforSomeone(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/repay/repayforSomeone.jsp");
				return modelAndView;
			}
		}, request, response);
	}

	/**
	 * 描述：统一下单调用微信支付 传入JSON格式字符串
	 * {"body":"","detail":"","total_fee":"","attach":""
	 * ,"goods_tag":"",limit_pay:""}
	 * 
	 * @author tianzhongshan
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/repay/crtorder")
	public void crtOrder(HttpServletRequest request, HttpServletResponse response) {

		HandlerProxy.assembleAjax(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 封装参数
				Map<String, String> data = new HashMap<String, String>();
				// 封装公共参数
				data.put("device_info", "WEB"); // 设备号
				data.put("attach", "精华隆"); // 附加数据
				String out_trade_no = new Date().getTime() + "" + (int) (Math.random() * 1000000);
				data.put("out_trade_no", out_trade_no); // 商户订单号
				data.put("fee_type", "CNY"); // 标价币种
				data.put("spbill_create_ip", getIp()); // 终端IP

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date currDate = new Date();
				String time_start = sdf.format(currDate);
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, 1);
				String time_expire = sdf.format(calendar.getTime());
				data.put("time_start", time_start); // 交易起始时间
				data.put("time_expire", time_expire); // 交易结束时间
				data.put("trade_type", "JSAPI"); // 交易类型
				String mpOpenId = wxAccount.getMpOpenId();
				data.put("openid", mpOpenId); // 用户标识

				// 获取参数并解析
				String param = WebParamUtils.getString("param", request);
				JSONObject jsonParam = JSONObject.fromObject(param);
				double param_total_fee = jsonParam.getDouble("total_fee");
				String total_fee = String.valueOf((int) (param_total_fee * 100));
				data.put("total_fee", total_fee); // 标价金额
				data.put("body", jsonParam.getString("body")); // 商品描述

				WXPay wxPay = WXTPSPay.getWXPay();
				Map<String, String> resp = wxPay.unifiedOrder(data);
				if ("SUCCESS".equals(resp.get("return_code")) && "SUCCESS".equals(resp.get("result_code"))) {

					String prepay_id = resp.get("prepay_id");
					String code_url = resp.get("code_url");

					// 获取微信支付配置
					WXPayConfig wxPayConfig = WXTPSPay.getWXPayConfig();

					// 封装下单结果H5调起微信支付参数
					Map<String, String> result = new HashMap<String, String>();
					result.put("appId", Constants.INNOAPPID);
					result.put("nonceStr", WXPayUtil.generateUUID());
					result.put("package", "prepay_id=" + prepay_id);
					result.put("signType", "MD5");
					result.put("timeStamp", new Date().getTime() + "");
					result.put("paySign", WXPayUtil.generateSignature(result, wxPayConfig.getKey(), SignType.MD5));
					result.put("code_url", code_url);

					// 保存订单
					RepayRecord repayRecord = new RepayRecord();
					repayRecord.setWaId(wxAccount.getWaId());
					repayRecord.setOrdId(out_trade_no);
					repayRecord.setWxOpenId(mpOpenId);
					repayRecord.setStatus((short) 0);
					repayRecord.setAmount((float) param_total_fee);

					String name = wxAccount.getRealName();
					repayRecord.setPayMan(name);
					logger.info("会费缴纳的用户为：" + name);

					repayRecord.setCreateDtm(currDate);
					repayRecord.setUpdateDtm(currDate);
					repayRecord.setAttribute1(wxPayConfig.getUseSandbox() ? "true" : "false");
					repayRecord.setAttribute3("1");// 缴费类别；1-会费缴纳；2-为他人缴费（将姓名带过去）;3-热心赞助

					String leiBie = wxAccount.getAttribute1();// 用户类别
					logger.info("用户类别：" + leiBie);
					repayRecord.setAttribute4(leiBie);

					int add = repayRecordService.addRepayRecord(repayRecord);
					if (add > 0) {
						resultVo.setResult(result);
						resultVo.setErrMsg("微信公众号支付统一下单保存订单数据成功...");

					} else {
						resultVo.setErrCode(-1);
						resultVo.setErrMsg("微信公众号支付统一下单保存订单数据发生错误...");
					}

				} else {
					resultVo.setErrCode(-1);
					resultVo.setErrMsg(resp.get("return_msg"));
				}
				logger.info(resultVo.getErrMsg());
				return null;
			}
		}, request, response);

	}

	/**
	 * 描述：为他人支付 传入JSON格式字符串
	 * {"body":"","detail":"","total_fee":"","attach":"","goods_tag"
	 * :"",limit_pay:""}
	 * 
	 * @author news
	 * @date 下午3:21:02 2017年12月1日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/repay/repayforsomeone")
	public void repayforsomeone(HttpServletRequest request, HttpServletResponse response) {

		HandlerProxy.assembleAjax(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 获取参数并解析
				String param = WebParamUtils.getString("param", request);
				JSONObject jsonParam = JSONObject.fromObject(param);
				String realName = jsonParam.getString("realName");
				String address = jsonParam.getString("address");
				String mobile = jsonParam.getString("mobile");

				WxAccount wx2 = new WxAccount();
				wx2.setRealName(realName);
				wx2.setAttribute2(address);
				wx2.setMobile(mobile);

				List<WxAccount> LW = wxAccountService.queryWxAccountList(wx2);

				for (WxAccount wxAccount2 : LW) {
					logger.info(wxAccount2.getRealName() + ",--" + wxAccount2.getMobile() + ",--"
							+ wxAccount2.getAttribute2());
				}

				logger.info("要被支付的用户：" + LW.size());
				if (LW.size() <= 0) {
					// WxAccount wx = LW.get(0);
					resultVo.setResult("未找到此用户");
					return null;
				}
				if (LW.size() > 1) {
					resultVo.setResult("个人信息重复，请前去修改");
					return null;
				}

				// 封装参数
				Map<String, String> data = new HashMap<String, String>();
				// 封装公共参数
				data.put("device_info", "WEB"); // 设备号
				data.put("attach", "精华隆"); // 附加数据
				String out_trade_no = new Date().getTime() + "" + (int) (Math.random() * 1000000);
				data.put("out_trade_no", out_trade_no); // 商户订单号
				data.put("fee_type", "CNY"); // 标价币种
				logger.info("获取ip：" + getIp());
				data.put("spbill_create_ip", getIp()); // 终端IP

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date currDate = new Date();
				String time_start = sdf.format(currDate);
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, 1);
				String time_expire = sdf.format(calendar.getTime());
				data.put("time_start", time_start); // 交易起始时间
				data.put("time_expire", time_expire); // 交易结束时间
				data.put("trade_type", "JSAPI"); // 交易类型
				String mpOpenId = wxAccount.getMpOpenId();
				data.put("openid", mpOpenId); // 用户标识

				//
				double param_total_fee = jsonParam.getDouble("total_fee");
				String total_fee = String.valueOf((int) (param_total_fee * 100));
				data.put("total_fee", total_fee); // 标价金额
				data.put("body", jsonParam.getString("body")); // 商品描述

				WXPay wxPay = WXTPSPay.getWXPay();
				Map<String, String> resp = wxPay.unifiedOrder(data);

				Date time = new Date();
				if ("SUCCESS".equals(resp.get("return_code")) && "SUCCESS".equals(resp.get("result_code"))) {

					String prepay_id = resp.get("prepay_id");
					String code_url = resp.get("code_url");

					// 获取微信支付配置
					WXPayConfig wxPayConfig = WXTPSPay.getWXPayConfig();

					// 封装下单结果H5调起微信支付参数
					Map<String, String> result = new HashMap<String, String>();
					result.put("appId", Constants.INNOAPPID);
					result.put("nonceStr", WXPayUtil.generateUUID());
					result.put("package", "prepay_id=" + prepay_id);
					result.put("signType", "MD5");
					result.put("timeStamp", time.getTime() + "");
					result.put("paySign", WXPayUtil.generateSignature(result, wxPayConfig.getKey(), SignType.MD5));
					result.put("code_url", code_url);

					// 保存订单
					RepayRecord repayRecord = new RepayRecord();
					repayRecord.setWaId(wxAccount.getWaId());
					repayRecord.setOrdId(out_trade_no);
					repayRecord.setWxOpenId(mpOpenId);
					repayRecord.setStatus((short) 0);
					repayRecord.setAmount((float) param_total_fee);

					String name = wxAccount.getRealName();
					repayRecord.setPayMan(name);
					logger.info("为他人支付的用户是：" + name);

					repayRecord.setCreateDtm(currDate);
					repayRecord.setUpdateDtm(currDate);
					repayRecord.setAttribute1(wxPayConfig.getUseSandbox() ? "true" : "false");
					repayRecord.setAttribute2("1");// 是否已被统计；1-已被统计
					repayRecord.setAttribute3(realName);// 缴费类别；1-会费缴纳；2-为他人缴费（将姓名带过去）;3-热心赞助

					String leiBie = wxAccount.getAttribute1();// 用户类别
					logger.info("用户类别：" + leiBie);
					repayRecord.setAttribute4(leiBie);

					int add = repayRecordService.addRepayRecord(repayRecord);
					if (add > 0) {
						WxAccount wa = LW.get(0);
						/*
						 * String user_key = wa.getAttribute3();
						 * logger.info("被支付的用户标示为："+user_key);
						 * 
						 * YearPayRecord yearPayRecord = new YearPayRecord();
						 * yearPayRecord.setUserNumber(user_key);
						 * 
						 * SimpleDateFormat format = new
						 * SimpleDateFormat("yyyy"); String year =
						 * format.format(time); yearPayRecord.setYear(year);
						 * 
						 * List<YearPayRecord> LY =
						 * yearPayRecordService.queryYearPayRecordList
						 * (yearPayRecord); if(LY.size() > 0){ YearPayRecord
						 * payRecord = LY.get(0); Long id = payRecord.getId();
						 * payRecord.setId(id); Float oldF =
						 * payRecord.getYearAnnualFee();//原有金额 String nianfei =
						 * param_total_fee+""; Float f = Float.valueOf(nianfei);
						 * logger.info("替别人交付金额为："+f);
						 * 
						 * Float newF = f + oldF;//累加的金额
						 * payRecord.setYearAnnualFee(newF);
						 * payRecord.setAttribute1(newF+"");//年费缓存
						 * 
						 * if(newF >= 300){ payRecord.setPayStatus((short)1); }
						 * if(0 < newF && newF < 300){
						 * payRecord.setPayStatus((short)3); } if(newF <= 0){
						 * payRecord.setPayStatus((short)2); }
						 * payRecord.setPayTime(currDate);
						 * 
						 * int resu =
						 * yearPayRecordService.modifyYearPayRecordById
						 * (payRecord); logger.info("为他人支付年费："+resu);
						 * 
						 * }
						 */

						String a3 = wa.getAttribute3();

						wa.setAttribute3(a3);
						wa.setAttribute6(param_total_fee + "");
						JSONObject resultData = JSONObject.fromObject(wa);
						result.put("resultData", resultData.toString());
						resultVo.setResult(result);
						resultVo.setErrMsg("微信公众号支付统一下单保存订单数据成功...");

					} else {
						resultVo.setErrCode(-1);
						resultVo.setErrMsg("微信公众号支付统一下单保存订单数据发生错误...");
					}

				} else {
					resultVo.setErrCode(-1);
					resultVo.setErrMsg(resp.get("return_msg"));
				}
				logger.info(resultVo.getErrMsg());
				return null;
			}
		}, request, response);

	}

	/**
	 * 描述：为他人支付成功后更改该用户状态
	 * 
	 * @author news
	 * @date 上午9:33:44 2017年12月8日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/repay/maintain_statistical")
	public void maintain_statistical(HttpServletRequest request, HttpServletResponse response) {

		HandlerProxy.assembleAjax(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {

				String param = WebParamUtils.getString("data", request);
				logger.info("为他人支付成功后更改该用户状态:" + param);
				JSONObject jsonParam = JSONObject.fromObject(param);

				String user_key = jsonParam.getString("attribute3");
				String param_total_fee = jsonParam.getString("attribute6");
				logger.info("user_key" + user_key + ",---,param_total_fee:" + param_total_fee);

				// String user_key = WebParamUtils.getString("attribute3",
				// request);
				// String param_total_fee =
				// WebParamUtils.getString("attribute6", request);

				// WxAccount wa = LW.get(0);
				// String user_key = wa.getAttribute3();
				logger.info("被支付的用户标示为：" + user_key);

				YearPayRecord yearPayRecord = new YearPayRecord();
				yearPayRecord.setUserNumber(user_key);

				Date time = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy");
				String year = format.format(time);
				yearPayRecord.setYear(year);

				List<YearPayRecord> LY = yearPayRecordService.queryYearPayRecordList(yearPayRecord);
				if (LY.size() > 0) {
					YearPayRecord payRecord = LY.get(0);
					Long id = payRecord.getId();
					payRecord.setId(id);
					Float oldF = payRecord.getYearAnnualFee();// 原有金额
					String nianfei = param_total_fee;
					Float f = Float.valueOf(nianfei);
					logger.info("替别人交付金额为：" + f);

					Float newF = f + oldF;// 累加的金额
					payRecord.setYearAnnualFee(newF);
					payRecord.setAttribute1(newF + "");// 年费缓存

					if (newF >= 300) {
						payRecord.setPayStatus((short) 1);
					}
					if (0 < newF && newF < 300) {
						payRecord.setPayStatus((short) 3);
					}
					if (newF <= 0) {
						payRecord.setPayStatus((short) 2);
					}
					payRecord.setPayTime(time);

					int resu = yearPayRecordService.modifyYearPayRecordById(payRecord);
					logger.info("为他人支付年费：" + resu);
					if (resu == 1) {
						resultVo.setErrCode(1);
					}

				}

				return null;
			}
		}, request, response);

	}

	/**
	 * 描述：热心赞助
	 * 
	 * @author news
	 * @date 下午5:22:42 2017年12月7日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/repay/warmSupport")
	public void warmSupport(HttpServletRequest request, HttpServletResponse response) {

		HandlerProxy.assembleAjax(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 封装参数
				Map<String, String> data = new HashMap<String, String>();
				// 封装公共参数
				data.put("device_info", "WEB"); // 设备号
				data.put("attach", "精华隆"); // 附加数据
				String out_trade_no = new Date().getTime() + "" + (int) (Math.random() * 1000000);
				data.put("out_trade_no", out_trade_no); // 商户订单号
				data.put("fee_type", "CNY"); // 标价币种
				data.put("spbill_create_ip", getIp()); // 终端IP

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date currDate = new Date();
				String time_start = sdf.format(currDate);
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, 1);
				String time_expire = sdf.format(calendar.getTime());
				data.put("time_start", time_start); // 交易起始时间
				data.put("time_expire", time_expire); // 交易结束时间
				data.put("trade_type", "JSAPI"); // 交易类型
				String mpOpenId = wxAccount.getMpOpenId();
				data.put("openid", mpOpenId); // 用户标识

				// 获取参数并解析
				String param = WebParamUtils.getString("param", request);
				JSONObject jsonParam = JSONObject.fromObject(param);
				double param_total_fee = jsonParam.getDouble("total_fee");
				String total_fee = String.valueOf((int) (param_total_fee * 100));
				data.put("total_fee", total_fee); // 标价金额
				data.put("body", jsonParam.getString("body")); // 商品描述

				WXPay wxPay = WXTPSPay.getWXPay();
				Map<String, String> resp = wxPay.unifiedOrder(data);
				if ("SUCCESS".equals(resp.get("return_code")) && "SUCCESS".equals(resp.get("result_code"))) {

					String prepay_id = resp.get("prepay_id");
					String code_url = resp.get("code_url");

					// 获取微信支付配置
					WXPayConfig wxPayConfig = WXTPSPay.getWXPayConfig();

					// 封装下单结果H5调起微信支付参数
					Map<String, String> result = new HashMap<String, String>();
					result.put("appId", Constants.INNOAPPID);
					result.put("nonceStr", WXPayUtil.generateUUID());
					result.put("package", "prepay_id=" + prepay_id);
					result.put("signType", "MD5");
					result.put("timeStamp", new Date().getTime() + "");
					result.put("paySign", WXPayUtil.generateSignature(result, wxPayConfig.getKey(), SignType.MD5));
					result.put("code_url", code_url);

					// 保存订单
					RepayRecord repayRecord = new RepayRecord();
					repayRecord.setWaId(wxAccount.getWaId());
					repayRecord.setOrdId(out_trade_no);
					repayRecord.setWxOpenId(mpOpenId);
					repayRecord.setStatus((short) 0);
					repayRecord.setAmount((float) param_total_fee);

					String name = wxAccount.getRealName();
					repayRecord.setPayMan(name);
					logger.info("热心赞助的用户是：" + name);

					repayRecord.setCreateDtm(currDate);
					repayRecord.setUpdateDtm(currDate);
					repayRecord.setAttribute1(wxPayConfig.getUseSandbox() ? "true" : "false");
					repayRecord.setAttribute2("1");// 是否已被统计；1-已被统计
					repayRecord.setAttribute3("3");// 缴费类别；1-会费缴纳；2-为他人缴费（将姓名带过去）;3-热心赞助

					String leiBie = wxAccount.getAttribute1();// 用户类别
					logger.info("用户类别：" + leiBie);
					repayRecord.setAttribute4(leiBie);

					int add = repayRecordService.addRepayRecord(repayRecord);
					if (add > 0) {
						resultVo.setResult(result);
						resultVo.setErrMsg("微信公众号支付统一下单保存订单数据成功...");

					} else {
						resultVo.setErrCode(-1);
						resultVo.setErrMsg("微信公众号支付统一下单保存订单数据发生错误...");
					}

				} else {
					resultVo.setErrCode(-1);
					resultVo.setErrMsg(resp.get("return_msg"));
				}
				logger.info(resultVo.getErrMsg());
				return null;
			}
		}, request, response);

	}

	/**
	 * 描述：支付结果通知地址
	 * 
	 * @author tianzhongshan
	 * @date 2017年9月22日下午2:13:51
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/repay/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) {
		// 响应消息
		PrintWriter out = null;
		try {

			Map<String, String> param = WXPayUtil.parseReqXml(request);
			logger.info("接收到微信支付结果通知参数:::" + JSONObject.fromObject(param).toString());

			// 表示响应的成功
			if ("SUCCESS".equals(param.get("return_code"))) {

				String out_trade_no = param.get("out_trade_no");
				Map<String, String> reqData = new HashMap<String, String>();
				reqData.put("out_trade_no", out_trade_no);
				WXPay wxPay = WXTPSPay.getWXPay();
				// 用微信支付订单号查询时如果是沙箱接入会出现问题
				Map<String, String> orderQuery = wxPay.orderQuery(reqData);
				// 表示查询微信支付订单成功
				if ("SUCCESS".equals(orderQuery.get("return_code")) && "SUCCESS".equals(orderQuery.get("return_code"))) {
					// 保存订单
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					RepayRecord repayRecord = new RepayRecord();
					repayRecord.setOrdId(orderQuery.get("out_trade_no"));
					String time_end = orderQuery.get("time_end");
					repayRecord.setUpdateDtm(sdf.parse(time_end));

					String trade_state = orderQuery.get("trade_state");
					if ("SUCCESS".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 1);
					} else if ("REFUND".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 3);
					} else if ("NOTPAY".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 0);
					} else if ("CLOSED".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 4);
					} else if ("REVOKED".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 5);
					} else if ("USERPAYING".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 6);
					} else if ("PAYERROR".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 2);
					}
					// 实际订单结算金额 更新数据库防止下单和实际金额不一致,以结算金额为准
					if ("SUCCESS".equals(trade_state)) {
						String total_fee = orderQuery.get("total_fee");
						repayRecord.setAmount(Float.parseFloat(total_fee) / 100);
					}

					int modify = repayRecordService.modifyRepayRecordByOrdId(repayRecord);
					logger.info("支付结果通知状态修改条数：：" + modify);

				}
			}

			// 待响应微信客户端表示支付结果通知已回调
			Map<String, String> result = new HashMap<String, String>();
			result.put("return_code", "SUCCESS");
			result.put("return_msg", "支付结果回调成功!");

			out = response.getWriter();
			out.print(WXPayUtil.mapToXml(result));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("支付结果通知返回错误...", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

	/**
	 * 描述：对状态为0--待支付 6-用户支付中 的交易记录进行状态修改
	 * 
	 * @author tianzhongshan
	 * @date 2017年10月10日上午10:40:47
	 * @return
	 */
	public Integer UpdateRepayStatus() {

		RepayRecord repayRecordResq = new RepayRecord();
		repayRecordResq.setInstatus("0,6");
		repayRecordResq.setAttribute1("false");
		List<RepayRecord> repayRecordList = repayRecordService.queryRepayRecordList(repayRecordResq);
		for (RepayRecord currRepayRecord : repayRecordList) {
			try {
				String out_trade_no = currRepayRecord.getOrdId();
				Map<String, String> reqData = new HashMap<String, String>();
				reqData.put("out_trade_no", out_trade_no);
				WXPay wxPay = WXTPSPay.getWXPay();
				// 用微信支付订单号查询时如果是沙箱接入会出现问题
				Map<String, String> orderQuery = wxPay.orderQuery(reqData);
				// 表示查询微信支付订单成功
				if ("SUCCESS".equals(orderQuery.get("return_code")) && "SUCCESS".equals(orderQuery.get("return_code"))) {
					// 保存订单
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					RepayRecord repayRecord = new RepayRecord();
					repayRecord.setOrdId(orderQuery.get("out_trade_no"));
					String time_end = orderQuery.get("time_end");
					repayRecord.setUpdateDtm(time_end == null ? new Date() : sdf.parse(time_end));

					String trade_state = orderQuery.get("trade_state");
					if ("SUCCESS".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 1);
					} else if ("REFUND".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 3);
					} else if ("NOTPAY".equals(orderQuery.get("trade_state"))) {
						// 判断当前订单是否已经超过一定时间，超过则让该订单关闭
						long time = currRepayRecord.getCreateDtm().getTime();
						if (new Date().getTime() - time >= 12 * 60 * 60 * 1000) {// /超过一天,关闭订单
							@SuppressWarnings("unused")
							Map<String, String> closeOrder = wxPay.closeOrder(reqData);
							repayRecord.setStatus((short) 4);
						} else {
							continue;
						}
					} else if ("CLOSED".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 4);
					} else if ("REVOKED".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 5);
					} else if ("USERPAYING".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 6);
					} else if ("PAYERROR".equals(orderQuery.get("trade_state"))) {
						repayRecord.setStatus((short) 2);
					}
					// 实际订单结算金额 更新数据库防止下单和实际金额不一致,以结算金额为准
					if ("SUCCESS".equals(trade_state)) {
						String total_fee = orderQuery.get("total_fee");
						repayRecord.setAmount(Float.parseFloat(total_fee) / 100);
					}
					repayRecordService.modifyRepayRecordByOrdId(repayRecord);
					logger.info("订单更新状态成功...当前订单号为：：" + out_trade_no);
				} else {// 订单状态有问题，下次不在扫描进行更新
					RepayRecord repayRecord = new RepayRecord();
					repayRecord.setOrdId(out_trade_no);
					repayRecord.setUpdateDtm(new Date());
					repayRecord.setStatus((short) 4);
					repayRecordService.modifyRepayRecordByOrdId(repayRecord);
					logger.info("订单状态异常关闭当前订单...当前订单号为：：" + out_trade_no);
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("支付状态更新发生错误...", e);
			}
		}

		return null;
	}
	
	
	/**
	 * 
	 * 描述：导出会员年会统计报表
	 * @author Jack 
	 * @date 2017年12月26日下午2:38:02
	 * @param ypIds
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/repay/exportYearPayRecordList")
	public void exportYearPayRecordList(@RequestParam("ypIds") final String ypIds,HttpServletRequest request, HttpServletResponse response){
		try {
			List<Long> list = new ArrayList<Long>();
			if(!StringUtil.isEmpty(ypIds)){
				String[] ypIdsArr = ypIds.split(",");
				for (String ypId : ypIdsArr) {
					list.add(Long.valueOf(ypId).longValue());
				}
			}
			List<YearPayRecord> yearPayRecordList = null;
			if(list!=null&&list.size()>0){
				yearPayRecordList = yearPayRecordService.queryYearPayRecordByIdList(list);
			}else{
				yearPayRecordList = yearPayRecordService.queryYearPayRecordByIdList(null);
			}
			
			List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
			if(yearPayRecordList!=null&&yearPayRecordList.size()>0){
				for (YearPayRecord bean:yearPayRecordList) {
					Map<String, Object> map = new HashMap<String, Object>();
					BeanUtil.beanTOMap2(bean, map);
					
					if(bean.getPayStatus()==-1){
						map.put("payStatus", "终生会员");
						map.put("yearAnnualFee", "无需缴费");
					}else if(bean.getPayStatus()==1){
						map.put("payStatus", "已支付");
					}else if(bean.getPayStatus()==2){
						map.put("payStatus", "未支付");
					}else if(bean.getPayStatus()==3){
						map.put("payStatus", "未完全支付");
					}else {
						map.put("payStatus", "未知");
					}
					
					
					if(bean.getUserNumber().contains("TB")){
						map.put("address", "田背村");
					}else if(bean.getUserNumber().contains("XY")){
						map.put("address", "下营村");
					}else if(bean.getUserNumber().contains("CS")){
						map.put("address", "赤水村");
					}else if(bean.getUserNumber().contains("DK")){
						map.put("address", "大坑村");
					}else if(bean.getUserNumber().contains("NC")){
						map.put("address", "南村");
					}else if(bean.getUserNumber().contains("ZA")){
						map.put("address", "嶂岸村");
					}else if(bean.getUserNumber().contains("HG")){
						map.put("address", "华光村");
					}else if(bean.getUserNumber().contains("BY")){
						map.put("address", "陂营村");
					}else if(bean.getUserNumber().contains("KP")){
						map.put("address", "葵坪村");
					}else{
						map.put("address", "其他");
					}
					
					if(bean.getPayTime()!=null){
						map.put("payTime", DateUtil.formatDate(bean.getPayTime(), DateUtil.YYYYMMDDHHMMSS));
					}
					
					if(bean.getYear()!=null){
						map.put("year", bean.getYear()+"年");
					}
					
					dataList.add(map);
				}
			}
			
			String[] titles = new String[] { "编号", "姓名", "缴费金额","状态","支付时间","地址","年份"};
			String[] fields = new String[] { "userNumber", "realName", "yearAnnualFee","payStatus","payTime","address","year"};
			int[] columnWidth = {5000,3000,3000,5000,6000,5000,4000,3000};
			HSSFWorkbook workbook = new HSSFWorkbook();
			workbook = ExcelUtils.excelPrint(workbook, "会员年费统计表", titles, fields, dataList, columnWidth, null);
			export(workbook, "会员年费统计表" + DateUtil.getCurrentDateString() + ".xls",response);
		} catch (Exception e) {
			logger.error(">>>>>>>导出会员年费统计表发生异常：",e);
		}
	}
	
	/**
	 * 
	 * 描述：导出excel表格
	 * @author Jack 
	 * @date 2017年8月12日上午10:59:07
	 * @param wb
	 * @param fileName //文件名称
	 * @param response
	 * @throws IOException
	 */
	protected void export(HSSFWorkbook wb, String fileName,HttpServletResponse response) throws IOException {
		// 设置response的编码方式
		response.setContentType("application/x-msdownload");
		// 解决中文乱码
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gbk"), "iso-8859-1"));
		OutputStream output = response.getOutputStream();
		wb.write(output);
		output.flush();
		output.close();
	}

}
