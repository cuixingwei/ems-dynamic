package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.CarWorkService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午2:03:48
 */
@Controller
@RequestMapping(value = "/page/base")
public class CarWorkController {
	private static final Logger logger = Logger
			.getLogger(CarWorkController.class);
	@Autowired
	private CarWorkService carWorkService;

	@RequestMapping(value = "/getCarWorkDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("车辆工作情况统计");
		return carWorkService.getData(parameter);
	}

	@RequestMapping(value = "/exportCarWorkDatas", method = RequestMethod.POST)
	public void export(Parameter parameter) {
		logger.info("导出车辆工作统计到excel");
	}
}
