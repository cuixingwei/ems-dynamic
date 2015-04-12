package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.StopTaskService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午3:16:51
 */
@Controller
@RequestMapping(value = "/page/base")
public class StopTaskController {
	private static final Logger logger = Logger
			.getLogger(StopTaskController.class);
	@Autowired
	private StopTaskService stopTaskService;

	@RequestMapping(value = "/getStopTaskDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("中止任务信息");
		return stopTaskService.getData(parameter);
	}

	@RequestMapping(value = "/exportStopTaskDatas", method = RequestMethod.POST)
	public @ResponseBody void export(Parameter parameter) {
		logger.info("导出中止任务信息到excel");
	}
}
