package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.SubstationLateVisitService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 下午8:07:25
 */
@Controller
@RequestMapping(value = "/page/base")
public class SubstationLateVisitController {
	private static final Logger logger = Logger
			.getLogger(SubstationLateVisitController.class);

	@Autowired
	private SubstationLateVisitService substationLateVisitService;

	@RequestMapping(value = "/getSubstationLateVisitDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("急救站3分钟未出诊统计");
		return substationLateVisitService.getData(parameter);
	}

	@RequestMapping(value = "/exportSubstationLateVisitDatas", method = RequestMethod.POST)
	public void export(Parameter parameter) {
		logger.info("导出急救站3分钟未出诊数据到Excel");
	}

}
