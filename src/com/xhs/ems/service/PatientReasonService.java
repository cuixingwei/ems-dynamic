package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:39:41
 */
public interface PatientReasonService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午4:39:18
	 * @param parameter
	 * @return 疾病原因统计
	 */
	public Grid getData(Parameter parameter);
}
