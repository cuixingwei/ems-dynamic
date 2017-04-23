<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<%
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	$(function() {
		var cxw = cxw || {};
		cxw.id = '<%=id%>';
		if (cxw.id.length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.get('getDetail', {
				id : cxw.id
			}, function(result) {
				$('#record').attr('src',result.record);
				$('form').form('load', result);
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>资源基本信息</legend>
			<table class="table" style="width: 100%;">
				<!--事件-->
				<tr>
					<td>现场地址</td>
					<td><input name="eventName" readonly="readonly" /></td>
					<td>等车地点</td>
					<td><input name="waitAddress" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>事件来源</td>
					<td><input name="eventSource" readonly="readonly" /></td>
					<td>事件类型</td>
					<td><input name="eventType" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>事件性质</td>
					<td><input name="eventNature" readonly="readonly" /></td>
					<td>主叫号码</td>
					<td><input name="callPhone" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>天气状态</td>
					<td><input name="weatherState" readonly="readonly" /></td>
					<td>路况</td>
					<td><input name="roadState" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>初步判断</td>
					<td><input name="preJudgment" readonly="readonly" /></td>
					<td>病情</td>
					<td><input name="sickCondition" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>送往地点</td>
					<td><input name="sendTarget" readonly="readonly" /></td>
					<td>人数</td>
					<td><input name="humanNumbers" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>患者姓名</td>
					<td><input name="sickName" readonly="readonly" /></td>
					<td>性别</td>
					<td><input name="gender" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>年龄</td>
					<td><input name="age" readonly="readonly" /></td>
					<td>身份</td>
					<td><input name="identity" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>联系人</td>
					<td><input name="contactMan" readonly="readonly" /></td>
					<td>联系电话</td>
					<td><input name="contactPhone" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>备注</td>
					<td><input name="remark" readonly="readonly" /></td>
					<td>是否要担架</td>
					<td><input name="isOrNoLitter" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>本次调度员</td>
					<td><input name="thisDispatcher" readonly="readonly" /></td>
				</tr>
				<tr>
					<!-- <td colspan="2" style="color: red;">第<input
						name="data.acceptNumber" readonly="readonly" />次受理
					</td> -->
				</tr>
				<tr>
					<td colspan="4"><hr
							style="height: 1px; border: none; border-top: 1px solid #555555;" /></td>
				</tr>

				<!--受理-->
				<tr>
					<td>开始时刻</td>
					<td><input name="acceptStartTime" readonly="readonly" /></td>
					<td>受理类型</td>
					<td><input name="acceptType" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>挂起原因</td>
					<td><input name="suspendReason" readonly="readonly" /></td>
					<td>结束时刻</td>
					<td><input name="endAcceptTime" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>撤消原因</td>
					<td><input name="cancelReason" readonly="readonly" /></td>

					<td>派车时刻</td>
					<td><input name="sendCarTime" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>出车医院</td>
					<td><input name="sendStation" readonly="readonly" /></td>
				</tr>
				<tr>
					<td colspan="4"><hr
							style="height: 1px; border: none; border-top: 1px solid #555555;" /></td>
				</tr>
				<!--任务-->
				<tr>
					<td>车辆标识</td>
					<td><input name="carIndentiy" readonly="readonly" /></td>
					<td>状态</td>
					<td><input name="state" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>接受命令时刻</td>
					<td><input name="receiveOrderTime" readonly="readonly" /></td>
					<td>出车结果</td>
					<td><input name="taskResult" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>原因</td>
					<td><input name="reason" readonly="readonly" /></td>
					<td>出车时刻</td>
					<td><input name="outCarTime" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>到达现场时刻</td>
					<td><input name="arriveSpotTime" readonly="readonly" /></td>
					<td>离开现场时刻</td>
					<td><input name="leaveSpotTime" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>空车原因</td>
					<td><input name="emptyVehicleReason" readonly="readonly" /></td>
					<td>中止原因</td>
					<td><input name="stopTaskReason" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>完成时刻</td>
					<td><input name="completeTime" readonly="readonly" /></td>
					<td>站内待命时刻</td>
					<td><input name="taskAwaitTime" readonly="readonly" /></td>
				</tr>
				<tr>
					<!-- <td style="color: red;">第<input name="data.outCarNumbers" style="width: 20px"
						readonly="readonly" />次出车
					</td> -->
					<td></td>
				</tr>
			</table>
		</fieldset>
	</form>
	<div align="center"><button style="background: url(../../style/images/ext_icons/cancel.png) no-repeat;width:20px;height: 20px" onclick="parent.$('#dialog').dialog('destroy')"></button>
	</div>
</body>
</html>