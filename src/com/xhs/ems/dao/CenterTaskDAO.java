package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author CUIXINGWEI
 * @datetime 2015年3月31日 上午9:01:08
 */
public interface CenterTaskDAO {
	/**
	 * @author CUIXINGWEI
	 * @param parameter
	 * @return 中心任务信息统计
	 */
	public Grid getData(Parameter parameter);
}
