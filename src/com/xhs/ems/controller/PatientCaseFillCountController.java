package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.PatientCaseFillCountService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午2:14:15
 */
@Controller
@RequestMapping(value = "/page/base")
public class PatientCaseFillCountController {
	private static final Logger logger = Logger
			.getLogger(PatientCaseFillCountController.class);

	@Autowired
	private PatientCaseFillCountService patientCaseFillCountService;

	@RequestMapping(value = "/getPatientCaseFillCountDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("病例回填率查询");
		return patientCaseFillCountService.getData(parameter);
	}

	@RequestMapping(value = "/exportPatientCaseFillCountDatas", method = RequestMethod.POST)
	public void export(Parameter parameter) {
		logger.info("导出病例回填率数据到excel");
	}
}
