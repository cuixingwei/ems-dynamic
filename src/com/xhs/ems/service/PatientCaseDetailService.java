package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @datetime 2016年9月27日 下午12:15:30
 * @author 崔兴伟
 */
public interface PatientCaseDetailService {
	/**
	 * 院前病历一览表
	 * @datetime 2016年9月27日 下午12:15:58
	 * @author 崔兴伟
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
