/**
 * @author cuixingwei
 * @datetime 2016年10月11日下午3:02:33
 */
package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author cuixingwei
 * @datetime 2016年10月11日下午3:02:33
 */
public interface CenterAnserDailySheetDAO {
	/**
	 * 中心接警统计日报表
	 * author cuixingwei
	 * datetime 2016年10月11日下午3:02:53
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
