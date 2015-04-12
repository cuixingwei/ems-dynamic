package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.AccidentDAO;
import com.xhs.ems.service.AccidentService;

@Service
public class AccidentServiceImpl implements AccidentService {
	@Autowired
	private AccidentDAO accidentDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return accidentDAO.getData(parameter);
	}

}
