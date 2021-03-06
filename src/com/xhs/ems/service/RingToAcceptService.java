package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

public interface RingToAcceptService {
	/**
	 * 返回振铃到接听大于X秒的相关数据
	 * 
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);

}
