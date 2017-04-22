package com.xhs.ems.bean;

/**
 * @author CUIXINGWEI
 * @datetime 2015年3月30日 下午5:12:23
 */
public class Station {
	/**
	 * 分站编码
	 */
	private String stationCode;
	/**
	 * 分站名称
	 */
	private String stationName;
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public Station(String stationCode, String stationName) {
		this.stationCode = stationCode;
		this.stationName = stationName;
	}
	
}
