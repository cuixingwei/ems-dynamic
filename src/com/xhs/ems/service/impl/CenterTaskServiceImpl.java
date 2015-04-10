package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.CenterTaskDAO;
import com.xhs.ems.service.CenterTaskService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年3月31日 上午9:04:45
 */
@Service
public class CenterTaskServiceImpl implements CenterTaskService {
	@Autowired
	private CenterTaskDAO centerTaskDAO;
	/** 
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.service.CenterTaskService#getData(com.xhs.ems.bean.Parameter)
	 * @datetime 2015年3月31日 上午9:04:45
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return centerTaskDAO.getData(parameter);
	}

}
