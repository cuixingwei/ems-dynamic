package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:58:38
 */
public interface PatientClassDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午4:58:51
	 * @param parameter
	 * @return 疾病种类统计
	 */
	public Grid getData(Parameter parameter);
}
