/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.controller.user;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.we.repay.common.BaseController;
import com.we.repay.common.Constants;
import com.we.repay.common.ControlHandler;
import com.we.repay.common.HandlerProxy;
import com.we.repay.common.IDGenerateStrategy;
import com.we.repay.dto.QueryRespDTO;
import com.we.repay.dto.user.WxAccountReqDTO;
import com.we.repay.po.repay.YearPayRecord;
import com.we.repay.po.user.WxAccount;
import com.we.repay.service.repay.YearPayRecordService;
import com.we.repay.service.user.WxAccountService;
import com.we.repay.tps.service.WXOAuthWebPage;
import com.we.repay.tps.util.TimedPush;
import com.we.repay.util.StringUtil;
import com.we.repay.util.WebParamUtils;
import com.we.repay.util.model.ResultVo;

/**
 * @ClassName: LoginController
 * @version 2.0 
 * @Desc: 用户
 * @author tianzhongshan
 * @date 2017年7月12日下午4:59:35
 * @history v2.0
 *
 */
@Controller
public class UserController extends BaseController {
	//日志
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private PasswordEncoder passwordEncoder;

	@Autowired
	private WxAccountService wxAccountService;
	
	@Autowired
	private YearPayRecordService yearPayRecordService;
	
	//发送模板接口
//	String url = MessageFormat.format(TPSConstants.SENDTEMPLATEMSG, AccessTokenTask.accessToken);
	
	/**
	 * 描述：进入用户个人详情页面
	 * @author tianzhongshan 
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/touserinfo")
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				String param = request.getParameter("param");
//				System.out.println("1_param:>>"+param);
				ModelAndView modelAndView = null;
				if(param == null){
					// 返回页面
					modelAndView = new ModelAndView("app/jsp/module/user/user_info.jsp");
				} else if(param.equals("2")) {
					modelAndView = new ModelAndView("app/jsp/module/user/complete_information.jsp");
				}
				
				
				return modelAndView;
			}
		}, request, response);
	}
	
	
	
	/**
	 * 描述：进入阅读章程的页面
	 * @author news
	 * @date 下午5:48:30  2017年11月20日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/toRegulations")
	public ModelAndView toRegulations(HttpServletRequest request,HttpServletResponse response){
		
		return HandlerProxy.assemble(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
				
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/user/regulations.jsp");
				
				return modelAndView;
			}
		}, request, response);
		
	}
	
	
	
	/**
	 * 描述：进入完善信息的页面
	 * @author news
	 * @date 下午3:01:28  2017年11月15日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/toComplete_information")
	public ModelAndView toComplete_information(HttpServletRequest request,HttpServletResponse response){
		
		return HandlerProxy.assemble(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
				String openId = request.getParameter("param");
				String sku_number = request.getParameter("sku");
				
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/user/complete_information.jsp");
				modelAndView.addObject("openId", openId);
				modelAndView.addObject("sku_number", sku_number);
				
				return modelAndView;
			}
		}, request, response);
		
	}
	
	/**
	 * 描述：保存完善的信息（注册）
	 * @author news
	 * @date 上午10:29:22  2017年11月16日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/complateUserInfo")
	public void complateUserInfo(HttpServletRequest request,HttpServletResponse response){
		
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
				
				Integer idCode = IDGenerateStrategy.instance().fetchIDCode();//分配的用户唯一编号
				if(idCode==null){
					resultVo.setErrMsg("不能分配会员编号，请重试！");
					return null;
				}
				
				String realName = WebParamUtils.getString("realName", request);
				String phone = WebParamUtils.getString("phone", request);
				Integer sex = WebParamUtils.getInteger("sex", request);
				String address = WebParamUtils.getString("address", request);
				String email = WebParamUtils.getString("email", request);//邮箱
				String wxOpenId = WebParamUtils.getString("wxOpenId", request);
				String adressKey = WebParamUtils.getString("adressKey", request);
				String sku_number = WebParamUtils.getString("sku_number", request);//唯一标示
				
				logger.info("wxOpenId:"+wxOpenId+",__sku_number:"+sku_number);
				
				
				WxAccount wxAccount3 = new WxAccount();
				@SuppressWarnings("unused")
				YearPayRecord yearPayRecord = new YearPayRecord();
				
				String key = null;//标示
				
				if(StringUtils.isNotBlank(wxOpenId)){//通过关注公众号
					logger.info("通过关注公众号");
					WxAccount wx = wxAccountService.queryByMpOpenId(wxOpenId);
					long waId = wx.getWaId();
					wxAccount3.setWaId(waId);
					wxAccount3.setRealName(realName);
					wxAccount3.setMobile(phone);
					wxAccount3.setAttribute2(address);
					wxAccount3.setEmail(email);
					wxAccount3.setSex(sex);
					
//					logger.info("newSku_number:"+newSku_number);
					String attribute3 = adressKey + idCode;
					key = attribute3;
					wxAccount3.setAttribute3(attribute3);
					//设置云能账号和密码
					wxAccount3.setCcUserName(realName);

					String passWord = passwordEncoder.encodePassword(phone, null);
					wxAccount3.setCcUserPwd(passWord);
					
					/*yearPayRecord.setUserNumber(sku_number);
					List<YearPayRecord> LY = yearPayRecordService.queryYearPayRecordList(yearPayRecord);
					if(LY == null || LY.size() == 0){//
						
					} else {//已存在
						YearPayRecord y = LY.get(0);
						y.setRealName(realName);
						y.setUserNumber(attribute3);
						long id = y.getId();
						y.setId(id);
						int r = yearPayRecordService.modifyYearPayRecordById(y);
						logger.info("维护年费统计表"+r);
					}*/
					
					
				} 
				else if(StringUtils.isNotBlank(sku_number)){//手动添加
					logger.info("手动添加");
					
					String attribute3 = adressKey + idCode;
					wxAccount3.setAttribute3(sku_number);
					
					List<WxAccount> LW = wxAccountService.queryWxAccountList(wxAccount3);
					if(LW != null && LW.size() != 0){
						logger.info("手动添加LW:>>>>>:"+LW.size());
						WxAccount account = LW.get(0);
						if(account.getMobile().equals(phone)){//两次手机号一致
							wxAccount3.setRealName(realName);
							wxAccount3.setMobile(phone);
							wxAccount3.setAttribute2(address);
							wxAccount3.setEmail(email);
							wxAccount3.setSex(sex);
							wxAccount3.setWaId(account.getWaId());
							wxAccount3.setAttribute3(attribute3);
							//设置云能账号和密码
							wxAccount3.setCcUserName(realName);

							String passWord = passwordEncoder.encodePassword(phone, null);
							wxAccount3.setCcUserPwd(passWord);
							
						}
					}
					
				} 
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = dateFormat.format(date);
				wxAccount3.setAttribute5(currentTime);//注册时间
				wxAccount3.setAttribute1("1");//成为普通会员
				logger.info("分配的id："+idCode);
				wxAccount3.setCcUserId(Long.valueOf(idCode));
				int resault = wxAccountService.modifyWxAccountById(wxAccount3);
				
				if(resault == 1){//维护统计表
					YearPayRecord yearPayRecord2 = new YearPayRecord();
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy");
					String year = format.format(date);
//					---------
					Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    
			        //获取年
			        int year2 = c.get(Calendar.YEAR);
			        //获取月份，0表示1月份
			        int month = c.get(Calendar.MONTH) + 1;
			        if(month <= 2){
			        	year = (year2-1)+"";
			        }
//					---------
					
					yearPayRecord2.setYear(year);
					yearPayRecord2.setRealName(realName);
					yearPayRecord2.setUserNumber(key);
					yearPayRecord2.setPayStatus((short)2);
					yearPayRecord2.setYearAnnualFee((float)0);
					yearPayRecord2.setAttribute1("0");
		    		
					int resp = yearPayRecordService.addYearPayRecord(yearPayRecord2);
					if(resp == 1){
						logger.info("年费统计表添加成功");
					}
					
					
				}
				wxAccount3.setMpOpenId(wxOpenId);
				
				//修改完成后更新session中用户信息
				WxAccount user = wxAccountService.queryWxAccountById(wxAccount.getWaId());
				request.getSession().setAttribute(Constants.USER_AUTH_KEY, user);
				
				TimedPush.push_news(wxAccount3,2);
				resultVo.setResult(resault);
				
				return null;
			}
		}, request, response);
		
	}
	
	
	/**
	 * 描述：进入修改用户真实姓名页面
	 * @author tianzhongshan 
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/tochangerealname")
	public ModelAndView showChangeRealName(HttpServletRequest request, HttpServletResponse response) {
		
		return HandlerProxy.assemble(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/user/change_realName.jsp");
				return modelAndView;
			}
		}, request, response);
	}
	
	/**
	 * 描述：进入修改用户手机号页面
	 * @author news
	 * @date 下午6:06:36  2017年12月12日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/tochangemobile")
	public ModelAndView tochangemobile(HttpServletRequest request, HttpServletResponse response) {
		
		return HandlerProxy.assemble(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/user/change_mobile.jsp");
				return modelAndView;
			}
		}, request, response);
	}
	
	/**
	 * 描述：进入用户列表页面
	 * @author tianzhongshan 
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/touserlist")
	public ModelAndView showUserList(HttpServletRequest request, HttpServletResponse response) {
		
		return HandlerProxy.assemble(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("pc/jsp/module/user/user_list.jsp");
				return modelAndView;
			}
		}, request, response);
	}

	/**
	 * 描述：添加信息界面
	 * @author news
	 * @date 下午4:37:14  2017年11月6日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/addInfo")
	public ModelAndView addInfo(HttpServletRequest request,HttpServletResponse response){
		
		return HandlerProxy.assemble(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
				ModelAndView modelAndView = new ModelAndView("pc/jsp/module/user/add.jsp");
				
				return modelAndView;
			}
		}, request, response);
		
	}
	
	/**
	 * 描述：调出修改页面
	 * @author news
	 * @date 下午4:48:04  2017年11月14日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/updateInfo")
	public ModelAndView updateInfo(HttpServletRequest request,HttpServletResponse response){
		
		return HandlerProxy.assemble(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
				
				ModelAndView modelAndView = new ModelAndView("pc/jsp/module/user/update.jsp");
				
				return modelAndView;
			}
		}, request, response);
		
	}
	
	/**
	 * 描述：查询指定用户信息
	 * @author news
	 * @date 下午5:18:57  2017年11月14日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/queryTheUser")
	public void queryTheUser(HttpServletRequest request,HttpServletResponse response){
		
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
				String wId = WebParamUtils.getString("data", request);
				WxAccount account = null;
				if(StringUtils.isNotBlank(wId)){
					account = wxAccountService.queryWxAccountById(Long.valueOf(wId));
				}
				resultVo.setResult(account);
				
				return null;
			}
		}, request, response);
	}
	
	/**
	 * 描述：添加会员列表信息
	 * @author news
	 * @date 上午10:29:45  2017年11月7日
	 * @param user
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/saveUser")
	public void saveUser(@RequestParam("data") final String data, HttpServletRequest request,HttpServletResponse response){
		
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {			
				
				Integer idCode = IDGenerateStrategy.instance().fetchIDCode();//分配的用户唯一编号
				if(idCode==null){
					resultVo.setErrMsg("不能分配会员编号，请重试！");
					return null;
				}
				
				JSONObject jsonObject = JSONObject.fromObject(data);
//				String nickname = (String) jsonObject.get("nickname");
				String realName = (String) jsonObject.get("realName");
				String sex = (String) jsonObject.get("sex");
				String address = (String) jsonObject.get("address");
				logger.info("address:>>:"+address);
				String adressKey = (String) jsonObject.get("adressKey");//用户住址的key
				String Membership_level = (String) jsonObject.get("Membership_level");//用户级别 1：终生会员；2：普通会员
								
				Integer s_ex = null;
				if(StringUtils.isNotBlank(sex)){
					s_ex = Integer.valueOf(sex);
				}
				String mobile = (String) jsonObject.get("phone");
				String email = (String) jsonObject.get("email");
				
				WxAccount account = new WxAccount();
				account.setCcUserId(Long.valueOf(idCode));
//				account.setNickname(nickname);
				account.setRealName(realName);
				account.setSex(s_ex);
				account.setMobile(mobile);
				account.setEmail(email);
				account.setAttribute2(address);//街道或乡镇
				
				if(Membership_level.equals("1")){
					account.setAttribute1("-1");//为-1时为终生会员，不用支付年费',
				}else{
					account.setAttribute1("1");//为1时为普通
				}
				
				WxAccount wxAccount2 = new WxAccount();
				List<WxAccount> LW = wxAccountService.queryWxAccountList(wxAccount2);
				WxAccount a = LW.get(LW.size()-1);
				String userNumber = a.getAttribute3();//用户唯一编号
				
				String newNumber = null;
//				String userAddress = "";
				
				newNumber = adressKey + idCode;
				logger.info("newNumber:"+newNumber);
				account.setAttribute3(newNumber);
				
				
				/*if(StringUtils.isBlank(userNumber)){//本字段不能为空，如果数据库最后一条记录为空，设为默认初始1000
					userAddress = adressKey + "1000";
					userAddress = idCode+"";
					account.setAttribute3(userAddress);
				} else {
					for (int i = 0; i < userNumber.length(); i++) {
						if (Character.isDigit(userNumber.charAt(i))){
							newNumber = userNumber.substring(i);
							break;
						}
					}
					newNumber = adressKey + (Integer.valueOf(newNumber) + 1);
					newNumber = adressKey + idCode;
					logger.info("newNumber:"+newNumber);
					account.setAttribute3(newNumber);
				}*/
				
				int result =  wxAccountService.addWxAccount(account);
				if(result == 1){
					//手动添加用户信息到年费统计表
//					long waId = account.getWaId();
//					WxAccount account2 = wxAccountService.queryWxAccountById(waId);
					List<WxAccount> LW2 = wxAccountService.queryWxAccountList(account);
					WxAccount account2 = LW2.get(0);
					
					String realName2 = account2.getRealName();
					String codeKey = account2.getAttribute3();//会员标示
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyy");
					String year = format.format(date);
					String user_level = account2.getAttribute1();//会员等级
					
					YearPayRecord yearPayRecord = new YearPayRecord();
					yearPayRecord.setRealName(realName2);
					yearPayRecord.setUserNumber(codeKey);
//					---------
					Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    
			        //获取年
			        int year2 = c.get(Calendar.YEAR);
			        //获取月份，0表示1月份
			        int month = c.get(Calendar.MONTH) + 1;
			        if(month <= 2){
			        	year = (year2-1)+"";
			        }
//					---------
					
					yearPayRecord.setYear(year);
					if(user_level.equals("-1")){//终生会员
						yearPayRecord.setYearAnnualFee((float)-1);
						yearPayRecord.setPayStatus((short)-1);
					}else{
						yearPayRecord.setYearAnnualFee((float)0);
						yearPayRecord.setPayStatus((short)2);
						yearPayRecord.setAttribute1("0");
					}
					
					int resp = yearPayRecordService.addYearPayRecord(yearPayRecord);
					if(resp == 1){
						logger.info("年费统计表维护成功");
					}
					
					resultVo.setErrCode(1);
					resultVo.setResult("添加成功！");
				} else {
					resultVo.setErrCode(0);
					resultVo.setResult("添加失败！");
				}
				
				return null;
			}
		}, request, response);
		
	}
	
	/**
	 * 描述：修改用户信息
	 * @author news
	 * @date 下午3:50:38  2017年11月30日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/updateUser")
	public void updateUser(@RequestParam("data") final String data, HttpServletRequest request,HttpServletResponse response){
		
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
				
				Integer idCode = IDGenerateStrategy.instance().fetchIDCode();//分配的用户唯一编号
				if(idCode==null){
					resultVo.setErrMsg("不能分配会员编号，请重试！");
					return null;
				}
				
				JSONObject jsonObject = JSONObject.fromObject(data);
				String nickname = (String) jsonObject.get("nickname");
				String realName = (String) jsonObject.get("realName");
				String phone = (String) jsonObject.get("phone");
				String sex = (String) jsonObject.get("sex");
				String address = (String) jsonObject.get("address");
				String adressKey = (String) jsonObject.get("adressKey");
				String Membership_level = (String) jsonObject.get("Membership_level");
				String userId = (String) jsonObject.get("userId");
				String only_sign = null;
				String only_sign2 = null;
				
				WxAccount account = new WxAccount();
				
				if(StringUtils.isNotBlank(nickname)){
					account.setNickname(nickname);
				}
				if(StringUtils.isNotBlank(realName)){
					account.setRealName(realName);
				}
				if(StringUtils.isNotBlank(phone)){
					account.setMobile(phone);
				}
				if(StringUtils.isNotBlank(sex)){
					Integer s = Integer.valueOf(sex);
					account.setSex(s);
				}
				if(StringUtils.isNotBlank(address)){
					account.setAttribute2(address);
				}
				if(StringUtils.isNotBlank(adressKey)){
					Long waId = Long.valueOf(userId);
					WxAccount wAccount = wxAccountService.queryWxAccountById(waId);
					String user_key = wAccount.getAttribute3();
					
					String newAddress = null;
					String newSku_number = null;
					if(StringUtils.isBlank(user_key)){
						newAddress = adressKey + idCode;
					}else{
//						only_sign = user_key;
						for (int i = 0; i < user_key.length(); i++) {
							if (Character.isDigit(user_key.charAt(i))){
								newSku_number = user_key.substring(i);
								break;
							}
						}
						if(StringUtils.isBlank(newSku_number)){
							newSku_number = idCode+"";
						}
						
					}
					
					newAddress = adressKey + newSku_number;
					only_sign2 = newAddress;
					
					account.setAttribute3(newAddress);
				}
				if(StringUtils.isNotBlank(Membership_level) && Membership_level.equals("1")){
					account.setAttribute1("-1");
				}else{
					account.setAttribute1("1");
				}
				if(StringUtils.isNotBlank(userId)){
					Long id = Long.valueOf(userId);
					account.setWaId(id);
				}
				
				
				
				YearPayRecord yearPayRecord = new YearPayRecord();
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy");
				String year = format.format(date);
				yearPayRecord.setYear(year);
				yearPayRecord.setUserNumber(only_sign);
				
				List<YearPayRecord> LY = yearPayRecordService.queryYearPayRecordList(yearPayRecord);
				if(LY.size() > 0){
					YearPayRecord payRecord = LY.get(0);
					Long id = payRecord.getId();
					@SuppressWarnings("unused")
					float money = payRecord.getYearAnnualFee();//年费
					
					
					yearPayRecord.setYear(null);
					yearPayRecord.setId(id);
					yearPayRecord.setUserNumber(only_sign2);
					yearPayRecord.setRealName(realName);
					
					if(StringUtils.isNotBlank(Membership_level) && Membership_level.equals("1")){
						yearPayRecord.setYearAnnualFee((float)-1);
						yearPayRecord.setPayStatus((short)-1);
					}else{
						String nianFei = payRecord.getAttribute1();//缓存的年费
						if(nianFei.equals("-1") || nianFei.equals("-1.0")){
							nianFei = "0";
						}
						Float f = Float.valueOf(nianFei);
						yearPayRecord.setYearAnnualFee(f);
						yearPayRecord.setPayStatus((short)2);
						if(f >= 300){
							yearPayRecord.setPayStatus((short)1);
		        		}
		        		if(0 < f && f < 300){
		        			yearPayRecord.setPayStatus((short)3);
		        		}
		        		if(f <= 0){
		        			yearPayRecord.setPayStatus((short)2);
		        		}
					}
					
					int y = yearPayRecordService.modifyYearPayRecordById(yearPayRecord);
					logger.info("修改用户信息后年费统计表用户唯一标示："+y);
					
				}
				int result = wxAccountService.modifyWxAccountById(account);
				
//				System.out.println("修改用户信息："+result);
				resultVo.setResult(result);
				return null;
			}
		}, request, response);
		
	}
	
	
	/**
	 * 描述：查询用户列表
	 * @author tianzhongshan 
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/userlist")
	public void queryUserList(HttpServletRequest request, HttpServletResponse response) {
		
		final WxAccountReqDTO wxAccountReqDTO = new WxAccountReqDTO();
		
		HandlerProxy.assembleAjaxGrid(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
			
				QueryRespDTO<WxAccount> wxAccountResp = wxAccountService.queryWxAccountPage(wxAccountReqDTO);
//				List<WxAccount> LW = wxAccountResp.getRows();
				/*if(LW != null && LW.size() > 0){
					for (WxAccount wxAccount2 : LW) {
						String a1 = wxAccount2.getAttribute1();
						if(StringUtils.isBlank(a1) || !a1.equals("-1")){
							wxAccount2.setAttribute1("普通会员");
						} else if(a1.equals("-1")) {
							wxAccount2.setAttribute1("终生会员");
						}
					}
				}*/
				
				
				resultVo.setGridData(wxAccountResp);
				
				return null;
			}
		}, request, response, wxAccountReqDTO);
		
	}
	
	/**
	 * 描述：获取用户信息
	 * @author tianzhongshan 
	 * @date 2017年8月30日上午11:42:13
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/userinfo")
	public void queryUserInfo(HttpServletRequest request, HttpServletResponse response) {
		
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
//				System.out.println(wxAccount.getAttribute3());
//				System.out.println(wxAccount.getMpOpenId());
				
				WxAccount userInfo = wxAccountService.queryWxAccountById(wxAccount.getWaId());
				
				resultVo.setResult(userInfo);
				
				return null;
			}
		}, request, response);
		
	}
	
	/**
	 * 
	 * @author news
	 * @date 下午1:07:49  2017年11月18日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/getUserInfo")
	public void getqQueryUserInfo(HttpServletRequest request, HttpServletResponse response) {
		
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
				WxAccount userInfo = wxAccountService.queryWxAccountById(wxAccount.getWaId());
				resultVo.setResult(userInfo);
				
				return null;
			}
		}, request, response);
		
	}
	
	
	/**
	 * 描述：修改用户信息
	 * @author tianzhongshan 
	 * @date 2017年8月30日上午11:42:13
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/useredit")
	public void userLocationEdit(final WxAccount modifyWxAccount, HttpServletRequest request, HttpServletResponse response) {
		
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
				//设置公共参数
				modifyWxAccount.setWaId(wxAccount.getWaId());
				int edit = wxAccountService.modifyWxAccountById(modifyWxAccount);
				//修改完成后更新session中用户信息
				WxAccount user = wxAccountService.queryWxAccountById(wxAccount.getWaId());
				request.getSession().setAttribute(Constants.USER_AUTH_KEY, user);
				resultVo.setResult(edit);
				
				return null;
			}
		}, request, response);
		
	}
	
	/**
	 * 描述：删除用户
	 * @author news
	 * @date 上午10:35:25  2017年11月14日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/delUser")
	public void delUser(@RequestParam("data")final String data, HttpServletRequest request,HttpServletResponse response){
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
				String[] waIsStr = data.split(",");
				int result = 0;
				for (int i = 0; i < waIsStr.length; i++) {
					result = wxAccountService.deleteWxAccountById(Long.valueOf(waIsStr[i]));
				}
				
				if(result == 0){
					resultVo.setErrCode(0);
					resultVo.setErrMsg("删除失败");
				} else {
					resultVo.setErrCode(1);
					resultVo.setErrMsg("删除成功");
				}
				
				return null;
			}
		}, request, response);
	}
	

	/**
	 * 描述：修改用户密码信息
	 * @author tianzhongshan 
	 * @date 2017年8月30日上午11:42:13
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/user/updatepwd")
	public void updatePwd(final String oldCcUserPwd,final String newCcUserPwd,HttpServletRequest request, HttpServletResponse response) {
		
		HandlerProxy.assembleAjax(new ControlHandler() {
			
			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount, ResultVo resultVo)
					throws Exception {
				//原始密码
				String oldCcUserPwdStr = passwordEncoder.encodePassword(oldCcUserPwd, null);
				//新密码
				String newCcUserPwdStr = passwordEncoder.encodePassword(newCcUserPwd, null);
				if(newCcUserPwdStr!=null && wxAccount.getCcUserPwd()!=null && wxAccount.getCcUserPwd().equals(oldCcUserPwdStr)){//原始密码对比
					WxAccount modifyWxAccount= new WxAccount();
					//设置公共参数
					modifyWxAccount.setWaId(wxAccount.getWaId());
					//设置新密码
					modifyWxAccount.setCcUserPwd(newCcUserPwdStr);
					int edit = wxAccountService.modifyWxAccountById(modifyWxAccount);
					if(edit<=0){
						resultVo.setErrCode(-1);
						resultVo.setErrMsg("修改密码发生未知错误!");
					}
					//修改完成后更新session中用户信息
					WxAccount user = wxAccountService.queryWxAccountById(wxAccount.getWaId());
					request.getSession().setAttribute(Constants.USER_AUTH_KEY, user);
				}else{
					resultVo.setErrCode(-1);
					resultVo.setErrMsg("原始密码不一致,请正确输入!");
				}
				
				return null;
			}
		}, request, response);
		
	}
	
	/**
	 * 描述：进入微信二次自动登录授权登录页面
	 * @author tianzhongshan 
	 * @date 2017年12月26日下午2:47:16
	 * @param redirectURL 自动登录成功后回调页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/towxauthorize")
	public ModelAndView showWxAuthorize(@RequestParam("redirectURL") final String redirectURL,HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/user/wx_authorize.jsp");
				modelAndView.addObject("redirectURL", URLEncoder.encode(redirectURL,"UTF-8"));
				
				return modelAndView;
			}
		}, request, response);
	}
	
	/**
	 * 描述：微信网页授权回调方法实现自动登录
	 * @author tianzhongshan 
	 * @date 2017年7月27日下午4:35:29
	 * @param code 获取用户信息微信授权code
	 * @param sendRedirectURL 静默授权完成后重定向页面
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/user/wxAutoLogonCallback")
	public void wxOAuthCallback(@RequestParam("code")String code,@RequestParam("redirectURL")String redirectURL,HttpServletRequest request, HttpServletResponse response) {
		try {
			if(code!=null){
				WXOAuthWebPage wxOAuthWebPage = new WXOAuthWebPage();//获取微信授权对象
				//获取用户AccessToken
				JSONObject jsonAccessToken = wxOAuthWebPage.GetOAuthAccessToken(code);
				Object errcodeAccessToken = jsonAccessToken.get("errcode");
				if(errcodeAccessToken==null){//错误errcode为空表示访问成功
					String accessToken = jsonAccessToken.getString("access_token");
					String openid = jsonAccessToken.getString("openid");
					WxAccount currWxAccount = wxAccountService.queryByMpOpenId(openid);
					//是否是补充数据
					String supplement = WebParamUtils.getString("supplement", request);
					if(currWxAccount!=null){
						// 保存用户信息 于session
						request.getSession().setAttribute(Constants.USER_AUTH_KEY,currWxAccount);
						//重定向
						((HttpServletResponse)response).sendRedirect(redirectURL);
						logger.info("微信授权自动登录成功..."+redirectURL);
						return;
					}else if("true".equals(supplement)){
						
						//对登录数据进行补充
						JSONObject userInfo = wxOAuthWebPage.GetOAuthUserInfo(accessToken, openid);
						Object errcodeUserInfo = userInfo.get("errcode");
						if(errcodeUserInfo==null){
							WxAccount wxAccount = new WxAccount();
							wxAccount.setMpOpenId((String) userInfo.get("openid"));
							wxAccount.setNickname((String) userInfo.get("nickname"));
							wxAccount.setSex((Integer) userInfo.get("sex"));
							wxAccount.setProvince((String) userInfo.get("province"));
							wxAccount.setCity((String) userInfo.get("city"));
							wxAccount.setCountry((String) userInfo.get("country"));
							wxAccount.setHeadimgurl((String) userInfo.get("headimgurl"));
							List privilege = (List) userInfo.get("privilege");
							if(privilege!=null&&privilege.size()>0){
								wxAccount.setPrivilege(JSONArray.fromObject(privilege).toString());
							}
							wxAccount.setUnionid((String) userInfo.get("unionid"));
							wxAccount.setLanguage(Locale.CHINA.toString());
							wxAccountService.addWxAccount(wxAccount);
						}
						
						WxAccount currWxAccountSupp = wxAccountService.queryByMpOpenId(openid);
						// 保存用户信息 于session
						request.getSession().setAttribute(Constants.USER_AUTH_KEY,currWxAccountSupp);
						//重定向
						((HttpServletResponse)response).sendRedirect(redirectURL);
						logger.info("微信授权自动登录补充关注数据成功..."+redirectURL);
						return;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("微信授权自动登录出现错误",e);
		}
		
		try {
			//自动登录不成功 重定向到登陆页面手动登录
			((HttpServletResponse)response).sendRedirect(Constants.BASE_PATH+Constants.USER_WXAUTHORIZE_LOGIN_URL+"?redirectURL="+redirectURL);
			logger.info("重定向到登陆页面手动登录...");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("重定向到登陆页面手动登录失败...",e);
		}
		
	}
	
	/**
	 * 描述：进入公告牌页面
	 * @author tianzhongshan 
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/tobillboard")
	public ModelAndView showBillboard(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/user/billboard.jsp");
				return modelAndView;
			}
		}, request, response);
	}
	
	/**
	 * 描述：进入组织机构
	 * @author news
	 * @date 下午4:36:22  2017年12月12日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/toinstitutional")
	public ModelAndView toinstitutional(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/user/institutional.jsp");
				return modelAndView;
			}
		}, request, response);
	}
	
	/**
	 * 描述：进入友好提示页面
	 * @author news
	 * @date 上午10:44:56  2017年12月13日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/topaystatus")
	public ModelAndView topay_status(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request,
					HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("app/jsp/module/user/pay_status.jsp");
				return modelAndView;
			}
		}, request, response);
	}
	
	
	
	/**
	 * 推送消息（缴费）
	 * @author news
	 * @date 下午4:59:56  2017年11月15日
	 * @param wxAccount
	 * @return
	 */
	/*public String push_news_take(WxAccount wxAccount){
		TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO();
		String sku_number = wxAccount.getAttribute4();//获取唯一标示
		
		templateMessageDTO.setTouser(wxAccount.getMpOpenId());
		templateMessageDTO.setTemplate_id("JgejMU6iYvwMqtTupbHJV4Gy27MSjgAthbNuAgTVZF0");
		templateMessageDTO.setUrl("http://weixin.cloudcould.cc/we-repay/repay/torepay.do");
		TemplateDataDTO item1 = new TemplateDataDTO();
		item1.setValue("您的会费即将到期");
		item1.setColor("#173177");
		TemplateDataDTO item2 = new TemplateDataDTO();
		item2.setValue("同乡会管理系统");
		item2.setColor("#173177");
		TemplateDataDTO item3 = new TemplateDataDTO();
		item3.setValue("2018/01/30 前");
		TemplateDataDTO item4 = new TemplateDataDTO();
		item4.setValue("请到会员管理系统平台进行续费！谢谢");

		Map<String, TemplateDataDTO> map = new HashMap<String, TemplateDataDTO>();
		map.put("first", item1);
		map.put("keyword1", item2);
		map.put("keyword2", item3);
		map.put("remark", item4);
		
		templateMessageDTO.setData(map);
		JSONObject jsonObject = JSONObject.fromObject(templateMessageDTO);
		HttpsClientUtil httpsClientUtil = HttpsClientUtil.getInstance();
		JSONObject jsonObject2 = httpsClientUtil.sendPostRequestJson(url, jsonObject.toString());
		String errcode = jsonObject2.getString("errcode");
		if(errcode.equals("40001") || !errcode.equals("0")){//如果token过期
    		AccessTokenTask accessTokenTask = new AccessTokenTask();
    		JSONObject jsonObject3 = accessTokenTask.GetAccessToken();
    		//把token存起来
    		AccessTokenTask.accessToken = jsonObject3.getString("access_token");
    		url = TPSConstants.SENDTEMPLATEMSG.replace("{0}", AccessTokenTask.accessToken);
    		jsonObject2 = httpsClientUtil.sendPostRequestJson(url, jsonObject.toString());
			
		}
		return jsonObject2.toString();
	}*/
}
