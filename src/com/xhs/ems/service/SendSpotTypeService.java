package com.xhs.ems.service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午3:46:22
 */
public interface SendSpotTypeService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午3:45:49
	 * @param parameter
	 * @return 送往地点类型统计
	 */
	public Grid getData(Parameter parameter);
	/**
	 * @datetime 2016年6月12日 下午4:34:48
	 * @author 崔兴伟
	 * @param parameter
	 * @return 送往地点类型统计
	 */
	public Grid getSendSpotDatas(Parameter parameter);
	
}
