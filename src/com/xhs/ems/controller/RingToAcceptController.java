package com.xhs.ems.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.RingToAccept;
import com.xhs.ems.service.RingToAcceptService;

@Controller
@RequestMapping(value = "/page")
public class RingToAcceptController {

	@Autowired
	private RingToAcceptService ringToAcceptService;

	@RequestMapping(value = "/getRingToAcceptDatas", method = RequestMethod.POST)
	public @ResponseBody List<RingToAccept> getData(
			@RequestParam(value = "overtimes") String overtimes,
			@RequestParam(value = "dispatcher") String dispatcher,
			@RequestParam(value = "startTime") String startTime,
			@RequestParam(value = "endTime") String endTime,
			HttpServletRequest request) {
		System.out
				.println("overtimes:" + overtimes + ";dispatcher:" + dispatcher
						+ ";statTime:" + startTime + ";endTime:" + endTime);
		return ringToAcceptService.getData(overtimes, dispatcher, startTime,
				endTime);
	}
}
