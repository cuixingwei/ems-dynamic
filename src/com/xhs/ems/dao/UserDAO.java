package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.User;

public interface UserDAO {
	/**
	 * 验证登录
	 * 
	 * @param employeeId
	 * @param password
	 * @param checkLogin
	 * @return
	 */
	public List<User> validateMrUser(User user);
	
	/**
	 * 获取所有有效调度员信息
	 * @return
	 */
	public List<User> getAvailableDispatcher();
}
