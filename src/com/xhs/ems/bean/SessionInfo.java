package com.xhs.ems.bean;

public class SessionInfo {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return user.getName();
	}
	
}
