/**
 * 
 */
(function(){
	
	
	function bamboo(){};
	
	//初始化angularjs
	bamboo.angularApp = angular.module('repay_count_list', []);
	
	
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
			 	url: IUSP.contextPath+"/repay/repay_statusRecordlist.do",
			 	queryParams : {queryParams : JSON.stringify( params )},
			    sortName : "user_number,pay_status",
			    sortOrder : "asc",
			    fitColumns:true,
			    checkOnSelect:false,
			    singleSelect:false,
			    columns:[[  
							{field:'',resizable:false,width:'50',checkbox:true,disabled:'disabled'},
							{field:'id',title:"waId",hidden:true,align:'center',resizable:true,width:'30'},
							{field:'year',title:"年份",align:'center',resizable:true,width:'120'},
							{field:'userNumber',title:"编号",align:'center',resizable:true,width:'120'},
							{field:'realName',title:"姓名",align:'center',resizable:true,width:'120'},
							{field:'yearAnnualFee',title:"金额(CNY元)",align:'center',resizable:true,width:'100',
								formatter:function(value,row,index){
									if(value=="-1"){
										return '<span style="color:#962e2e;">无需缴费</span>';
									} else {
										return '<span style="color:black;">'+ value +'</span>';
									}
								}
							
							},
							{field:'payTime',title:"交易时间",align:'center',resizable:true,width:'160'},
							{field:'payStatus',title:'状态',align:'center',resizable:true,width:'120',
								formatter:function(value,row,index){
									if(value=="1"){
										return '<span style="color:#962e2e;">已支付</span>';
									}else if(value=="2"){
										return '<span style="color:green;">未支付</span>';
									} else if(value == "3"){
										return '<span style="color:green;">未完全支付</span>';
									} else if(value == '-1'){
										return '<span style="color:#962e2e;">终生会员</span>';
									}
								}
							},
							{field:'operate',title:'操作',align:'center',width:$(this).width()*0.1,  
							    formatter:function(value, row, index){  
							        var str = '<a href="javascript:void(0)" name="opera" onclick="remind(event)">点击提醒</a>';  
							        return str;  
							    }
							},
					   ]],
			  rowStyler: function(index,row){
			  },
			  onCheck : function(rowIndex, rowData){
			  },
			  onUncheck : function(rowIndex, rowData){
			  },
			  onCheckAll : function(rows){
			  },
			  onUncheckAll : function(rows){
			  },
			  onLoadSuccess :function( data ){
				  $scope.dataTask = data.rows;
				  $compile($("#gridTable").parent())($scope);
				  
			  },
			  onLoadSuccess:function(data){  
			      $("a[name='opera']").linkbutton({plain:true,iconCls:'icon-tip'});    
			  }, 
		 });  
	}
	
	remind = function (event){
		
		var a = event.currentTarget.parentElement.parentElement.parentElement.children[3];
		var waId = $(a).find('.datagrid-cell-c2-userNumber')[0].innerText;
		console.log(waId);
		
		IUSP.post({
			url : IUSP.contextPath + "/login/remindUser.do",
			params : {data:waId},
			backCall : function( rsp ){
				
				if(rsp.result.errcode != 0){
					IUSP.message.show("提醒失败");
				} else if(rsp.result.errcode == 0){
					IUSP.message.show("已提醒");
				}
				
				
			}
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
			//初始化时间控件
			angular.element("#year").datetimepicker({
                language:  'zh-CN',
                weekStart: 1,
                todayBtn:  0,
        		autoclose: 1,
        		todayHighlight: 1,
        		startView: 4,
        		minView: 4,
        		forceParse: 0,
        		pickerPosition:'top-right',
        		format: 'yyyy'
            });
			
			//初始化年份
			var currDate = new Date();
			var currYear = currDate.getFullYear();
			angular.element("#year").val(currYear);
			
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
		
		//导出数据
		$("#remindBtn").click(function(){
			bamboo.exportExcel();
		})

    }
    
    //导出会员年费统计信息
    bamboo.exportExcel = function(){
    	debugger;
    	
       var checked = $('#gridTable').datagrid('getChecked');
    	
    	var eiIds = [];
    	for (var i = 0; i < checked.length; i++) {
    		eiIds.push(checked[i].id);
		}
    	eiIds = eiIds.join(",");
		//判断审核工作日志状态
    	var str = "请确认是否要导出会员年费统计信息？";
		
    	IUSP.popupConfirm.show(str,function(flag){
    		if(flag){
    			 window.location.href= IUSP.contextPath + "/repay/exportYearPayRecordList.do?ypIds="+eiIds;
    		}
	    });
    };
    
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