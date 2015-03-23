package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.RingToAcceptService;

@Controller
@RequestMapping(value = "/page/base")
public class RingToAcceptController {
	private static final Logger logger = Logger
			.getLogger(RingToAcceptController.class);

	@Autowired
	private RingToAcceptService ringToAcceptService;

	@RequestMapping(value = "/getRingToAcceptDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("响铃到接听大于X秒");
		return ringToAcceptService.getData(parameter);

	}
}
