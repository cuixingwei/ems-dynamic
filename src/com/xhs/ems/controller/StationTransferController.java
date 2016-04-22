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
import com.xhs.ems.service.StationTransferService;

/**
 * @datetime 2016年4月19日 下午8:14:28
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "/page/base")
public class StationTransferController {
	private static final Logger logger = Logger
			.getLogger(StationTransferController.class);
	@Autowired
	private StationTransferService stationTransferService;

	@RequestMapping(value = "/getStationTransferDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("转院统计");
		return stationTransferService.getData(parameter);
	}

	@RequestMapping(value = "/exportStationTransferDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出转院统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "转院统计";
		String[] headers = new String[] { "项目", "次数" };
		String[] fields = new String[] { "name", "numbers" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				stationTransferService.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(headers, spanCount), fields);
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
