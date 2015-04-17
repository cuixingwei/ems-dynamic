package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:56:57
 * @category 疾病种类编码统计
 */
public class PatientType {
	/**
	 * 疾病种类
	 */
	private String patientClass;
	/**
	 * 接诊人数
	 */
	private String receivePeopleNumbers;
	/**
	 * 比率
	 */
	private String rate;

	public String getPatientClass() {
		return patientClass;
	}

	public void setPatientClass(String patientClass) {
		this.patientClass = patientClass;
	}

	public String getReceivePeopleNumbers() {
		return receivePeopleNumbers;
	}

	public void setReceivePeopleNumbers(String receivePeopleNumbers) {
		this.receivePeopleNumbers = receivePeopleNumbers;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public PatientType(String patientClass, String receivePeopleNumbers,
			String rate) {
		this.patientClass = patientClass;
		this.receivePeopleNumbers = receivePeopleNumbers;
		this.rate = rate;
	}

}
