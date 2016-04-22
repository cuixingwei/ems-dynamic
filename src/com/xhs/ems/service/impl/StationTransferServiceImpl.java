package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.StationTransferDAO;
import com.xhs.ems.service.StationTransferService;

/**
 * @datetime 2016年4月19日 下午8:06:09
 * @author 崔兴伟
 */
@Service
public class StationTransferServiceImpl implements StationTransferService {
	@Autowired
	private StationTransferDAO stationTransferDAO;
	@Override
	public Grid getData(Parameter parameter) {
		return stationTransferDAO.getData(parameter);
	}

}
