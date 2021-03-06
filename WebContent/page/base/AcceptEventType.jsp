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
		var url = "exportAcceptEventTypeDatas?startTime="
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
		})
		grid = $('#grid').datagrid(
				{
					url : 'getAcceptEventTypeDatas',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'dispatcher',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'dispatcher',
						title : '调度员',
						width : "5%",
						rowspan : 2,
						align : 'center'
					}, {
						field : 'numbersOfPhone',
						title : '接电话数',
						width : "5%",
						rowspan : 2,
						align : 'center',
					}, {
						field : 'numbersOfSendCar',
						title : '派车数',
						width : "5%",
						rowspan : 2,
						align : 'center'
					}, {
						title : '受理类型',
						colspan : 10
					}, {
						title : '出车结果',
						colspan : 6
					} ], [ {
						field : 'numbersOfNormalSendCar',
						title : '正常派车',
						width : "5%",
						align : 'center'
					}, {
						field : 'numbersOfNormalHangUp',
						title : '正常挂起',
						width : "5%",
						align : 'center'
					}, {
						field : 'numbersOfReinforceSendCar',
						title : '增援派车',
						width : "5%",
						align : 'center'
					}, {
						field : 'numbersOfReinforceHangUp',
						title : '增援挂起',
						width : "5%",
						align : 'center'
					}, {
						field : 'numbersOfStopTask',
						title : '中止任务',
						width : "5%",
						align : 'center'
					}, {
						field : 'specialEvent',
						title : '特殊事件',
						width : "5%",
						align : 'center'
					}, {
						field : 'noCar',
						title : '欲派无车',
						width : "5%",
						align : 'center'
					}, {
						field : 'transmitCenter',
						title : '转分中心',
						width : "5%",
						align : 'center'
					}, {
						field : 'refuseSendCar',
						title : '拒绝出车',
						width : "5%",
						align : 'center'
					}, {
						field : 'wakeSendCar',
						title : '唤醒待派',
						width : "5%",
						align : 'center'
					}, {
						field : 'stopTask',
						title : '中止任务',
						width : "5%",
						align : 'center'
					}, {
						field : 'ratioStopTask',
						title : '中止任务比率',
						width : "7%",
						align : 'center'
					}, {
						field : 'emptyCar',
						title : '放空车',
						width : "5%",
						align : 'center'
					}, {
						field : 'ratioEmptyCar',
						title : '空车比率',
						width : "5%",
						align : 'center'
					}, {
						field : 'nomalComplete',
						title : '正常完成',
						width : "4.9%",
						align : 'center'
					}, {
						field : 'ratioComplete',
						title : '正常完成比率',
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