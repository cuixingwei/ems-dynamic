package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午4:41:52
 * @category 医生护士工作情况统计
 */
public class DoctorNurseWork {
	private String station;
	private String name;
	/**
	 * 出车数
	 */
	private String outCarNumbers;
	/**
	 * 有效出车数
	 */
	private String validOutCarNumbers;
	/**
	 * 中止数(中止、空车)
	 */
	private String stopNumbers;
	/**
	 * 拒绝出车数
	 */
	private String refuseNumbers;
	/**
	 * 救治人数
	 */
	private String curePeopleNumbers;
	/**
	 * 平均救治时间
	 */
	private String averateCureTimes;
	

	public String getRefuseNumbers() {
		return refuseNumbers;
	}

	public void setRefuseNumbers(String refuseNumbers) {
		this.refuseNumbers = refuseNumbers;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOutCarNumbers() {
		return outCarNumbers;
	}

	public void setOutCarNumbers(String outCarNumbers) {
		this.outCarNumbers = outCarNumbers;
	}

	public String getValidOutCarNumbers() {
		return validOutCarNumbers;
	}

	public void setValidOutCarNumbers(String validOutCarNumbers) {
		this.validOutCarNumbers = validOutCarNumbers;
	}

	public String getStopNumbers() {
		return stopNumbers;
	}

	public void setStopNumbers(String stopNumbers) {
		this.stopNumbers = stopNumbers;
	}

	public String getCurePeopleNumbers() {
		return curePeopleNumbers;
	}

	public void setCurePeopleNumbers(String curePeopleNumbers) {
		this.curePeopleNumbers = curePeopleNumbers;
	}

	public String getAverateCureTimes() {
		return averateCureTimes;
	}

	public void setAverateCureTimes(String averateCureTimes) {
		this.averateCureTimes = averateCureTimes;
	}

	public DoctorNurseWork(String station, String name, String outCarNumbers, String validOutCarNumbers,
			String stopNumbers, String refuseNumbers, String curePeopleNumbers, String averateCureTimes) {
		this.station = station;
		this.name = name;
		this.outCarNumbers = outCarNumbers;
		this.validOutCarNumbers = validOutCarNumbers;
		this.stopNumbers = stopNumbers;
		this.refuseNumbers = refuseNumbers;
		this.curePeopleNumbers = curePeopleNumbers;
		this.averateCureTimes = averateCureTimes;
	}

	public DoctorNurseWork() {
		
	}


}
