package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午8:18:26
 */
public interface AnswerAlarmDAO {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午8:19:02
	 * @param parameter
	 * @return 中心接警情况统计
	 */
	public Grid getData(Parameter parameter);

	/**
	 * 通话记录表
	 * 
	 * @datetime 2015年11月26日 下午8:55:40
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public Grid getPhoneRecord(Parameter parameter);
}
