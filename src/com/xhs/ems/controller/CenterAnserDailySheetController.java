package com.xhs.ems.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.SessionInfo;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.CenterAnserDailySheetService;

/**
 * @author cuixingwei
 * @datetime 2016年10月11日下午3:33:22
 */
@Controller
@RequestMapping(value = "/page/base")
public class CenterAnserDailySheetController {
	private static final Logger logger = Logger.getLogger(CenterAnserDailySheetController.class);

	@Autowired
	private CenterAnserDailySheetService centerAnserDailySheetService;

	@RequestMapping(value = "/getCenterAnserDailySheet", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("120急救指挥中心接警统计-日报表");
		return centerAnserDailySheetService.getData(parameter);
	}

	@RequestMapping(value = "/exportCenterAnserDailySheet", method = RequestMethod.GET)
	public @ResponseBody void exportCenterAnserDailySheet(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出120急救指挥中心接警统计-日报表到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "120急救指挥中心接警统计-日报表";
		String[] headers = new String[] { "医院", "收入院", "抢救", "空返(次)", "被抢诊（次）", "总计(人)" };
		String[] fields = new String[] { "hospital", "inHosCounts", "spotCure", "emptyTask", "byRobCure",
				"totalSendCount" };
		TableData td = ExcelUtils.createTableData(centerAnserDailySheetService.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(headers), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);

		HttpSession session = request.getSession();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		if (null != sessionInfo) {
			report.exportToExcel(title, sessionInfo.getUser().getName(), td, parameter);
		} else {
			report.exportToExcel(title, "", td, parameter);
		}
	}
}
