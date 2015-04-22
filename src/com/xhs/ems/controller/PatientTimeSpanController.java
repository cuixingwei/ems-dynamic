package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
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

	@RequestMapping(value = "/exportPatientTimeSpanDatas", method = RequestMethod.POST)
	public @ResponseBody void export(Parameter parameter) {
		logger.info("导出病发事件段统计到excel");
	}
}
