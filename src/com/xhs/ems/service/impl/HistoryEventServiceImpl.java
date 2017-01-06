package com.xhs.ems.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.HistoryEvent;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.AcceptSendCarDAO;
import com.xhs.ems.dao.HistoryEventDAO;
import com.xhs.ems.service.HistoryEventService;

/**
 * @author cuixingwei
 * @datetime 2017年1月5日下午5:42:30
 */
@Service
public class HistoryEventServiceImpl implements HistoryEventService {
	@Autowired
	private HistoryEventDAO historyEventDAO;
	/**
	 * @author cuixingwei
	 * @datetime 2017年1月5日下午5:42:34
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return historyEventDAO.getData(parameter);
	}

	/**
	 * @author cuixingwei
	 * @datetime 2017年1月5日下午5:42:34
	 */
	@Override
	public List<HistoryEvent> getDetail(String eventCode, HttpServletRequest request) {
		return historyEventDAO.getDetail(eventCode, request);
	}

}
