package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.AcceptSendCarService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月7日  下午3:21:21
 */
@Controller
@RequestMapping(value = "/page/base")
public class AcceptSendCarController {
	private static final Logger logger = Logger
			.getLogger(AcceptSendCarController.class);

	@Autowired
	private AcceptSendCarService acceptSendCarService;

	@RequestMapping(value = "/getAcceptSendCarDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("开始受理到派车大于X秒统计");
		return acceptSendCarService.getData(parameter);
	}

	@RequestMapping(value = "/exportAcceptSendCarDatas", method = RequestMethod.POST)
	public void export(Parameter parameter) {
		logger.info("开始受理到派车大于X秒统计");
	}
}
