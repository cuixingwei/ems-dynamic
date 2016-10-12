/**
 * @author cuixingwei
 * @datetime 2016年10月11日下午3:04:02
 */
package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author cuixingwei
 * @datetime 2016年10月11日下午3:04:02
 */
public interface CenterAnserDailySheetService {
	/**
	 * 中心接警统计日报表
	 * author cuixingwei
	 * datetime 2016年10月11日下午3:02:53
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
}
