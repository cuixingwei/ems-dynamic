<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String stationName = (String) session.getAttribute("stationName");
	if (stationName == null) {
		stationName = "未知分站";
	}
	String userName = (String) session.getAttribute("userName");
	if (userName == null) {
		userName = "未知人员";
	}
	String pathContext = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=pathContext%>/css/header.css" />
<script type="text/javascript"
	src="<%=pathContext%>/lib/jquery-2.1.3.js"></script>
<title></title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#logOut").click(function() {
			window.parent.location.replace("<%=pathContext%>");
		});
	});
	function GetTimes(tCount) {
		var result = tCount;
		if (parseInt(tCount, 10) < 10) {
			result = "0" + tCount;
		}
		return result;
	}
	function show() {
		var date = new Date();
		$("#a").html(
				'现在是：' + date.getFullYear() + '年'
						+ GetTimes((date.getMonth() + 1)) + '月'
						+ GetTimes(date.getDate()) + '日&nbsp;&nbsp;'
						+ GetTimes(date.getHours()) + ':'
						+ GetTimes(date.getMinutes()) + ':'
						+ GetTimes(date.getSeconds()) + '&nbsp;&nbsp;星期'
						+ '日一二三四五六'.charAt(new Date().getDay()));
	}

	setInterval(show, 500);

	function modifyPassword() {
		var left = (parseInt(screen.width, 10) - 500) / 2;
		var top = (parseInt(screen.height, 10) - 300) / 2;
		window
				.open(
						"ModifyPassword.jsp",
						"newwin",
						"top"
								+ top
								+ ",left="
								+ left
								+ ", width=500,height=300,location=no,toolbar=no,scrollbars=no,menubar=no,z-look=yes,resizable=no");
	}

	function helpUse() {
		window
				.open("helpUse.jsp", "newwin",
						"location=no,toolbar=no,menubar=no,z-look=yes,resizable=no,scrollbars=yes");
	}
</script>

<style type="text/css">
a:link {
	color: #0000ff;
	text-decoration: none
} /* 未被访问的链接 红色 没有下划线*/
a:visited {
	color: #0000ff;
	text-decoration: none
} /* 已被访问过的链接 绿色 没有下划线*/
a:hover {
	color: #FF0000;
	text-decoration: underline
} /* 鼠标悬浮在上的链接 橙色 有下划线 下划线为underline 上划线为overline 无下划线为none*/
a:active {
	color: #0000FF;
	text-decoration: none
} /* 鼠标点中激活链接 蓝色 没有下划线*/
</style>
</head>
<body style="margin: 0 auto">
	<div class="hBgG"
		style="width: 100%; height: 69px; background-color: #44ACFB;">
		<div class="leftBgG" style="width: 500px; height: 69px; float: left;"></div>
		<div class="hTopRight"
			style="width: 450px; height: 69px; float: right;"></div>
	</div>
	<div style="width: 100%; height: 38px;" class="htopBgG">
		<div class="hleftBotom"
			style="width: 150px; height: 38px; float: left;"></div>
		<div
			style="width: 220px; height: 38px; float: left; background: url(../image/topmid_spilter.png) right center no-repeat;">
			<span
				style="background: url(../image/curretperon.png) left center no-repeat; padding-left: 20px; margin-top: 12px; margin-left: 10px; display: inline-block;"><%=userName%></span>
			<span
				style="background: url(../image/station.png) left center no-repeat; padding-left: 20px; margin-left: 10px;"><%=stationName%></span>
		</div>

		<span id="a"
			style="color: #FF0000; float: left; width: 600px; height: 25px; margin-top: 10px; margin-left: 10px; display: inline-block;"></span>

		<div class="hleftRight"
			style="width: 125px; height: 38px; float: right;"></div>

		<div style="width: 220px; height: 38px; float: right; color: blue;">
			<a href="javascript:void(0)" onclick="modifyPassword()"
				style="width: 70px; height: 36px; display: inline-block; line-height: 36px; text-align: center; vertical-align: middle; padding-left: 10px; background: url(../image/topmid_spilter.png) left center no-repeat;">修改密码</a>
			<a href="javascript:void(0)" onclick="helpUse()"
				style="width: 30px; height: 36px; line-height: 36px; vertical-align: middle; padding-left: 10px; background: url(../image/topmid_spilter.png) left center no-repeat;">帮助</a>
			<a id="logOut"
				style="width: 30px; height: 36px; line-height: 36px; vertical-align: middle; padding-left: 10px; background: url(../image/topmid_spilter.png) left center no-repeat;">退出</a>
		</div>
	</div>
</body>
</html>