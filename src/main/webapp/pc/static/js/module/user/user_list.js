/**
 * 
 */
(function(){
	
	
	function bamboo(){};
	
	//初始化angularjs
	bamboo.angularApp = angular.module('user_list', []);
	
	
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
		
//		 var datagrid; //定义全局变量datagrid
//		 var editRow = undefined; //定义全局变量：当前编辑的行
//		 var original_name = '';
		
		 IUSP.Grid.init("gridTable",{  
			 	accessKey:"",
			 	url: IUSP.contextPath+"/user/userlist.do",
			 	queryParams : {},
			    sortName : "attribute4 desc",
			    sortOrder : "",
			    checkOnSelect:false,
			    singleSelect:false,
			    columns:[[  
					{field:'',resizable:false,width:'50',checkbox:true,disabled:'disabled'},
					{field:'waId',title:"waId",hidden:true,align:'center',resizable:true,width:'30'},
					{field:'attribute3',title:"编号",align:'center',resizable:true,width:'80'},
					{field:'realName',title:"姓名",align:'center',resizable:true,width:'140'},
					/*{field:'nickname',title:"用户昵称",align:'center',resizable:true,width:'140'},*/
	                {field:'sex',title:'性别',align:'center',resizable:true,width:'60',
		        		formatter:function(value,row,index){
							return  value==1?'男':(value==2?'女':"未知");
		        		}
			        },
			        {field:'attribute1',title:'会员级别',align:'center',resizable:true,width:'160',"editor":"text",
			        	formatter:function(value,row,index){
			        		if(value == '-1'){
			        			return '<span style="color:#962e2e;">终生会员</span>';
			        		}
			        		else if(value == '1'){
			        			return '<span>普通会员</span>';
			        		}
			        		else if(value == '0' || !value){
			        			return '<span style="color:green;">关注非会员</span>';
			        		}
			        		
			        	}
			        },
			       	{field:'mobile',title:"手机号码",align:'center',resizable:true,width:'160'},
	            	{field:'attribute2',title:"自然村",align:'center',resizable:true,width:'80'},
	            	{field:'attribute4',title:"关注时间",align:'center',resizable:true,width:'220'},
	            	{field:'attribute5',title:"注册时间",align:'center',resizable:true,width:'80'},
			   ]],
			  rowStyler: function(index,row){
			  },
			  onCheck : function(rowIndex, rowData){
				  console.log(rowIndex);
//				  $scope.checkbox_click();
			  },
			  onUncheck : function(rowIndex, rowData){
//				  $scope.checkbox_click();
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
				 /* debugger;
				  original_name = rowData.attribute1;
				  
	                //双击开启编辑行
                    if (editRow != undefined) {
                    	 $(this).datagrid('endEdit', editRow);  
                    	 editRow = undefined;
                    }
                    if (editRow == undefined) {
                        editRow = rowIndex;
                    	 $(this).datagrid('beginEdit', rowIndex);  
                         var ed = $(this).datagrid('getEditor', {});  
                         $(ed.target).focus();  
                    }*/
	           },
	           /*onAfterEdit: function(rowIndex, rowData, changes){
	        	   debugger;
	        	   var updated = $('#gridTable').datagrid('getChanges', 'updated'); 
	        	   if (updated.length < 1) {  
	        		    editRow = undefined;
//	        	        $('#gridTable').datagrid('unselectAll');  
	        		    $(this).datagrid('endEdit', editRow);  
	        	        return;  
	        	    } else {  
	        	        // 传值  
	        	        submitForm(rowIndex, rowData, changes);  
	        	    }  
	        	   
	        	   
	           },*/
	          /* onClickCell: function(rowIndex, field, value){
	        	   debugger;
	        	   
	           },*/
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
//		$scope.loginErrorInfo = "";
//		//提交登录
//		$scope.submitLogin = function(param){
//			//登录
//			IUSP.$http($http,{
//				config : {
//					url : IUSP.contextPath + '/login/login.do',
//					data : param
//				},
//				success : function(resp) {
//		
//				}
//			});
//			
//		}
		
		
		
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
		 * 新增表格
		 */
		$("#addBtn").click(function(){
			//打开用户 新增页面
			IUSP.modal.create({
				title:"添加信息",
				url : IUSP.contextPath+"/user/addInfo.do",
				width:650,
				height:420
			});
		});
		
		/**
		 * 保存数据
		 */
		$("#saveBtn").click( function(e){
			var mobile = /^1[2|3|4|5|6|7|8|9][0-9]{9}$/;
			var objData = IUSP.getFormData("#editForm");
			if(objData.address == 0){
				alert("请选择住址");
				return false;
			}
			if(!mobile.test(objData.phone)){
				alert("手机号格式有误");
				return false;
			}
			
			objData.adressKey = objData.address;
			objData.address = IUSP.GetAddress[objData.address];
			$('#saveBtn').attr('disabled',true);
			bamboo.saveUser(objData,1);
		});
    	
		/**
		 * 保存数据
		 * @param user
		 */
		bamboo.saveUser= function ( user,judge ){		
			var url = '';
			if(judge == 1){
				url = IUSP.contextPath + "/user/saveUser.do";
			}
			else if(judge == 2){
				url = IUSP.contextPath + "/user/updateUser.do";
			}
			
			//验证表单
			var flag = $("#editForm").form('enableValidation').form('validate');
			console.log(flag);
			if(flag){
				user.dateOnBoard = $("#dateOnBoard").val()?$("#dateOnBoard").val():null;
				console.log( user);
				IUSP.post({
					url : url,
					params : {data:JSON.stringify(user)},
					backCall : function( rsp ){
						
						if(judge == 1){
							if(rsp.errCode == 1){
								IUSP.message.show("保存成功！");
								$("#modalCloseBtn").click();
							}else{
								IUSP.popupAlert.show( rsp.errMsg || "保存失败");
							}
						}
						if(judge == 2){
							if(rsp.result == 1){
								IUSP.message.show("修改成功！");
								$("#modalCloseBtn").click();
							}else{
								IUSP.popupAlert.show( rsp.errMsg || "修改失败");
							}
						}
					}
				});
			}
			
		};
		
	
		//删除
		$("#delBtn").click(function(){
			
			var checkBox = document.getElementsByTagName("tbody")[3].getElementsByTagName('tr');
			var waId = [];
			for(var i = 0;i < checkBox.length;i++){
				if(checkBox[i].classList.contains('datagrid-row-selected') == true){
					var wId = checkBox[i].getElementsByClassName('datagrid-cell-c2-waId')[0].innerText;
					waId.push(wId);
					waId.join(',');
				 }
			}
			console.log(waId.toString());
			if(waId.length > 0){
				$.messager.confirm('确定操作', '您确定要删除当前的记录吗？',function(flag){
					if(flag == true){
						console.log(waId.toString());
						bamboo.deleteInfo(waId.toString());
					} else {
//						alert('取消');
						IUSP.popupAlert.show( "删除失败");
					}
				});
			}
			
		});
		//执行删除
		bamboo.deleteInfo = function(param){
			IUSP.post({
				url : IUSP.contextPath + "/user/delUser.do",
				params : {data:param},
				backCall : function( rsp ){
					if(rsp.errCode == 1){
						IUSP.message.show("删除成功！");
						location.reload();
					}else{
						IUSP.popupAlert.show( rsp.errMsg || "删除失败");
					}
				}
			});
		};
		
		
		/**
		 * 保存修改数据
		 */
		$("#saveUpdateBtn").click( function(e){
			var objData = IUSP.getFormData("#editForm");
			objData.adressKey = objData.address;
			objData.address = IUSP.GetAddress[objData.address];
			bamboo.saveUser(objData,2);
		});
		
		
		/**
		 * 调出修改界面
		 */
		$('#updateBtn').click(function(){
			
			var checkBox = $($('tbody')[3]).find('tr');
			for(var i = 0;i < checkBox.length;i++){
				if(checkBox[i].classList.contains('datagrid-row-selected') == true){
					var wId = checkBox[i].getElementsByClassName('datagrid-cell-c2-waId')[0].innerText;
					//查询某一用户信息
					IUSP.post({
						url : IUSP.contextPath + "/user/queryTheUser.do",
						params : {data:wId},
						backCall : function( rsp ){
							localStorage.info = JSON.stringify(rsp.result);
							IUSP.modal.create({
								title:"修改信息",
								url : IUSP.contextPath+"/user/updateInfo.do",
								width:600,
								height:450
							});
						}
					});
					
					return;
				 }
			}
			
		});
		
		
		
//    	
//    	/**
//    	 * 加载表格
//    	 */
//    	bamboo.initTable();
//    	
//    	//绑定新增事件
//    	$("#addBtn").click(function(){
//    		bamboo.addUser();
//    	});
//    	//绑定 修改事件
//    	$("#updateBtn").click(function(){
//    		bamboo.updateUser();
//    	});
//    	//绑定删除事件
//    	$("#updateUserBtn").click(function(){
//    		
//    		bamboo.updateUserById();
//    	});
//    	
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