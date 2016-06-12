package com.xhs.ems.bean;

/**
 * @datetime 2016年6月12日 下午6:18:31
 * @author 崔兴伟
 */
public class SubstationVisitQualified {
	private String station;
	private String total; //总出诊
	private String normal; //正常出诊数
	private String late; //晚出诊数（2分钟）
	private String rate; //比率
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getNormal() {
		return normal;
	}
	public void setNormal(String normal) {
		this.normal = normal;
	}
	public String getLate() {
		return late;
	}
	public void setLate(String late) {
		this.late = late;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public SubstationVisitQualified() {
		
	}
	/**
	 * @param station
	 * @param total
	 * @param normal
	 * @param late
	 * @param rate
	 */
	public SubstationVisitQualified(String station, String total,
			String normal, String late, String rate) {
		this.station = station;
		this.total = total;
		this.normal = normal;
		this.late = late;
		this.rate = rate;
	}
	
	
}
