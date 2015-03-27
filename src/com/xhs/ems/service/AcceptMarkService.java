package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * 
 * @author CUIXINGWEI
 *
 */
public interface AcceptMarkService {
	/**
	 * @param parameter
	 * @return 受理备注数据
	 */
	public Grid getData(Parameter parameter);
}
