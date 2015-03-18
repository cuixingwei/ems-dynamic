package com.xhs.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.User;
import com.xhs.ems.service.UserService;

@Controller
@RequestMapping(value = "page/base")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public @ResponseBody List<User> getUser() {
		System.out.println("获取调度员");
		return userService.getAvailableDispatcher();
	}
}
