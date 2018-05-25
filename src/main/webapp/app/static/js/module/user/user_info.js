(function(){
	function bamboo(){};
	//初始化angularjs
	bamboo.angularApp = angular.module('user_info', []);
	
	
	/**
	 * 内容控制器
	 * @param $http http请求
	 * @param $rootScope 根作用域
	 * @param $scope 当前内容作用域
	 * @param $compile 服务 用于将html字符串或者一个DOM进行编译,返回一个链接函数
	 */
	bamboo.contentHandleCtrl = function ($http,$rootScope,$scope,$compile) {
		//用户详情
		$scope.userInfo = {};
		
		//是否已经会员注册
		$scope.fnIsRegister = function(){
			var isRegister = true;
			if($scope.userInfo['ccUserId']==null){
				isRegister = false;
				alert("未注册不能修改!");
			};
			return isRegister;
		} 
		
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
						$scope.userInfo = result;
					}
				}
			});
			
		}
		
		//跳转到会员注册页面
		$scope.jump = function(){
			location.href = IUSP.contextPath + '/user/touserinfo.do?param=2';
			
		}
		
		//打开模态
		$scope.openModal = function(contentId){
			$("#modal_module").show();
			$(contentId).addClass("active");
		}
		
		//关闭模态
		$scope.closeModal = function(){
			$("#modal_module").hide();
			$(".modal_module_child").removeClass("active");
		}
		
		//退出登陆
		$scope.fnLoginOut = function(){
			//退出登录
			IUSP.$http($http,{
				config : {
					url : IUSP.contextPath + '/login/logout.do',
					data : {}
				},
				success : function(resp) {
					if(resp.errCode == 0){
						window.location.href = IUSP.contextPath + "/login/tologin.do";
					}
				}
			});
		}
		
		//进入修改用户真实姓名界面
		$scope.fnToChangeRealName = function(){
			var isRegister = $scope.fnIsRegister();
			if(!isRegister){return false;}
			window.location.href = IUSP.contextPath + "/user/tochangerealname.do";
		}
		
		//进入修改手机界面
		$scope.fnToChangemobile = function(){
			var isRegister = $scope.fnIsRegister();
			if(!isRegister){return false;}
			window.location.href = IUSP.contextPath + "/user/tochangemobile.do";
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