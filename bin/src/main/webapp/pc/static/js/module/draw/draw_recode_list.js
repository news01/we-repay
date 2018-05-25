/**
 * 
 */
(function(){
	
	
	function bamboo(){};
	
	//初始化angularjs
	bamboo.angularApp = angular.module('draw_recode_list', []);
	
	
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
	bamboo.initTable = function($scope,$compile,$event){
		
		 IUSP.Grid.init("gridTable",{  
			 	accessKey:"",
			 	url: IUSP.contextPath+"/draw/queryDrawRecodeList.do",
			 	queryParams : {},
			    sortName : "status desc,prize_dtm desc",
			    sortOrder : "",
			    checkOnSelect:false,
			    singleSelect:false,
			    columns:[[  
					{field:'',resizable:false,width:'50',checkbox:true,disabled:'disabled'},
					{field:'rdId',title:"rdId",hidden:true,align:'center',resizable:true,width:'30'},
					{field:'numberCode',title:"会员编号",align:'center',resizable:true,width:'80'},
					{field:'name',title:"会员姓名",align:'center',resizable:true,width:'140'},
			        {field:'memberType',title:'会员级别',align:'center',resizable:true,width:'160',"editor":"text",
			        	formatter:function(value,row,index){
			        		if(value == '-1'){
			        			return '<span style="color:#962e2e;">终生会员</span>';
			        		}else if(value == '1'){
			        			return '<span>普通会员</span>';
			        		}else if(value == '0' || !value){
			        			return '<span style="color:green;">关注非会员</span>';
			        		}
			        	}
			        },
			        {field:'status',title:'抽奖资格',align:'center',resizable:true,width:'160',"editor":"text",
			        	formatter:function(value,row,index){
			        		if(value == 1){
			        			return '正常';
			        		} else if(value == 0 || !value){
			        			return '<span style="color:red;">异常</span>';
			        		}
			        	}
			        },
			        {field:'prizeLevel',title:'中奖等级',align:'center',resizable:true,width:'60',
		        		formatter:function(value,row,index){
		        			if(value == -2){
			        			return '<span style="color:#962e2e;">未中奖</span>';
			        		}else if(value == -1){
			        			return '<span style="color:green;">未抽奖</span>';
			        		}else if(value == 0){
			        			return '<span style="color:red;">特等奖</span>';
			        		}else if(value == 1){
			        			return '<span style="color:blue;" >一等奖</span>';
			        		}else if(value == 2){
			        			return '<span style="color:#ff3100;">二等奖</span>';
			        		}else if(value == 3){
			        			return '<span style="color:green;">三等奖</span>';
			        		}else if(value == 4){
			        			return '<span style="color:#1a7bc9;">四等奖</span>';
			        		}else if(value == 5){
			        			return '<span style="color:#0a291c9c;">五等奖</span>';
			        		}else if(value == 6){
			        			return '<span style="color:#ef119e9c;">六等奖</span>';
			        		}
		        		}
			        },
			     	{field:'prizeDtm',title:"中奖时间",align:'center',resizable:true,width:'220'},
			     	
			     	{field:'attribute1',title:"抽奖批次",align:'center',resizable:true,width:'220',
			     		formatter:function(value,row,index){
			     			if(value!=null&&value!=""&&value!=0){
			     				return "第"+value+"批";
			     			}else{
			     				return " ";
			     			}
			     		}
			     	},
			     	{field:'attribute2',title:"年份",align:'center',resizable:true,width:'220',
			     		formatter:function(value,row,index){
			     			if(value!=null&&value!=""){
			     				return value+"年";
			     			}else{
			     				return " ";
			     			}
			     		}
			     	},
			     	{field:'attribute3',title:"签到领奖",align:'center',resizable:true,width:'220',
			     		formatter:function(value,row,index){
			     			if(value!=null&&value!=""){
			     				return "<span style='color:green'>已领取</span>";
			     			}else{
			     				return "<span style='color:green'>未领取</span>";
			     			}
			     		}
			     	},
			        {field:'crtDtm',title:"创建时间",align:'center',resizable:true,width:'80'}
			   ]],
			  rowStyler: function(index,row){
			  },
			  onCheck : function(rowIndex, rowData){
				  console.log(rowIndex);
			  },
			  onUncheck : function(rowIndex, rowData){
			  },
			  onCheckAll : function(rows){
				  $scope.checkbox_click();
			  },
			  onUncheckAll : function(rows,rowData){
				  $scope.checkbox_click();
			  },
			  onLoadSuccess :function( data ){
				  $scope.dataTask = data.rows;
				  $compile($("#gridTable").parent())($scope);
				  
			  },
			  onDblClickRow: function (rowIndex, rowData) {
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
		
		$scope.initHandleCtrl();
	}

	bamboo.start = function(){
		
    	//初始化框架
    	bamboo.layout();
    	
    	//内容操作控制器
		bamboo.angularApp.controller("contentHandleCtrl",bamboo.contentHandleCtrl);
		
		/**
		 * 同步
		 */
		$("#addBtn").click(function(){
			bamboo.syncUser(); 
		});
		
		//取消用户抽奖资格
		$("#updateBtn").click(function(){
			bamboo.cancelPrize(); 
		});
		
		//抽奖页面
		$("#drawPrize").click(function(){
			window.open( IUSP.contextPath +"/draw/drawInit.do");            
		});
    }
	
	//同步用户
	bamboo.syncUser = function(){
		var data = {};
		IUSP.post({
			url : IUSP.contextPath + "/draw/synchAccount.do",
			params : {data:data},
			backCall : function( rsp ){
				if(rsp.errCode == 0){
					IUSP.message.show("同步成功！");
					location.reload();
				}else{
					IUSP.popupAlert.show( rsp.errMsg || "同步失败");
				}
			}
		});
	}
	
	//取消用户抽检资格
	bamboo.cancelPrize = function(){
        var checked = $('#gridTable').datagrid('getChecked');
    	if(checked.length<=0){
			IUSP.popupAlert.show("请选择需要取消抽奖资格的会员！");
			return;
		}
    	var rdIds = [];
    	for (var i = 0; i < checked.length; i++) {
    		if(checked[i].status==0){
    			IUSP.popupAlert.show(checked[i].name+"会员已被冻结，请重新选择！");
    			return;
    		}
    		rdIds.push(checked[i].rdId);
		}
    	rdIds = rdIds.join(",");
		$.messager.confirm('确定操作', '您确定要取消这些客户的抽奖资格吗？',function(flag){
			if(flag == true){
				IUSP.post({
					url : IUSP.contextPath + "/draw/frozenUser.do",
					params : {rdIds:rdIds},
					backCall : function( rsp ){
						if(rsp.errCode == 0){
							IUSP.message.show("冻结成功！");
							location.reload();
						}else{
							IUSP.popupAlert.show( rsp.errMsg || "冻结失败");
						}
					}
				});
			}else{
			}
		});
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