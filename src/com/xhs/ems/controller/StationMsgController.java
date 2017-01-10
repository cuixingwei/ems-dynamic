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
import com.xhs.ems.service.StationMsgService;

/**
 * @author cuixingwei
 * @datetime 2017年1月9日下午9:47:28
 */
@Controller
@RequestMapping(value = "/page/base")
public class StationMsgController {
	private static final Logger logger = Logger.getLogger(StationMsgController.class);

	@Autowired
	private StationMsgService stationMsgService;

	@RequestMapping(value = "/getStationMsgDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("急救站回单超时统计");
		return stationMsgService.getData(parameter);
	}

	@RequestMapping(value = "/exportStationMsgDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("导出急救站回单超时统计数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "急救站回单超时统计";
		String[] headers = new String[] { "分站", "总数", "正常回单数", "晚回单数","未回单" };
		String[] fields = new String[] { "station", "totalCount", "normalReturn", "lateReturn","noReturn" };
		TableData td = ExcelUtils.createTableData(stationMsgService.getData(parameter).getRows(),
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
