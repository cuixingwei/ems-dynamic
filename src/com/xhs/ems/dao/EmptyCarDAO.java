package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午8:54:10
 */
public interface EmptyCarDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 上午8:54:33
	 * @param parameter
	 * @return 放空车统计
	 */
	public Grid getData(Parameter parameter);
}
