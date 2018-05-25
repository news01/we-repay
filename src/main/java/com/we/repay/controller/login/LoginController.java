/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.controller.login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.we.repay.common.BaseController;
import com.we.repay.common.Constants;
import com.we.repay.common.ControlHandler;
import com.we.repay.common.HandlerProxy;
import com.we.repay.dto.user.WxAccountLoginReqDTO;
import com.we.repay.po.user.WxAccount;
import com.we.repay.service.user.WxAccountService;
import com.we.repay.tps.service.WXOAuthWebPage;
import com.we.repay.tps.util.TimedPush;
import com.we.repay.util.WebParamUtils;
import com.we.repay.util.model.ResultVo;

/**
 * @ClassName: LoginController
 * @version 2.0
 * @Desc: 登录
 * @author tianzhongshan
 * @date 2017年7月12日下午4:59:35
 * @history v2.0
 *
 */
@Controller
public class LoginController extends BaseController {
	// 日志
	private Logger logger = Logger.getLogger(LoginController.class);

	// 验证手机号码
	@SuppressWarnings("unused")
	private final String TELEPHONEREG = "^1[3578]\\d{9}";

	// 验证邮箱
	@SuppressWarnings("unused")
	private final String EMAILREG = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.[a-zA-Z0-9_-]+)$";

	@Resource
	private PasswordEncoder passwordEncoder;

	@Autowired
	private WxAccountService wxAccountService;

	/**
	 * 描述：进入登录页面
	 * 
	 * @author tianzhongshan
	 * @date 2017年8月12日上午10:38:39
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login/tologin")
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 返回页面
				ModelAndView modelAndView = new ModelAndView("pc/jsp/module/user/login.jsp");
				System.out.println(request.getRequestURL());
				System.out.println(this.getClass().getClassLoader().getResource("/").getPath());
				System.out.println(Thread.currentThread().getContextClassLoader().getResource("")); 
				
				
				
				
				
				
				return modelAndView;
			}
		}, request, response);
	}

	/**
	 * 描述：用户登录
	 * 
	 * @author tianzhongshan
	 * @date 2017年7月12日下午5:04:02
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/login/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {

		HandlerProxy.assembleAjax(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				// 用户名
				String loginName = WebParamUtils.getString("loginName", request);
				if (StringUtils.isEmpty(loginName)) {
					resultVo.setErrMsg("用户名不能为空！");
					return null;
				}

				// 密码
				String pwd = WebParamUtils.getString("pwd", request);
				if (StringUtils.isEmpty(pwd)) {
					resultVo.setErrMsg("密码不能为空！");
					return null;
				}

				WxAccountLoginReqDTO wxAccountLoginReqDTO = new WxAccountLoginReqDTO();
				// //设置用户名
				// if
				// (Pattern.compile(EMAILREG).matcher(loginName.trim()).matches())
				// {
				// wxAccountLogin.setEmail(loginName);
				// } else if
				// (Pattern.compile(TELEPHONEREG).matcher(loginName.trim()).matches())
				// {
				// wxAccountLogin.setMobile(loginName);
				// } else {
				// wxAccountLogin.setCcUserName(loginName);
				// }
				wxAccountLoginReqDTO.setCcUserName(loginName);

				// 设置加密密码
				String passWord = passwordEncoder.encodePassword(pwd, null);
				wxAccountLoginReqDTO.setCcUserPwd(passWord);

				// 执行登录操作
				WxAccount currLoginWxAccount = wxAccountService.login(wxAccountLoginReqDTO);
				if (null != currLoginWxAccount) {// 登录成功
					// 保存用户信息 于session
					request.getSession().setAttribute(Constants.USER_AUTH_KEY, currLoginWxAccount);
					resultVo.setErrCode(0);
					resultVo.setErrMsg(loginName + "登录成功！");
					logger.info(">>>>>>>登录成功：" + resultVo.getErrMsg() + ";登录IP：" + getIp());
				} else {
					resultVo.setErrCode(1);
					resultVo.setErrMsg(loginName + "登录失败");
				}

				return null;
			}
		}, request, response);

	}

	/**
	 * 描述：退出系统 清除session 用户信息
	 * 
	 * @author tianzhongshan
	 * @date 2017年7月13日下午6:10:02
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {

		return HandlerProxy.assemble(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				ModelAndView mav = new ModelAndView("redirect:" + Constants.USER_LOGIN_URL);

				request.getSession().invalidate();
				// 设置cookie过期
				Cookie sessionCookie = BaseController.isSesessionIdExist(request);
				sessionCookie.setMaxAge(0);
				response.addCookie(sessionCookie);
				logger.info(">>>>>" + wxAccount.getMpOpenId() + " :退出系统成功！");

				return mav;
			}
		}, request, response);

	}

	//
	//
	// /**
	// * 描述：微信网页授权回调方法获取用户信息
	// * @author tianzhongshan
	// * @date 2017年7月27日下午4:35:29
	// * @param code 获取用户信息微信授权code
	// * @param sendRedirectURL 静默授权完成后重定向页面
	// * @param request
	// * @param response
	// */
	// @RequestMapping(value = "/login/wxoauthcallback")
	// public void wxOAuthCallback(@RequestParam("code")String
	// code,@RequestParam("redirectURL")String
	// sendRedirectURL,HttpServletRequest request, HttpServletResponse response)
	// {
	// try {
	// WxAccount userQueryBo = (WxAccount)
	// request.getSession().getAttribute(Constants.USER_AUTH_KEY);
	// WXOAuthWebPage wxOAuthWebPage = new WXOAuthWebPage();//获取微信授权对象
	// //获取用户AccessToken
	// JSONObject jsonAccessToken = wxOAuthWebPage.GetOAuthAccessToken(code);
	// Object errcodeAccessToken = jsonAccessToken.get("errcode");
	// if(errcodeAccessToken==null){//错误errcode为空表示访问成功
	// String accessToken = jsonAccessToken.getString("access_token");
	// String openid = jsonAccessToken.getString("openid");
	// //获取用户信息
	// JSONObject userInfo = wxOAuthWebPage.GetOAuthUserInfo(accessToken,
	// openid);
	// Object errcodeUserInfo = userInfo.get("errcode");
	// if(errcodeUserInfo==null){
	// WxAccount wxAccount = new WxAccount();
	// wxAccount.setCcUserId(userQueryBo.getUserId());
	// wxAccount.setCcUserName(userQueryBo.getAccount());
	// wxAccount.setMpOpenId((String) userInfo.get("openid"));
	// wxAccount.setNickname((String) userInfo.get("nickname"));
	// wxAccount.setSex((Integer) userInfo.get("sex"));
	// wxAccount.setProvince((String) userInfo.get("province"));
	// wxAccount.setCity((String) userInfo.get("city"));
	// wxAccount.setCountry((String) userInfo.get("country"));
	// wxAccount.setHeadimgurl((String) userInfo.get("headimgurl"));
	// @SuppressWarnings("rawtypes")
	// List privilege = (List) userInfo.get("privilege");
	// if(privilege!=null&&privilege.size()>0){
	// wxAccount.setPrivilege(JSONArray.fromObject(privilege).toString());
	// }
	// wxAccount.setUnionid((String) userInfo.get("unionid"));
	// wxAccount.setLanguage(Locale.CHINA.toString());
	// wxAccountService.addWxAccount(wxAccount);
	// }
	// }
	// //转发
	// ((HttpServletResponse)response).sendRedirect(Constants.BASE_PATH+sendRedirectURL);
	// }catch (Exception e) {
	// e.printStackTrace();
	// logger.error("微信绑定用户出现错误",e);
	// }
	//
	// }

	/**
	 * 描述：保存微信用户信息
	 * 
	 * @author tianzhongshan
	 * @date 2017年9月20日上午10:49:04
	 */
	public void addWxAccount(String openid) {
		WXOAuthWebPage wxOAuthWebPage = new WXOAuthWebPage();// 获取微信授权对象
		// 获取用户信息
		JSONObject userInfo = wxOAuthWebPage.GetUserInfo(openid);
		Object errcodeUserInfo = userInfo.get("errcode");
		if (errcodeUserInfo == null) {
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
			if (privilege != null && privilege.size() > 0) {
				wxAccount.setPrivilege(JSONArray.fromObject(privilege).toString());
			}
			wxAccount.setUnionid((String) userInfo.get("unionid"));
			wxAccount.setLanguage(Locale.CHINA.toString());

			// WxAccount wxAccount2 = new WxAccount();
			// List<WxAccount> LW =
			// wxAccountService.queryWxAccountList(wxAccount2);
			// String userNumber = LW.get(LW.size()-1).getAttribute3();//用户唯一编号
			// String newNumber = null;
			/*
			 * if(StringUtils.isBlank(userNumber)){//本字段不能为空，如果数据库最后一条记录为空，
			 * 设为默认初始1000 wxAccount.setAttribute3("1000"); } else { for (int i =
			 * 0; i < userNumber.length(); i++) { if
			 * (Character.isDigit(userNumber.charAt(i))){ newNumber =
			 * userNumber.substring(i); //
			 * System.out.println(userNumber.charAt(i)); break; } } // newNumber
			 * = userNumber.substring(2); newNumber =
			 * (Integer.valueOf(newNumber) + 1)+"";
			 * wxAccount.setAttribute3(newNumber); }
			 */

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = dateFormat.format(date);
			wxAccount.setAttribute4(currentTime);
			wxAccount.setAttribute1("0");// 关注但并非会员
			wxAccount.setRealName(nikeName);

			WxAccount WA = wxAccountService.queryByMpOpenId(opId);// 插入之前进行查询
			int result = 0;// 插入结果
			String push_result = null;// 推送结果
			if (WA == null) {
				result = wxAccountService.addWxAccount(wxAccount);
				logger.info("执行插入结果：" + result);
				if (result == 1) {
					push_result = TimedPush.push_news(wxAccount, 1);
				}
				/*
				 * if(StringUtils.isNotBlank(opId)){ String mobile =
				 * WA.getMobile(); if(StringUtils.isBlank(mobile)){ push_result
				 * = TimedPush.push_news(wxAccount,1); } }
				 */

			}

			logger.info("关注：" + result);

			// String opId2 = (String) userInfo.get("openid");
			/*
			 * if(StringUtils.isNotBlank(opId)){ // WxAccount WA =
			 * wxAccountService.queryByMpOpenId(opId); String mobile =
			 * WA.getMobile(); if(StringUtils.isBlank(mobile)){ push_result =
			 * TimedPush.push_news(wxAccount,1); }
			 * 
			 * }
			 */

			/*
			 * if(result > 0){ // wxAccount.setAttribute4(newNumber);//临时保存唯一标示
			 * push_result = TimedPush.push_news(wxAccount,1); }
			 */
			logger.info("订阅推送结果：" + push_result);
			logger.info("保存微信关注用户信息成功 :::: " + openid);
		}

	}

	/**
	 * 描述：单独向指定用户推送消息
	 * 
	 * @author news
	 * @date 下午1:57:04 2017年11月17日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/login/remindUser")
	public void remindUser(HttpServletRequest request, HttpServletResponse response) {

		HandlerProxy.assembleAjax(new ControlHandler() {

			@Override
			public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, WxAccount wxAccount,
					ResultVo resultVo) throws Exception {
				String wId = WebParamUtils.getString("data", request);

				WxAccount wxa = new WxAccount();
				wxa.setAttribute3(wId);
				List<WxAccount> LW = wxAccountService.queryWxAccountList(wxa);
				// System.out.println("LW>>>:"+LW);
				String result = null;
				if (LW.size() > 0) {
					WxAccount wax2 = LW.get(0);
					result = TimedPush.push_news(wax2, 1);
					// JSONObject jsonObject = JSONObject.fromObject(result);
					// String errcode = jsonObject.getString("errcode");
				}
				logger.info("result:" + result);
				resultVo.setResult(result);
				return null;
			}
		}, request, response);

	}

}
