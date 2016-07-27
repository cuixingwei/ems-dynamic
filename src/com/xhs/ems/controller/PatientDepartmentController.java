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
import com.xhs.ems.service.PatientDepartmentService;

/**
 * @datetime 2016年7月27日 下午1:10:42
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "/page/base")
public class PatientDepartmentController {
	private static final Logger logger = Logger
			.getLogger(PatientDepartmentController.class);
	@Autowired
	private PatientDepartmentService patientDepartmentService;

	@RequestMapping(value = "/getPatientDepartment", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("疾病科别统计");
		return patientDepartmentService.getData(parameter);
	}

	@RequestMapping(value = "/exportPatientDepartment", method = RequestMethod.GET)
	public @ResponseBody void exportPatientDepartment(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出疾病科别统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "疾病科别统计";
		String[] headers = new String[] { "疾病科别", "人数", "百分比" };
		String[] fields = new String[] { "patientDepartment", "numbers",
				"radio" };
		TableData td = ExcelUtils.createTableData(
				patientDepartmentService.getData(parameter).getRows(),
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
