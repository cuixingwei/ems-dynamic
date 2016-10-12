package com.xhs.ems.service;

import com.xhs.ems.bean.ChangeShift;
import com.xhs.ems.bean.Parameter;

/**
 * @author cuixingwei
 * @datetime 2016年10月11日下午4:25:18
 */
public interface ChangeShiftService {
	/**
	 * 交接班
	 * author cuixingwei
	 * datetime 2016年10月11日下午4:24:10
	 * @param parameter
	 * @return
	 */
	public ChangeShift getData(Parameter parameter);
}
