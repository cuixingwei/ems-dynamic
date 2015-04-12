package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.AcceptEventTypeDAO;
import com.xhs.ems.service.AcceptEventTypeService;

@Service
public class AcceptEventTypeServiceImpl implements AcceptEventTypeService {

	@Autowired
	private AcceptEventTypeDAO acceptEventTypeDAO;
	@Override
	public Grid getData(Parameter parameter) {
		return acceptEventTypeDAO.getData(parameter);
	}

}
