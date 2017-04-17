package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author cuixingwei
 * @datetime 2017年1月9日下午9:27:28
 */
public interface StationMsgDAO {
	/**
	 * 分站晚回单统计
	 * author cuixingwei
	 * datetime 2017年1月9日下午9:27:39
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
	
	/**
	 * 分站晚回单详情
	 * author cuixingwei
	 * datetime 2017年4月17日下午7:36:59
	 * @param parameter
	 * @return
	 */
	public Grid getStationMsgDetail(Parameter parameter);
}
