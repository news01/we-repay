(function(){
	function bamboo(){};
	//初始化angularjs
	bamboo.angularApp = angular.module('change_realName', []);
	
	
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
		$scope.name_reg = /^[\u2E80-\u9FFF]+$/;//Unicode编码中的汉字范围
		$scope.reg = /^[\u4e00-\u9fa5a-z]+$/i;
		
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
			if(!$scope.name_reg.test(param.realName)){
				$scope.error_tips = "姓名只能为汉字！";
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