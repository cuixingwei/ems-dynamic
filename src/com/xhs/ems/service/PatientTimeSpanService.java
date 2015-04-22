package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 上午9:51:55
 */
public interface PatientTimeSpanService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月22日 上午9:51:26
	 * @param parameter
	 * @return 病发时间段统计
	 */
	public Grid getData(Parameter parameter);
}
