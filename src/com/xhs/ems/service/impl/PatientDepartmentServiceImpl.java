package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.PatientDepartmentDAO;
import com.xhs.ems.service.PatientDepartmentService;

/**
 * @datetime 2016年7月27日 下午1:01:16
 * @author 崔兴伟
 */
@Service
public class PatientDepartmentServiceImpl implements PatientDepartmentService {
	
	@Autowired
	private PatientDepartmentDAO patientDepartmentDAO;
	@Override
	public Grid getData(Parameter parameter) {
		return patientDepartmentDAO.getData(parameter);
	}

}
