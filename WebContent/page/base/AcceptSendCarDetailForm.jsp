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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	$(function() {
		var id ='<%=id%>';
		if (id.length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post('getDetail', {
				id : id
			}, function(result) {
				$('form').form('load', {
					'data.eventType' : result.eventType,
					'data.eventNature' : result.eventNature,
					'data.callPhone' : result.callPhone,
					'data.callAddress' : result.callAddress,
					'data.patientNeed' : result.patientNeed,
					'data.preJudgment' : result.preJudgment,
					'data.sickCondition' : result.sickCondition,
					'data.specialNeeds' : result.specialNeeds,
					'data.humanNumbers' : result.humanNumbers,
					'data.sickName' : result.sickName,
					'data.gender' : result.gender,
					'data.age' : result.age,
					'data.identity' : result.identity,
					'data.contactMan' : result.contactMan,
					'data.contactPhone' : result.contactPhone,
					'data.extension' : result.extension,
					'data.thisDispatcher' : result.thisDispatcher,
					'data.remark' : result.remark,
					'data.isOrNoLitter' : result.isOrNoLitter,
					'data.acceptNumber' : result.acceptNumber,
					'data.acceptStartTime' : result.acceptStartTime,
					'data.acceptType' : result.acceptType,
					'data.toBeSentReason' : result.toBeSentReason,
					'data.endAcceptTime' : result.endAcceptTime,
					'data.cancelReason' : result.cancelReason,
					'data.sendCarTime' : result.sendCarTime,
					'data.sendStation' : result.sendStation,
					'data.carIndentiy' : result.carIndentiy,
					'data.state' : result.state,
					'data.receiveOrderTime' : result.receiveOrderTime,
					'data.taskResult' : result.taskResult,
					'data.reason' : result.reason,
					'data.outCarTime' : result.outCarTime,
					'data.taskRemark' : result.taskRemark,
					'data.arriveSpotTime' : result.arriveSpotTime,
					'data.takeHumanNumbers' : result.takeHumanNumbers,
					'data.toHospitalNumbers' : result.toHospitalNumbers,
					'data.leaveSpotTime' : result.leaveSpotTime,
					'data.deathNumbers' : result.deathNumbers,
					'data.stayHospitalNumbers' : result.stayHospitalNumbers,
					'data.backHospitalNumbers' : result.backHospitalNumbers,
					'data.completeTime' : result.completeTime,
					'data.stationDispatcher' : result.stationDispatcher,
					'data.outCarNumbers' : result.outCarNumbers
				});
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
					<td>事件来源</td>
					<td><input value="120" /></td>
					<td>事件类型</td>
					<td><input name="data.eventType" /></td>
				</tr>
				<tr>
					<td>事件性质</td>
					<td><input name="data.eventNature" /></td>
					<td>主叫号码</td>
					<td><input name="data.callPhone" /></td>
				</tr>
				<tr>
					<td>呼救地点</td>
					<td><input name="data.callAddress" /></td>
					<td>病人需求</td>
					<td><input name="data.patientNeed" /></td>
				</tr>
				<tr>
					<td>初步判断</td>
					<td><input name="data.preJudgment" /></td>
					<td>病情</td>
					<td><input name="data.sickCondition" /></td>
				</tr>
				<tr>
					<td>特殊要求</td>
					<td><input name="data.specialNeeds" /></td>
					<td>人数</td>
					<td><input name="data.humanNumbers" /></td>
				</tr>
				<tr>
					<td>患者姓名</td>
					<td><input name="data.sickName" /></td>
					<td>性别</td>
					<td><input name="data.gender" /></td>
				</tr>
				<tr>
					<td>年龄</td>
					<td><input name="data.age" /></td>
					<td>身份</td>
					<td><input name="data.identity" /></td>
				</tr>
				<tr>
					<td>联系人</td>
					<td><input name="data.contactMan" /></td>
					<td>联系电话</td>
					<td><input name="data.contactPhone" /></td>
				</tr>
				<tr>
					<td>分机</td>
					<td><input name="data.extension" /></td>
					<td>本次调度员</td>
					<td><input name="data.thisDispatcher" /></td>
				</tr>
				<tr>
					<td>备注</td>
					<td><input name="data.remark" /></td>
					<td>是否要担架</td>
					<td><input name="data.isOrNoLitter" /></td>
				</tr>
				<tr>
					<td colspan="2" style="color: red;">第<input
						name="data.acceptNumber" />次受理
					</td>
				</tr>
				<tr>
					<td colspan="4"><hr
							style="height: 1px; border: none; border-top: 1px solid #555555;" /></td>
				</tr>

				<!--受理-->
				<tr>
					<td>开始时刻</td>
					<td><input name="data.acceptStartTime" /></td>
					<td>受理类型</td>
					<td><input name="data.acceptType" /></td>
				</tr>
				<tr>
					<td>待派原因</td>
					<td><input name="data.toBeSentReason" /></td>
					<td>结束时刻</td>
					<td><input name="data.endAcceptTime" /></td>
				</tr>
				<tr>
					<td>撤消原因</td>
					<td><input name="data.cancelReason" /></td>

					<td>派车时刻</td>
					<td><input name="data.sendCarTime" /></td>
				</tr>
				<tr>
					<td>出车医院</td>
					<td><input name="data.sendStation" /></td>
				</tr>
				<tr>
					<td colspan="4"><hr
							style="height: 1px; border: none; border-top: 1px solid #555555;" /></td>
				</tr>
				<!--任务-->
				<tr>
					<td>车辆标识</td>
					<td><input name="data.carIndentiy" /></td>
					<td>状态</td>
					<td><input name="data.state" /></td>
				</tr>
				<tr>
					<td>接受命令时刻</td>
					<td><input name="data.receiveOrderTime" /></td>
					<td>出车结果</td>
					<td><input name="data.taskResult" /></td>
				</tr>
				<tr>
					<td>原因</td>
					<td><input name="data.reason" /></td>
					<td>出车时刻</td>
					<td><input name="data.outCarTime" /></td>
				</tr>
				<tr>
					<td>备注说明</td>
					<td><input name="data.taskRemark" /></td>
					<td>到达现场时刻</td>
					<td><input name="data.arriveSpotTime" /></td>
				</tr>
				<tr>
					<td>接回人数</td>
					<td><input name="data.takeHumanNumbers" /></td>
					<td>入院人数</td>
					<td><input name=data.toHospitalNumbers /></td>
				</tr>
				<tr>
					<td>离开现场时刻</td>
					<td><input name="data.leaveSpotTime" /></td>
					<td>死亡人数</td>
					<td><input name="data.deathNumbers" /></td>
				</tr>
				<tr>
					<td>留观人数</td>
					<td><input name="data.stayHospitalNumbers" /></td>
					<td>返院(转院)人数</td>
					<td><input name="data.backHospitalNumbers" /></td>
				</tr>
				<tr>
					<td>完成时刻</td>
					<td><input name="data.completeTime" /></td>
				</tr>
				<tr>
					<td>分站调度员</td>
					<td><input name="data.stationDispatcher" /></td>
					<td colspan="2" style="color: red;">第<input
						name="data.outCarNumbers" />次出车
					</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="4"><hr
							style="height: 1px; border: none; border-top: 1px solid #555555;" /></td>
				</tr>
				<!--关闭按钮-->
				<tr align="center">
					<td></td>
					<td></td>
					<td align="center"><img class="ext-icon-cancel" title="关闭"
						onclick="parent.$('#dialog').dialog('destroy')"></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>