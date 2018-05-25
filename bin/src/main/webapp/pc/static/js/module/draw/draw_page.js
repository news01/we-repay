/**
 * 
 */
(function(){
	
	
	function bamboo(){};
	
    var flag = true;
	bamboo.start = function(){
		
		//选择抽奖等级 改变事件
		$("#prizeLevel").change(function(){
			$(".user_box_left li").removeClass("active");
			var prizlevel = $("#prizeLevel").val();
			var index = Number(prizlevel);
			$(".user_box_left li").eq(prizlevel).addClass("active");
			for (var i = 1; i <= 4; i++) {$("#tableData"+i).empty();}
		});
		
		//抽奖
		$("#drawPrize").click(function(){
			if(flag){
				bamboo.drawPrize();
			}else{
				$("#span_msg").show();
				$("#span_msg").text("正在抽奖中，请勿重复抽奖~~~！");
				return;
			}
		});
		
    }
	
	//抽奖的数目
	bamboo.drawPrize = function(){
		flag = false;
		for (var i = 1; i <= 4; i++) {$("#tableData"+i).empty();}
		$("#span_msg").hide();
		$("#span_msg").text("")
		//抽奖等级
		var prizlevel = $("#prizeLevel").val();
		//奖品数量
		var prizeNum = $("#prizeNum").val();
		if(prizeNum==null||prizeNum==""){
			$("#span_msg").show();
			$("#span_msg").text("奖项数目不能为空！");
			flag = true;
			return;
		}
		
		if(prizeNum==0){
			$("#span_msg").show();
			$("#span_msg").text("奖项数目必须大于0！");
			flag = true;
			return;
		}
		
		var param = {};
		param["prizlevel"] = prizlevel;
		param["prizeNum"] = prizeNum;
		IUSP.post({
			url : IUSP.contextPath + "/draw/drawPrize.do",
			params : param,
			backCall : function( rsp ){
				$("#span_msg").show();
				if(rsp.errCode == 0){
					$("#drawPrize").html("抽奖中...");
					console.log(rsp.result);
					$("#rpIds").val(rsp.result)
					bamboo.drawPrizefulllist(rsp.result,prizlevel);
				}else{
					$("#span_msg").text(rsp.errMsg || "抽奖失败~~~");
					flag = true;
				}
				//$("#drawPrize").html("开始抽奖");
			}
		});
	}
	
	bamboo.queryDrawPrizes = function(prizlevel){
		//填充抽奖记录
		var indexNum=10;//每页展示的条数（控制每页显示的天数）
		
		$(".user_box_left li").removeClass("active");
		var index = Number(prizlevel);
		$(".user_box_left li").eq(prizlevel).addClass("active");
		
		var param = {};
		param["prizlevel"] = prizlevel;
		IUSP.post({
			url : IUSP.contextPath + "/draw/drawPrizeList.do",
			params : param,
			backCall : function( rsp ){
				var listLength = rsp.result.length;
				if(rsp.result != null){
					var list = rsp.result;
					for (var i = 1; i <= 4; i++) {$("#tableData"+i).empty();}
					
					var pageIndex  = 0;
					if(listLength<indexNum*4){//小于40条
						pageIndex = Math.ceil(listLength/indexNum);
					}else{
						pageIndex = 4;
						indexNum = Math.ceil(listLength/4);
					}
					 
					 for (var i = 1; i <= pageIndex; i++) {
						 
						 var pageCount = i*indexNum;
						 if(listLength<=pageCount){
							 pageCount = listLength;
						 }
						 
						 for (var int = (i-1)*indexNum; int < pageCount; int++) {
								var bean = list[int];
								$("#tableData"+i).append("<tr><td>"+(int+1)+"</td><td>"+bean.noPrefixCode+"</td><td>"+bean.name+"</td><td>第"+bean.attribute1+"批</td></tr>");
							}
						 
					 }
				}else{
				}
			}
		});
	}
	
    //定时刷新 抽奖记录填充数据
	bamboo.drawPrizefulllist = function(ids,prizlevel){
		//填充抽奖记录
		var indexNum=10;//每页展示的条数（控制每页显示的天数）
		var rdIds = [];
		for (var i = 0; i < ids.length; i++) {
			rdIds.push(ids[i]);
		}
		rdIds = rdIds.join(",");
		
		var param = {};
		param["prizlevel"] = prizlevel;//奖品等级
		param["rdIds"] = rdIds;//返回所有抽检中奖的记录Id
		IUSP.post({
			url : IUSP.contextPath + "/draw/drawPrizeListByIds.do",
			params : param,
			backCall : function( rsp ){
				    var list = rsp.result;
					console.log(list);
				    var listLength = list.length;
				    var pageIndex  = 0;
					if(listLength<indexNum*4){//小于40条
						pageIndex = Math.ceil(listLength/indexNum);
					}else{
						pageIndex = 4;
						indexNum = Math.ceil(listLength/4);
					}
					
					var indexVar = 0;
					if(pageIndex==0){
						return false;
					}
					
					var indexNumber = 10;//设置刷新的数据时间
					var randIndex = 1;
					var varInterval = setInterval(function(){
						
						if(indexNumber==0){
							indexNumber = 10;//设置刷新的数据时间
							var pageNum =  indexVar/10;
							 var pageCount = pageNum*indexNum;
							 if(listLength<=pageCount){
								 pageCount = listLength;
							 }
							 //填充数据
							for (var int = (pageNum-1)*indexNum; int < pageCount; int++) {
								var bean = list[int];
								$("#tableData"+pageNum).append("<tr><td>"+(int+1)+"</td><td>"+bean.noPrefixCode+"</td><td>"+bean.name+"</td><td>第"+bean.attribute1+"批</td></tr>");
							}
							
							$("#loadingImag"+randIndex).hide();
							randIndex++;
							
							//页数循环完之后 清掉定时器
							if(indexVar/10==pageIndex){
								//关闭定时器
								clearInterval(varInterval);
								//提示信息修改
								flag = true;
								$("#drawPrize").html("开始抽奖");
								$("#span_msg").text("抽奖完成~~~");
								//发送推送消息
								bamboo.setWeiXinMsg(rdIds,prizlevel);
								return;
							}
							
						}
						
						$("#loadingImag"+randIndex).show();
						$("#indexNumber"+randIndex).text(indexNumber);
						
						//页面定时器（秒数）赋值
                        indexNumber--;
						indexVar++;
						
					}, 1000);
				}
			})
	}
	
	//微信
	bamboo.setWeiXinMsg = function(rdIds,prizlevel){
		var param = {};
		param["prizlevel"] = prizlevel;//奖品等级
		param["rdIds"] = rdIds;//返回所有抽检中奖的记录Id
		IUSP.post({
			url : IUSP.contextPath + "/draw/sendDrawMsgByIds.do",
			params : param,
			backCall : function( rsp ){
				
				}
		   })
	}
	
	
	
	
	/**
	 * 赋值给BAMBOO
	 */
	window.BAMBOO = window.BAMBOO ? $.extend(window.BAMBOO ,bamboo):bamboo
})();