<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<%
	String version = "20150311";
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var grid;
	var parameter = $('#searchForm').serializeArray();
	function search() {
		grid.datagrid('load', {
			overtimes : $('#overtimes').val(),
			startTime : $('#startTime').val(),
			endTime : $('#endTime').val(),
			dispatcher : $('#dispatcher').val()
		});
	}
	/* 初始化页面标签 */
	function init() {
		$('#overtimes').numberbox({
			min : 0,
			value : 15
		});
		$('#startTime').datetimebox({
			required : true,
			value : firstOfMouth()
		});
		$('#endTime').datetimebox({
			value : getCurrentTime()
		})
		$('#dispatcher').combobox({
			url : 'getUsers',
			valueField : 'employeeId',
			textField : 'name',
			method : 'get'
		});
		grid = $('#grid').datagrid({
			url : 'getRingToAcceptDatas',
			pagePosition : 'bottom',
			loadMsg : '加载中，请稍后',
			pagination : true,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'ringTime',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				field : 'dispatcher',
				title : '调度员',
				width : "15%",
				align : 'center'
			}, {
				field : 'ringTime',
				title : '电话振铃时刻',
				width : "15%",
				align : 'center',
				sortable : true
			}, {
				field : 'callTime',
				title : '通话时刻',
				width : "15%",
				align : 'center'
			}, {
				field : 'ringDuration',
				title : '响铃时长(秒)',
				width : "20%",
				align : 'center'
			}, {
				field : 'acceptCode',
				title : '受理台号',
				width : "15%",
				align : 'center'
			}, {
				field : 'acceptRemark',
				title : '受理备注',
				width : "20%",
				align : 'center'
			} ] ],
			toolbar : '#toolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', cxw.pixel_0);
				parent.$.messager.progress('close');
			}
		});
	}

	$(document).ready(function() {
		init();
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
								<td>超时时长:</td>
								<td><input style="width: 80px;" id="overtimes"
									name="overtimes" /></td>
								<td>调度员:</td>
								<td><input style="width: 80px;" id="dispatcher"
									name="dispatcher" /></td>
								<td>查询时间</td>
								<td><input id="startTime" name="startTime" class="Wdate"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									readonly="readonly" style="width: 180px;" />-<input
									id="endTime" name="endTime" class="Wdate"
									onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									readonly="readonly" style="width: 180px;" /></td>
								<td><a href="javascript:void(0);" class="easyui-linkbutton"
									data-options="iconCls:'ext-icon-zoom',plain:true"
									onclick="search()">查询</a><a href="javascript:void(0);"
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
					data-options="iconCls:'ext-icon-table_go',plain:true" onclick="">导出</a></td>
			</tr>
		</table>
	</div>

	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"></table>
	</div>


</body>
</html>