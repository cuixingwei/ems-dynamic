package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.AcceptTimeService;

@Controller
@RequestMapping(value = "/page/base")
public class AcceptTimeController {
	private static final Logger logger = Logger
			.getLogger(AcceptTimeController.class);
	@Autowired
	private AcceptTimeService acceptTimeService;
	
	@RequestMapping(value = "/getAcceptTimeDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("受理时间统计");
		return acceptTimeService.getData(parameter);
	}
	
	@RequestMapping(value = "/exportAcceptTimeDatas", method = RequestMethod.POST)
	public @ResponseBody void exportAcceptMarkDatas(Parameter parameter) {
		logger.info("导出受理时间统计到excel");
	}
}
