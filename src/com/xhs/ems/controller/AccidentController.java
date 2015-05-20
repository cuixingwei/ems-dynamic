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
import com.xhs.ems.service.AccidentService;

@Controller
@RequestMapping(value = "/page/base")
public class AccidentController {
	private static final Logger logger = Logger
			.getLogger(AccidentController.class);
	@Autowired
	private AccidentService accidentService;

	@RequestMapping(value = "/getAccidentDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("重大事故统计");
		return accidentService.getData(parameter);
	}

	@RequestMapping(value = "/exportAccidentDatas", method = RequestMethod.GET)
	public void exportAccidentDatas(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出重大事故统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "重大事故统计";
		String[] headers = new String[] { "事件编码", "事发时间", "事件名称", "呼救电话", "调度员" };
		String[] fields = new String[] { "eventCode", "eventTime", "eventName",
				"callPhone", "dispatcher" };
		TableData td = ExcelUtils.createTableData(
				accidentService.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(headers), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		
		HttpSession session = request.getSession();
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute("sessionInfo");
		if (null != sessionInfo) {
			report.exportToExcel(title, sessionInfo.getUser().getName(), td,
					parameter);
		} else {
			report.exportToExcel(title, "", td, parameter);
		}
	}
}
