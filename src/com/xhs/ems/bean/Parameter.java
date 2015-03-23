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
	 * datagrid 参数
	 * 页码
	 */
	private int page;
	/**
	 * datagrid 参数
	 * 每页的行数
	 */
	private int rows;
	/**
	 * datagrid 参数
	 * 排序字段
	 */
	private String sort;
	/**
	 * datagrid 参数
	 * 排序类型desc , asc
	 */
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
	
	
}
