package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

public interface AcceptTimeDAO {
	/**
	 * @author CUIXINGWEI
	 * @param parameter
	 * @return 受理时间查询
	 */
	public Grid getData(Parameter parameter);
}
