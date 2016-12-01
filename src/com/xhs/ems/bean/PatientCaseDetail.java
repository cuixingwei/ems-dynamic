package com.xhs.ems.bean;

/**
 * @datetime 2016年9月27日 下午12:08:03
 * @author 崔兴伟
 */
public class PatientCaseDetail {
	private String alarmTime; //接警时间
	private String alarmAddr; //接警地址
	private String arriveSpotTime; //到达现场时间
	private String patientName;//患者姓名
	private String age; //年龄
	private String sex; //性别
	private String judgementOnPhone; //初步判断
	private String pastIllness; //既往病史
	private String arriveHospitalTime; //到达医院时刻
	private String cureMeasure; //治疗措施
	private String sendHospital; //送往医院
	private String plateNo; //车牌号码
	
	public PatientCaseDetail() {
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getAlarmAddr() {
		return alarmAddr;
	}

	public void setAlarmAddr(String alarmAddr) {
		this.alarmAddr = alarmAddr;
	}

	public String getArriveSpotTime() {
		return arriveSpotTime;
	}

	public void setArriveSpotTime(String arriveSpotTime) {
		this.arriveSpotTime = arriveSpotTime;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getJudgementOnPhone() {
		return judgementOnPhone;
	}

	public void setJudgementOnPhone(String judgementOnPhone) {
		this.judgementOnPhone = judgementOnPhone;
	}

	public String getPastIllness() {
		return pastIllness;
	}

	public void setPastIllness(String pastIllness) {
		this.pastIllness = pastIllness;
	}

	public String getArriveHospitalTime() {
		return arriveHospitalTime;
	}

	public void setArriveHospitalTime(String arriveHospitalTime) {
		this.arriveHospitalTime = arriveHospitalTime;
	}

	public String getCureMeasure() {
		return cureMeasure;
	}

	public void setCureMeasure(String cureMeasure) {
		this.cureMeasure = cureMeasure;
	}

	public String getSendHospital() {
		return sendHospital;
	}

	public void setSendHospital(String sendHospital) {
		this.sendHospital = sendHospital;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	
	
}
