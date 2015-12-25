package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.Show;

/**
 * @datetime 2015年12月25日 下午5:24:49
 * @author 崔兴伟
 */
public interface ShowDAO {
	/**
	 * 获取电视上显示的数据
	 * 
	 * @datetime 2015年12月25日 下午5:25:45
	 * @author 崔兴伟
	 * @param parameter
	 * @return
	 */
	public List<Show> getShow(Parameter parameter);
}
