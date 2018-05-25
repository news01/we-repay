<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/pc/jsp/common/common.jsp"%>

</head>
<body>
	<div style="width: 100%;">
		<form class="edit-form" id="editForm">
			<table>


				<input type="text" placeholder="用户昵称" id="account" name="nickname" class="easyui-validatebox"
					data-options="required:true,validType:'length[4,20]',missingMessage:'用户名不能为空，请输入用户名',invalidMessage:'用户名的长度为4-20位'" />
				<input type="hidden" id="userId" name="userId" />

				<input type="text" placeholder="真实姓名" class="easyui-validatebox" id="realName" name="realName"
					data-options="required:true,validType:'CHS',missingMessage:'姓名不能为空，请输入姓名'" />
					
				<input type="text" placeholder="性别" id="sex" name="sex" class="easyui-validatebox"
					data-options="required:true,validType:'length[0,1]',missingMessage:'性别不能为空，请输入公众号标示'" />

				<input type="text" placeholder="电话" id="mobile" name="mobile" class="easyui-validatebox"
					data-options="required:true,validType:'phone',missingMessage:'手机号码不能为空，请输入手机号码'" />

				<input type="text" placeholder="邮箱" id="email" name="email" />

				<input type="text" placeholder="公众号标示" id="public_sign" name="public_sign" class="easyui-validatebox"
					data-options="required:true,validType:'length[4,20]',missingMessage:'公众号标示不能为空，请输入公众号标示'" />

				<input type="text" placeholder="国家" id="country" name="country" class="easyui-validatebox"
					data-options="required:true,validType:'length[2,20]',missingMessage:'国家不能为空，请输入国家'" />
					
				<input type="text" placeholder="省份" id="province" name="province" class="easyui-validatebox"
					data-options="required:true,validType:'length[2,20]',missingMessage:'省份不能为空，请输入省份'" />

				<input type="text" placeholder="城市" id="city" name="city" class="easyui-validatebox"
					data-options="required:true,validType:'length[2,20]',missingMessage:'城市不能为空，请输入城市'" />
					


			</table>
		</form>

		<div class="modal-footer">
			<button class="btn btn-primary" id="saveBtn">保存</button>
			<button id="modalCloseBtn" class="btn btn-default">关闭</button>
		</div>
	</div>

</body>
<script type="text/javascript" src="${basePath}/static/js/module/user/user_list.js"></script>
<script type="text/javascript">
	if(IUSP){
		IUSP.start();
	}
</script>
</html>