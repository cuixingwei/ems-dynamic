package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.PatientReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:44:29
 */
@Controller
@RequestMapping(value = "/page/base")
public class PatientReasonController {
	private static final Logger logger = Logger
			.getLogger(PatientReasonController.class);
	@Autowired
	private PatientReasonService patientReasonService;

	@RequestMapping(value = "getPatientReasonData", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("疾病原因统计");
		return patientReasonService.getData(parameter);
	}

	@RequestMapping(value = "exportPatientReasonData", method = RequestMethod.POST)
	public void export() {
		logger.info("导出疾病原因统计到Excel");
	}

}
