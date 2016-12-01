package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.PatientCaseDetailDAO;
import com.xhs.ems.service.PatientCaseDetailService;

/**
 * @datetime 2016年9月27日 下午12:38:20
 * @author 崔兴伟
 */
@Service
public class PatientCaseDetailServiceImpl implements PatientCaseDetailService {
	
	@Autowired
	private PatientCaseDetailDAO patientCaseDetailDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return patientCaseDetailDAO.getData(parameter);
	}

}
