package com.xhs.ems.bean;

/**
 * 
 * @author CUIXINGWEI
 *
 */
public class Accident {
	/**
	 * 事件编码
	 */
	private String eventCode;
	/**
	 * 事发时间
	 */
	private String eventTime;
	/**
	 * 事件名称
	 */
	private String eventName;
	/**
	 * 呼救电话
	 */
	private String callPhone;
	/**
	 * 调度员
	 */
	private String dispatcher;
	private String className;
	private String type;
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getCallPhone() {
		return callPhone;
	}
	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @param eventCode
	 * @param eventTime
	 * @param eventName
	 * @param callPhone
	 * @param dispatcher
	 * @param className
	 * @param type
	 */
	public Accident(String eventCode, String eventTime, String eventName,
			String callPhone, String dispatcher, String className, String type) {
		this.eventCode = eventCode;
		this.eventTime = eventTime;
		this.eventName = eventName;
		this.callPhone = callPhone;
		this.dispatcher = dispatcher;
		this.className = className;
		this.type = type;
	}
	
	
}
