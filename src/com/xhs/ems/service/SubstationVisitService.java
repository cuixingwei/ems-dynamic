package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 上午10:55:54
 */
public interface SubstationVisitService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月16日 上午10:53:58
	 * @param parameter
	 * @return 急救站出诊情况统计
	 */
	public Grid getData(Parameter parameter);
}
