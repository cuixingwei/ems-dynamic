package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.EmptyCarReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 下午2:23:11
 */
@Controller
@RequestMapping(value = "/page/base")
public class EmptyCarReasonController {
	private static final Logger logger = Logger
			.getLogger(EmptyCarReasonController.class);

	@Autowired
	private EmptyCarReasonService emptyCarReasonService;

	@RequestMapping(value = "/getEmptyCarReasonDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("放空车任务原因查询");
		return emptyCarReasonService.getData(parameter);
	}

	@RequestMapping(value = "/exportEmptyCarReasonDatas", method = RequestMethod.POST)
	public void export(Parameter parameter) {
		logger.info("导出放空车任务原因数据到excel");
	}

}
