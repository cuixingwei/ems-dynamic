package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @datetime 2016年5月18日 下午6:32:43
 * @author 崔兴伟
 */
public interface PatientTypeService {
	/**
	 * 疾病类型统计
	 * @datetime 2016年5月18日 下午6:32:08
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
