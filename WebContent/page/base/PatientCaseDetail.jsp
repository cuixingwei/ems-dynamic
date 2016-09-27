<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String version = "20150311";
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var grid;
	var exportData = function() {
		var url = "exportPatientCaseDetailDatas?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&driver="
				+ $('#driver').val() + "&nurse=" + $("#nurse").val()
				+ "&doctor=" + $("#doctor").val() + "&profession="
				+ $('#profession').combobox('getValue') + "&identity="
				+ $('#identity').combobox('getValue') + "&patientCooperation="
				+ $('#patientCooperation').combobox('getValue') + "&outCome="
				+ $('#outCome').combobox('getValue') + "&carPlate="
				+ $('#carPlate').combobox('getValue') + "&illReason="
				+ $('#illReason').combobox('getValue') + "&spotAddrType="
				+ $('#spotAddrType').combobox('getValue') + "&sendAddrType="
				+ $('#sendAddrType').combobox('getValue') + "&deathProof="
				+ $('#deathProof').combobox('getValue') + "&aidResult="
				+ $('#aidResult').combobox('getValue') + "&illState="
				+ $('#illState').combobox('getValue') + "&illClass="
				+ $('#illClass').combobox('getValue') + "&illDepartment="
				+ $('#illDepartment').combobox('getValue') + "&sex="
				+ $('#sex').combobox('getValue') + "&patientName="
				+ $("#patientName").val() + "&doctorDiagnosis="
				+ $("#doctorDiagnosis").val();
		window.location.href = url;
	};
	/* 初始化页面标签 */
	function init() {
		$('#startTime').datetimebox({
			required : true,
			value : firstOfMouth()
		});
		$('#endTime').datetimebox({
			value : getCurrentTime()
		});

		$('#carPlate').combobox({
			url : 'getCars',
			valueField : 'carCode',
			textField : 'carIdentification',
			editable : false,
			method : 'get'
		});

		$('#illDepartment').combobox({
			url : 'GetPatientDepartment',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		$('#illClass').combobox({
			url : 'GetPatientClass',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		$('#illReason').combobox({
			url : 'GetPatientReason',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		$('#illState').combobox({
			url : 'GetIllState',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		$('#aidResult').combobox({
			url : 'GetAidResult',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		$('#deathProof').combobox({
			url : 'GetDeathProve',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		$('#sendAddrType').combobox({
			url : 'GetTakenPlaceType',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		$('#spotAddrType').combobox({
			url : 'GetLocaleType',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		$('#outCome').combobox({
			url : 'GetOutCome',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		$('#patientCooperation').combobox({
			url : 'GetCooperate',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		$('#identity').combobox({
			url : 'GetIdentity',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		$('#profession').combobox({
			url : 'GetProfession',
			valueField : 'code',
			textField : 'name',
			editable : false,
			method : 'get'
		});

		grid = $('#grid').datagrid(
				{
					url : 'getPatientCaseDetailDatas',
					pagePosition : 'top',
					pagination : true,
					striped : true,
					singleSelect : true,
					nowrap : false,
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'alarmTime',
						title : '接警时间',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'alarmAddr',
						title : '接警地址',
						resizable : true,
						width : "9%",
						align : 'center',
					}, {
						field : 'arriveSpotTime',
						title : '到达现场时间',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'patientName',
						title : '患者姓名',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'age',
						title : '年龄',
						resizable : true,
						width : "6%",
						align : 'center',
					}, {
						field : 'sex',
						title : '性别',
						resizable : true,
						width : "6%",
						align : 'center'
					}, {
						field : 'judgementOnPhone',
						title : '医生诊断',
						resizable : true,
						width : "8%",
						align : 'center'
					}, {
						field : 'pastIllness',
						title : '既往病史',
						resizable : true,
						width : "9%",
						align : 'center',
					}, {
						field : 'arriveHospitalTime',
						title : '到达医院时间',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'cureMeasure',
						title : '治疗措施',
						resizable : true,
						width : "9%",
						align : 'center'
					}, {
						field : 'sendHospital',
						title : '送往医院',
						resizable : true,
						width : "8%",
						align : 'center',
					}, {
						field : 'plateNo',
						title : '车牌号码',
						resizable : true,
						width : "7%",
						align : 'center'
					} ] ],
					//toolbar : '#toolbar',
					onBeforeLoad : function(param) {
						var varify = cxw.checkStartTimeBeforeEndTime(
								'#startTime', '#endTime');
						if (!varify) {
							$.messager.alert('警告', '结束时间要大于开始时间', 'warning');
						}
					}
				});
	}

	$(document).ready(function() {
		init();
		grid.datagrid('load', cxw.serializeObject($('#searchForm')))
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',title:'查询条件',split:true"
		style="height: 230px;">
		<div id="toolbar">
			<table>
				<tr>
					<td>
						<form id="searchForm">
							<table>
								<tr>
									<td>查询时间:</td>
									<td><input id="startTime" name="startTime"
										style="width: 150px;" />至<input id="endTime" name="endTime"
										style="width: 150px;" /></td>
									<td>医生诊断:</td>
									<td><input id="doctorDiagnosis" name="doctorDiagnosis"
										style="width: 300px;" /></td>
									<td colspan="1">&nbsp;<a href="javascript:void(0);"
										class="easyui-linkbutton"
										data-options="iconCls:'ext-icon-zoom',plain:true"
										onclick="grid.datagrid('load',cxw.serializeObject($('#searchForm')));">查询</a></td>
									<td><a href="javascript:void(0);"
										class="easyui-linkbutton"
										data-options="iconCls:'ext-icon-table_go',plain:true"
										onclick="exportData();">导出</a></td>
								</tr>
								<tr>
									<td>疾病科別:</td>
									<td><input id="illDepartment" name="illDepartment"
										style="width: 150px;" /></td>
									<td>疾病分类:</td>
									<td><input id="illClass" name="illClass"
										style="width: 150px;" /></td>
									<td>患者姓名:</td>
									<td><input id="patientName" name="patientName"
										style="width: 150px;" /></td>
								</tr>
								<tr>
									<td>病情:</td>
									<td><input id="illState" name="illState"
										style="width: 150px;" /></td>
									<td>救治结果:</td>
									<td><input id="aidResult" name="aidResult"
										style="width: 150px;" /></td>
									<td>死亡证明:</td>
									<td><input id="deathProof" name="deathProof"
										style="width: 150px;" /></td>
								</tr>
								<tr>
									<td>送往地点类型:</td>
									<td><input id="sendAddrType" name="sendAddrType"
										style="width: 150px;" /></td>
									<td>现场地点类型:</td>
									<td><input id="spotAddrType" name="spotAddrType"
										style="width: 150px;" /></td>
									<td>疾病原因:</td>
									<td><input id="illReason" name="illReason"
										style="width: 150px;" /></td>
								</tr>
								<tr>
									<td>车辆标识:</td>
									<td><input id="carPlate" name="carPlate"
										style="width: 150px;" /></td>
									<td>转归结果:</td>
									<td><input id="outCome" name="outCome"
										style="width: 150px;" /></td>
									<td>病家合作:</td>
									<td><input id="patientCooperation"
										name="patientCooperation" style="width: 150px;" /></td>
								</tr>
								<tr>
									<td>性别:</td>
									<td><select id="sex" class="easyui-combobox" name="sex" style="width: 150px;">
											<option value="">--请选择--</option>
											<option value="男">男</option>
											<option value="女">女</option>
									</select></td>
									<td>身份:</td>
									<td><input id="identity" name="identity"
										style="width: 150px;" /></td>
									<td>职业:</td>
									<td><input id="profession" name="profession"
										style="width: 150px;" /></td>
								</tr>
								<tr>
									<td>司机:</td>
									<td><input id="driver" name="driver" style="width: 150px;" /></td>
									<td>医生:</td>
									<td><input id="doctor" name="doctor" style="width: 150px;" /></td>
									<td>护士:</td>
									<td><input id="nurse" name="nurse" style="width: 150px;" /></td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>

</body>
</html>