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

import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.PatientSex;
import com.xhs.ems.bean.SessionInfo;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.PatientSexService;

/**
 * @datetime 2016年5月18日 下午5:39:33
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "/page/base")
public class PatientSexController {
	private static final Logger logger = Logger
			.getLogger(PatientSexController.class);
	@Autowired
	private PatientSexService patientSexService;

	@RequestMapping(value = "/getPatientSex", method = RequestMethod.POST)
	public @ResponseBody List<PatientSex> getData(Parameter parameter) {
		logger.info("患者性别统计");
		return patientSexService.getData(parameter);
	}

	@RequestMapping(value = "/exportPatientSex", method = RequestMethod.GET)
	public @ResponseBody void exportDatas(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出患者性别统计到excel");
		response.setContentType("application/msexcel;charset=UTF-8");

		String title = "患者性别统计";
		String[] headers = new String[] { "时间", "男", "女",
				"合计","男:女" };
		String[] fields = new String[] { "time", "male",
				"female", "total","radio" };
		TableData td = ExcelUtils.createTableData(
				patientSexService.getData(parameter),
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
