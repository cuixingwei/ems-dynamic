package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.StateChangeService;

/**
 * @author CUIXINGWEI
 * @date 2015年3月30日
 */
@Controller
@RequestMapping(value = "/page/base")
public class StateChangeController {
	private static final Logger logger = Logger.getLogger(StateChangeController.class);
	
	@Autowired
	private StateChangeService stateChangeService;
	
	@RequestMapping(value = "/getStateChangeDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("状态变化统计");
		return stateChangeService.getData(parameter);
	}
	
	@RequestMapping(value = "/exportStateChangeDatas", method = RequestMethod.POST)
	public @ResponseBody void exportStateChangeDatas(Parameter parameter) {
		logger.info("导出状态变化统计到excel");
		
	}
}
