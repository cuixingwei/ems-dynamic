package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.DailySheetDAO;
import com.xhs.ems.service.DailySheetService;

@Service
public class DailySheetServiceImpl implements DailySheetService {
	@Autowired
	private DailySheetDAO dailySheetDAO;
	@Override
	public Grid getData(Parameter parameter) {
		return dailySheetDAO.getData(parameter);
	}

}
