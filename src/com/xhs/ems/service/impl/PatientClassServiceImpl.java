package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.PatientClassDAO;
import com.xhs.ems.service.PatientClassSerivce;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午5:00:00
 */
@Service
public class PatientClassServiceImpl implements PatientClassSerivce {

	@Autowired
	private PatientClassDAO patientClassDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午5:00:00
	 * @see com.xhs.ems.service.PatientClassSerivce#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return patientClassDAO.getData(parameter);
	}

}
