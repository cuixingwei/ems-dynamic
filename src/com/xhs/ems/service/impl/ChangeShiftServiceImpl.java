package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.ChangeShift;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.ChangeShiftDAO;
import com.xhs.ems.service.ChangeShiftService;

/**
 * @author cuixingwei
 * @datetime 2016年10月11日下午4:26:29
 */
@Service
public class ChangeShiftServiceImpl implements ChangeShiftService {
	@Autowired
	private ChangeShiftDAO changeShiftDAO;
	/**
	 * @author cuixingwei
	 * @datetime 2016年10月11日下午4:26:29
	 */
	@Override
	public ChangeShift getData(Parameter parameter) {
		return changeShiftDAO.getData(parameter);
	}

}
