package com.xhs.ems.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.RingToAccept;
import com.xhs.ems.service.RingToAcceptService;

@Controller
@RequestMapping(value = "/page/base")
public class RingToAcceptController {
	private static final Logger logger = Logger
			.getLogger(RingToAcceptController.class);

	@Autowired
	private RingToAcceptService ringToAcceptService;

	@RequestMapping(value = "/getRingToAcceptDatas", method = RequestMethod.POST)
	public @ResponseBody List<RingToAccept> getData(int page, int rows,
			String sort, String order, Parameter parameter) {
		// System.out
		// .println("overtimes:" + overtimes + ";dispatcher:" + dispatcher
		// + ";statTime:" + startTime + ";endTime:" + endTime);
		// return ringToAcceptService.getData(overtimes, dispatcher, startTime,
		// endTime);
		// logger.info("overtimes:" + overtimes);
		return null;

	}
}
