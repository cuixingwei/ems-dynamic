package com.xhs.ems.bean;

public class Parameter {
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 响铃到接听大于X秒（超时时长）
	 */
	private String overtimes;
	/**
	 * 调试员
	 */
	private String dispatcher;
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOvertimes() {
		return overtimes;
	}
	public void setOvertimes(String overtimes) {
		this.overtimes = overtimes;
	}
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	
}
