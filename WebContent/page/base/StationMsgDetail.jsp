<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var grid;
	var exportData = function() {
		var url = "exportStationMsgDetail?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&overtimes="
				+ $('#overtimes').numberbox('getValue') + "&station="
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
		});
		$('#station').combobox({
			url : 'getStations',
			valueField : 'stationCode',
			textField : 'stationName',
			method : 'get'
		});
		grid = $('#grid').datagrid(
				{
					url : 'getStationMsgDetail',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					nowrap : false,
					rownumbers : true,
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'eventName',
						title : '事件名称',
						width : "16%",
						align : 'center'
					}, {
						field : 'dispatcher',
						title : '中心调度员',
						width : "10%",
						align : 'center',
					}, {
						field : 'station',
						title : '分站',
						width : "10%",
						align : 'center',
					}, {
						field : 'stationDispatcher',
						title : '分站调度员',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'createTaskTime',
						title : '生成任务时刻',
						resizable : true,
						width : "16%",
						align : 'center'
					}, {
						field : 'acceptTaskTime',
						title : '分站接受任务时刻',
						resizable : true,
						width : "16%",
						align : 'center'
					}, {
						field : 'times',
						title : '回单时间(秒)',
						resizable : true,
						width : "16%",
						align : 'center',
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
								<td>分站:</td>
								<td><input style="width: 120em;" id="station"
									name="station" /></td>
								<td>晚回单时间(秒):</td>
								<td><input type="text" style="width: 150px;" id="overtimes"
									name="overtimes" class="easyui-numberbox" value="30"
									data-options="min:0,precision:0"></td>
								<td>&nbsp;查询时间:</td>
								<td colspan="3"><input id="startTime" name="startTime"
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