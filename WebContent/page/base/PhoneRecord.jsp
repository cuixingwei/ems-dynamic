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
	/* 导出录音 */
	var exportFun = function(recordPath, alarmTime, phone) {
		var fileName = alarmTime + '__' + phone;
		window.location.href = "exportRecordFile?recordPath=" + recordPath
				+ "&fileName=" + fileName;
	}
	/* 初始化页面标签 */
	function init() {
		$('#startTime').datetimebox({
			required : true,
			value : firstOfMouth()
		});
		$('#endTime').datetimebox({
			value : getCurrentTime()
		});
		$('#dispatcher').combobox({
			url : 'getUsers',
			valueField : 'employeeId',
			textField : 'name',
			method : 'get'
		});
		grid = $('#grid')
				.datagrid(
						{
							url : 'getPhoneRecord',
							pagePosition : 'bottom',
							pagination : true,
							striped : true,
							singleSelect : true,
							rownumbers : true,
							nowrap : false,
							idField : 'id',
							emptyMsg : '无记录',
							pageSize : 20,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
									400, 500 ],
							columns : [ [
									{
										field : 'answerAlarmTime',
										title : '振铃时刻',
										width : "14%",
										align : 'center'
									},
									{
										field : 'alarmPhone',
										title : '电话号码',
										width : "7%",
										align : 'center'
									},
									{
										field : 'dispatcher',
										title : '调度员',
										width : "11%",
										align : 'center'
									},
									{
										field : 'result',
										title : '通话结果',
										width : "11%",
										align : 'center'
									},
									{
										field : 'recordType',
										title : '通话类型',
										width : "10%",
										align : 'center'
									},
									{
										field : 'recordPath',
										title : '录音',
										width : "30%",
										resizable : true,
										align : 'center',
										formatter : function(value, row, index) {
											if (value) {
												return '<audio src='+ value+ ' controls="controls"></audio>';
											}
										}
									},
									{
										field : 'record',
										title : '导出录音',
										width : "10%",
										align : 'center',
										formatter : function(value, row, index) {
											if (row.recordPath) {
												return cxw
														.formatString(
																'<button onclick="exportFun(\'{0}\',\'{1}\',\'{2}\')">{3}</button>',
																row.recordPath,
																row.answerAlarmTime,
																row.alarmPhone,
																'导出录音');
											}
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
								<td>报警电话:</td>
								<td><input type="text" style="width: 80px" id="alarmPhone"
									name="alarmPhone" /></td>
								<td>调度员:</td>
								<td><input style="width: 100em" id="dispatcher"
									name="dispatcher" /></td>
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
		</table>
	</div>

	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>


</body>
</html>