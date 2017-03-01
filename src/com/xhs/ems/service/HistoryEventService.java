package com.xhs.ems.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.HistoryEvent;
import com.xhs.ems.bean.Parameter;

/**
 * @author cuixingwei
 * @datetime 2017年1月5日下午5:37:38
 */
public interface HistoryEventService {
	/**
	 * 获取事件列表
	 * author cuixingwei
	 * datetime 2017年1月5日下午5:38:28
	 * @param parameter
	 * @return
	 */
	public Grid getData(Parameter parameter);
	/**
	 * 获取事件详情
	 * author cuixingwei
	 * datetime 2017年1月5日下午5:40:08
	 * @param eventCode
	 * @param request
	 * @return
	 */
	public List<HistoryEvent> getDetail(String eventCode, HttpServletRequest request);
	
}
