package com.xhs.ems.service;

import java.util.List;

import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.Show;

/**
 * @datetime 2015年12月25日 下午5:26:13
 * @author 崔兴伟
 */
public interface ShowService {
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
