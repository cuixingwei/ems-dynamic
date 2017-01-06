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
		var url = "exportHistoryEventDatas?startTime="
				+ $('#startTime').datetimebox('getValue') + "&endTime="
				+ $('#endTime').datetimebox('getValue') + "&overtimes="
				+ $('#eventName').val() + "&dispatcher="
				+ $('#alarmPhone').val();
		window.location.href = url;
	};
	var showFun = function(eventCode) {
		var dialog = parent.cxw.modalDialog({
			title : '事件详细信息',
			url : 'base/HistoryEventForm.jsp?eventCode=' + eventCode
		});
	};
	/* 初始化页面标签 */
	function init() {
		$('#startTime').datetimebox({
			required : true,
			value : firstOfMouth(),
		});
		$('#endTime').datetimebox({
			required : true,
			value : getCurrentTime()
		})
		grid = $('#grid')
				.datagrid(
						{
							url : 'getHistoryEventDatas',
							pagePosition : 'bottom',
							pagination : true,
							striped : true,
							singleSelect : true,
							rownumbers : true,
							idField : 'id',
							emptyMsg : '无记录',
							pageSize : 20,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
									400, 500 ],
							columns : [ [
									{
										field : 'thisDispatcher',
										title : '调度员',
										resizable : true,
										width : "15%",
										align : 'center'
									},
									{
										field : 'acceptStartTime',
										title : '受理时刻',
										width : "15%",
										align : 'center'
									},
									{
										field : 'acceptCount',
										title : '受理次数',
										width : "8%",
										align : 'center'
									},
									{
										field : 'eventType',
										title : '事件类型',
										width : "7%",
										align : 'center'
									},
									{
										field : 'eventResult',
										title : '事件结果',
										width : "7%",
										align : 'center'
									},
									{
										field : 'callPhone',
										title : '呼救电话',
										width : "10%",
										align : 'center'
									},
									{
										field : 'eventName',
										title : '事件名称',
										width : "15%",
										align : 'center'
									},
									{
										field : 'eventSource',
										title : '联动来源',
										resizable : true,
										width : "14%",
										align : 'center'
									},
									{
										field : 'id',
										title : '详情',
										width : "10%",
										resizable : true,
										align : 'center',
										formatter : function(value, row) {
											var str = '';
											str += cxw.formatString('<img class="iconImg ext-icon-note" title="查看" onclick="showFun(\'{0}\');"/>', row.eventCode)
											return str;
										}
									} ] ],
							toolbar : '#toolbar',
							onBeforeLoad : function(param) {
								var varify = cxw.checkStartTimeBeforeEndTime(
										'#startTime', '#endTime');
								if (!varify) {
									$.messager.alert('警告', '结束时间要大于开始时间',
											'warning');
								}
							},
				            onLoadSuccess: function (row, data) {
				                $('.iconImg').attr('src', cxw.pixel_0);
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
								<td>呼救电话:</td>
								<td><input style="width: 100px" type="text"
									name="alarmPhone" /></td>
								<td>事件名称:</td>
								<td><input type="text" style="width: 100px" name="eventName" /></td>
								<td>事件编码:</td>
								<td><input type="text" style="width: 100px" name="eventCode" /></td>
								<td>查询时间</td>
								<td><input id="startTime" name="startTime"
									style="width: 150em;" />至<input id="endTime" name="endTime"
									style="width: 150em;" /></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton"
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