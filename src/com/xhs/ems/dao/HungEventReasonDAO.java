package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午2:08:21
 */
public interface HungEventReasonDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午2:08:43
	 * @param parameter
	 * @return 挂起事件原因统计
	 */
	public Grid getData(Parameter parameter);
}
