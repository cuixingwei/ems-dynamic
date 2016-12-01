package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @datetime 2016年7月27日 下午1:00:25
 * @author 崔兴伟
 */
public interface PatientDepartmentService {
	/**
	 * 疾病科别统计
	 * @datetime 2016年7月27日 下午12:59:51
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
