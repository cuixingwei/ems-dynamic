package com.xhs.ems.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.DriverWorkService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:10:21
 */
@Controller
@RequestMapping(value = "/page/base")
public class DriverWorkController {
	private static final Logger logger = Logger
			.getLogger(DriverWorkController.class);

	@Autowired
	private DriverWorkService driverWorkService;

	@RequestMapping(value = "/getDriverWorkDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("司机工作情况统计");
		return driverWorkService.getData(parameter);
	}

	@RequestMapping(value = "/exportDriverWorkDatas", method = RequestMethod.GET)
	public @ResponseBody void export(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出司机工作情况数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "司机工作情况统计";
		String[] headers = new String[] { "分站", "司机", "出车次数", "有效出车数", "中止数",
				" 空车数", "拒绝出车", "暂停调用数", " 平均出车时间", "平均到达时间" };
		String[] fields = new String[] { "station", "driver", "outCarNumbers",
				"nomalNumbers", "stopNumbers", "emptyNumbers", "refuseNumbers",
				"pauseNumbers", "averageOutCarTimes", "averageArriveSpotTimes" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				driverWorkService.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(headers, spanCount), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, "admin", td);

	}
}
