package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @datetime 2016年7月27日 下午12:59:27
 * @author 崔兴伟
 */
public interface PatientDepartmentDAO {
	/**
	 * 疾病科别统计
	 * @datetime 2016年7月27日 下午12:59:51
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
