$(document).ready(function(){
	$("#menu li").click(function(){
		
		if(this.id=="log_div"){
			//不用管
		}else{
			$("#menu li").removeClass("active");
			$(this).addClass("active");	
		}
		if(this.id=="user"){
			window.location.href=IUSP.contextPath+"/user/touserlist.do?page=user"
		}else if(this.id=="repayrecord"){//交易记录
			window.location.href=IUSP.contextPath+"/repay/torepayrecordlist.do?page=repayrecord"
		}else if(this.id=="repaycount"){//交易记录统计
//			window.location.href=IUSP.contextPath+"/repay/torepaycountlist.do?page=repaycount"
			window.location.href=IUSP.contextPath+"/repay/torepaycountlist.do?page=repaycount"
		}else if(this.id=="pay"){
			window.open("https://pay.weixin.qq.com/index.php/core/home/login?return_url=%2F");            
		}else if(this.id=="draw"){//会员抽奖记录
			window.location.href = IUSP.contextPath+"/draw/drawRecodePage.do?page=draw";            
		}
	})
	$("#menu li").mousemove(function(){
		//$("#menu li").removeClass("hover");		
		if(this.id=="log_div")
		{
			//$(this).css("backgroundColor","#4373ea");
		}else if(this.id=="equipment" || this.id=="space" ||this.id=="instancy"){
		
		}
		else
		{
			$(this).addClass("hover");
		}
		
	})
	$("#menu li").mouseout(function(){
		if(this.id=="log_div")
		{
			//$(this).css("backgroundColor","rgb(107, 149, 255)");
		}else
		{
			$(this).removeClass("hover");
		}
		
	})
	$("#user_div li").mousemove(function(){
		$(this).addClass("hover");
	})
	$("#user_div li").mouseout(function(){
		$(this).removeClass("hover");
	})
	$("#user_info_row").click(function(){
		if($("#dropdown_menu").css("display")=="block"){
			$("#dropdown_menu").css("display","none")
			$(".user_icon_down").removeClass("user_icon_up")
		}else
		{
			$("#dropdown_menu").css("display","block");
			$(".user_icon_down").addClass("user_icon_up");
			
		}
	})
	
	$("#update_pass").click(function(){
		$("#update_Modal").modal("show")
	})
	
	$(document).click(function(e){
		if($(e.target).closest("#user_div").length==0)
		{
			$("#dropdown_menu").css("display","none");
			$(".user_icon_down").removeClass("user_icon_up");
		}
	})
	//var url = location.search; 
	
	function GetRequest() { 
		var url = location.search; //获取url中"?"符后的字串 
		var theRequest = new Object(); 
		if (url.indexOf("?") != -1) { 
		var str = url.substr(1); 
		strs = str.split("&"); 
		for(var i = 0; i < strs.length; i ++) { 
		theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
		} 
		} 
		return theRequest; 
	} 
	var parameters= GetRequest()
	if(parameters.page){
		$("#menu li").removeClass("active");
		$("#"+parameters.page).addClass("active");
	}else if(window.pageMenu){
		$("#menu li").removeClass("active");
		$("#"+window.pageMenu).addClass("active");
	}
});

