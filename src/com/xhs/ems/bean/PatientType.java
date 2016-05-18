package com.xhs.ems.bean;

/**
 * @datetime 2016年5月18日 下午6:29:36
 * @author 崔兴伟
 */
public class PatientType {
	private String patientType;
	private String numbers;
	private String radio;
	public String getPatientType() {
		return patientType;
	}
	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}
	public String getNumbers() {
		return numbers;
	}
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
	public String getRadio() {
		return radio;
	}
	public void setRadio(String radio) {
		this.radio = radio;
	}
	/**
	 * @param patientType
	 * @param numbers
	 * @param radio
	 */
	public PatientType(String patientType, String numbers, String radio) {
		this.patientType = patientType;
		this.numbers = numbers;
		this.radio = radio;
	}
	public PatientType() {
		
	}
	
	
}
