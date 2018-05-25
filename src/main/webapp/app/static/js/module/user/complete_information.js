(function(){
	function bamboo(){};
	//初始化angularjs
	bamboo.angularApp = angular.module('complete_information', []);
	
	
	/**
	 * 内容控制器
	 * @param $http http请求
	 * @param $rootScope 根作用域
	 * @param $scope 当前内容作用域
	 * @param $compile 服务 用于将html字符串或者一个DOM进行编译,返回一个链接函数
	 */
	bamboo.contentHandleCtrl = function ($http,$rootScope,$scope,$compile) {
		$scope.loginErrorInfo = "";
		$scope.wxOpenId = null;//openId
		$scope.sku_number = null;//sku_number  唯一标示
		$scope.reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; //邮箱
		$scope.name_reg = /^[\u2E80-\u9FFF]+$/;//Unicode编码中的汉字范围
		
//		/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/
		$scope.mobile = /^1[2|3|4|5|6|7|8|9][0-9]{9}$/;
		
		// 正则验证
		$scope.regex = function($event, p, k) {
			var name = p.realName;
			if(!$scope.name_reg.test(name)){
				$scope.error_tips = "姓名只能为汉字！";
				return false;
			}
			
			if(p.email && !$scope.reg.test(p.email)){
				$scope.error_tips = "邮箱格式有误！";
				return false;
			} 
			var a = $('#change_address_input').val();
			if(a == 0){
				$scope.error_tips = "请选择住址！";
				return false;
			}
			$.extend(p,{
				'sex':$('#change_sex_input').val(),
				'address':IUSP.GetAddress[a],
				'wxOpenId':$scope.wxOpenId,
				'sku_number':$scope.sku_number,
				'adressKey':a
			});
			
			
			var t = typeof (p);
			if (t != "undefined") {
				var tel = $("[name=phone]").val();
				if (tel != null && tel != "") {
					if (!($scope.mobile.test(tel))) {
						$scope.error_tips = "手机号格式不正确！";
//						document.editForm.telephone.focus();
						return false;
					} else {
						if (k == 1) {// 获取验证码
							
						} else if (k == 2) {
							
							$scope.submitChange(p);
						}
						return true;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}

		};
		
		// 确定修改
		$scope.submitChange = function(param) {
			$('#define').attr("disabled",true);

			IUSP.$http($http, {
				config : {
					url : IUSP.contextPath + '/user/complateUserInfo.do',
					data : param
				},
				success : function(resp) {	
					window.location = IUSP.contextPath + '/user/touserinfo.do';
				}
			});

		}
		
		/**
		 * 获得用户信息
		 */
		$scope.getUserInfo = function(){
			var param = {};
			IUSP.$http($http, {
				config : {
					url : IUSP.contextPath + '/user/userinfo.do',
					data : param
				},
				success : function(resp) {
					
					$scope.wxOpenId = resp.result.mpOpenId;
					$scope.sku_number = resp.result.attribute3;
		
				
				}
			});
		}
		
		
		//初始化
		$scope.initHandleCtrl = function(){
			$scope.getUserInfo();
			
//			$scope.wxOpenId = wxOpenId;
//			$scope.sku_number = sku_number;
			
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