package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.PatientTypeDAO;
import com.xhs.ems.service.PatientTypeSerivce;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午5:00:00
 */
@Service
public class PatientTypeServiceImpl implements PatientTypeSerivce {

	@Autowired
	private PatientTypeDAO patientTypeDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午5:00:00
	 * @see com.xhs.ems.service.PatientTypeSerivce#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return patientTypeDAO.getData(parameter);
	}

}
