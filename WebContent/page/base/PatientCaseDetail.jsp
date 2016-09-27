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
				+ $('#endTime').datetimebox('getValue');
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

		grid = $('#grid').datagrid(
				{
					url : 'getPatientCaseDetailDatas',
					pagePosition : 'bottom',
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
					},{
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
					},{
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
					},{
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
					toolbar : '#toolbar',
					onBeforeLoad : function(param) {
						var varify = cxw.checkStartTimeBeforeEndTime(
								'#startTime', '#endTime');
						if (!varify){
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
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchForm">
						<table>
							<tr>
								<td>查询时间:</td>
								<td><input id="startTime" name="startTime"
									style="width: 150em;" />至<input id="endTime" name="endTime"
									style="width: 150em;" /></td>
								<td colspan="2">&nbsp;<a href="javascript:void(0);"
									class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom',plain:true"
									onclick="grid.datagrid('load',cxw.serializeObject($('#searchForm')));">查询</a></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td><a href="javascript:void(0);" class="easyui-linkbutton"
					data-options="iconCls:'ext-icon-table_go',plain:true"
					onclick="exportData();">导出</a></td>
			</tr>
		</table>
	</div>

	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>

</body>
</html>