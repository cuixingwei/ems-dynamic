package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日  下午2:52:09
 */
public interface StopTaskReasonDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日  下午2:52:56
	 * @param parameter
	 * @return 终止任务原因查询
	 */
	public Grid getData(Parameter parameter);
}
