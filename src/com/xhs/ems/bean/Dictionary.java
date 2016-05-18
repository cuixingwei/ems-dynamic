package com.xhs.ems.bean;

/**
 * 字典表类
 * 
 * @datetime 2016年5月18日 下午4:55:40
 * @author 崔兴伟
 */
public class Dictionary {
	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param code
	 * @param name
	 */
	public Dictionary(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public Dictionary() {
	}

}
