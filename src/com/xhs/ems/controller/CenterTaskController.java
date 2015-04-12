package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.CenterTaskService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年3月31日 上午9:32:24
 */
@Controller
@RequestMapping(value = "/page/base")
public class CenterTaskController {
	private static final Logger logger = Logger
			.getLogger(CenterTaskController.class);
	@Autowired
	private CenterTaskService centerService;

	@RequestMapping(value = "/getCenterTaskDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("中心任务信息统计");
		return centerService.getData(parameter);
	}

	@RequestMapping(value = "/exportCenterTaskDatas", method = RequestMethod.POST)
	public @ResponseBody void export(Parameter parameter) {
		logger.info("导出中心任务信息统计到excel");
	}
}
