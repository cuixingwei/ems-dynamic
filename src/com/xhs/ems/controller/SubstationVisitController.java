package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.SubstationVisitService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 上午10:57:59
 */
@Controller
@RequestMapping(value = "/page/base")
public class SubstationVisitController {
	private static final Logger logger = Logger
			.getLogger(SubstationVisitController.class);

	@Autowired
	private SubstationVisitService substationVisitService;

	@RequestMapping(value = "/getSubstationVisitDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("急救站出诊情况查询");
		return substationVisitService.getData(parameter);
	}

	@RequestMapping(value = "/exportSubstationVisitDatas", method = RequestMethod.POST)
	public void export(Parameter parameter) {
		logger.info("导出急救站出诊情况数据到excel");
	}

}
