/**
 * @author cuixingwei
 * @datetime 2016年10月11日下午2:55:41
 */
package com.xhs.ems.bean;

/**
 * 中心接警日报表
 * 
 * @author cuixingwei
 * @datetime 2016年10月11日下午2:55:41
 */
public class CenterAnserDailySheet {
	public String hospital;// 医院
	public String inHosCounts; // 入院人数
	public String spotCure; // 现场救治人数
	public String emptyTask; // 空车任务数
	public String byRobCure; // 被抢诊
	public String totalSendCount; // 总计人数

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getInHosCounts() {
		return inHosCounts;
	}

	public void setInHosCounts(String inHosCounts) {
		this.inHosCounts = inHosCounts;
	}

	public String getSpotCure() {
		return spotCure;
	}

	public void setSpotCure(String spotCure) {
		this.spotCure = spotCure;
	}

	public String getEmptyTask() {
		return emptyTask;
	}

	public void setEmptyTask(String emptyTask) {
		this.emptyTask = emptyTask;
	}

	public String getByRobCure() {
		return byRobCure;
	}

	public void setByRobCure(String byRobCure) {
		this.byRobCure = byRobCure;
	}

	public String getTotalSendCount() {
		return totalSendCount;
	}

	public void setTotalSendCount(String totalSendCount) {
		this.totalSendCount = totalSendCount;
	}

	public CenterAnserDailySheet(String hospital, String inHosCounts, String spotCure, String emptyTask,
			String byRobCure, String totalSendCount) {
		this.hospital = hospital;
		this.inHosCounts = inHosCounts;
		this.spotCure = spotCure;
		this.emptyTask = emptyTask;
		this.byRobCure = byRobCure;
		this.totalSendCount = totalSendCount;
	}

	public CenterAnserDailySheet() {
	}

}
