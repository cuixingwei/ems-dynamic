package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.AcceptEventTypeService;

@Controller
@RequestMapping(value = "/page/base")
public class AcceptEventTypeController {
	private static final Logger logger = Logger
			.getLogger(AcceptEventTypeController.class);
	@Autowired
	private AcceptEventTypeService acceptEventTypeService;
	
	@RequestMapping(value = "/getAcceptEventTypeDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("事件类型统计");
		return acceptEventTypeService.getData(parameter);
	}
	
	@RequestMapping(value = "/exportAcceptEventTypeDatas", method = RequestMethod.POST)
	public @ResponseBody void exportAcceptEventTypeDatas(Parameter parameter) {
		logger.info("导出事件类型统计到excel");
		
	}
	
}
