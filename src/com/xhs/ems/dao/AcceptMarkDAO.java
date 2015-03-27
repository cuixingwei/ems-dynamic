package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

public interface AcceptMarkDAO {
	/**
	 * @author CUIXINGWEI
	 * @param parameter
	 * @return 受理备注数据
	 */
	public Grid getData(Parameter parameter);
}
