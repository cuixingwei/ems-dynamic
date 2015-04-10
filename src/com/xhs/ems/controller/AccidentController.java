package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.AccidentService;

@Controller
@RequestMapping(value = "/page/base")
public class AccidentController {
	private static final Logger logger = Logger
			.getLogger(AccidentController.class);
	@Autowired
	private AccidentService accidentService;
	
	@RequestMapping(value = "/getAccidentDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("重大事故统计");
		return accidentService.getData(parameter);
	}
	
	@RequestMapping(value = "/exportAccidentDatas", method = RequestMethod.POST)
	public @ResponseBody void exportAccidentDatas(Parameter parameter) {
		logger.info("导出重大事故统计到excel");
	}
}
