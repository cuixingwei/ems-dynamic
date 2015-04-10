package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

public interface AccidentDAO {
	/**
	 * @author CUIXINGWEI
	 * @param parameter
	 * @return 重大事故
	 */
	public Grid getData(Parameter parameter);
}
