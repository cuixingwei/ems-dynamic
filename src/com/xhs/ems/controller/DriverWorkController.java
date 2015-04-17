package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.DriverWorkService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:10:21
 */
@Controller
@RequestMapping(value = "/page/base")
public class DriverWorkController {
	private static final Logger logger = Logger
			.getLogger(DriverWorkController.class);

	@Autowired
	private DriverWorkService driverWorkService;

	@RequestMapping(value = "/getDriverWorkDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("司机工作情况统计");
		return driverWorkService.getData(parameter);
	}

	@RequestMapping(value = "/exportDriverWorkDatas", method = RequestMethod.POST)
	public @ResponseBody void export(Parameter parameter) {
		logger.info("导出司机工作情况数据到excel");

	}
}
