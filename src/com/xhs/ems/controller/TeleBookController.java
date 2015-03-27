package com.xhs.ems.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.service.TeleBookService;

@Controller
@RequestMapping(value = "/page/base")
public class TeleBookController {
	private static final Logger logger = Logger
			.getLogger(TeleBookController.class);

	@Autowired
	private TeleBookService teleBookService;

	@RequestMapping(value = "/getTeleBookDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("通讯录");
		return teleBookService.getData(parameter);
	}
	
	@RequestMapping(value = "/exportTeleBookDatas", method = RequestMethod.POST)
	public @ResponseBody void exportTeleBookDatas(Parameter parameter) {
		logger.info("导出通讯录为excel");
		
	}
}
