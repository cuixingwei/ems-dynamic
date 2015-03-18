<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../lib/jquery-2.1.3.js"></script>
<script type="text/javascript" src="../lib/jquery.cookie.js"></script>
<title>主界面</title>
</head>
<frameset border="0" framespacing="0" rows="110,*,35" frameborder="no"
	cols="*">
	<frame id="topFrame" name="topFrame" src="header.jsp" noresize="noresize"
		scrolling="no"></frame>
	<frameset id="mainFrm" border="0" rows="*" frameborder="no"
		cols="220,10,*">
		<frame id="left" style="OVERFLOW: auto" name="left" src="menu.jsp"
			frameborder="0"></frame>
		<frame id="middle" frameborder="0" src="middle.html"></frame>
		<frame id="right" style="OVERFLOW: auto" name="right" src="RingToAnswerTimes.jsp"
			frameborder="0"></frame>
	</frameset>
	<frame id="footFrame" name="footFrame" src="footer.jsp" noresize="noresize"
		scrolling="no"></frame>
</frameset>
</html>