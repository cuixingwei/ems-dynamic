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
		});

		grid = $('#grid').datagrid({
			url : 'getSubstationVisitDatas',
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
				title : '分站名称',
				resizable : true,
				width : "8%",
				align : 'center'
			}, {
				field : 'sendNumbers',
				title : '120派诊',
				resizable : true,
				width : "8%",
				align : 'center',
			}, {
				field : 'nomalNumbers',
				title : '正常完成',
				resizable : true,
				width : "8%",
				align : 'center'
			}, {
				field : 'nomalRate',
				title : '正常完成比率',
				resizable : true,
				width : "9%",
				align : 'center'
			}, {
				field : 'stopNumbers',
				title : '中止任务',
				resizable : true,
				width : "8%",
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
				width : "8%",
				align : 'center'
			}, {
				field : 'emptyRate',
				title : '空车比率',
				resizable : true,
				width : "8%",
				align : 'center',
			}, {
				field : 'refuseNumbers',
				title : '拒绝出车',
				resizable : true,
				width : "8%",
				align : 'center'
			}, {
				field : 'refuseRate',
				title : '拒绝出车比率',
				resizable : true,
				width : "9%",
				align : 'center'
			}, {
				field : 'pauseNumbers',
				title : '暂停调用',
				resizable : true,
				width : "8%",
				align : 'center',
			}, {
				field : 'treatNumbers',
				title : '救治人数',
				resizable : true,
				width : "8%",
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
								<td>查询时间:</td>
								<td><input id="startTime" name="startTime" class="Wdate"
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