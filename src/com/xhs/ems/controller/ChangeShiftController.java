package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.ChangeShift;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.ChangeShiftService;

/**
 * 交接班
 * @author cuixingwei
 * @datetime 2016年10月11日下午5:44:17
 */
@Controller
@RequestMapping(value = "/page/base")
public class ChangeShiftController {
	private static final Logger logger = Logger
			.getLogger(ChangeShiftController.class);
	@Autowired
	private ChangeShiftService changeShiftService;

	@RequestMapping(value = "/getChangeShift", method = RequestMethod.POST)
	public @ResponseBody ChangeShift getData(Parameter parameter) {
		logger.info("交接班");
		return changeShiftService.getData(parameter);
	}
}
