package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author CUIXINGWEI
 * @datetime 2015年3月31日 上午9:02:53
 */
public interface CenterTaskService {
	/**
	 *@datetime 2015年3月31日 上午9:03:55
	 *@author CUIXINGWEI
	 *@param parameter
	 *@return 中心任务信息统计
	 */
	public Grid getData(Parameter parameter);
}
