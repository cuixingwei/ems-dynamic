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
import com.xhs.ems.service.PatientTypeSerivce;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午5:04:54
 */
@Controller
@RequestMapping(value = "/page/base")
public class PatientTypeController {
	private static final Logger logger = Logger
			.getLogger(PatientTypeController.class);
	@Autowired
	private PatientTypeSerivce patientTypeSerivce;

	@RequestMapping(value = "getPatientTypeData", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("疾病种类统计");
		return patientTypeSerivce.getData(parameter);
	}

	@RequestMapping(value = "exportPatientTypeData", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出疾病种类统计到Excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "疾病种类统计";
		String[] headers = new String[] { "疾病种类", "接诊人数", "比率" };
		String[] fields = new String[] { "patientClass",
				"receivePeopleNumbers", "rate" };
		TableData td = ExcelUtils.createTableData(
				patientTypeSerivce.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(headers), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, "admin", td);
	}

}
