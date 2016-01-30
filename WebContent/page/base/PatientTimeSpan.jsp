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
		var url = "exportPatientTimeSpanDatas?startTime="
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
					url : 'getPatientTimeSpanDatas',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'patientType',
						title : '疾病类型',
						resizable : true,
						width : "8%",
						align : 'center'
					}, {
						field : 'summary',
						title : '合计',
						resizable : true,
						width : "3%",
						align : 'center',
					}, {
						field : 'span0_1',
						title : '0_1',
						resizable : true,
						width : "3.5%",
						align : 'center'
					}, {
						field : 'span1_2',
						title : '1_2',
						resizable : true,
						width : "3.5%",
						align : 'center'
					}, {
						field : 'span2_3',
						title : '2_3',
						resizable : true,
						width : "3.5%",
						align : 'center'
					}, {
						field : 'span3_4',
						title : '3_4',
						resizable : true,
						width : "3.5%",
						align : 'center'
					}, {
						field : 'span4_5',
						title : '4_5',
						resizable : true,
						width : "3.5%",
						align : 'center'
					}, {
						field : 'span5_6',
						title : '5_6',
						resizable : true,
						width : "3.5%",
						align : 'center'
					}, {
						field : 'span6_7',
						title : '6_7',
						resizable : true,
						width : "3.5%",
						align : 'center'
					}, {
						field : 'span7_8',
						title : '7_8',
						resizable : true,
						width : "3.5%",
						align : 'center'
					}, {
						field : 'span8_9',
						title : '8_9',
						resizable : true,
						width : "3.5%",
						align : 'center'
					}, {
						field : 'span9_10',
						title : '9_10',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span10_11',
						title : '10_11',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span11_12',
						title : '11_12',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span12_13',
						title : '12_13',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span13_14',
						title : '13_14',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span14_15',
						title : '14_15',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span15_16',
						title : '15_16',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span16_17',
						title : '16_17',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span17_18',
						title : '17_18',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span18_19',
						title : '18_19',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span19_20',
						title : '19_20',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span20_21',
						title : '20_21',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span21_22',
						title : '21_22',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span22_23',
						title : '22_23',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'span23_24',
						title : '23_24',
						resizable : true,
						width : "4%",
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