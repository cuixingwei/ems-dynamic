package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 下午2:24:41
 */
public interface EmptyCarReasonService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 下午2:25:01
	 * @param parameter
	 * @return 放空车原因统计
	 */
	public Grid getData(Parameter parameter);
}
