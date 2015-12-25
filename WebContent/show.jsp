<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示</title>
<jsp:include page="inc.jsp"></jsp:include>
<style type="text/css">
.divhalf {
	padding: 20px;
}

.divRow {
	display: block;
	margin-top: 20px;
}

.spanKey {
	font-family: SimSunS, TSong, FangSong_GB2312;
	font-size: 150%; display : inline-block;
	width: 150px;
	text-align: left;
	font-weight: bold;
	display: inline-block;
}

.spanValue {
	font-family: SimSunS, TSong, FangSong_GB2312;
	font-size: 150%;
	display: inline-block;
	width: 150px;
	text-align: left;
}
</style>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						/* 查询数据方法 */
						var query = function() {
							console.info('查询间隔:'
									+ $("#ss").numberbox('getValue'));
							$
									.get(
											"getShow",
											function(data, status) {
												if (data) {
													$("#left").empty();
													$("#right").empty();
													$(data)
															.each(
																	function(
																			index,
																			node) {
																		if (node.type == 1) {
																			$(
																					"#left")
																					.append(
																							"<div class='divRow'><span class='spanKey'>"
																									+ node.keys
																									+ "</span><span class='spanValue'>"
																									+ node.value
																									+ "</span></div>");
																		}
																		if (node.type == 2) {
																			$(
																					"#right")
																					.append(
																							"<div class='divRow'><span class='spanKey'>"
																									+ node.keys
																									+ "</span><span class='spanValue'>"
																									+ node.value
																									+ "</span></div>");
																		}
																	});

												}
											});
						}
						/* 设置定时任务方法 */
						myInterval = setInterval(function() {
							query()
						}, $("#ss").numberbox('getValue') * 1000 * 60);

						/* 点击刷新按钮执行方法 */
						var btn_Click = function() {
							clearInterval(myInterval);
							myInterval = setInterval(function() {
								query()
							}, $("#ss").numberbox('getValue') * 1000 * 60);
						}
						/* 把按钮的点击事件和刷新方法绑定 */
						$('#btn').bind('click', function() {
							btn_Click();
						});
						$('#btn_Cancel').bind('click', function() {
							clearInterval(myInterval);
						});
						query();

					});
</script>
</head>
<body class="easyui-layout">
	<div
		data-options="region:'north',title:'显示设置',split:true,collapsible:false"
		style="height: 100px; text-align: center; padding: 18px 0px;">
		<div style="width: 400px; height: 30px; margin: 0px auto;">
			<span>刷新频率(分钟):</span><input id="ss" class="easyui-numberspinner"
				style="width: 80px;" required="required"
				data-options="min:1,max:100,editable:false" value="1"><a
				id="btn" class="easyui-linkbutton"
				data-options="iconCls:'ext-icon-page_refresh '">刷新</a> <a
				id="btn_Cancel" class="easyui-linkbutton"
				data-options="iconCls:'ext-icon-stop'">停止刷新</a>
		</div>
	</div>
	<div
		data-options="region:'west',title:'出车信息',split:true,collapsible:false"
		style="width: 50%; text-align: center;">
		<div id="left" class="divhalf"></div>
	</div>
	<div data-options="region:'center',title:'疾病信息'"
		style="padding: 5px; text-align: center;">
		<div id="right" class="divhalf"></div>
	</div>
</body>
</html>