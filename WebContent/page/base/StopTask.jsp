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
		$('#stopReason').combobox({
			url : 'getStopReasons',
			valueField : 'code',
			textField : 'name',
			method : 'get'
		});
		$('#carCode').combobox({
			url : 'getCars',
			valueField : 'carCode',
			textField : 'carIdentification',
			method : 'get'
		});
		$('#dispatcher').combobox({
			url : 'getUsers',
			valueField : 'employeeId',
			textField : 'name',
			method : 'get'
		});
		grid = $('#grid').datagrid({
			url : 'getStopTaskDatas',
			pagePosition : 'bottom',
			pagination : true,
			striped : true,
			singleSelect : true,
			rownumbers : true,
			idField : 'id',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				field : 'acceptTime',
				title : '受理时间',
				resizable : true,
				width : "10%",
				align : 'center'
			}, {
				field : 'sickAddress',
				title : '患者地址',
				resizable : true,
				width : "10%",
				align : 'center',
			}, {
				field : 'phone',
				title : '呼救电话',
				resizable : true,
				width : "10%",
				align : 'center'
			}, {
				field : 'dispatcher',
				title : '调度员',
				resizable : true,
				width : "10%",
				align : 'center'
			}, {
				field : 'carCode',
				title : '车辆编码',
				resizable : true,
				width : "10%",
				align : 'center'
			}, {
				field : 'drivingTime',
				title : '出车时间',
				resizable : true,
				width : "10%",
				align : 'center'
			}, {
				field : 'emptyRunTime',
				title : '空跑时长	',
				resizable : true,
				width : "10%",
				align : 'center',
			}, {
				field : 'staion',
				title : '分站',
				resizable : true,
				width : "10%",
				align : 'center'
			}, {
				field : 'stopReason',
				title : '中止原因',
				resizable : true,
				width : "10%",
				align : 'center'
			}, {
				field : 'remark',
				title : '备注说明',
				resizable : true,
				width : "10%",
				align : 'center'
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
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
								<td>分站:</td>
								<td><input style="width: 120em;" id="station"
									name="station" /></td>
								<td>中止原因:</td>
								<td><input style="width: 120em;" id="stopReason"
									name="stopReason" /></td>
								<td>车辆:</td>
								<td><input style="width: 120em;" id="carCode"
									name="carCode" /></td>
								<td>调度员:</td>
								<td><input style="width: 120em;" id="dispatcher"
									name="dispatcher" /></td>
								<td>空跑时间:</td>
								<td><input type="text" id="emptyRunTime"
									style="width: 120em" name="emptyRunTime"
									class="easyui-numberbox" data-options="min:0,precision:0" /></td>
								<td>查询时间</td>
								<td><input id="startTime" name="startTime" class="Wdate"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									style="width: 180px;" />-<input id="endTime" name="endTime"
									class="Wdate"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									style="width: 180px;" /></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom',plain:true"
									onclick="grid.datagrid('load',cxw.serializeObject($('#searchForm')));">查询</a><a
									href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom_out',plain:true"
									onclick="$('#searchForm input').val('');grid.datagrid('load',{});">重置查询</a></td>
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