package com.xhs.ems.bean;

/**
 * 用于电视显示
 * 
 * @datetime 2015年12月25日 下午4:20:58
 * @author 崔兴伟
 */
public class Show {
	private String type; // 1出车信息 2疾病信息
	private String keys;
	private String value;

	public Show() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
