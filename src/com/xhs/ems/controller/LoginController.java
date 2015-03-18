package com.xhs.ems.controller;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.User;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.service.UserService;

@Controller
@RequestMapping(value = "/page")
public class LoginController {

	@Autowired
	private UserService userService;

	/**
	 * 返回菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMenu", method = RequestMethod.POST)
	public @ResponseBody String getMenu() {
		System.out.println("获取菜单");
		URL url = LoginController.class.getClassLoader().getResource(
				"menu.json");
		String menuString = "";
		try {
			menuString = CommonUtil.readContentFromFile(new File(url.toURI()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return menuString;
	}

	/**
	 * 登录方法
	 * 
	 * @param userId
	 * @param password
	 * @param request
	 * @return 登录结果result:fail代表用户不存在,error表示用户名错误,success表示登录成功
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String login(
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "password") String password,
			HttpServletRequest request) {
		System.out.println("用户名:" + userId + ":密码" + password);
		User user = new User(userId, "", password, 1);
		User usertemp = null;
		List<User> list = userService.validateMrUser(user);
		String result = "fail";
		if (list.size() > 0) {
			result = "error";
			usertemp = list.get(0);
			if (usertemp.getPassword().equalsIgnoreCase(password)) {
				result = "success";
				String currentTime = CommonUtil.getSystemTime();
				if (CommonUtil.isNullOrEmpty(usertemp.getStationName())) {
					usertemp.setStationName("未知分站");
				}
				HttpSession session = request.getSession();
				session.setAttribute("user", usertemp);
				session.setAttribute("stationName", usertemp.getStationName());
				session.setAttribute("userName", usertemp.getName());
				session.setAttribute("currentTime", currentTime);
			}
		}
		return "{\"status\":200,\"msg\":\"" + result + "\"}";
	}

}
