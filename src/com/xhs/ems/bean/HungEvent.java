package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 下午9:03:44
 */
public class HungEvent {
	/**
	 * 事件名称
	 */
	private String eventName;
	/**
	 * 受理类型
	 */
	private String acceptType;
	/**
	 * 挂起时刻
	 */
	private String hungTime;
	/**
	 * 挂起原因
	 */
	private String hungReason;
	/**
	 * 操作人
	 */
	private String dispatcher;
	/**
	 * 结束时刻
	 */
	private String endTime;
	/**
	 * 时长
	 */
	private String hungtimes;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getAcceptType() {
		return acceptType;
	}

	public void setAcceptType(String acceptType) {
		this.acceptType = acceptType;
	}

	public String getHungTime() {
		return hungTime;
	}

	public void setHungTime(String hungTime) {
		this.hungTime = hungTime;
	}

	public String getHungReason() {
		return hungReason;
	}

	public void setHungReason(String hungReason) {
		this.hungReason = hungReason;
	}

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getHungtimes() {
		return hungtimes;
	}

	public void setHungtimes(String hungtimes) {
		this.hungtimes = hungtimes;
	}

	public HungEvent(String eventName, String acceptType, String hungTime,
			String hungReason, String dispatcher, String endTime,
			String hungtimes) {
		this.eventName = eventName;
		this.acceptType = acceptType;
		this.hungTime = hungTime;
		this.hungReason = hungReason;
		this.dispatcher = dispatcher;
		this.endTime = endTime;
		this.hungtimes = hungtimes;
	}

}
