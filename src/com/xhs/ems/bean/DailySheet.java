package com.xhs.ems.bean;
/**
 * @author cuixingwei
 * @datetime 2016年10月11日上午11:04:25
 */
public class DailySheet {
	public String hospital;//医院
	public String inHosCounts; //入院人数
	public String spotCure; //现场救治(事件)
	public String emptyTask; //空车任务数
	public String robCure; //抢诊
	public String byRobCure; //被抢诊
	public String transferCure; //转诊
	public String specialTask; //特殊事件
	public String hungOn; //无车(挂起)
	public String refuseTransferCount; //拒绝转送病人数
	public String refuseNoOwnerCount; //拒接无主病人数
	public String totalSendCar; //总派车数
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
	public String getRobCure() {
		return robCure;
	}
	public void setRobCure(String robCure) {
		this.robCure = robCure;
	}
	public String getByRobCure() {
		return byRobCure;
	}
	public void setByRobCure(String byRobCure) {
		this.byRobCure = byRobCure;
	}
	public String getTransferCure() {
		return transferCure;
	}
	public void setTransferCure(String transferCure) {
		this.transferCure = transferCure;
	}
	public String getSpecialTask() {
		return specialTask;
	}
	public void setSpecialTask(String specialTask) {
		this.specialTask = specialTask;
	}
	public String getHungOn() {
		return hungOn;
	}
	public void setHungOn(String hungOn) {
		this.hungOn = hungOn;
	}
	public String getRefuseTransferCount() {
		return refuseTransferCount;
	}
	public void setRefuseTransferCount(String refuseTransferCount) {
		this.refuseTransferCount = refuseTransferCount;
	}
	public String getRefuseNoOwnerCount() {
		return refuseNoOwnerCount;
	}
	public void setRefuseNoOwnerCount(String refuseNoOwnerCount) {
		this.refuseNoOwnerCount = refuseNoOwnerCount;
	}
	public String getTotalSendCar() {
		return totalSendCar;
	}
	public void setTotalSendCar(String totalSendCar) {
		this.totalSendCar = totalSendCar;
	}
	
	public DailySheet() {
		
	}
	public DailySheet(String hospital, String inHosCounts, String spotCure, String emptyTask, String robCure,
			String byRobCure, String transferCure, String specialTask, String hungOn, String refuseTransferCount,
			String refuseNoOwnerCount, String totalSendCar) {
		this.hospital = hospital;
		this.inHosCounts = inHosCounts;
		this.spotCure = spotCure;
		this.emptyTask = emptyTask;
		this.robCure = robCure;
		this.byRobCure = byRobCure;
		this.transferCure = transferCure;
		this.specialTask = specialTask;
		this.hungOn = hungOn;
		this.refuseTransferCount = refuseTransferCount;
		this.refuseNoOwnerCount = refuseNoOwnerCount;
		this.totalSendCar = totalSendCar;
	}
	
	
	
}
