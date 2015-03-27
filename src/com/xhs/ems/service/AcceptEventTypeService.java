package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * 
 * @author CUIXINGWEI
 *
 */
public interface AcceptEventTypeService {
	/**
	 * @param parameter
	 * @return 事件类型统计
	 */
	public Grid getData(Parameter parameter);
}
