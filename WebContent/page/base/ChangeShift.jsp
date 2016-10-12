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
<title></title>
<style type="text/css">
table {
	border-collapse: collapse;
}

table td {
	border: 1px solid #000000;
	text-align: center;
}

table th {
	border: 1px solid #000000;
	text-align: center;
}
</style>
<script type="text/javascript">
	function getDateStr(dstr, AddDayCount) {
		var dd;
		if (dstr == "") {
			dd = new Date();
		} else {
			dd = new Date(dstr);
		}
		dd.setDate(dd.getDate() + AddDayCount);// 获取AddDayCount天后的日期
		var y = dd.getFullYear();
		var m = dd.getMonth() + 1;// 获取当前月份的日期
		var d = dd.getDate();
		return y + "-" + m + "-" + d;
	}

	function getDateFormat(dstr, AddDayCount) {
		var dd;
		if (dstr == "") {
			dd = new Date();
		} else {
			dd = new Date(dstr);
		}
		dd.setDate(dd.getDate() + AddDayCount);// 获取AddDayCount天后的日期
		var y = dd.getFullYear();
		var m = dd.getMonth() + 1;// 获取当前月份的日期
		var d = dd.getDate();
		return y + "年" + m + "月" + d + "日";
	}

	var printData = function(oper) {
		if (oper < 10) {
			bdhtml = window.document.body.innerHTML;//获取当前页的html代码
			sprnstr = "<!--startprint" + oper + "-->";//设置打印开始区域
			eprnstr = "<!--endprint" + oper + "-->";//设置打印结束区域
			prnhtml = bdhtml.substring(bdhtml.indexOf(sprnstr) + 18); //从开始代码向后取html
			prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));//从结束代码向前取html
			prnhtml = '<style type="text/css"> table {  border-collapse: collapse;   }   table td {  border: 1px solid #000000;  text-align: center; font-size: 10px }      table th {     border: 1px solid #000000;   text-align: center;   }   </style>'
					+ prnhtml;
			// 生成并打印ifrme
			var syfPrint = window.frames[0];
			syfPrint.document.body.innerHTML = prnhtml;
			syfPrint.document.execCommand("Print");
		} else {
			window.print();
		}
	};

	/* 初始化页面标签 */
	function init() {
		$("#endTime").val(getDateStr("", 1));
		$('#startTime').datebox({
			required : true,
			editable : false,
			value : getDateStr("", 0),
			onSelect : function(date) {
				$("#endTime").html(getDateStr(date, 1));
			}
		});
		$("#dataTime").html(
				getDateFormat($('#startTime').datebox('getValue'), 0));
	}

	/*查询操作*/
	var query = function() {
		$.messager.progress({
			text : '数据加载中....'
		});
		$("#dataTime").html(
				getDateFormat($('#startTime').datebox('getValue'), 0));
		$.post("getChangeShift", {
			startTime : $('#startTime').datebox('getValue'),
			endTime : $("#endTime").val()
		}, function(result, status) {
			if (status == 'success') {
				$.messager.progress('close');
				var str1 = '', str2 = "", str3 = "";
				var day1 = result.day1;
				var day2 = result.day2;
				var day3 = result.day3;
				if (day1.length > 0) {
					$.each(day1, function(i, ob) {
						str1 += "<tr><td>" + ob.hospital + "</td><td>"
								+ ob.inHosCounts + "</td><td>" + ob.spotCure
								+ "</td><td>" + ob.emptyTask + "</td><td>"
								+ ob.robCure + "</td><td>" + ob.byRobCure
								+ "</td><td>" + ob.transferCure + "</td><td>"
								+ ob.specialTask + "</td><td>" + ob.emptyTask
								+ "</td><td>" + ob.refuseTransferCount
								+ "</td><td>" + ob.refuseNoOwnerCount
								+ "</td><td>" + ob.totalSendCar + "</td></tr>";
					});
				}
				if (day2.length > 0) {
					$.each(day2, function(i, ob) {
						str2 += "<tr><td>" + ob.hospital + "</td><td>"
								+ ob.inHosCounts + "</td><td>" + ob.spotCure
								+ "</td><td>" + ob.emptyTask + "</td><td>"
								+ ob.robCure + "</td><td>" + ob.byRobCure
								+ "</td><td>" + ob.transferCure + "</td><td>"
								+ ob.specialTask + "</td><td>" + ob.emptyTask
								+ "</td><td>" + ob.refuseTransferCount
								+ "</td><td>" + ob.refuseNoOwnerCount
								+ "</td><td>" + ob.totalSendCar + "</td></tr>";
					});
				}
				if (day3.length > 0) {
					$.each(day3, function(i, ob) {
						str3 += "<tr><td>" + ob.hospital + "</td><td>"
								+ ob.inHosCounts + "</td><td>" + ob.spotCure
								+ "</td><td>" + ob.emptyTask + "</td><td>"
								+ ob.robCure + "</td><td>" + ob.byRobCure
								+ "</td><td>" + ob.transferCure + "</td><td>"
								+ ob.specialTask + "</td><td>" + ob.emptyTask
								+ "</td><td>" + ob.refuseTransferCount
								+ "</td><td>" + ob.refuseNoOwnerCount
								+ "</td><td>" + ob.totalSendCar + "</td></tr>";
					});
				}
				console.log(result);
				$("#table1 tbody").empty().append(str1);
				$("#table2 tbody").empty().append(str2);
				$("#table3 tbody").empty().append(str3);
			} else {
				$.messager.progress('close');
				$.messager.alert('提示', '查询失败!', 'info');
			}
		});
	};
	$(document).ready(function() {
		init();
		query();
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<iframe src="" width="0" height="0" frameborder="0"></iframe>
	<div data-options="region:'north',title:'查询条件',split:true"
		style="height: 80px;">
		<form id="searchForm">
			<div style="padding: 10px;">
				<div style="width: 24%; float: left; display: inline-block;">
					<label
						style="width: 70px; text-align: right; display: inline-block; margin-right: 5px;">开始时刻:</label>
					<input id="startTime" name="startTime" style="width: 150px;" /> <input
						id="endTime" name="endTime" type="hidden" />
				</div>
				<div style="width: 24%; display: inline-block;">
					<a href="javascript:void(0);" class="easyui-linkbutton"
						style="width: 100px; text-align: right; display: inline-block; margin-right: 5px;"
						data-options="iconCls:'ext-icon-zoom',plain:true"
						onclick="query();">查询</a>
				</div>
				<div style="width: 24%; display: inline-block; float: right;">
					<a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'ext-icon-printer',plain:true"
						onclick="printData(1);">打印</a>
				</div>
			</div>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<!--startprint1-->
		<table id="table1" width="99%" cellpadding="0" cellspacing="0"
			border="0">
			<caption style="font-size: 20px;">
				<b>交接班记录登记本</b><br> <span id="dataTime"></span>
			</caption>
			<thead>
				<tr>
					<td colspan="4"
						style="border: none; font-size: 10px; text-align: left"><span>时间:</span>
						<span>08:00-12:00</span></td>
					<td colspan="4" style="border: none; font-size: 10px;"><span>交班人员:</span>
						<span id=""></span></td>
					<td colspan="4" align="left" style="border: none; font-size: 10px;"><span>接班人员:</span>
						<span id=""></span></td>
				</tr>
				<tr>
					<th>单位</th>
					<th>收入院</th>
					<th>现场救治</th>
					<th>空返</th>
					<th>抢诊数</th>
					<th>被抢诊数</th>
					<th>转诊数</th>
					<th>特殊事件</th>
					<th>总无车数</th>
					<th>拒绝转送病人数</th>
					<th>拒接无主病人数</th>
					<th>总派车数</th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
		<table id="table2" width="99%" cellpadding="0" cellspacing="0"
			border="0">
			<thead>
				<tr>
					<td colspan="4"
						style="border: none; font-size: 10px; text-align: left"><span>时间:</span>
						<span>12:00-20:00</span></td>
					<td colspan="4" style="border: none; font-size: 10px;"><span>交班人员:</span>
						<span id=""></span></td>
					<td colspan="4" align="left" style="border: none; font-size: 10px;"><span>接班人员:</span>
						<span id=""></span></td>
				</tr>
				<tr>
					<th>单位</th>
					<th>收入院</th>
					<th>现场救治</th>
					<th>空返</th>
					<th>抢诊数</th>
					<th>被抢诊数</th>
					<th>转诊数</th>
					<th>特殊事件</th>
					<th>总无车数</th>
					<th>拒绝转送病人数</th>
					<th>拒接无主病人数</th>
					<th>总派车数</th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>

		<table id="table3" width="99%" cellpadding="0" cellspacing="0"
			border="0">
			<thead>
				<tr>
					<td colspan="4"
						style="border: none; font-size: 10px; text-align: left"><span>时间:</span>
						<span>20:00-08:00</span></td>
					<td colspan="4" style="border: none; font-size: 10px;"><span>交班人员:</span>
						<span id=""></span></td>
					<td colspan="4" align="left" style="border: none; font-size: 10px;"><span>接班人员:</span>
						<span id=""></span></td>
				</tr>
				<tr>
					<th>单位</th>
					<th>收入院</th>
					<th>现场救治</th>
					<th>空返</th>
					<th>抢诊数</th>
					<th>被抢诊数</th>
					<th>转诊数</th>
					<th>特殊事件</th>
					<th>总无车数</th>
					<th>拒绝转送病人数</th>
					<th>拒接无主病人数</th>
					<th>总派车数</th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
		<!--endprint1-->
	</div>
</body>
</html>