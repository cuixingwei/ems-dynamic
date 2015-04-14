package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午8:56:09
 */
public interface EmptyCarService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 上午8:56:28
	 * @param parameter
	 * @return 放空车统计
	 */
	public Grid getData(Parameter parameter);
}
