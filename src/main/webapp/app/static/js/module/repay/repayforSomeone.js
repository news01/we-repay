(function(){
	function bamboo(){};
	//初始化angularjs
	bamboo.angularApp = angular.module('repayforSomeone', []);
	
	
	/**
	 * 内容控制器
	 * @param $http http请求
	 * @param $rootScope 根作用域
	 * @param $scope 当前内容作用域
	 * @param $compile 服务 用于将html字符串或者一个DOM进行编译,返回一个链接函数
	 */
	bamboo.contentHandleCtrl = function ($http,$rootScope,$scope,$compile) {
		$scope.queryUserResult = '';//查询用户结果
		
		/**
		 * 为他人支付
		 */
		$scope.submitrepay = function($event,param){

			var address = $('#address').val();
			var money = param.total_fee;
			if(money <= 0){
				IUSP.popupAlert.show('请输入大于0的金额！');
				$(".messager-window").css('background-color','#64d364');
				$(".l-btn-small").css({'background':'#64d364','border-color':'#64d364'});
				return ;
			} else {
				$($event.target).attr("disabled",true);
			}
			
			//--------
			param["body"] = "微信支付-洲瑞同乡会";
			param['address'] = IUSP.GetAddress[address];
			IUSP.$http($http,{
				config : {
					url : IUSP.contextPath + '/repay/repayforsomeone.do',
					data : {"param":JSON.stringify(param)}
				},
				success : function(resp) {
					if(resp.result == '未找到此用户' || resp.result == '个人信息重复，请前去修改'){
						IUSP.popupAlert.show('未找到此用户');
						$(".messager-window").css('background-color','#64d364');
						$(".l-btn-small").css({'background':'#64d364','border-color':'#64d364'});
						$($event.target).attr("disabled",false);
						return ;
					}
					
					if(resp.errCode==-1){
						IUSP.popupAlert.show(resp.errMsg);
						$(".messager-window").css('background-color','#64d364');
						$(".l-btn-small").css({'background':'#64d364','border-color':'#64d364'});
					}else{
						var result = resp.result;
						IUSPWX.fnChooseWXPay({
							timestamp: result['timeStamp'], // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
						    nonceStr: result['nonceStr'], // 支付签名随机串，不长于 32 位
						    'package': result['package'], // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
						    signType: result['signType'], // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5',注意后台签名和前台签名必须一致
						    paySign: result['paySign'], // 支付签名
						    success: function (res) {
						    	var resultData = result['resultData'];
						    	if(resultData){
						    		var results  = JSON.parse(resultData);
						    		var user_key = results.attribute3;//用户标示
									var param_total_fee = results.attribute6;//金额
									
									// 支付成功后的回调函数
							        window.location.href= IUSP.contextPath + "/repay/tomyrepayrecord.do?user_key="+user_key+"&param_total_fee="+param_total_fee;
									
						    		
						    	}
						    	
						    	//支付成功后维护统计表
						    	/*IUSP.$http($http,{
									config : {
										url : IUSP.contextPath + '/repay/maintain_statistical.do',
										data : {"data":resultData}
									},
									success : function(resp) {
										if(resp.errCode == 1){
											
											// 支付成功后的回调函数
									        window.location.href= IUSP.contextPath + "/repay/tomyrepayrecord.do";
											
										}
									}
								});*/
						    	
						    	
						    }
						});
					}
					
				}
			});
			
		}
		
		
		
		//初始化
		$scope.initHandleCtrl = function(){
			
			
			
			
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