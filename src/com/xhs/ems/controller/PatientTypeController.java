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
		String[] headers = new String[] { "分站", "交通事故外伤", "其他类外伤","烧伤", "电击伤 溺水", "其他外科疾病","心血管系统疾病", "脑血管系统疾病", "呼吸道系统疾病","食物中毒", "药物中毒", "酒精中毒","CO中毒", "其他内科疾病", "妇科 产科","儿科", "气管异物","其他五官科","传染病", "抢救前死亡", "抢救后死亡","其他", "合计" };
		String[] fields = new String[] { "station", "type1", "type2","type3", "type4", "type5","type6", "type7", "type8","type9", "type10", "type11","type12", "type13", "type14","type15", "type16","type17","type18", "type19", "type20","type21", "total" };
		TableData td = ExcelUtils.createTableData(
				patientTypeSerivce.getData(parameter).getRows(),
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
