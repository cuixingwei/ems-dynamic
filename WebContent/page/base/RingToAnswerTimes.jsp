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
	/* 初始化页面标签 */
	function init() {
		$('#queryCondition').panel({
			title : '查询条件',
			iconCls : 'icon-edit',
			collapsible : true,
			collapsed : false
		});
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
		$('#search').linkbutton({
			iconCls : 'icon-search'
		})
		$('#content').panel({
			title : '查询结果',
			iconCls : 'icon-reload',
			collapsible : true,
			collapsed : false
		});
		$('#dispatcher').combobox({
			url : 'getUsers',
			valueField : 'employeeId',
			textField : 'name',
			method : 'get'
		});
		$('#dg').datagrid({
			url : '',
			columns : [ [ {
				field : 'dispatcher',
				title : '调度员',
				width : 100,
				align : 'center'
			}, {
				field : 'ringTime',
				title : '电话振铃时刻',
				width : 120,
				align : 'center'
			}, {
				field : 'callTime',
				title : '通话时刻',
				width : 120,
				align : 'center'
			}, {
				field : 'ringDuration',
				title : '响铃时长(秒)',
				width : 100,
				align : 'center'
			}, {
				field : 'acceptCode',
				title : '受理台号',
				width : 100,
				align : 'center'
			}, {
				field : 'acceptRemark',
				title : '受理备注',
				width : 200,
				align : 'center'
			} ] ],
			method : 'post',
			pagePosition : 'bottom',
			loadMsg : '加载中，请稍后',
			pagination : true,
			rownumbers : true
		});
	}

	/* 表单提交 */
	function submitForm() {
		$('#query').form('submit', {
			url : 'getRingToAcceptDatas',
			onSubmit : function() {
				return $(this).form('enableValidation').form('validate');
			},
			success : function(data) {

			}
		});
	}

	$(document).ready(function() {
		init();
	});
</script>
</head>
<body>

	<div id="queryCondition" style="padding: 10px;">
		<form class="easyui-form" id="query" method="post">
			<table cellpadding="10">
				<tr>
					<td>超时时长:</td>
					<td><input type="text" width="200px" id="overtimes"
						name="overtimes" /></td>
					<td>调度员:</td>
					<td><input id="dispatcher" name="dispatcher" /></td>
					<td>开始时间:</td>
					<td><input id="startTime" type="text" name="startTime" /></td>
					<td>结束时间:</td>
					<td><input id="endTime" type="text" name="endTime" /></td>
					<td><a href="javascript:void(0)" id="search"
						onclick="submitForm()">查询</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="content" style="padding: 10px;">
		<table id="dg"></table>
	</div>

</body>
</html>