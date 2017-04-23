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
			url : 'getDoctorNurseWorkData',
			queryParams : cxw.serializeObject($('#searchForm'))
		});
	}
	var exportData = function() {
		var url = "exportDoctorNurseWorkData?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&station="
				+ $('#station').combobox('getValue')+ "&doctorOrNurse="
				+ $('#doctorOrNurse').combobox('getValue');
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

		$('#station').combobox({
			url : 'getStations',
			valueField : 'stationCode',
			textField : 'stationName',
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
					emptyMsg : '无记录',
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'station',
						title : '分站',
						resizable : true,
						width : "15%",
						align : 'center'
					}, {
						field : 'name',
						title : '姓名',
						resizable : true,
						width : "10%",
						align : 'center',
					}, {
						field : 'outCarNumbers',
						title : '出车数',
						resizable : true,
						width : "12%",
						align : 'center'
					}, {
						field : 'validOutCarNumbers',
						title : '有效出车数',
						resizable : true,
						width : "14%",
						align : 'center'
					}, {
						field : 'stopNumbers',
						title : '中止数(中止、空车)',
						resizable : true,
						width : "15%",
						align : 'center'
					}, {
						field : 'refuseNumbers',
						title : '拒绝出车数',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'curePeopleNumbers',
						title : '救治人数',
						resizable : true,
						width : "10%",
						align : 'center',
					}, {
						field : 'averateCureTimes',
						title : '平均救治时间',
						resizable : true,
						width : "13%",
						align : 'center'
					} ] ],
					toolbar : '#toolbar',
					onBeforeLoad : function(param) {
						var varify = cxw.checkStartTimeBeforeEndTime(
								'#startTime', '#endTime');
						if (!varify)  {
							$.messager.alert('警告', '结束时间要大于开始时间', 'warning');
						}
					},
					onLoadSuccess : function(data) {
						$(this).datagrid("autoMergeCells", [ 'station' ]);
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
								<td>分站:</td>
								<td><input style="width: 120em;" id="station"
									name="station" /></td>
								<td>查询类别:</td>
								<td><select id="doctorOrNurse" class="easyui-combobox" data-options="editable:false,panelHeight:'auto'"
									name="doctorOrNurse" style="width: 120em;">
										<option value="1">医生</option>
										<option value="2">护士</option>
								</select></td>
								<td>查询时间:</td>
								<td><input id="startTime" name="startTime"
									style="width: 150em;" />至<input id="endTime" name="endTime"
									style="width: 150em;" /></td>
								<td colspan="2">&nbsp;<a href="javascript:void(0);"
									class="easyui-linkbutton"
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