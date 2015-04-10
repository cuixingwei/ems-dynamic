package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

public interface AccidentService {
	/**
	 * @author CUIXINGWEI
	 * @param parameter
	 * @return 重大事故
	 */
	public Grid getData(Parameter parameter);
}
