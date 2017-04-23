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
			url : 'getSubstationVisitDatas',
			queryParams : cxw.serializeObject($('#searchForm'))
		});
	}
	var exportData = function() {
		var url = "exportSubstationVisitDatas?startTime="
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
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					nowrap : false,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'station',
						title : '分站名称',
						resizable : true,
						width : "10%",
						align : 'center',
						nowrap : false
					}, {
						field : 'sendNumbers',
						title : '120派诊',
						resizable : true,
						width : "9%",
						align : 'center',
					}, {
						field : 'nomalNumbers',
						title : '正常完成',
						resizable : true,
						width : "9%",
						align : 'center'
					}, {
						field : 'nomalRate',
						title : '正常完成比率',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'stopNumbers',
						title : '中止任务',
						resizable : true,
						width : "9%",
						align : 'center',
					}, {
						field : 'stopRate',
						title : '中止任务比率',
						resizable : true,
						width : "9%",
						align : 'center'
					}, {
						field : 'emptyNumbers',
						title : '空车',
						resizable : true,
						width : "9%",
						align : 'center'
					}, {
						field : 'emptyRate',
						title : '空车比率',
						resizable : true,
						width : "9%",
						align : 'center',
					}, {
						field : 'refuseNumbers',
						title : '拒绝出车',
						resizable : true,
						width : "9%",
						align : 'center'
					}, {
						field : 'refuseRate',
						title : '拒绝出车比率',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'treatNumbers',
						title : '救治人数',
						resizable : true,
						width : "8%",
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