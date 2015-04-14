package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.EmptyCarService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午8:58:13
 */
@Controller
@RequestMapping(value = "/page/base")
public class EmptyCarController {
	private static final Logger logger = Logger
			.getLogger(EmptyCarController.class);
	@Autowired
	private EmptyCarService emptyCarService;

	@RequestMapping(value = "getEmptyCarData", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("放空车统计");
		return emptyCarService.getData(parameter);
	}

	@RequestMapping(value = "exportEmptyCarData", method = RequestMethod.POST)
	public void export() {
		logger.info("导出放空车统计到Excel");
	}
}
