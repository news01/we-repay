(function(){
	function bamboo(){};
	//初始化angularjs
	bamboo.angularApp = angular.module('wx_authorize', []);
	
	
	/**
	 * 内容控制器
	 * @param $http http请求
	 * @param $rootScope 根作用域
	 * @param $scope 当前内容作用域
	 * @param $compile 服务 用于将html字符串或者一个DOM进行编译,返回一个链接函数
	 */
	bamboo.contentHandleCtrl = function ($http,$rootScope,$scope,$compile) {
		
		//防止重复点击
		$scope.repeatedWxAuthorize = false;
		
		//默认用户中心
		$scope.redirectURL = "/user/touserinfo.do";
		
		//授权重新登陆
		$scope.fnWxAuthorize = function(){
			$scope.repeatedWxAuthorize = true;
			var redirectUri = IUSP.contextPath+"/user/wxAutoLogonCallback.do?supplement=true&&redirectURL="+$scope.redirectURL;
			var requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+IUSP.appid+"&redirect_uri="+encodeURIComponent(redirectUri)+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect"
			window.location.href = requestUrl;
			
//			String param = request.getQueryString();
//			String redirectUri = Constants.BASE_PATH+"/user/wxAutoLogonCallback.do?redirectURL="+URLEncoder.encode(Constants.BASE_PATH+servletPath + (param==null?"":"?"+param),"UTF-8");
//			//微信端网页授权code值
//	    	String requestUrl = MessageFormat.format(TPSConstants.GETOAUTHCODE,
//					TPSConstants.APPID,
//					URLEncoder.encode(redirectUri,"UTF-8"),
//					"code",
//					"snsapi_base",
//					"code#wechat_redirect");
//	    	redirectURL = requestUrl;
			
		}
		
		
		//初始化
		$scope.initHandleCtrl = function(){
			if(page_redirectURL){
				//初始化回调页面地址
				$scope.redirectURL = page_redirectURL;
			}
			
		}
		
		//初始化
		$scope.initHandleCtrl();
		
	}
	
	bamboo.start = function(){
		//初始化框架
		bamboo.initlayout();
	
		//内容操作控制器
		bamboo.angularApp.controller("contentHandleCtrl",bamboo.contentHandleCtrl);
	
	}
	
	/**
	 * 初始化框架
	 */
	bamboo.initlayout = function(){
		
		var winSize = IUSP.getInnerSize();
		
	}
	
	/**
	 * 赋值给BAMBOO
	 */
	window.BAMBOO = window.BAMBOO ? $.extend(window.BAMBOO ,bamboo):bamboo
})();