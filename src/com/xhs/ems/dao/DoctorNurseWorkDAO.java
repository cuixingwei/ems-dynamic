package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日  下午4:42:00
 */
public interface DoctorNurseWorkDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月22日  下午4:42:16
	 * @param parameter
	 * @return 医生护士工作统计
	 */
	public Grid getData(Parameter parameter);
}
