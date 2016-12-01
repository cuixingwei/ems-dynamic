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
<style type="text/css">
.combobox-item{cursor: pointer} /*修改easyUI的combobox下拉列表默认鼠标样式*/
li {
	list-style: none;
}

ul {
	width: 98%;
	margin-bottom: 3px;
	margin-top: 3px;
}

ul li {
	display: inline-block;
	width: 24%;
}

ul li label {
	width: 30%;
	display: inline-block;
	text-align: right;
}

ul li input {
	width: 13em;
}
ul li select {
	width: 13em;
}
</style>
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
					emptyMsg : '无记录',
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
		style="height: 195px;">
		<div id="toolbar">
			<form id="searchForm">
				<ul>
					<li><label>开始时间:</label> <input id="startTime"
						name="startTime" /></li>
					<li><label>结束时间:</label> <input id="endTime" name="endTime" />
					</li>
					<li><label></label> <a href="javascript:void(0);"
						class="easyui-linkbutton"
						data-options="iconCls:'ext-icon-table_go',plain:true"
						onclick="exportData();">导出</a></li>
					<li><label></label> <a href="javascript:void(0);"
						class="easyui-linkbutton"
						data-options="iconCls:'ext-icon-zoom',plain:true"
						onclick="grid.datagrid('load',cxw.serializeObject($('#searchForm')));">查询</a>
					</li>
				</ul>
				<ul>
					<li><label>医生诊断:</label> <input id="doctorDiagnosis"
						name="doctorDiagnosis" /></li>
					<li><label>疾病科別:</label> <input id="illDepartment"
						name="illDepartment" /></li>
					<li><label>疾病分类:</label> <input id="illClass" name="illClass" /></li>
					<li><label>患者姓名:</label> <input id="patientName"
						name="patientName" /></li>
				</ul>
				<ul>
					<li><label>病情:</label> <input id="illState" name="illState" /></li>
					<li><label>救治结果:</label> <input id="aidResult"
						name="aidResult" /></li>
					<li><label>死亡证明:</label> <input id="deathProof"
						name="deathProof" /></li>
					<li><label>送往地点类型:</label> <input id="sendAddrType"
						name="sendAddrType" /></li>
				</ul>
				<ul>
					<li><label>现场地点类型:</label> <input id="spotAddrType"
						name="spotAddrType" /></li>
					<li><label>疾病原因:</label> <input id="illReason"
						name="illReason" /></li>
					<li><label>车辆标识:</label> <input id="carPlate" name="carPlate" /></li>
					<li><label>转归结果:</label> <input id="outCome" name="outCome" /></li>
				</ul>
				<ul>
					<li><label>病家合作:</label> <input id="patientCooperation"
						name="patientCooperation" /></li>
					<li><label>性别:</label><select id="sex" class="easyui-combobox"
						name="sex">
							<option value="">--请选择--</option>
							<option value="男">男</option>
							<option value="女">女</option>
					</select></li>
					<li><label>身份:</label> <input id="identity" name="identity" /></li>
					<li><label>职业:</label> <input id="profession"
						name="profession" /></li>
				</ul>
				<ul>
					<li><label>司机:</label> <input id="driver" name="driver" /></li>
					<li><label>医生:</label> <input id="doctor" name="doctor" /></li>
					<li><label>护士:</label> <input id="nurse" name="nurse" /></li>
				</ul>
			</form>
		</div>
	</div>

	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>

</body>
</html>