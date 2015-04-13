package com.xhs.ems.bean;

public class Parameter {
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
