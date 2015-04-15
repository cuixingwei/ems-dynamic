package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.CallSpotTypeService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午3:17:33
 */
@Controller
@RequestMapping(value = "/page/base")
public class CallSpotTypeController {
	private static final Logger logger = Logger
			.getLogger(EmptyCarReasonController.class);

	@Autowired
	private CallSpotTypeService callSpotTypeService;

	@RequestMapping(value = "/getCallSpotTypeDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("呼救现场地点类型统计");
		return callSpotTypeService.getData(parameter);
	}

	@RequestMapping(value = "/exportCallSpotTypeDatas", method = RequestMethod.POST)
	public void export(Parameter parameter) {
		logger.info("呼救现场地点类型统计");
	}

}
