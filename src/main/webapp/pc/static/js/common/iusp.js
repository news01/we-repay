
/*Bamboo Framework.*/
(function(window,iusp) {
	
	// 时间格式化
	Date.prototype.Format = function (fmt) {
	    var o = {
	        "M+": this.getMonth() + 1, // 月份
	        "d+": this.getDate(), // 日
	        "h+": this.getHours(), // 小时
	        "m+": this.getMinutes(), // 分
	        "s+": this.getSeconds(), // 秒
	        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
	        "S": this.getMilliseconds() // 毫秒
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}

	
	// 上下文路径
	iusp.contextPath = "";
	// 公众号APPID
	iusp.appid = "";
	// 公众号JSSDK--jsapi_ticket
	iusp.jsapiTicket = "";
	iusp.getCommonData = function(){
		
		iusp.contextPath = window.contextPath;
		iusp.appid = window.appid;
		iusp.jsapiTicket = window.jsapiTicket;
		
	}
	
	iusp.getCommonData();
	
	
	/**
	 * 获取页面内部大小
	 */
	iusp.getInnerSize = function(){
		
		var winWidth = 0;
		var winHeight = 1;
		// 获取窗口宽度
		if(window.innerWidth){
			winWidth = window.innerWidth;	
		}else if( document.body && document.body.clientWidth){
			winWidth = document.body.clientWidth;
		}
		// 获取窗口高度
		if(window.innerHeight){
			winHeight = window.innerHeight;
		}else if(document.body && document.body.clientHeight){
			winHeight = document.body.clientHeight;
		}
		// 返回窗口大小
		return {
			width : winWidth,
			height : winHeight
		}
	}
	
	/**
	 * 对表格进行封装
	 */ 
	iusp.Grid = {
			// 刷新表格数据
			reload :function(tableId,param) {
					
				$( "#"+tableId ).datagrid("reload",{queryParams : JSON.stringify( param )});
			},
			// 刷新指定的行
			refreshRow :function(tableId,rowIndex){
				$( "#"+tableId ).datagrid("refreshRow",rowIndex);
			},
			// 修改指定行
			updateRow :function(tableId,rowIndex,row){
				$("#"+tableId).datagrid("updateRow",{index:rowIndex,row:row});
			},
			defaultOption : {
				 mothed : "post",
			     rownumbers : true,
			     striped : true,
			     fitColumns:true,
			     selectFirstRow : false,// 选中第一行
			     singleSelect : false,// 单选
			     pageSize :10,
			     pageList : [10,20,50,100,200,300],
			     onBeforeLoad:function(param){
			    	 
			    	if(param.sort){
			    		
			    		var cloOption = $(this).datagrid("getColumnOption",param.sort);
			    		
			    		if(cloOption) param.sort = cloOption.sortName || "";
			    	}
			     }
			},
			/**
			 * 根据窗口大小与列定义的宽度按比例重新设定宽度
			 */
			resetColumWidth : function(option){
				
				var tableWidth = option.tableWidth;
				if(!tableWidth) tableWidth = iusp.getInnerSize().width-30;
				var columTotalWidth =0;
				// 累加列的宽度
				$.each(option.columns[0],function(i,colum){
					if(colum.width) columTotalWidth += parseFloat( colum.width );
				})
				// 重置宽度
				$.each(option.columns[0],function(i,colum){
					
					if(colum.width) {
						colum.width = colum.width/columTotalWidth*tableWidth;
					}
				})
			},
			init: function(tableId, option ){
				
				var newoption = $.extend({},this.defaultOption,option,{url:(option.url+"?accessKey="+option.accessKey)});
				
				$.extend(newoption,{
					/**
					 * 系统封装onloadSuccess方法，监听无权访问或系统报错
					 */
					 onLoadSuccess:function(data){
						 
						// 处理公共提示
						var bool = iusp.resultNotice(data);
						if(bool){
							
							 if($.isFunction(option.onLoadSuccess)) option.onLoadSuccess.call(this,data);
							 
							 // 默认选中第一行
					    	 if( newoption.selectFirstRow ){
					        	  if(data.rows.length>0){
					        		  $("#"+tableId).datagrid("selectRow",0);
					        	  }
					    	 }
						}
					  }
				});
		          
				if(newoption.resetColum != false){
					// 重置列宽
					// this.resetColumWidth(newoption);
				}
				
				
				
				if(!newoption.width){
					var winSize = IUSP.getInnerSize();
					$("#secondFloor").height(winSize.height - $("#header").height());
					$('#girdTable').datagrid({
						width:winSize.width,
						height:winSize.height - $("#header").height(),
						pagination:{pagePosition:"bottom"}
					});
				}
				// 渲染表格
				$('#'+tableId).datagrid( newoption );  
			}
	}
	
    /**
	 * 提示框
	 */
    iusp.popupAlert = {
    	
     currentShow : function( content ,options){
	 
         $.messager.alert("提示", content, "info", function () {  
            
         });  
     },
     show : function( content, options){
    	// 在父页面显示
    	if(window.top == window.self ){
    		this.currentShow( content );
    	}else{
    		window.top.IUSP.popupAlert.currentShow( content );
    	}
     }
    }
	
	
	
    
    /**
	 * 对话框
	 */
    iusp.popupConfirm = {
    	
     currentShow : function( content,fun,options){
	 
    	 $.messager.confirm("提示", content,function (data) {  
    		 
    		 if($.isFunction( fun ))  fun.call( this,data ? true:false );
         });  
     },
     show : function( content,fun, options){
    	// 在父页面显示
    	if(window.top == window.self ){
    		this.currentShow( content,fun,options );
    	}else{
    		window.top.IUSP.popupConfirm.currentShow( content,fun,options);
    	}
     }
    }
    
    /**
	 * 成功提示框
	 */
    iusp.message = {
    	
     currentShow : function( content,fun, options){
    	 
    	 $.messager.show({
                title:'操作提示',
                msg:content,// 显示内容
                timeout:3000,// 显示时间长度
                showType:'slide'
            });
     },
     show :  function( content,fun, options){
    	 // 在父页面显示
    	if(window.top == window.self ){
    		this.currentShow( content );
    	}else{
    		window.top.IUSP.message.currentShow( content );
    	}
     }
    }
	
	
    
    /**
	 * 对话框
	 */
    iusp.innoPopupConfirm = {
			
		 callback : function(fun,data){
			 var $element = $('body>#innoPopupConfirm');
			 $element.hide();
			 if(fun && $.isFunction( fun ))  fun.call( this,data ? true:false );
		 },
	     currentShow : function( content,fun,options){
    		var $element = $('body>#innoPopupConfirm');
    		if($element.length>0){
    			$element.show();
    		}else{
    			var html = [];
	   	    	html.push('<div id="innoPopupConfirm" style=" position: fixed; height: 100%;max-width: 16rem;width: 100%;top: 0px; color: #333;z-index: 100;">');
	   			html.push('<div style="background-color: black;width: 100%; height: 100%;filter: alpha(opacity=50); -moz-opacity: 0.5;opacity: 0.5;">');
	   			html.push('</div>');
	   			html.push('<div style="height: 6.0625rem; width: 11.6875rem; border-radius: 0.10417rem; position: absolute; top: 50%;left: 50%; margin-left: -5.84375rem;margin-top: -3.03125rem;background-color: white;text-align: center;">');
	   			html.push('<div class="row-fluid" style="padding: 0.6rem 0rem;height: 3.9583rem;border-bottom: solid 1px #d2d3d5;font-size: 0.75rem; color: #999999;">');
	   			html.push('<div class="row-fluid" style="text-align: center;padding: 0.25rem 0.5rem;color: #10C55B;line-height: 1.2rem;">');
	   			html.push('<i class="messager-question" style="display: inline-block;vertical-align: middle;text-align: center;width: 1.2rem; height: 1.06rem; margin-top: -0.2rem;"></i>');
	   			html.push(content?content:'提示');
	   			html.push('</div>');
	   			html.push('</div>');    
	   			html.push('<div class="row-fluid" style="height: 2.08333rem;line-height: 2.08333rem;color: #000000;font-size: 0.75rem;">');
	   			html.push('<div id="innoPopupConfirmOk" class="span6" style="color: #10C55B;border-right: solid 1px #d2d3d5;">确定</div>');    
	   			html.push('<div id="innoPopupConfirmCancel" class="span6" style="">取消</div>');    
	   			html.push('</div>');
	   			html.push('<div id="innoPopupConfirmClose" style="margin-top: 0.5rem;"><i class="close_icon_btn" style="display: inline-block;width: 1.333333rem;height: 1.333333rem;vertical-align: middle;background-repeat: no-repeat;background-size: 100% 100%;"></i></div>')
	   			html.push('</div>');
	   			html.push('</div>');
	   			$("body").append(html.join(''));
	   			var that = this;    
	   			$("#innoPopupConfirmClose").bind("click",function(){
	   				that.callback();
	   			});
	   			$("#innoPopupConfirmOk").bind("click",function(){
	   				that.callback(fun,true);
	   			});
	   			$("#innoPopupConfirmCancel").bind("click",function(){
	   				that.callback(fun,false);
	   			});
    		}
	     },
	     show : function( content,fun, options){
	    	// 在父页面显示
	    	if(window.top == window.self ){
	    		this.currentShow( content,fun,options );
	    	}else{
	    		window.top.IUSP.popupConfirm.currentShow( content,fun,options);
	    	}
	     }
    }
	
	/*
	 * 弹出窗口
	 */
	iusp.modal = {
			defualtOption:{
				title:"",// 标题
				width:650,// 窗口宽度
				height:400,// 窗口高度
				url:null,// 页面路径 ，与content互斥
				content:null,// 页面内容，与url互斥
				top:true,// 是否在顶层页面打开
				onCloseBack:function(){
					
				}
			},
			create:function( option ){
				
				option = $.extend(this.defualtOption,option);
				
				if(option.top && window.top != window.self){
					
					window.top.IUSP.modal.create( option );
					
				}else{
					
					// 先移除原先模式框
					var winHtml = '<div class="modal fade" id="modalWin" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="width:'+option.width+'px;height: '+option.height+'px;position: absolute;top:0;left: 0;right: 0;bottom: 0;margin: auto;">'+
							'<div class="modal-dialog" role="document" style="width:'+option.width+'px;height: '+option.height+'px;overflow:hidden;margin:0px;">'+
						        '<div class="modal-content">'+
							         '<div class="modal-header" style="padding: 0px 15px;height:52px;">'+
							           '<button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="line-height:52px;"> &times; </button> '+
							            '<h4 class="modal-title" id="defendTitle" style="font: bold 16px/52px \'微软雅黑\';color:white;line-height:52px;">'+option.title+'</h4>'+
							         '</div>'+
							         '<div class="modal-body" style="padding: 0px;height: '+(option.height-52)+'px;overflow:hidden;max-height: 768px;width:100%">'+
							         '</div>'+
						        '</div>'+
						     ' </div>'+
						 '</div>'
						     
					var win = option.window ? option.window : window.top;
					
					$("#modalWin").remove();
				    $("body").append( winHtml );
					// 实例化窗口对象
					$('#modalWin').modal({ keyboard: true,show:true});
					
					// 如果路径不为空，则追加iframe展示，否则展示传入的HTML
					if(option.url){
						
						var iframe = '<iframe id="modalFrame" name="modalFrame" src='+ option.url+' frameborder="0" scrolling="auto" allowtransparency="true" height="100%" width="100%"  ></iframe>'
						$("#modalWin").find(".modal-body").html(iframe);
						
						var frame = window.frames["modalFrame"];
						frame.onload = function(){
							
							// 绑定弹出框关闭事件
							$("#modalCloseBtn",frame.document).click(function(){
								
								$('#modalWin').modal("hide");
							});
						}
					}else{
						
						$("#modalWin").find("modal-body").html( option.content );
						
						// 绑定弹出框关闭事件
						$(".modal-close").click(function(){
							
							$('#modalWin').modal("hide");
						});
					}
					
					if($.isFunction(option.onCloseBack)){
						
						$('#modalWin').on('hidden.bs.modal', function (e) {
							
							option.onCloseBack.call(this);
						})
					}
				}

		},
		close : function(modalId){
		
			if(modalId){
				
				$( modalId ).modal( "hide" );
			}else{
				
				$('#modalWin').modal("hide");
			}
		}
	}
	
	/**
	 * 
	 * 
	 * 
	 * /** 右键菜单事件
	 */
	iusp.Menu = {
			/**
			 * 创建右键菜单
			 */
			create : function(menuId,menuAddr,clickFun,showBack){
				$(menuId).menu({
				    onClick:clickFun,// 点击事件
				    onShow : showBack// 菜单显示后事件
				});
				this._bindMenu( menuId,menuAddr );
			},
			/**
			 * 绑定右键菜单事件
			 */
			_bindMenu :function(menuId, menuAddr ){
				
				// 绑定右键
				$(menuAddr).tree({
					onContextMenu: function(e, node){
						e.preventDefault();
						// 选中右键点击节点
						$(menuAddr).tree("select", node.target);
						// 显示快捷菜单
						e.preventDefault();// 防止事件冒泡
						$( menuId ).menu("show",{
							top : e.pageY,
							left: e.pageX
						});
					}
				});
				
			}
	}
	
	
	//住址
    iusp.GetAddress = {
	    	"TB":"田背",
	    	"XY":"下营",
	    	"CS":"赤水",
	    	"DK":"大坑",
	    	"NC":"南村",
	    	"ZA":"嶂岸",
	    	"HG":"华光",
	    	"BY":"陂营",
	    	"KP":"葵坪",
	    	"QT":"其它",
	    }
	//住址
    iusp.GetAddressCH = {
	    	"田背":"TB",
	    	"下营":"XY",
	    	"赤水":"CS",
	    	"大坑":"DK",
	    	"南村":"NC",
	    	"嶂岸":"ZA",
	    	"华光":"HG",
	    	"陂营":"BY",
	    	"葵坪":"KP",
	    	"其它":"QT",
	    }
	    
	
	/**
	 * TODO
	 */
	iusp.fillData = function(data,formId){
		
		$.each(data,function(id,value){
			
			var ele = $(formId).find("[name='"+id+"']");
			
			if(ele.length>0){
				var tagName = ele[0].tagName;
				var type = $(ele[0]).attr("type");
				
				if(ele.length==1){
					
					if(tagName == "div" || tagName=="DIV" || tagName == "span" || tagName == "SPAN" ){
						
						ele.text(value);
					}else if(tagName == "select" || tagName=="SELECT"){
						
						ele.val(value);
					}else if(tagName == "radio" || tagName=="RADIO"){
						alert(11)
					}else{
						
						ele.val(value);
					}
				
				}else if(ele.length > 1){
						
					if(type == "radio" || type=="RADIO"){
						
						$("input[name='"+id+"'][value='"+value+"']")[0].checked=true;
					}else if(type == "checkbox" || type=="CHECKBOX"){
						
					}
						
				}
			}
		})
	}
	
	/**
	 * 获取表单数据
	 */
	iusp.getFormData  = function( editForm ){
		
		var editForm = editForm || "#editForm"
		var dataArray = $(editForm).find("input,select,textarea").serializeArray();
		var object = {};
		
		$.each(dataArray,function(i,item){
			
			if(item.value){
				
				object[item.name] = item.value; 
			}
		});
		
		return object;
	}
	
	/**
	 * 封装post url : 访问路径 params ：参数，对象 如{id:2,code:'zhangsan'} fun :
	 * 回调方法，function selfNotice : 自定义提示，如果selfNotice=ture 则不走共同拦截提示
	 */
	
	iusp.post = function( options ){
		
		var defaultOptions = {
				selfNotice : false,
				backCall : function(){
				}
		}
		options = $.extend( defaultOptions,options)
		// 追加参数
		var accessKey = options.accessKey;
		if(accessKey && accessKey.length>0){
			options.url += (options.url.indexOf("?")==-1)?("?accessKey="+accessKey):("&accessKey="+accessKey);
		}
		// var params = $.extend( {accessKey:options.accessKey},options.params);
		
		$.post(options.url,options.params,function( rsp ){
			// 走自定义提示
			if( options.selfNotice ){
				if( $.isFunction( options.backCall ) ) options.backCall.call(this,rsp);// 调用回调方法
			}else{
				// 处理公共提示
				var bool = iusp.resultNotice(rsp);
				if(bool){
					if( $.isFunction( options.backCall ) ) options.backCall.call(this,rsp);// 调用回调方法
				}
			}
		});
	}
	
	/**
	 * 封装ajax
	 */
	iusp.ajax = function( options ){
		
		var accessKey = options.accessKey;
		if(accessKey && accessKey.length>0){
			options.url += (options.url.indexOf("?")==-1)?("?accessKey="+accessKey):("&accessKey="+accessKey);
		}
		
		// options.data = $.extend({accessKey:options.accessKey},options.data)
		// 封装默认参数
		var defaultOption = $.extend(
				{
					async: true,
					type: "POST",
					dataType: "json"
				},
				options,
				{success : function( rsp ){

						// iusp.waiting.hide();//关闭遮幕
						// 走自定义提示
						if( options.selfNotice ){
							if( $.isFunction( options.success ) ) options.success.call(this,rsp);// 调用回调方法
						}else{
							// 处理公共提示
							var bool = iusp.resultNotice(rsp);
							if(bool){
								if( $.isFunction( options.success ) ) options.success.call(this,rsp);// 调用回调方法
							}
						}
					},error : function(){
						
						IUSP.popupAlert.show( "页面出现了问题！" );
					}
				}
		);
		return $.ajax( defaultOption );
	}
	
	/**
	 * 
	 * @param options
	 * @param successCallback
	 * @param errorCallback
	 */
	iusp.$http = function($http,options,successCallback, errorCallback){
		var config = options.config;
		var defaultOptions = $.extend(true,
				{
					method: 'POST',
					headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
					transformRequest:function (data) {
				        return data==null?"":$.param(data);
				    }
				},config);
		$http(defaultOptions).then(function successCallback(response) {
			// iusp.waiting.hide();//关闭遮幕
			var resp = response.data;
			if( options.selfNotice ){// 走自定义提示
				if( $.isFunction( options.success ) ) options.success.call(this,resp);// 调用回调方法
			}else{
				// 处理公共提示
				var bool = iusp.resultNotice(resp);
				if(bool){
					if( $.isFunction( options.success ) ) options.success.call(this,resp);// 调用回调方法
				}
			}
		},function errorCallback(response){
			if(options.error && $.isFunction( options.error ) ) options.error.call(this,response);// 调用回调方法
		});
		
	}
	
	
	/**
	 * 返回数据公共提示
	 */
	iusp.resultNotice = function( rsp ){
	
		var win = window.top || window;
		// 无权限访问
		if(rsp.errCode && rsp.errCode == 445){
			IUSP.popupAlert.show( "Sorry,您无权限操作!" );
		}
		// 系统报错
		else if(rsp.errCode && rsp.errCode == 444){
			IUSP.popupAlert.show( "系统出现了问题，请重试！" );
		}
		// 会话失效
		else if(rsp.errCode && rsp.errCode == 406){
			win.location.href = IUSP.contextPath + "/login.jsp?errCode=406";
		}
		// 异地登录，强制退出
		else if(rsp.errCode && rsp.errCode == 601){
			win.location.href = IUSP.contextPath + "/login.jsp?errCode=601";
		}
		else{
			return true;
		}
		return false;
	}
	
	
	
	/***************************************************************************
	 * 与有关代码
	 **************************************************************************/
	
	iusp.layout = function(){
    	
		var win = IUSP.getInnerSize();
		
		var headerHeight = $("#headerDiv").height() || 0;
		var footerHeight = $("#footerDiv").height() || 0;
		$("#contentDiv").height(win.height - headerHeight - footerHeight - 2);
    }
	
	/**
	 * 绑定input控件数据改变后调用查询列表方法
	 * 
	 * queryFormId ：需要绑定控件的父类控件，一般是表单 fun : 自定义回调方法；
	 */
	iusp.initAutoQuery = function(queryFormId,fun){
		
		queryFormId = queryFormId || ".query-form"
		// 获取需要绑定的控件
		var inputs =$(queryFormId).find("input[type='text'],select").not(".ignoreQuery").not(":hidden");
		
		$(inputs).change(function(){
			// 判断自定义回调方法，如果存在，则执行，否则执行searchBtn按钮绑定事件
			if($.isFunction(fun)){
				fun.call(this);
			}else{
				//
				$("#searchBtn").click();
			}
		});
		
		/**
		 * 绑定查询事件
		 */
		$("#searchBtn").click( function(){
			
			// 获取查询参数
			var params = iusp.getSearchParams( queryFormId );
			BAMBOO.reloadGrid( params );
		});
		
		/**
		 * 绑定重置按钮事件
		 */
		$("#resetBtn").click( function(){
			
			$(queryFormId)[0].reset();// 清空查询条件
			$("#searchBtn").click();// 重新加载数据
		});
	}
	
    /**
	 * 
	 * 获取查询参数，同时去掉值两边的空格，未填写的参数移除 param fromId 如 #queryForm
	 */
    iusp.getSearchParams = function( fromId ){
    	
    	var params ={};
    	$( fromId ).find(":input").each(function(i,input){
    		
    		var type = input.type,name = input.name,tagName = input.tagName ,value;
    		
    		// input 为checkbox
    		if( type == "checkbox"){
    			
    		}
    		// input为radio
    		else if( type == "radio"){
    			
    		}
    		// 其他
    		else{
    			value = $.trim( input.value );
    		}
    		if(value){
    			params[name]=value;
    		}
    	})
    	return params;
    }
    
	/**
	 * 加载 history 列表中的某个具体页面。
	 * 
	 * @param win
	 *            window对象
	 * @param value
	 *            number 参数使用的是要访问的 URL 在 History 的 URL 列表中的相对位置。 如：-1 表示返回上一页
	 *            URL 参数使用的是要访问的 URL 如：页面地址
	 */
	iusp.historyGo = function(win,value){
		win.history.go(value);
	}	
    
    
	/**
	 * 开始执行页面方法
	 */
	iusp.start = function(){
		
		// 处理框架
		iusp.layout();
		
		// 处理查询控件
		iusp.initAutoQuery();
		
		if(window.BAMBOO){
			
			window.BAMBOO.start();
		}
	}
})(window,(function() {
	if (window.IUSP)
		return false;
	window.IUSP = {};
	return window.IUSP;
})());