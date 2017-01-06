package com.xhs.ems.controller;

import java.util.List;

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
import com.xhs.ems.bean.HistoryEvent;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.SessionInfo;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.HistoryEventService;

/**
 * @author cuixingwei
 * @datetime 2017年1月5日下午5:36:23
 */
@Controller
@RequestMapping(value = "/page/base")
public class HistoryEventController {
	private static final Logger logger = Logger
			.getLogger(HistoryEventController.class);

	@Autowired
	private HistoryEventService historyEventService;

	@RequestMapping(value = "/getHistoryEventDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		return historyEventService.getData(parameter);
	}

	@RequestMapping(value = "/getHistoryEventDetail", method = RequestMethod.GET)
	public @ResponseBody List<HistoryEvent> getDetail(String eventCode,
			HttpServletRequest request) {
		
		return historyEventService.getDetail(eventCode,request);
	}

	@RequestMapping(value = "/exportHistoryEventDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/msexcel;charset=UTF-8");
		logger.info("导出历史事件列表");

		String title = "历史事件";
		String[] headers = new String[] { "调度员", "受理时刻", "受理次数", "事件类型",
				"呼救号码", "事件名称", "联动来源" };
		String[] fields = new String[] { "thisDispatcher", "acceptStartTime",
				"acceptCount", "eventType", "callPhone", "eventName",
				"eventSource" };
		int spanCount = 0; // 需要合并的列数。从第0列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				historyEventService.getData(parameter).getRows(),
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
