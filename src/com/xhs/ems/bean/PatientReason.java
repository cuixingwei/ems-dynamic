package com.xhs.ems.bean;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:28:46
 * @category 疾病原因统计
 */
public class PatientReason {
	/**
	 * 病因名称
	 */
	private String patientReason;
	/**
	 * 接诊人数
	 */
	private String receivePeopleNumbers;
	/**
	 * 比率
	 */
	private String rate;

	public String getPatientReason() {
		return patientReason;
	}

	public void setPatientReason(String patientReason) {
		this.patientReason = patientReason;
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

	public PatientReason(String patientReason, String receivePeopleNumbers,
			String rate) {
		this.patientReason = patientReason;
		this.receivePeopleNumbers = receivePeopleNumbers;
		this.rate = rate;
	}

}
