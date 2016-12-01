package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @datetime 2016年9月27日 下午12:16:26
 * @author 崔兴伟
 */
public interface PatientCaseDetailDAO {
	/**
	 * 院前病历一览表
	 * @datetime 2016年9月27日 下午12:15:58
	 * @author 崔兴伟
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
