<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%
	String contextPath = request.getContextPath();
%>
<%
	String data = request.getParameter("data");
	if (data == null) {
		data = "";
	}
	String User_Agent = request.getHeader("User-Agent");
	if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE") > -1
			&& (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1
					|| StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 7") > -1
					|| StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 8") > -1)) {
		out.println("<script src='" + contextPath
				+ "/jslib/jquery-1.9.1.js' type='text/javascript' charset='utf-8'></script>");
	} else {
		out.println("<script src='" + contextPath
				+ "/jslib/jquery-2.0.3.js' type='text/javascript' charset='utf-8'></script>");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var data;
	$(function() {
		data = '<%=data%>';
		data = JSON.parse(data);
		if(data){
			$("#eventName").html(data.eventName);
			$("#waitAddress").html(data.waitAddress);
			$("#eventSource").html(data.eventSource);
			$("#eventType").html(data.eventType);
			$("#eventNature").html(data.eventNature);
			$("#callPhone").html(data.callPhone);
			$("#callAddress").html(data.callAddress);
			$("#patientNeed").html(data.patientNeed);
			$("#preJudgment").html(data.preJudgment);
			$("#sickCondition").html(data.sickCondition);
			$("#specialNeeds").html(data.specialNeeds);
			$("#humanNumbers").html(data.humanNumbers);
			$("#sickName").html(data.sickName);
			$("#gender").html(data.gender);
			$("#age").html(data.age);
			$("#identity").html(data.identity);
			$("#contactMan").html(data.contactMan);
			$("#contactPhone").html(data.contactPhone);
			$("#extension").html(data.extension);
			$("#thisDispatcher").html(data.thisDispatcher);
			$("#remark").html(data.remark);
			$("#isOrNoLitter").html(data.isOrNoLitter);
			$("#acceptStartTime").html(data.acceptStartTime);
			$("#acceptType").html(data.acceptType);
			$("#toBeSentReason").html(data.toBeSentReason);
			$("#endAcceptTime").html(data.endAcceptTime);
			$("#cancelReason").html(data.cancelReason);
			$("#sendCarTime").html(data.sendCarTime);
			$("#sendStation").html(data.sendStation);
			$("#carIndentiy").html(data.carIndentiy);
			$("#state").html(data.state);
			$("#receiveOrderTime").html(data.receiveOrderTime);
			$("#taskResult").html(data.taskResult);
			$("#reason").html(data.reason);
			$("#outCarTime").html(data.outCarTime);
			$("#taskRemark").html(data.taskRemark);
			$("#arriveSpotTime").html(data.arriveSpotTime);
			$("#takeHumanNumbers").html(data.takeHumanNumbers);
			$("#toHospitalNumbers").html(data.toHospitalNumbers);
			$("#leaveSpotTime").html(data.leaveSpotTime);
			$("#deathNumbers").html(data.deathNumbers);
			$("#stayHospitalNumbers").html(data.stayHospitalNumbers);
			$("#backHospitalNumbers").html(data.backHospitalNumbers);
			$("#completeTime").html(data.completeTime);
			$("#stationDispatcher").html(data.stationDispatcher);
		}
	});
</script>
</head>
<body>
	<form method="post" class="form">

		<table class="table"
			style="width: 80%; margin-left: 10%;">
			<caption style="font-size: 20px;margin-bottom: 10px;">
				<b>事件详细信息</b>
			</caption>
			<!--事件-->
			<tr>
				<td>事件名称</td>
				<td><span id="eventName"></span></td>
				<td>等车地点</td>
				<td><span id="waitAddress"></span></td>
			</tr>
			<tr>
				<td>事件来源</td>
				<td><span id="eventSource"></span></td>
				<td>事件类型</td>
				<td><span id="eventType"></span></td>
			</tr>
			<tr>
				<td>事件性质</td>
				<td><span id="eventNature"></span></td>
				<td>主叫号码</td>
				<td><span id="callPhone"></span></td>
			</tr>
			<tr>
				<td>呼救地点</td>
				<td><span id="callAddress"></span></td>
				<td>病人需求</td>
				<td><span id="patientNeed"></span></td>
			</tr>
			<tr>
				<td>初步判断</td>
				<td><span id="preJudgment"></span></td>
				<td>病情</td>
				<td><span id="sickCondition"></span></td>
			</tr>
			<tr>
				<td>特殊要求</td>
				<td><span id="specialNeeds"></span></td>
				<td>人数</td>
				<td><span id="humanNumbers"></span></td>
			</tr>
			<tr>
				<td>患者姓名</td>
				<td><span id="sickName"></span></td>
				<td>性别</td>
				<td><span id="gender"></span></td>
			</tr>
			<tr>
				<td>年龄</td>
				<td><span id="age"></span></td>
				<td>身份</td>
				<td><span id="identity"></span></td>
			</tr>
			<tr>
				<td>联系人</td>
				<td><span id="contactMan"></span></td>
				<td>联系电话</td>
				<td><span id="contactPhone"></span></td>
			</tr>
			<tr>
				<td>分机</td>
				<td><span id="extension"></span></td>
				<td>本次调度员</td>
				<td><span id="thisDispatcher"></span></td>
			</tr>
			<tr>
				<td>备注</td>
				<td><span id="remark"></span></td>
				<td>是否要担架</td>
				<td><span id="isOrNoLitter"></span></td>
			</tr>
			<tr>
				<td colspan="4"><hr
						style="height: 1px; border: none; border-top: 1px solid #555555;"></td>
			</tr>

			<!--受理-->
			<tr>
				<td>开始时刻</td>
				<td><span id="acceptStartTime"></span></td>
				<td>受理类型</td>
				<td><span id="acceptType"></span></td>
			</tr>
			<tr>
				<td>待派原因</td>
				<td><span id="toBeSentReason"></span></td>
				<td>结束时刻</td>
				<td><span id="endAcceptTime"></span></td>
			</tr>
			<tr>
				<td>撤消原因</td>
				<td><span id="cancelReason"></span></td>

				<td>派车时刻</td>
				<td><span id="sendCarTime"></span></td>
			</tr>
			<tr>
				<td>出车医院</td>
				<td><span id="sendStation"></span></td>
			</tr>
			<!--任务-->
			<tr>
				<td>车辆标识</td>
				<td><span id="carIndentiy"></span></td>
				<td>状态</td>
				<td><span id="state"></span></td>
			</tr>
			<tr>
				<td>接受命令时刻</td>
				<td><span id="receiveOrderTime"></span></td>
				<td>出车结果</td>
				<td><span id="taskResult"></span></td>
			</tr>
			<tr>
				<td>原因</td>
				<td><span id="reason"></span></td>
				<td>出车时刻</td>
				<td><span id="outCarTime"></span></td>
			</tr>
			<tr>
				<td>备注说明</td>
				<td><span id="taskRemark"></span></td>
				<td>到达现场时刻</td>
				<td><span id="arriveSpotTime"></span></td>
			</tr>
			<tr>
				<td>接回人数</td>
				<td><span id="takeHumanNumbers"></span></td>
				<td>入院人数</td>
				<td><span id="toHospitalNumbers"></span></td>
			</tr>
			<tr>
				<td>离开现场时刻</td>
				<td><span id="leaveSpotTime"></span></td>
				<td>死亡人数</td>
				<td><span id="deathNumbers"></span></td>
			</tr>
			<tr>
				<td>留观人数</td>
				<td><span id="stayHospitalNumbers"></span></td>
				<td>返院(转院)人数</td>
				<td><span id="backHospitalNumbers"></span></td>
			</tr>
			<tr>
				<td>完成时刻</td>
				<td><span id="completeTime"></span></td>
			</tr>
			<tr>
				<td>分站调度员</td>
				<td><span id="stationDispatcher"></span></td>
				<td colspan="2" id="taskTD"></td>
			</tr>
		</table>
	</form>
</body>
</html>