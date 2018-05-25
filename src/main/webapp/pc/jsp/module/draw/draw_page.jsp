<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>抽奖页面</title>
		<%@include file="/pc/jsp/common/common.jsp"%>
		<link rel="stylesheet" href="${basePath}/static/css/module/draw/luck_draw.css" />
	</head>
	<body onload="onload()">
		<div class="draw_set_box" id="draw_set_box">
			<div class="title_box"></div>
			<div class="set_div">
				<div class="set_div_reset">
					<div class="left_div">
						<ul>
							<li class="draw_div">
								<p class="txt_sty">奖项等级</p>
								<select name="select" class="grade_select" id="prizeLevel">
								    <option value="0">特等奖</option>
								    <option value="1">一等奖</option>
									<option value="2">二等奖</option>
									<option value="3">三等奖</option>
									<option value="4">四等奖</option>
									<option value="5">五等奖</option>
									<option value="6">六等奖</option>
							    </select>
							</li>
							<li class="draw_div">
								<p class="txt_sty">奖项数量</p>
								<input class="inp_sty" id="prizeNum" />
							</li>
						</ul>
					</div>
					<div class="right_div">
						<div class="line_sty"></div>
						<button class="btn_sty" id="drawPrize">开始抽奖</button>
						<span style="color: #ca5a5b; display: block; margin-top: 6px;" id="span_msg"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="draw_user_box" id="draw_user_box">
			<div class="user_title_box">
				<ul class="user_box_left">
				    <li role="presentation" class="select_normal active" onclick="window.BAMBOO.queryDrawPrizes(0)"><a href="javascript:void(0);">特等奖用户</a></li>
					<li role="presentation" class="select_normal" onclick="window.BAMBOO.queryDrawPrizes(1)"><a href="javascript:void(0);">一等奖用户</a></li>
					<li role="presentation" class="select_normal" onclick="window.BAMBOO.queryDrawPrizes(2)"><a href="javascript:void(0);">二等奖用户</a></li>
					<li role="presentation" class="select_normal" onclick="window.BAMBOO.queryDrawPrizes(3)"><a href="javascript:void(0);">三等奖用户</a></li>
					<li role="presentation" class="select_normal" onclick="window.BAMBOO.queryDrawPrizes(4)"><a href="javascript:void(0);">四等奖用户</a></li>
					<li role="presentation" class="select_normal" onclick="window.BAMBOO.queryDrawPrizes(5)"><a href="javascript:void(0);">五等奖用户</a></li>
					<li role="presentation" class="select_normal" onclick="window.BAMBOO.queryDrawPrizes(6)"><a href="javascript:void(0);">六等奖用户</a></li>
				</ul>
				<div class="user_box_right" id="user_box_right">
					<i  id="direction_img" class="up_img"></i>  向上展开
				</div>
			</div>
			<div class="user_name_box" id="user_name_box" style="overflow: hidden;min-height: 928px;">
				<div class="user_name_div user_name_div1">
					<table>
						<thead class="table_title">
							<tr class="table_title">
								<th>序号</th>
								<th>用户编号</th>
								<th>用户姓名</th>
								<th>抽奖批次</th>
							</tr>
						</thead>
						<tbody id="tableData1">
							   <div class="nub_sty_box" id="loadingImag1">
									<div class="nub_sty"></div>
									<div class="big_nub" id="indexNumber1">10</div>
								</div>
						
						</tbody>
					</table>
				</div>
				<div class="user_name_div user_name_div2">
				<table>
						<thead>
							<tr class="table_title">
								<th>序号</th>
								<th>用户编号</th>
								<th>用户姓名</th>
								<th>抽奖批次</th>
							</tr>	
						</thead>
						<tbody id="tableData2">
							<div class="nub_sty_box" id="loadingImag2">
								<div class="nub_sty"></div>
								<div class="big_nub" id="indexNumber2">10</div>
							</div>
							
						</tbody>
					</table>
				</div>
				<div class="user_name_div user_name_div3">
				    <table>
						<thead>
							<tr class="table_title">
								<th>序号</th>
								<th>用户编号</th>
								<th>用户姓名</th>
								<th>抽奖批次</th>
							</tr>
						</thead>
						<tbody id="tableData3">
						    <div class="nub_sty_box" id="loadingImag3">
								<div class="nub_sty"></div>
								<div class="big_nub" id="indexNumber3">10</div>
							</div>
							
						</tbody>
					</table>
				</div>
				<div class="user_name_div user_name_div4">
				<table>
						<thead>
							<tr class="table_title">
								<th>序号</th>
								<th>用户编号</th>
								<th>用户姓名</th>
								<th>抽奖批次</th>
							</tr>
						</thead>
						<tbody id="tableData4">
						   <div class="nub_sty_box" id="loadingImag4">
								<div class="nub_sty"></div>
								<div class="big_nub" id="indexNumber4">10</div>
							</div>
							
						</tbody>
					</table>
				</div>
			</div>
			<input id="rpIds" type="hidden" />
		</div>
	</body>
	<script type="text/javascript">
		function onload(){
			var user_box_right=document.getElementById("user_box_right");
			var user_name_box=document.getElementById("user_name_box");
			user_box_right.onclick=function(){
				var draw_set_box=document.getElementById("draw_set_box");
				if(draw_set_box.style.marginTop=="-300px")
				{
					draw_set_box.style.marginTop="0px";
					//user_name_box.style.height="860px";
					user_box_right.innerHTML='<i  id="direction_img" class="up_img"></i>  向上展开';
					
				}else
				{
					draw_set_box.style.marginTop="-300px";
					//user_name_box.style.height="906px";
					user_box_right.innerHTML='<i  id="direction_img" class="down_img"></i>  向下收起';
				}
				
			}
		}
	</script>
	<script type="text/javascript" src="${basePath}/static/js/module/draw/draw_page.js"></script>
	<script type="text/javascript">
		if (IUSP) {
			IUSP.start();
		}
	</script>
</html>