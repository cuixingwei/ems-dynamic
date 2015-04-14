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
			method : 'get',
			onSelect : function(rec) {
				var url = 'getCars?id=' + rec.stationCode;
				$('#carCode').combobox('reload', url);
			}
		});
		$('#emptyCarReason').combobox({
			url : 'getEmptyCarReasons',
			valueField : 'code',
			textField : 'name',
			method : 'get'
		});
		$('#dispatcher').combobox({
			url : 'getUsers',
			valueField : 'employeeId',
			textField : 'name',
			method : 'get'
		});
		grid = $('#grid').datagrid({
			url : 'getEmptyCarData',
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
				width : "20%",
				align : 'center'
			}, {
				field : 'sickAddress',
				title : '患者地址',
				resizable : true,
				width : "20%",
				align : 'center',
			}, {
				field : 'dispatcher',
				title : '调度员',
				resizable : true,
				width : "20%",
				align : 'center'
			}, {
				field : 'emptyRunTimes',
				title : '空跑时间',
				resizable : true,
				width : "19%",
				align : 'center'
			}, {
				field : 'emptyReason',
				title : '空跑原因',
				resizable : true,
				width : "19%",
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
								<td>&nbsp;空车原因:</td>
								<td><input style="width: 120em;" id="emptyCarReason"
									name="emptyCarReason" /></td>
								<td>&nbsp;调度员:</td>
								<td><input style="width: 120em;" id="dispatcher"
									name="dispatcher" /></td>
								<td>&nbsp;查询时间:</td>
								<td colspan="3"><input id="startTime" name="startTime"
									class="Wdate"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									style="width: 150em;" />-<input id="endTime" name="endTime"
									class="Wdate"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									style="width: 150em;" /></td>
								<td colspan="2">&nbsp;<a href="javascript:void(0);"
									class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom',plain:true"
									onclick="grid.datagrid('load',cxw.serializeObject($('#searchForm')));">查询</a></td>
								<td colspan="2">&nbsp;<a href="javascript:void(0);"
									class="easyui-linkbutton"
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