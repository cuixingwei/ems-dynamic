package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午2:55:48
 * @category 司机工作情况统计
 */
public class DriverWork {
	/**
	 * 分站
	 */
	private String station;
	/**
	 * 司机
	 */
	private String driver;
	/**
	 * 出车数
	 */
	private String outCarNumbers;
	/**
	 * 有效出车数
	 */
	private String nomalNumbers;
	/**
	 * 中止数
	 */
	private String stopNumbers;
	/**
	 * 空车数
	 */
	private String emptyNumbers;
	/**
	 * 拒绝出车
	 */
	private String refuseNumbers;
	/**
	 * 暂停调用数
	 */
	private String pauseNumbers;
	/**
	 * 平均出车时间
	 */
	private String averageOutCarTimes;

	/**
	 * 平均到达时间
	 */
	private String averageArriveSpotTimes;

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getOutCarNumbers() {
		return outCarNumbers;
	}

	public void setOutCarNumbers(String outCarNumbers) {
		this.outCarNumbers = outCarNumbers;
	}

	public String getNomalNumbers() {
		return nomalNumbers;
	}

	public void setNomalNumbers(String nomalNumbers) {
		this.nomalNumbers = nomalNumbers;
	}

	public String getStopNumbers() {
		return stopNumbers;
	}

	public void setStopNumbers(String stopNumbers) {
		this.stopNumbers = stopNumbers;
	}

	public String getEmptyNumbers() {
		return emptyNumbers;
	}

	public void setEmptyNumbers(String emptyNumbers) {
		this.emptyNumbers = emptyNumbers;
	}

	public String getPauseNumbers() {
		return pauseNumbers;
	}

	public void setPauseNumbers(String pauseNumbers) {
		this.pauseNumbers = pauseNumbers;
	}

	public String getAverageOutCarTimes() {
		return averageOutCarTimes;
	}

	public void setAverageOutCarTimes(String averageOutCarTimes) {
		this.averageOutCarTimes = averageOutCarTimes;
	}

	public String getAverageArriveSpotTimes() {
		return averageArriveSpotTimes;
	}

	public void setAverageArriveSpotTimes(String averageArriveSpotTimes) {
		this.averageArriveSpotTimes = averageArriveSpotTimes;
	}

	public String getRefuseNumbers() {
		return refuseNumbers;
	}

	public void setRefuseNumbers(String refuseNumbers) {
		this.refuseNumbers = refuseNumbers;
	}

	public DriverWork() {
	}


}
