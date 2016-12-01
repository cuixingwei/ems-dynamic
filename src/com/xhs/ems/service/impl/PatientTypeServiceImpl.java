package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.PatientTypeDAO;
import com.xhs.ems.service.PatientTypeService;

/**
 * @datetime 2016年5月18日 下午6:33:23
 * @author 崔兴伟
 */
@Service
public class PatientTypeServiceImpl implements PatientTypeService {
	@Autowired
	private PatientTypeDAO patientTypeDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return patientTypeDAO.getData(parameter);
	}

}
