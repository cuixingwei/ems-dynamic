package com.xhs.ems.bean;

import java.util.List;

/**
 * 换班
 * 
 * @author cuixingwei
 * @datetime 2016年10月11日下午5:33:59
 */
public class ChangeShift {
	public List<DailySheet> day1;// 早班
	public List<DailySheet> day2;// 下午班
	public List<DailySheet> day3;// 夜班

	public List<DailySheet> getDay1() {
		return day1;
	}

	public void setDay1(List<DailySheet> day1) {
		this.day1 = day1;
	}

	public List<DailySheet> getDay2() {
		return day2;
	}

	public void setDay2(List<DailySheet> day2) {
		this.day2 = day2;
	}

	public List<DailySheet> getDay3() {
		return day3;
	}

	public void setDay3(List<DailySheet> day3) {
		this.day3 = day3;
	}

	public ChangeShift() {
	}

}
