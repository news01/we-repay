<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	    <title>IUSP</title>
	    <%@include file="/app/jsp/common/common.jsp"%>
	    
	    <link href="${basePath}/static/plugin/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	    <link href="${basePath}/static/plugin/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
	   <!--重写bootstrap-->
	    <link href="${basePath}/static/css/common/bootstrap_edit.css" rel="stylesheet">
	    <!--
	    	作者：offline
	    	时间：2017-06-22
	    	描述：公用的css
	    -->
	    <link href="${basePath}/static/css/common/common.css" rel="stylesheet">
	    <!---->
	    <style type="text/css">
	    	/* .success_icon{
	    		display: inline-block;
			  	width: 4.9583rem;
			  	height:4.9583rem;
			  	margin-left:1rem;
			  	vertical-align: middle;
			  	background-repeat: no-repeat;
			  	background-image: url(${basePath}/static/images/module/@2x/success_icon.png);
	    	} */
	    	.Failure_icon{
	    		display: inline-block;
			  	width: 4.9583rem;
			  	height:4.9583rem;
			  	margin-left:1rem;
			  	vertical-align: middle;
			  	background-repeat: no-repeat;
			  	background-image: url(${basePath}/static/images/module/@2x/Failure_icon.png);
	    	}
	    	/* .Failure_icon{
	    		display: inline-block;
			  	width: 1.9583rem;
			  	height:1.9583rem;
			  	vertical-align: middle;
			  	background-repeat: no-repeat;
			  	background-image: url(${basePath}/static/images/module/@2x/Failure_icon.png);
	    	}  */
	    	.pay_status_text{
	    		font-size:0.75rem;
	    		color:#10c55b
	    	}
	    	#carryOut{
	    		
	    		width:7.75rem;
	    		height: 1.875rem;
	    		font-size:0.75rem;
	    		margin: 0px auto;
	    		margin-top:2rem;
	    		background-color: #10c55b;
	    		color: white;
	    		line-height: 1.875rem;
	    		border-radius: 0.125rem;
	    	}
	    	.child_title_div{
	    		background-color: #FCFCFC;
	    	}
	    </style>
	</head>
	<body>
		<div class="container">
			<!--支付-->
			<div id="equipment_Details" class="pay_status_page" >
				<div class="row-fluid child_title_div" >
					<p class="pagetitle">提醒</p>
				</div>
				<div class="row-fluid">
					<div class="row-fluid" style="margin-top: 2rem;">
						<!-- <p><i class="success_icon"></i></p> -->
						<p><i class="Failure_icon"></i></p>
						<p class="pay_status_text">微信自动登录失败</p>
					</div>
					<!-- <div class="row-fluid" id="carryOut"">
						重试
					</div> -->
				</div>
			</div>
		</div>
	<!--rem.js-->
	<script type="text/javascript" src="${basePath}/static/js/common/client_width_rem.js" ></script>
	<script type="text/javascript">
		(function(){
			
			/*  $('#carryOut').click(function(){
				//window.location = IUSP.contextPath + '/user/touserinfo.do';
			});  */
		})();
		
	</script>
	</body>
	
</html>
