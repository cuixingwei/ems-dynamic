package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.AcceptMarkService;

@Controller
@RequestMapping(value = "/page/base")
public class AcceptMarkController {
	private static final Logger logger = Logger
			.getLogger(AcceptMarkController.class);
	
	@Autowired
	private AcceptMarkService acceptMarkService;
	
	@RequestMapping(value = "/getAcceptMarkDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("受理备注查询");
		return acceptMarkService.getData(parameter);
	}
	
	@RequestMapping(value = "/exportAcceptMarkDatas", method = RequestMethod.POST)
	public @ResponseBody void exportAcceptMarkDatas(Parameter parameter) {
		logger.info("导出受理备注查询到excel");
	}
	
}
