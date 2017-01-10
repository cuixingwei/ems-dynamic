package com.xhs.ems.bean;

/**
 * 分站回单超时情况
 * @author cuixingwei
 * @datetime 2017年1月9日下午9:24:20
 */
public class StationMsg {
	public String station;
	public String totalCount; //总数
	public String normalReturn; //正常回单数
	public String lateReturn; //晚回单数
	public String noReturn;//未回单
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
	public StationMsg() {
		
	}
	
	
}
