package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.AnswerAlarmService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午8:22:33
 */
@Controller
@RequestMapping(value = "/page/base")
public class AnswerAlarmController {
	private static final Logger logger = Logger
			.getLogger(AnswerAlarmController.class);

	@Autowired
	private AnswerAlarmService answerAlarmService;

	@RequestMapping(value = "/getAnswerAlarmDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("中心接警统计");
		return answerAlarmService.getData(parameter);
	}

	@RequestMapping(value = "/exportAnswerAlarmDatas", method = RequestMethod.POST)
	public void export(Parameter parameter) {
		logger.info("中心接警统计");
	}

}
