package com.we.repay.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.MessageFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.we.repay.common.BaseController;
import com.we.repay.common.Constants;
import com.we.repay.po.user.WxAccount;
import com.we.repay.tps.common.TPSConstants;
import com.we.repay.util.json.JSONUtil;
import com.we.repay.util.model.ResultVo;


/**
 * @ClassName: SecurityFilter
 * @version 2.0
 * @Desc:  资源过滤器
 * @author huhuaping
 * @date 2017年6月19日下午3:55:18
 * @history v2.0
 */
public class SecurityFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 3000000030000001L;
	//日志
	public final Logger logger = Logger.getLogger(SecurityFilter.class);
	//接入认证 ---不过滤权限路径
	private String accessAuthStr;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		request.setCharacterEncoding("utf-8");
		response.setContentType("utf-8");
		response.setCharacterEncoding("UTF-8");
		StringBuffer buff = new StringBuffer();
		String servletPath = request.getServletPath();
		String contextPath=request.getContextPath();
		
		buff.append(contextPath);
		buff.append(servletPath);
		String  user_agent = ((HttpServletRequest) request).getHeader("user-agent");
		String ua = user_agent==null?"":user_agent.toLowerCase();//判断浏览器类型  
		try {
			long startTimeMillis = System.currentTimeMillis();
			//不验证路径
			if (!StringUtils.isEmpty(this.accessAuthStr)
					&& this.accessAuthStr.indexOf(servletPath) != -1) {
				chain.doFilter(request, response);
				return;
			}
			HttpSession session = request.getSession();
			WxAccount userqueryBo = (WxAccount) session.getAttribute(Constants.USER_AUTH_KEY);
			if(userqueryBo!=null){
				if(ua.indexOf("micromessenger")>0){ //微信浏览器进行自动登录操作
					//更新cookie有效时间
					Cookie sessionCookie = BaseController.isSesessionIdExist(request);
					sessionCookie.setPath("/");
					sessionCookie.setMaxAge(request.getSession().getMaxInactiveInterval());
					response.addCookie(sessionCookie);
				}
				chain.doFilter(request, response);
			}else{
				//判断访问路径是否是异步请求的
		    	String isAjax= request.getHeader("x-requested-with");
		    	if(StringUtils.isNotEmpty(isAjax)){
		    		//会话失效
		    		ResultVo resultVo = ResultVo.getSessionInvalid();
		    		//输出数据
					outJSONData(JSONUtil.toFormatJSON(resultVo), response);
		    	}else{
		    		//请求微信端获取openid实现自动登录
		    		String redirectURL = Constants.BASE_PATH+Constants.USER_LOGIN_URL;//默认重定向手动登录界面
		    		if(ua.indexOf("micromessenger")>0){ //微信浏览器进行自动登录操作
		    			redirectURL = Constants.BASE_PATH+Constants.USER_WXAUTHORIZE_LOGIN_URL;//默认重定向到二次授权页面进行手动授权登录
		    			String param = request.getQueryString();
		    			String redirectUri = Constants.BASE_PATH+"/user/wxAutoLogonCallback.do?redirectURL="+URLEncoder.encode(Constants.BASE_PATH+servletPath + (param==null?"":"?"+param),"UTF-8");
		    			//微信端网页授权code值
		    	    	String requestUrl = MessageFormat.format(TPSConstants.GETOAUTHCODE,
								TPSConstants.APPID,
								URLEncoder.encode(redirectUri,"UTF-8"),
								"code",
								"snsapi_base",
								"code");
		    	    	redirectURL = requestUrl;
		    		}
		    		((HttpServletResponse)response).sendRedirect(redirectURL);
		    	}
			}
			
			long endTimeMillis = System.currentTimeMillis();
			logger.info("请求消耗时间::"+(endTimeMillis-startTimeMillis));
		} catch (Exception e) {
			
			logger.error("过滤权限发生了异常\n",e);
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
	}

	public static void main(String[] args) {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		StringBuffer sb = new StringBuffer("");
		sb.append("/login/tologin.do"+",");
		sb.append("/login/login.do"+",");
		sb.append("/user/addInfo.do"+",");
		sb.append("/user/towxauthorize.do"+",");//自动登录二次授权页面
		sb.append("/user/wxAutoLogonCallback.do"+",");
		sb.append("/user/saveUser.do"+",");
		sb.append("/repay/notify.do"+",");
		sb.append("/user/toComplete_information.do"+",");
		sb.append("/user/complateUserInfo.do"+",");
		
		sb.append("/draw/wxScavengPrize.do"+",");
		sb.append("/draw/wxAutoCallback.do"+",");
		
		
		this.accessAuthStr = sb.toString(); 
		
	}
	
	/**
	 * 
	 * 描述：向页面输出数据
	 * @author huhuaping 
	 * @date 2017年6月19日上午10:01:28
	 * @param data
	 * @param response
	 */
	private void outJSONData(String data,ServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = null;
		try {
			//输出数据
			writer = response.getWriter();
			writer.print( data );
			
		} catch (IOException e) {
			logger.error("输出数据出错",e);
		}finally{
			//关闭流
			if(writer != null){
				writer.flush();
				writer.close();
			}
		}
	}

}
