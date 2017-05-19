<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<%
	String eventCode = request.getParameter("eventCode");
	if (eventCode == null) {
		eventCode = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.button {
	text-decoration: none;
	display: inline-block;
	overflow: hidden;
	padding: 3px;
	cursor: pointer;
	outline: none;
	text-align: center;
	vertical-align: middle;
	border-radius: 3px; line-height : normal;
	font-size: 12px;
	line-height: normal;
}

.button_click {
	text-decoration: none;
	display: inline-block;
	overflow: hidden;
	padding: 3px;
	cursor: pointer;
	outline: none;
	text-align: center;
	vertical-align: middle;
	border-radius: 3px;
	line-height: normal;
	font-size: 12px;
	padding: 3px;
	background: #77DDFF;
}
</style>
<script type="text/javascript">
	var resultData = [];
	var acceptCount = 0;//受理次数
	var taskCount = 0;//任务次数
	var acceptOrder = 1;//当前受理序号
	var taskOrder = 1;//当前任务序号
	function acceptClick(target,index) {
		$(target).addClass("button_click");
		$(target).siblings().removeClass("button_click");
		var length = resultData.length;
		acceptCount = index;
		taskCount = 0;
		for(var i=0;i<length;i++){
			if((resultData[i].acceptOrder == index) && (resultData[i].taskOrder == 1)){
				$('form').form('load', resultData[i]);
				$('#record').attr('src', resultData[i].record);
			}
			if(resultData[i].acceptOrder == index){
				taskCount += 1; //任务次数
			}
		}
		var str = "";
		for(var i=0;i<taskCount;i++){
			str += cxw.formatString('<a class="button" onclick="taskClick(this,\'{0}\');" id="task{1}">第{2}次出车</a>', i+1,i+1,i+1);
		}
		$("#taskTD").empty().append(str);
		$("#task1").addClass("button_click");
	};
	function taskClick(target,index) {
		taskOrder = index;
		var length = resultData.length;
		for(var i=0;i<length;i++){
			if((resultData[i].acceptOrder == acceptOrder) && (resultData[i].taskOrder == index)){
				$('form').form('load', resultData[i]);
				$('#record').attr('src', resultData[i].record);
			}
		}
	};
	$(function() {
		$("#printBtn").click(function(){
			var param;
			var length = resultData.length;
			for(var i=0;i<length;i++){
				if((resultData[i].acceptOrder == acceptOrder) && (resultData[i].taskOrder == taskOrder)){
					param = resultData[i];
					param = JSON.stringify(param);
				}
			}
			parent.window.open("printHistoryEvent.jsp?data="+param);
		});
		eventCode = '<%=eventCode%>';
		if (eventCode.length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.get('getHistoryEventDetail', {
				eventCode : eventCode
			}, function(result) {
				resultData = result;
				var rLength = result.length;
				if (rLength > 0) {
					data = result[0];
					$('form').form('load', data);
					acceptCount = data.acceptCount; //受理次数
					for(var i=0;i<rLength;i++){
						if(result[i].acceptOrder == 1){
							taskCount += 1; //任务次数
						}
					}
					$('#record').attr('src', result.record);
					var str = '';
					for(var i=0;i<acceptCount;i++){
						str += cxw.formatString('<a class="button" onclick="acceptClick(this,\'{0}\');" id="accept{1}">第{2}次受理</a>', i+1,i+1,i+1);
					}
					$("#acceptTD").empty().append(str);
					$("#accept1").addClass("button_click");
					str = "";
					for(var i=0;i<taskCount;i++){
						str += cxw.formatString('<a class="button" onclick="taskClick(this,\'{0}\');" id="task{1}">第{2}次出车</a>', i+1,i+1,i+1);
					}
					$("#taskTD").empty().append(str);
					$("#task1").addClass("button_click");
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
</head>
<body>
	<div align="center">
		<a href="#" id="printBtn" class="easyui-linkbutton"
			data-options="iconCls:'icon-print'">打印</a>
	</div>
	<form method="post" class="form">
		<fieldset>
			<legend>基本信息</legend>
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
					<td colspan="4" id="acceptTD"></td>
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
					<td colspan="2"><audio id="record" src='' controls='controls'></audio></td>
				</tr>
				<tr>
					<td colspan="2" id="taskTD"></td>
				</tr>
			</table>
		</fieldset>
	</form>
	<div align="center">
		<button
			style="background: url(../../style/images/ext_icons/cancel.png) no-repeat; width: 20px; height: 20px"
			onclick="parent.$('#dialog').dialog('destroy')"></button>
	</div>
</body>
</html>