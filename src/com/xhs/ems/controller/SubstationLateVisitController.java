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
import com.xhs.ems.service.SubstationLateVisitService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 下午8:07:25
 */
@Controller
@RequestMapping(value = "/page/base")
public class SubstationLateVisitController {
	private static final Logger logger = Logger
			.getLogger(SubstationLateVisitController.class);

	@Autowired
	private SubstationLateVisitService substationLateVisitService;

	@RequestMapping(value = "/getSubstationLateVisitDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("急救站3分钟未出诊统计");
		return substationLateVisitService.getData(parameter);
	}

	@RequestMapping(value = "/exportSubstationLateVisitDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出急救站3分钟未出诊数据到Excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "急救站3分钟未出诊统计";
		String[] headers = new String[] { "现场地址", "事件类型", "车辆标识", "受理时刻",
				"生成任务时刻", "出车时刻", "出车时长", "出车结果", "任务备注", "调度员" };
		String[] fields = new String[] { "siteAddress", "eventType", "carCode",
				"acceptTime", "createTaskTime", "outCarTime", "outCarTimes",
				"taskResult", "remark", "dispatcher" };
		TableData td = ExcelUtils.createTableData(substationLateVisitService
				.getData(parameter).getRows(), ExcelUtils
				.createTableHeader(headers), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, "admin", td);
	}

}
