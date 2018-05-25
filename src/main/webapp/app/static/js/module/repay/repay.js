(function(){
	function bamboo(){};
	//初始化angularjs
	bamboo.angularApp = angular.module('repay', []);
	
	
	/**
	 * 内容控制器
	 * @param $http http请求
	 * @param $rootScope 根作用域
	 * @param $scope 当前内容作用域
	 * @param $compile 服务 用于将html字符串或者一个DOM进行编译,返回一个链接函数
	 */
	bamboo.contentHandleCtrl = function ($http,$rootScope,$scope,$compile) {
		$scope.ccUserId = '';
		$scope.remind = '';
		
		//输入框失去焦点
		$scope.inputblur  = function($event){
			$($event.target).css("border-color","#ccc");
			$($event.target).next(".money_unit").css("color","#ccc");
		}
		//输入框获得焦点
		$scope.inputFocus  = function($event){
			$($event.target).css("border-color","#64d364");
			$($event.target).next(".money_unit").css("color","#64d364");
		}
		
		//提交支付请求
		$scope.submintRepayResq = function($event,param){
			
			//防止重复点击
//			$($event.target).attr("disabled",true);
			var money = $('.input_money').val();
			if(money <= 0){
				IUSP.popupAlert.show('请输入大于0的金额！');
				$(".messager-window").css('background-color','#64d364');
				$(".l-btn-small").css({'background':'#64d364','border-color':'#64d364'});
				return ;
			} else {
				$($event.target).attr("disabled",true);
			}
			
			/*if(money != 300 || money != 300.00) {
				IUSP.popupAlert.show('请输入正确金额，金额为300元！');
				$(".messager-window").css('background-color','#64d364');
				$(".l-btn-small").css({'background':'#64d364','border-color':'#64d364'});
				return ;
			} else {
				$($event.target).attr("disabled",true);
			}*/
			
			param["body"] = "微信支付-洲瑞同乡会";
			IUSP.$http($http,{
				config : {
					url : IUSP.contextPath + '/repay/crtorder.do',
					data : {"param":JSON.stringify(param)}
				},
				success : function(resp) {
					if(resp.errCode==-1){
						IUSP.popupAlert.show(resp.errMsg);
					}else{
						var result = resp.result;
						IUSPWX.fnChooseWXPay({
							timestamp: result['timeStamp'], // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
						    nonceStr: result['nonceStr'], // 支付签名随机串，不长于 32 位
						    'package': result['package'], // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
						    signType: result['signType'], // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5',注意后台签名和前台签名必须一致
						    paySign: result['paySign'], // 支付签名
						    success: function (res) {
						    	// 支付成功后的回调函数
						        window.location.href= IUSP.contextPath + "/repay/tomyrepayrecord_myself.do";
						    }
						});
					}
					
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
					$scope.ccUserId = resp.result.ccUserId;
					if(!$scope.ccUserId){
						$('.input_money').attr('readonly',true);
						$('.zhifu').attr("disabled",true);
						$scope.remind = '用户未注册，请到个人中心注册！';
						
					}
						
				}
			});
		}
		
		
		//初始化
		$scope.initHandleCtrl = function(){
			$scope.getUserInfo();
			
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