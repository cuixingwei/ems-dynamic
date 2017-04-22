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
	/*查询*/
	var queryGrid = function() {
		grid.datagrid({
			url : 'getAnswerAlarmDatas',
			queryParams : cxw.serializeObject($('#searchForm'))
		});
	}
	var exportData = function() {
		var url = "exportAnswerAlarmDatas?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&alarmPhone="
				+ $('#alarmPhone').val() + "&dispatcher="
				+ $('#dispatcher').combobox('getValue') + "&siteAddress="
				+ $('#siteAddress').val() + "&station="
				+ $('#station').combobox('getValue');
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
		})
		$('#station').combobox({
			url : 'getStations',
			valueField : 'stationCode',
			textField : 'stationName',
			editable : false,
			method : 'get'
		});
		$('#dispatcher').combobox({
			url : 'getUsers',
			valueField : 'employeeId',
			textField : 'name',
			editable : false,
			method : 'get'
		});
		grid = $('#grid').datagrid(
				{
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					nowrap : false,
					idField : 'id',
					emptyMsg : '无记录',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'answerAlarmTime',
						title : '接诊时间',
						width : "14%",
						align : 'center'
					}, {
						field : 'alarmPhone',
						title : '报警电话',
						width : "10%",
						align : 'center',
						sortable : true
					}, {
						field : 'relatedPhone',
						title : '相关电话',
						width : "7%",
						align : 'center'
					}, {
						field : 'siteAddress',
						title : '报警地址',
						width : "11%",
						align : 'center'
					}, {
						field : 'judgementOnPhone',
						title : '电话判断',
						width : "11%",
						align : 'center'
					}, {
						field : 'station',
						title : '出车急救站',
						width : "11%",
						resizable : true,
						align : 'center'
					}, {
						field : 'sendCarTime',
						title : '派车时间',
						width : "14%",
						align : 'center'
					}, {
						field : 'dispatcher',
						title : '调度员',
						width : "10%",
						resizable : true,
						align : 'center'
					}, {
						field : 'patientName',
						title : '患者姓名',
						width : "10%",
						resizable : true,
						align : 'center'
					} ] ],
					toolbar : '#toolbar',
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
		queryGrid();
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
								<td>报警电话:</td>
								<td><input type="text" style="width: 120px" id="alarmPhone"
									name="alarmPhone" /></td>
								<td>调度员:</td>
								<td><input style="width: 120em" id="dispatcher"
									name="dispatcher" /></td>
								<td>报警地点:</td>
								<td><input type="text"
									style="width: 120px; border-radius: 6px;" id="siteAddress"
									name="siteAddress" /></td>
								<td>查询时间:</td>
								<td><input id="startTime" name="startTime"
									style="width: 150em;" />至<input id="endTime" name="endTime"
									style="width: 150em;" /></td>
							</tr>
							<tr>
								<td>分站:</td>
								<td><input style="width: 120em;" id="station"
									name="station" /></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom',plain:true"
									onclick="queryGrid();">查询</a></td>
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