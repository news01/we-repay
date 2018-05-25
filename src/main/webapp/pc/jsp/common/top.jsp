<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="header" >
		<div class="pull-left" >
			<ul id="menu" style="height: 100%;">
				<li id="log_div"><img src="${basePath}/static/images/common/ZHOUTIANBAIRUI.png"/></li>
				<li id="user" class="active"><i class="icon_index"></i> 会员列表</li>
				<li id="repayrecord"><i class="icon_instancy"></i>支付流水记录</li>
				<li id="repaycount"><i class="icon_statistics"></i>会员年费统计</li>
				<li id="draw"><i class="icon_instancy"></i>抽奖记录</li>
				<li id="pay"><i class="icon_instancy"></i>微信商户平台</li>
			</ul> 
		</div>
		<div  class="pull-right">
			<div id="user_div">
					<div class="row-fluid" id="user_info_row" >
							<img src="${basePath}/static/images/common/user_img.png" />
						<span>欢迎您!${sessionScope.USER_INFO.realName }(
						   <c:set var="testStr" value="${sessionScope.USER_INFO.ccUserName }"/>  
						   <c:choose> 
								<c:when test="${fn:length(testStr) > 5}">  
							        <c:out value="${fn:substring(testStr, 0, 5)}..." />  
							    </c:when>  
							    <c:otherwise>  
							        <c:out value="${testStr }" />  
							     </c:otherwise>
						    </c:choose>
						)登录</span><i  class="user_icon_down"></i>
					</div>
					<ul id="dropdown_menu">
					<!-- 	<li><i class="user_icon_name"></i> 个人信息</li> -->
						<li id="update_pass"><i class="user_icon_pass"></i> 修改密码</li>
						<li id="logout"><i class="user_icon_logout"></i> 退出</li>
					</ul>
				</div>
		</div>

		<div class="modal fade" id="update_Modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h5 class="modal-title" id="myModalLabel">
							修改密码
						</h5>
					</div>
					<div class="modal-body">
						<div style="width:460px;margin: 0px auto;">
							<div class="row-fluid">
								<label class="col-sm-3">原始密码： </label>
								<div class="col-sm-9">
									<input type="password" id="oldPwd"/>
								</div>
							</div>
							<div class="row-fluid">
								<label class="col-sm-3">新密码： </label>
								<div class="col-sm-9">
									<input type="password"  id="newPwd"/>
								</div>
							</div>
							<div class="row-fluid">
								<label class="col-sm-3">确认新密码： </label>
								<div class="col-sm-9">
									<input type="password" id="comfirmPwd"/>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn " data-dismiss="modal">取消
						</button>
						<button type="button" class="btn btnblue" id="submitBtn">提交</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>

</div>
<script type="text/javascript">
$(function(){
	
	
	//修改密码
	$("#submitBtn").click(function(){
		var oldPwd = $("#oldPwd").val();
		if(oldPwd==null||oldPwd==""){
			IUSP.popupAlert.show("原始密码不能为空！");
			return;
		}
		var newPwd = $("#newPwd").val();
		if(newPwd==null||newPwd==""){
			IUSP.popupAlert.show("新密码不能为空！");
			return;
		}
		var comfirmPwd = $("#comfirmPwd").val();
		if(comfirmPwd==null||comfirmPwd==""){
			IUSP.popupAlert.show("确认新密码不能为空！");
			return;
		}
		if(newPwd!=comfirmPwd){
			IUSP.popupAlert.show("新密码与确认新密码不一致！");
			return;
		}
		
		var param={};
		param["oldCcUserPwd"] = oldPwd;
		param["newCcUserPwd"] = newPwd;
		IUSP.post({
			url : IUSP.contextPath + "/user/updatepwd.do",
			params : param,
			backCall : function( rsp ){
				if(rsp.errCode == 0){
					IUSP.message.show("修改成功！");
					window.location.href=IUSP.contextPath+"/login/logout.do";
				}else{
					IUSP.popupAlert.show( rsp.errMsg || "修改失败");
				}
			}
		})
	});
	
	
	//退出登录
	$("#logout").click(function(){
		window.location.href=IUSP.contextPath+"/login/logout.do";
	});
})


</script>
