package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.CarPauseService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午4:44:44
 */
@Controller
@RequestMapping(value = "/page/base")
public class CarPauseController {
	private static final Logger logger = Logger
			.getLogger(CarPauseController.class);

	@Autowired
	private CarPauseService carPauseService;

	@RequestMapping(value = "/getCarPauseDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("车辆暂停调用原因查询");
		return carPauseService.getData(parameter);
	}
	
	@RequestMapping(value = "/exportCarPauseDatas", method = RequestMethod.POST)
	public @ResponseBody void export(Parameter parameter) {
		logger.info("导出车辆暂停调用原因数据到excel");
		
	}

}
