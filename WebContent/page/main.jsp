<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String version = "20150311";
%>
<!DOCTYPE html>
<html>
<head>
<title>主界面</title>
<jsp:include page="../inc.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/style/header.css?version=<%=version%>">
<script type="text/javascript">
	var mainMenu;
	var mainTabs;
</script>
</head>
<body id="mainLayout" class="easyui-layout">
	<div
		data-options="region:'north',href:'<%=contextPath%>/page/north.jsp'"
		style="height: 110px; overflow: hidden;" class="logo"></div>
	<div data-options="region:'west',href:'',split:true" title="导航"
		style="width: 200px; padding: 10px;">
		<ul id="mainMenu"></ul>
	</div>
	<div
		data-options="region:'south',href:'<%=contextPath%>/page/south.jsp',border:false"
		style="height: 30px; overflow: hidden;"></div>

	<div data-options="region:'center'" style="overflow: hidden;">
		<div id="mainTabs" style="width: 100%; height: 99%;">
			<iframe src="<%=contextPath%>/page/base/RingToAnswerTimes.jsp"
				allowTransparency="true"
				style="border: 0; width: 100%; height: 98%;" frameBorder="0"></iframe>
		</div>
	</div>


	<div id="loginDialog" title="解锁登录" style="display: none;">
		<form method="post" class="form" onsubmit="return false;">
			<table class="table">
				<tr>
					<th width="50">登录名</th>
				</tr>
				<tr>
					<th>密码</th>
					<td><input name="data.pwd" type="password"
						class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="passwordDialog" title="修改密码" style="display: none;">
		<form method="post" class="form" onsubmit="return false;">
			<table class="table">
				<tr>
					<th>新密码</th>
					<td><input id="pwd" name="data.pwd" type="password"
						class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>重复密码</th>
					<td><input type="password" class="easyui-validatebox"
						data-options="required:true,validType:'eqPwd[\'#pwd\']'" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>