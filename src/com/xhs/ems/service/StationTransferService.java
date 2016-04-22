package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @datetime 2016年4月19日 下午8:05:22
 * @author 崔兴伟
 */
public interface StationTransferService {
	/**
	 * 转院情况统计
	 * @datetime 2016年4月19日 下午8:04:55
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
