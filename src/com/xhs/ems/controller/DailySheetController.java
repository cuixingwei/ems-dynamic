/**
 * @author cuixingwei
 * @datetime 2016年10月11日下午2:35:43
 */
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
import com.xhs.ems.service.DailySheetService;

/**
 * @author cuixingwei
 * @datetime 2016年10月11日下午2:35:43
 */
@Controller
@RequestMapping(value = "/page/base")
public class DailySheetController {
	private static final Logger logger = Logger.getLogger(DailySheetController.class);

	@Autowired
	private DailySheetService dailySheetService;

	@RequestMapping(value = "/getDailySheet", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("日报表");
		return dailySheetService.getData(parameter);
	}

	@RequestMapping(value = "/exportDailySheet", method = RequestMethod.GET)
	public @ResponseBody void exportDailySheet(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出日报表到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "日报表";
		String[] headers = new String[] { "医院", "收入院（人）", "现场救治", "空返", "抢诊数", "被抢诊数", "转诊数（次）", "特殊事件", "无车数",
				"拒绝转送病人数", "拒接无主病人数", "总派车数" };
		String[] fields = new String[] { "hospital", "inHosCounts", "spotCure", "emptyTask", "robCure", "byRobCure",
				"transferCure", "specialTask", "hungOn", "refuseTransferCount", "refuseNoOwnerCount", "totalSendCar" };
		TableData td = ExcelUtils.createTableData(dailySheetService.getData(parameter).getRows(),
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
