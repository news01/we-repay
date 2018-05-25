(function(){
	function bamboo(){};
	//初始化angularjs
	bamboo.angularApp = angular.module('change_mobile', []);
	
	
	/**
	 * 内容控制器
	 * @param $http http请求
	 * @param $rootScope 根作用域
	 * @param $scope 当前内容作用域
	 * @param $compile 服务 用于将html字符串或者一个DOM进行编译,返回一个链接函数
	 */
	bamboo.contentHandleCtrl = function ($http,$rootScope,$scope,$compile) {
		$scope.loginErrorInfo = "";
		//初始化信息
		$scope.param = {};
//		$scope.mobile = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
		$scope.mobile = /^1[2|3|4|5|6|7|8|9][0-9]{9}$/;
		
		//获取用户详情
		$scope.fnUserInfo = function(){
			var param = {};
			IUSP.$http($http,{
				config : {
					url : IUSP.contextPath + '/user/userinfo.do',
					data : param
				},
				success : function(resp) {
					if(resp.errCode == 0){
						var result = resp.result;
						$scope.param['realName'] = result['realName'];
					}
				}
			});
			
		}
		
		//确定修改
		$scope.submitChangeName = function(param){
			
			if(!$scope.mobile.test(param.mobile)){
				$scope.error_tips = "手机格式不正确！";
				return false;
			}
			
			IUSP.$http($http,{
				config : {
					url : IUSP.contextPath + '/user/useredit.do',
					data : param
				},
				success : function(resp) {
					resp.errCode == 0 ? IUSP.historyGo(window,-1) : $scope.ErrorInfo = resp.errMsg;
				}
			});
			
		}
		
		//初始化
		$scope.initHandleCtrl = function(){
			//获取用户详情
			$scope.fnUserInfo();
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