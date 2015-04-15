package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.SendSpotTypeService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午3:48:16
 */
@Controller
@RequestMapping(value = "/page/base")
public class SendSpotTypeController {
	private static final Logger logger = Logger
			.getLogger(SendSpotTypeController.class);

	@Autowired
	private SendSpotTypeService sendSpotTypeService;

	@RequestMapping(value = "/getSendSpotTypeDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("送往地点类型查询");
		return sendSpotTypeService.getData(parameter);
	}

	@RequestMapping(value = "/exportSendSpotTypeDatas", method = RequestMethod.POST)
	public @ResponseBody void export(Parameter parameter) {
		logger.info("导出送往地点类型统计数据到excel");

	}

}
