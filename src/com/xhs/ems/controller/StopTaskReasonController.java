package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.StopTaskReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午3:10:49
 */
@Controller
@RequestMapping(value = "/page/base")
public class StopTaskReasonController {

	private static final Logger logger = Logger
			.getLogger(StopTaskReasonController.class);

	@Autowired
	private StopTaskReasonService stopTaskReasonService;

	@RequestMapping(value = "/getStopTaskReasonDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("中止任务原因查询");
		return stopTaskReasonService.getData(parameter);
	}
	
	@RequestMapping(value = "/exportStopTaskReasonDatas", method = RequestMethod.POST)
	public @ResponseBody void export(Parameter parameter) {
		logger.info("导出中止任务原因数据到excel");
		
	}

}
