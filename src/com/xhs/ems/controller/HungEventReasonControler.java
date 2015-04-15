package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.HungEventReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午2:12:50
 */
@Controller
@RequestMapping(value = "/page/base")
public class HungEventReasonControler {
	private static final Logger logger = Logger
			.getLogger(HungEventReasonControler.class);

	@Autowired
	private HungEventReasonService hungEventReasonService;

	@RequestMapping(value = "/getHungEventReasonDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("挂起事件原因查询");
		return hungEventReasonService.getData(parameter);
	}

	@RequestMapping(value = "/exportHungEventReasonDatas", method = RequestMethod.POST)
	public void export(Parameter parameter) {
		logger.info("导出挂起事件原因数据到excel");
	}

}
