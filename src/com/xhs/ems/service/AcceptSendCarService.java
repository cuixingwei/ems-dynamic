package com.xhs.ems.service;

import javax.servlet.http.HttpServletRequest;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.HistoryEvent;
import com.xhs.ems.bean.Parameter;

/**
 * @author 崔兴伟
 * @datetime 2015年5月7日 下午3:07:20
 */
public interface AcceptSendCarService {
	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月7日 下午3:06:29
	 * @param parameter
	 * @return 开始受理到派车大于X秒
	 */
	public Grid getData(Parameter parameter);

	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月9日 上午11:33:22
	 * @param id request
	 * @return 事件详情
	 */
	public HistoryEvent getDetail(String id, HttpServletRequest request);
}
