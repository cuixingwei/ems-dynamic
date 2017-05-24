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
	
	@RequestMapping(value = "/GetPatientDepartment", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetPatientDepartment() {
		logger.info("获取疾病科别字典表");
		return dictionaryService.GetPatientDepartment();
	}
	
	@RequestMapping(value = "/GetPatientReason", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetPatientReason() {
		logger.info("获取疾病原因字典表");
		return dictionaryService.GetPatientReason();
	}
	
	@RequestMapping(value = "/GetIllState", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetIllState() {
		logger.info("获取病情字典表");
		return dictionaryService.GetIllState();
	}
	
	@RequestMapping(value = "/GetAidResult", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetAidResult() {
		logger.info("获取救治结果字典表");
		return dictionaryService.GetAidResult();
	}
	
	@RequestMapping(value = "/GetDeathProve", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetDeathProve() {
		logger.info("获取死亡证明字典表");
		return dictionaryService.GetDeathProve();
	}
	
	@RequestMapping(value = "/GetTakenPlaceType", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetTakenPlaceType() {
		logger.info("获取送往地点类型字典表");
		return dictionaryService.GetTakenPlaceType();
	}
	
	@RequestMapping(value = "/GetLocaleType", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetLocaleType() {
		logger.info("获取现场地点类型字典表");
		return dictionaryService.GetLocaleType();
	}
	
	@RequestMapping(value = "/GetOutCome", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetOutCome() {
		logger.info("获取转归结果字典表");
		return dictionaryService.GetOutCome();
	}
	
	@RequestMapping(value = "/GetCooperate", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetCooperate() {
		logger.info("获取病家合作字典表");
		return dictionaryService.GetCooperate();
	}
	
	@RequestMapping(value = "/GetIdentity", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetIdentity() {
		logger.info("获取身份编码字典表");
		return dictionaryService.GetIdentity();
	}
	
	@RequestMapping(value = "/GetProfession", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetProfession() {
		logger.info("获取职业编码字典表");
		return dictionaryService.GetProfession();
	}
	
	@RequestMapping(value = "/GetPhoneType", method = RequestMethod.GET)
	public @ResponseBody List<Dictionary> GetPhoneType() {
		logger.info("获取职业编码字典表");
		return dictionaryService.GetPhoneType();
	}
	
	
}
