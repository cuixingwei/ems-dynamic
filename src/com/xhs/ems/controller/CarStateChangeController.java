package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.CarStateChangeDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午9:17:33
 */
@Controller
@RequestMapping(value = "/page/base")
public class CarStateChangeController {
	private static final Logger logger = Logger
			.getLogger(CarStateChangeController.class);

	@Autowired
	private CarStateChangeDAO carStateChangeDAO;

	@RequestMapping(value = "/getCarStateChangeDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("车辆状态变化查询");
		return carStateChangeDAO.getData(parameter);
	}

	@RequestMapping(value = "/exportCarStateChangeDatas", method = RequestMethod.POST)
	public @ResponseBody void export(Parameter parameter) {
		logger.info("导出车辆状态变化数据到excel");

	}

}
