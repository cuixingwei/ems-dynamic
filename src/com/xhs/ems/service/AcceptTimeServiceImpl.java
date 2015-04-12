package com.xhs.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.AcceptTimeDAO;

@Service
public class AcceptTimeServiceImpl implements AcceptTimeService {
	
	@Autowired
	private AcceptTimeDAO acceptTimeDAO;
	@Override
	public Grid getData(Parameter parameter) {
		return acceptTimeDAO.getData(parameter);
	}

}
