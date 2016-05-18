package com.xhs.ems.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Dictionary;
import com.xhs.ems.service.DictionaryService;

/**
 * @datetime 2016年5月18日 下午5:14:00
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "page/base")
public class DictionaryController {
	private static final Logger logger = Logger
			.getLogger(DictionaryController.class);
	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(value = "/GetPatientClass", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetPatientClass() {
		logger.info("获取分类统计字典表");
		return dictionaryService.getPatientClass();
	}
	
	@RequestMapping(value = "/GetPatientType", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetPatientType() {
		logger.info("获取疾病种类字典表");
		return dictionaryService.GetPatientType();
	}
}
