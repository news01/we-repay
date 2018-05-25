(function(){
	function bamboo(){};
	//初始化angularjs
	bamboo.angularApp = angular.module('my_repay_record', []);
	
	
	/**
	 * 内容控制器
	 * @param $http http请求
	 * @param $rootScope 根作用域
	 * @param $scope 当前内容作用域
	 * @param $compile 服务 用于将html字符串或者一个DOM进行编译,返回一个链接函数
	 */
	bamboo.contentHandleCtrl = function ($http,$rootScope,$scope,$compile) {
		
	
		//我的支付记录列表集合信息
		$scope.repayRecordList = [];
		$scope.repayRecordTotal = 0;
		//显示加载更多
		$scope.repayRecordMore = true;
		//分页
		var pageNumber = 0;
		var pageSize = 10;
		
		//我的支付记录列表信息
		$scope.fnRepayRecordList = function(currParam){
			pageNumber += 1;
			//默认参数
			var defaultParam = {
					status:1,
					attribute1:"false",
			};
			if(currParam && currParam!=null){
				$.extend(defaultParam,currParam);
			}
			//获取我的支付记录列表信息
			IUSP.$http($http,{
				config : {
					url : IUSP.contextPath + '/repay/repayrecordlist.do',
					data : {page:pageNumber,rows:pageSize,sort:"update_dtm desc",queryParams : JSON.stringify( defaultParam )},
				},
				success : function(resp) {
					if(resp.errCode == 0){
						var result = resp;
						$scope.repayRecordTotal = result.total;
						if(pageNumber*pageSize>=$scope.repayRecordTotal){//如果当前显示大于总条数,去掉加载更多
							$scope.repayRecordMore = false;
						}
						var rows = result.rows;
						angular.forEach(rows,function(row, key){
							row['showUpdateDtm'] = new Date(row['updateDtm'].replace(/-/g,'/')).Format("yyyy-MM-dd hh:mm:ss");
						});
						$scope.repayRecordList = $scope.repayRecordList.concat(result.rows);
						
					}
				}
			});
		}
		
		
		//初始化
		$scope.initHandleCtrl = function(){
			
			$scope.fnRepayRecordList();
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