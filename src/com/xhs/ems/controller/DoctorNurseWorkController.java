package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.DoctorNurseWorkService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日  下午4:44:25
 */
@Controller
@RequestMapping(value = "/page/base")
public class DoctorNurseWorkController {
	private static final Logger logger = Logger
			.getLogger(DoctorNurseWorkController.class);
	@Autowired
	private DoctorNurseWorkService doctorNurseWorkService;

	@RequestMapping(value = "getDoctorNurseWorkData", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("医生护士工作情况统计");
		return doctorNurseWorkService.getData(parameter);
	}

	@RequestMapping(value = "exportDoctorNurseWorkData", method = RequestMethod.POST)
	public void export() {
		logger.info("导出医生护士工作情况统计到Excel");
	}
}
