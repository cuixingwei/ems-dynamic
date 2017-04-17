package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.StationMsgDAO;
import com.xhs.ems.service.StationMsgService;

/**
 * @author cuixingwei
 * @datetime 2017年1月9日下午9:28:57
 */
@Service
public class StationMsgServiceImpl implements StationMsgService {
	@Autowired
	private StationMsgDAO stationMsgDAO;

	/**
	 * @author cuixingwei
	 * @datetime 2017年1月9日下午9:29:16
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return stationMsgDAO.getData(parameter);
	}

	/**
	 * @author cuixingwei
	 * @datetime 2017年4月17日下午7:37:45
	 */
	@Override
	public Grid getStationMsgDetail(Parameter parameter) {
		return stationMsgDAO.getStationMsgDetail(parameter);
	}
}
