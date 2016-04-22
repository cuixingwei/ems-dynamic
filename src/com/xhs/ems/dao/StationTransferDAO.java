package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @datetime 2016年4月19日 下午8:04:39
 * @author 崔兴伟
 */
public interface StationTransferDAO {
	/**
	 * 转院情况统计
	 * @datetime 2016年4月19日 下午8:04:55
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
