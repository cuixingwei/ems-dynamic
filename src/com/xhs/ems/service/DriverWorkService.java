package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午3:59:29
 */
public interface DriverWorkService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午3:59:02
	 * @param parameter
	 * @return 司机工作情况统计
	 */
	public Grid getData(Parameter parameter);
}
