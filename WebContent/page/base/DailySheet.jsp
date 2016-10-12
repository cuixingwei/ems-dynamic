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
		var url = "exportDailySheet?startTime="
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
					url : 'getDailySheet',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'hospital',
						title : '医院',
						resizable : true,
						width : "10%",
						align : 'center'
					}, {
						field : 'inHosCounts',
						title : '收入院（人）',
						resizable : true,
						width : "9%",
						align : 'center',
					}, {
						field : 'spotCure',
						title : '现场救治',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'emptyTask',
						title : '空返',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'robCure',
						title : '抢诊数',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'byRobCure',
						title : '被抢诊数',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'transferCure',
						title : '转诊数（次）',
						resizable : true,
						width : "9%",
						align : 'center'
					}, {
						field : 'specialTask',
						title : '特殊事件',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'hungOn',
						title : '无车数',
						resizable : true,
						width : "7%",
						align : 'center'
					}, {
						field : 'refuseTransferCount',
						title : '拒绝转送病人数',
						resizable : true,
						width : "11%",
						align : 'center'
					}, {
						field : 'refuseNoOwnerCount',
						title : '拒接无主病人数',
						resizable : true,
						width : "11%",
						align : 'center'
					}, {
						field : 'totalSendCar',
						title : '总派车数',
						resizable : true,
						width : "7%",
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