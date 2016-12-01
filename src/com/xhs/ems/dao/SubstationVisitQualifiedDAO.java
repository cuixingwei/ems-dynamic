package com.xhs.ems.dao;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;

/**
 * @datetime 2016年6月12日 下午6:20:11
 * @author 崔兴伟
 */
public interface SubstationVisitQualifiedDAO {
	/**
	 * @datetime 2016年6月12日 下午6:23:55
	 * @author 崔兴伟
	 * @param parameter
	 * @return 分站出诊合格率统计
	 */
	public Grid getData(Parameter parameter);
}
