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
		var url = "exportDriverWorkDatas?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&station="
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
			method : 'get'
		});
		grid = $('#grid').datagrid(
				{
					url : 'getDriverWorkDatas',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					emptyMsg : '无记录',
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'station',
						title : '分站',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'driver',
						title : '司机',
						resizable : true,
						width : "10%",
						align : 'center',
					}, {
						field : 'outCarNumbers',
						title : '出车次数',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'nomalNumbers',
						title : '有效出车数',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'stopNumbers',
						title : '中止数',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'emptyNumbers',
						title : ' 空车数',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'refuseNumbers',
						title : '拒绝出车',
						resizable : true,
						width : "9%",
						align : 'center',
					}, {
						field : 'pauseNumbers',
						title : '暂停调用数',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'averageOutCarTimes',
						title : '平均出车时间',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'averageArriveSpotTimes',
						title : '平均到达时间',
						resizable : true,
						width : "10%",
						align : 'center',
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
								<td>查询时间:</td>
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