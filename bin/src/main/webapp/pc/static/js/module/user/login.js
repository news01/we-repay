(function(){
	function bamboo(){};
	//初始化angularjs
	bamboo.angularApp = angular.module('login', []);
	
	
	/**
	 * 内容控制器
	 * @param $http http请求
	 * @param $rootScope 根作用域
	 * @param $scope 当前内容作用域
	 * @param $compile 服务 用于将html字符串或者一个DOM进行编译,返回一个链接函数
	 */
	bamboo.contentHandleCtrl = function ($http,$rootScope,$scope,$compile) {
		$scope.loginErrorInfo = "";
		//提交登录
		$scope.submitLogin = function(param){
			//登录
			IUSP.$http($http,{
				config : {
					url : IUSP.contextPath + '/login/login.do',
					data : param
				},
				success : function(resp) {
					if (resp.errCode == 0) {
						//待重定向跳转界面
						var actualPage = "/user/touserlist.do";
						window.location.href = IUSP.contextPath+actualPage;
					} else {
						// 错误提醒
						$scope.loginErrorInfo = resp.errMsg;
					}
				}
			});
			
		}
		
		
		
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