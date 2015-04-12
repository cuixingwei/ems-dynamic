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
		grid = $('#grid').datagrid({
			url : 'getCenterTaskDatas',
			pagePosition : 'bottom',
			pagination : true,
			striped : true,
			singleSelect : true,
			rownumbers : true,
			idField : 'ringTime',
			pageSize : 20,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				field : 'name',
				title : '姓名',
				width : "5%",
				rowspan : 2,
				align : 'center'
			}, {
				field : 'sickAddress',
				title : '患者地址',
				rowspan : 2,
				width : "5%",
				align : 'center',
			}, {
				field : 'sickDescription',
				title : '主诉',
				rowspan : 2,
				width : "5%",
				align : 'center'
			}, {
				field : 'phone',
				title : '呼救电话',
				rowspan : 2,
				width : "5%",
				align : 'center'
			}, {
				field : 'acceptTime',
				title : '受理时间',
				rowspan : 2,
				width : "9%",
				align : 'center'
			}, {
				field : 'sendCarTime',
				title : '派车时间',
				width : "9%",
				rowspan : 2,
				resizable : true,
				align : 'center'
			}, {
				field : 'drivingTime',
				title : '出车时间',
				rowspan : 2,
				width : "9%",
				align : 'center',
			}, {
				field : 'arrivalTime',
				title : '到达时间',
				rowspan : 2,
				width : "9%",
				align : 'center'
			}, {
				field : 'returnHospitalTime',
				title : '返院时间',
				rowspan : 2,
				width : "9%",
				align : 'center'
			}, {
				field : 'toAddress',
				title : '送住地点',
				width : "5%",
				rowspan : 2,
				align : 'center'
			}, {
				field : 'carCode',
				title : '车辆',
				width : "5%",
				rowspan : 2,
				resizable : true,
				align : 'center'
			}, {
				title : '出诊人员',
				colspan : 3
			}, {
				field : 'dispatcher',
				title : '调度员',
				rowspan : 2,
				width : "5%",
				resizable : true,
				align : 'center'
			}, {
				field : 'taskResult',
				title : '出车结果',
				rowspan : 2,
				align : 'center',
				width : '5%'
			} ], [ {
				field : 'doctor',
				title : '医生',
				width : "5%",
				align : 'center'
			}, {
				field : 'nurse',
				title : '护士',
				width : "5%",
				align : 'center'
			}, {
				field : 'driver',
				title : '司机',
				width : "4.9%",
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
								<td><input style="width: 120px;" id="station"
									name="station" /></td>
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