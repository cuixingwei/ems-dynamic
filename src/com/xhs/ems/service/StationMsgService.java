package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author cuixingwei
 * @datetime 2017年1月9日下午9:28:08
 */
public interface StationMsgService {
	/**
	 * 分站晚回单统计
	 * author cuixingwei
	 * datetime 2017年1月9日下午9:27:39
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
