package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午3:58:35
 */
public interface DriverWorkDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午3:59:02
	 * @param parameter
	 * @return 司机工作情况统计
	 */
	public Grid getData(Parameter parameter);
}
