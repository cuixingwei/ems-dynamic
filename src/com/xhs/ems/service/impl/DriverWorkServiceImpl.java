package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.DriverWorkDAO;
import com.xhs.ems.service.DriverWorkService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:00:06
 */
@Service
public class DriverWorkServiceImpl implements DriverWorkService {
	@Autowired
	private DriverWorkDAO driverWorkDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午4:00:06
	 * @see com.xhs.ems.service.DriverWorkService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return driverWorkDAO.getData(parameter);
	}

}
