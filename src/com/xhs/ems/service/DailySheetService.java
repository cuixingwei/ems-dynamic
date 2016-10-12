package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

public interface DailySheetService {
	/**
	 * author cuixingwei
	 * datetime 2016年10月11日上午11:35:41
	 * @param parameter
	 * @return 日报表
	 */
	public Grid getData(Parameter parameter);
}
