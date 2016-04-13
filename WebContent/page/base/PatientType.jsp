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
		var url = "exportPatientTypeData?startTime="
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
					url : 'getPatientTypeData',
					pagePosition : 'bottom',
					pagination : true,
					striped : true,
					singleSelect : true,
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [ {
						field : 'station',
						title : '分站',
						resizable : true,
						width : "6%",
						align : 'center'
					}, {
						field : 'type1',
						title : '交通事<br>故外伤',
						resizable : true,
						width : "5%",
						align : 'center',
					}, {
						field : 'type2',
						title : '其他类<br>外伤',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'type3',
						title : '烧伤',
						resizable : true,
						width : "4%",
						align : 'center',
					}, {
						field : 'type4',
						title : '电击伤<br>溺水',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'type5',
						title : '其他外<br>科疾病',
						resizable : true,
						width : "5%",
						align : 'center',
					}, {
						field : 'type6',
						title : '心血管系<br>统疾病',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'type7',
						title : '脑血管系<br>统疾病',
						resizable : true,
						width : "5%",
						align : 'center',
					}, {
						field : 'type8',
						title : '呼吸道系<br>统疾病',
						resizable : true,
						width : "5%",
						align : 'center'
					}, {
						field : 'type9',
						title : '食物<br>中毒',
						resizable : true,
						width : "4%",
						align : 'center',
					}, {
						field : 'type10',
						title : '药物<br>中毒',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'type11',
						title : '酒精<br>中毒',
						resizable : true,
						width : "4%",
						align : 'center',
					}, {
						field : 'type12',
						title : 'CO中毒',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'type13',
						title : '其他内<br>科疾病',
						resizable : true,
						width : "4%",
						align : 'center',
					}, {
						field : 'type14',
						title : '妇科<br>产科',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'type15',
						title : '儿科',
						resizable : true,
						width : "4%",
						align : 'center',
					}, {
						field : 'type16',
						title : '气管<br>异物',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'type17',
						title : '其他五<br>官科',
						resizable : true,
						width : "4%",
						align : 'center',
					}, {
						field : 'type18',
						title : '传染病',
						resizable : true,
						width : "4%",
						align : 'center'
					}, {
						field : 'type19',
						title : '抢救前<br>死亡',
						resizable : true,
						width : "4%",
						align : 'center',
					}, {
						field : 'type20',
						title : '抢救后<br>死亡',
						resizable : true,
						width : "4%",
						align : 'center'
					} , {
						field : 'type21',
						title : '其他',
						resizable : true,
						width : "4%",
						align : 'center',
					}, {
						field : 'total',
						title : '合计',
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
					},
					onLoadSuccess : function(data) {
						parent.$.messager.progress('close');
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