/**
 * 
 */
(function(){
	
	
	function bamboo(){};
	
	//初始化angularjs
	bamboo.angularApp = angular.module('repay_record_list', []);
	
	
	bamboo.reloadGrid = function(param){
		if(param){
			$( "#gridTable" ).datagrid("reload",{queryParams : JSON.stringify( param )});
		}else{
			
			$( "#gridTable" ).datagrid("reload");
		}
	}
	
	/**
	 * 初始化表格
	 */
	bamboo.initTable = function($scope,$compile){
		 //获取查询参数
		 var params = IUSP.getSearchParams(".query-form");
		 IUSP.Grid.init("gridTable",{  
			 	accessKey:"",
			 	url: IUSP.contextPath+"/repay/repayrecordlist.do",
			 	queryParams : {queryParams : JSON.stringify( params )},
			    sortName : "wr_id",
			    sortOrder : "desc",
			    fitColumns:true,
			    checkOnSelect:false,
			    singleSelect:true,
			    columns:[[  
					{field:'',resizable:false,width:'50',checkbox:true,disabled:'disabled'},
					{field:'waId',title:"waId",hidden:true,align:'center',resizable:true,width:'30'},
					{field:'payMan',title:"支付人",align:'center',resizable:true,width:'120'},
					{field:'amount',title:"金额(CNY元)",align:'center',resizable:true,width:'100'},
					{field:'updateDtm',title:"交易时间",align:'center',resizable:true,width:'160'},
					{field:'status',title:'状态',align:'center',resizable:true,width:'100',
						formatter:function(value,row,index){
							if(value=="0"){
								return '<span style="color:#962e2e;">待支付</span>';
							}else if(value=="1"){
								return '<span style="color:green;">交易成功</span>';
							}else if(value=="2"){
								return '<span style="color:red;">交易失败</span>';
							}else if(value=="3"){
								return '<span style="color:#95a400;">转入退款</span>';
							}else if(value=="4"){
								return '<span style="color:#6f6f6f;">已关闭</span>';
							}else if(value=="5"){
								return '<span style="color:#6f6f6f;">已撤销</span>';
							}else if(value=="6"){
								return '<span style="color:#36b4f9;">用户支付中</span>';
							}
						}
					},
					{field:'ordId',title:"订单ID",align:'center',resizable:true,width:'230'},
					{field:'realName',title:"真实姓名",align:'center',resizable:true,width:'160'},
//					{field:'wxOpenId',title:"公众号标识",align:'center',resizable:true,width:'220'},
					/*{field:'attribute4',title:"会员类型",align:'center',resizable:true,width:'220',
						formatter:function(value,row,index){
							if(value == '0'){
								return '<span style="color:#6f6f6f;">关注非会员</span>';
							}else if(value == '-1'){
								return '<span style="color:#962e2e;">终生会员</span>';
							}else if(value == '1'){
								return '<span style="color:green">普通会员</span>';
							}
							
						}
					},*/
					{field:'attribute3',title:"缴费类别",align:'center',resizable:true,width:'220',
						formatter:function(value,roe,index){
//							1-会费缴纳；2-为他人缴费;3-热心赞助；
							if(value == 1){
								return '<span style="color:green">会费缴纳</span>';
							}else if(value != 1 && value != 3 && value){
								return '<span>为'+value+'缴费</span>';
							}else if(value == 3){
								return '<span style="color:#962e2e">热心赞助</span>';
							} else if(!value){
								return '<span>暂无数据</span>';
							}
							
						}
					},
			   ]],
			  rowStyler: function(index,row){
			  },
			  onCheck : function(rowIndex, rowData){
				  $scope.checkbox_click();
			  },
			  onUncheck : function(rowIndex, rowData){
				  $scope.checkbox_click();
			  },
			  onCheckAll : function(rows){
				  $scope.checkbox_click();
			  },
			  onUncheckAll : function(rows){
				  $scope.checkbox_click();
			  },
			  onLoadSuccess :function( data ){
				  $scope.dataTask = data.rows;
				  $compile($("#gridTable").parent())($scope);
				  
			  },
		 });  
	}
	
	 
    /**
	 * 内容控制器
	 * @param $http http请求
	 * @param $rootScope 根作用域
	 * @param $scope 当前内容作用域
	 * @param $compile 服务 用于将html字符串或者一个DOM进行编译,返回一个链接函数
	 */
	bamboo.contentHandleCtrl = function ($http,$rootScope,$scope,$compile) {
		
		//初始化
		$scope.initHandleCtrl = function(){
			
			//初始化表格
			bamboo.initTable($scope,$compile);
		}
		
		//初始化
		$scope.initHandleCtrl();
	}
	  
    bamboo.start = function(){
    	
    	//初始化框架
    	bamboo.layout();
    	
    	//内容操作控制器
		bamboo.angularApp.controller("contentHandleCtrl",bamboo.contentHandleCtrl);

    }
    
    bamboo.layout = function(){
    	
    	var winSize = IUSP.getInnerSize();
    	//初始化表格大小
		$('#gridTable').datagrid({
			pagination:{pagePosition:"bottom"}
		});
    }
    
	/**
	 * 赋值给BAMBOO
	 */
	window.BAMBOO = window.BAMBOO ? $.extend(window.BAMBOO ,bamboo):bamboo
})();