package com.xhs.ems.bean;

public class Parameter {
	private String eventCode; //事件编码
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 响铃到接听大于X秒（超时时长）
	 */
	private String overtimes;
	/**
	 * 调试员
	 */
	private String dispatcher;
	/**
	 * datagrid 参数 页码
	 */
	private int page;
	/**
	 * datagrid 参数 每页的行数
	 */
	private int rows;
	/**
	 * datagrid 参数 排序字段
	 */
	private String sort;
	/**
	 * datagrid 参数 排序类型desc , asc
	 */
	/**
	 * 通讯录查询（姓名）
	 */
	private String name;
	/**
	 * 通讯录查询（电话）
	 */
	private String phone;
	/**
	 * 中心任务信息统计（分站）
	 */
	private String station;
	/**
	 * 中止任务信息（中止任务原因编码）
	 */
	private String stopReason;
	/**
	 * 中止任务信息（车辆编码）
	 */
	private String carCode;
	/**
	 * 中止任务信息（空跑时间）
	 */
	private String emptyRunTime;
	/**
	 * 车辆暂停调用情况(暂停调用原因)
	 */
	private String pauseReason;
	/**
	 * 车辆状态变化（事件名称）
	 */
	private String eventName;
	/**
	 * 放空车统计（空车原因）
	 */
	private String emptyReason;
	/**
	 * 挂起事件流水统计(挂起原因)
	 */
	private String hungReason;
	/**
	 * 中心接警情况统计(报警电话)
	 */
	private String alarmPhone;
	/**
	 * 中心接警情况统计(报警地点)
	 */
	private String siteAddress;
	/**
	 * 急救站晚出诊统计(出诊时长最小值)
	 */
	private String outCarTimesMin;
	/**
	 * 急救站晚出诊统计(出诊时长最大值)
	 */
	private String outCarTimesMax;
	/**
	 * 医生护士工作统计（1代表医生，2代表护士）
	 */
	private String doctorOrNurse;
	/**
	 * 事件详情查询（受理表的ID）
	 */
	private String id;
	/**
	 * 疾病分类统计编码
	 */
	private String patientClassCode;
	/**
	 * 疾病类型统计
	 */
	private String patientTypeCode;
	/**
	 * 医生诊断
	 */
	private String doctorDiagnosis;
	/**
	 * 疾病科别
	 */
	private String illDepartment;
	/**
	 * 疾病分类
	 */
	private String illClass;
	/**
	 * 患者姓名
	 */
	private String patientName;
	/**
	 * 病情
	 */
	private String illState;
	/**
	 * 救治结果
	 */
	private String aidResult;
	/**
	 * 死亡证明
	 */
	private String deathProof;
	/**
	 * 送往地点类型
	 */
	private String sendAddrType;
	/**
	 * 现场地点类型
	 */
	private String spotAddrType;
	/**
	 * 疾病原因
	 */
	private String illReason;
	/**
	 * 车辆标识
	 */
	private String carPlate;
	/**
	 * 转归编码
	 */
	private String outCome;
	/**
	 * 病家合作
	 */
	private String patientCooperation;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 身份
	 */
	private String identity;
	/**
	 * 职业
	 */
	private String profession;
	/**
	 * 医生
	 */
	private String doctor;
	/**
	 * 护士
	 */
	private String nurse;
	/**
	 * 司机
	 */
	private String driver;
	
	
	

	
	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getDoctorDiagnosis() {
		return doctorDiagnosis;
	}

	public void setDoctorDiagnosis(String doctorDiagnosis) {
		this.doctorDiagnosis = doctorDiagnosis;
	}

	public String getIllDepartment() {
		return illDepartment;
	}

	public void setIllDepartment(String illDepartment) {
		this.illDepartment = illDepartment;
	}

	public String getIllClass() {
		return illClass;
	}

	public void setIllClass(String illClass) {
		this.illClass = illClass;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getIllState() {
		return illState;
	}

	public void setIllState(String illState) {
		this.illState = illState;
	}

	public String getAidResult() {
		return aidResult;
	}

	public void setAidResult(String aidResult) {
		this.aidResult = aidResult;
	}

	public String getDeathProof() {
		return deathProof;
	}

	public void setDeathProof(String deathProof) {
		this.deathProof = deathProof;
	}

	public String getSendAddrType() {
		return sendAddrType;
	}

	public void setSendAddrType(String sendAddrType) {
		this.sendAddrType = sendAddrType;
	}

	public String getSpotAddrType() {
		return spotAddrType;
	}

	public void setSpotAddrType(String spotAddrType) {
		this.spotAddrType = spotAddrType;
	}

	public String getIllReason() {
		return illReason;
	}

	public void setIllReason(String illReason) {
		this.illReason = illReason;
	}

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public String getOutCome() {
		return outCome;
	}

	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}

	public String getPatientCooperation() {
		return patientCooperation;
	}

	public void setPatientCooperation(String patientCooperation) {
		this.patientCooperation = patientCooperation;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getNurse() {
		return nurse;
	}

	public void setNurse(String nurse) {
		this.nurse = nurse;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getPatientTypeCode() {
		return patientTypeCode;
	}

	public void setPatientTypeCode(String patientTypeCode) {
		this.patientTypeCode = patientTypeCode;
	}

	public String getPatientClassCode() {
		return patientClassCode;
	}

	public void setPatientClassCode(String patientClassCode) {
		this.patientClassCode = patientClassCode;
	}

	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getDoctorOrNurse() {
		return doctorOrNurse;
	}

	public void setDoctorOrNurse(String doctorOrNurse) {
		this.doctorOrNurse = doctorOrNurse;
	}

	public String getOutCarTimesMin() {
		return outCarTimesMin;
	}

	public void setOutCarTimesMin(String outCarTimesMin) {
		this.outCarTimesMin = outCarTimesMin;
	}

	public String getOutCarTimesMax() {
		return outCarTimesMax;
	}

	public void setOutCarTimesMax(String outCarTimesMax) {
		this.outCarTimesMax = outCarTimesMax;
	}

	public String getAlarmPhone() {
		return alarmPhone;
	}

	public void setAlarmPhone(String alarmPhone) {
		this.alarmPhone = alarmPhone;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public String getHungReason() {
		return hungReason;
	}

	public void setHungReason(String hungReason) {
		this.hungReason = hungReason;
	}

	public String getEmptyReason() {
		return emptyReason;
	}

	public void setEmptyReason(String emptyReason) {
		this.emptyReason = emptyReason;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getPauseReason() {
		return pauseReason;
	}

	public void setPauseReason(String pauseReason) {
		this.pauseReason = pauseReason;
	}

	public String getEmptyRunTime() {
		return emptyRunTime;
	}

	public void setEmptyRunTime(String emptyRunTime) {
		this.emptyRunTime = emptyRunTime;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private String order;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOvertimes() {
		return overtimes;
	}

	public void setOvertimes(String overtimes) {
		this.overtimes = overtimes;
	}

	public String getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

}
