package com.xhs.ems.bean;

/**
 * 分站回单超时情况
 * @author cuixingwei
 * @datetime 2017年1月9日下午9:24:20
 */
public class StationMsg {
	private String station;
	private String totalCount; //总数
	private String normalReturn; //正常回单数
	private String lateReturn; //晚回单数
	private String noReturn;//未回单
	private String eventName;//事件名称 
	private String dispatcher;//中心调度员 
	private String stationDispatcher;//分站调度员
	private String times;//回单时间
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getNormalReturn() {
		return normalReturn;
	}
	public void setNormalReturn(String normalReturn) {
		this.normalReturn = normalReturn;
	}
	public String getLateReturn() {
		return lateReturn;
	}
	public void setLateReturn(String lateReturn) {
		this.lateReturn = lateReturn;
	}
	
	public String getNoReturn() {
		return noReturn;
	}
	public void setNoReturn(String noReturn) {
		this.noReturn = noReturn;
	}
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}
	public String getStationDispatcher() {
		return stationDispatcher;
	}
	public void setStationDispatcher(String stationDispatcher) {
		this.stationDispatcher = stationDispatcher;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public StationMsg() {
		
	}
	
	
}
