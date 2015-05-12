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
import com.xhs.ems.service.PatientTimeSpanService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 上午10:19:16
 */
@Controller
@RequestMapping(value = "page/base")
public class PatientTimeSpanController {
	private static final Logger logger = Logger
			.getLogger(PatientTimeSpanController.class);
	@Autowired
	private PatientTimeSpanService patientTimeSpanService;

	@RequestMapping(value = "/getPatientTimeSpanDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("病发事件段统计");
		return patientTimeSpanService.getData(parameter);
	}

	@RequestMapping(value = "/exportPatientTimeSpanDatas", method = RequestMethod.GET)
	public @ResponseBody void export(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出病发事件段统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "病发事件段统计";
		String[] headers = new String[] { "疾病类型", "合计", "0_1", "1_2", "2_3",
				"3_4", "4_5", "5_6", "6_7", "7_8", "8_9", "9_10", "10_11",
				"11_12", "12_13", "13_14", "14_15", "15_16", "16_17", "17_18",
				"18_19", "19_20", "20_21", "21_22", "22_23", "23_24" };
		String[] fields = new String[] { "patientType", "summary", "span0_1",
				"span1_2", "span2_3", "span3_4", "span4_5", "span5_6",
				"span6_7", "span7_8", "span8_9", "span9_10", "span10_11",
				"span11_12", "span12_13", "span13_14", "span14_15",
				"span15_16", "span16_17", "span17_18", "span18_19",
				"span19_20", "span20_21", "span21_22", "span22_23", "span23_24" };
		TableData td = ExcelUtils.createTableData(patientTimeSpanService
				.getData(parameter).getRows(), ExcelUtils
				.createTableHeader(headers), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, "admin", td);
	}
}
