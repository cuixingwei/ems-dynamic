package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.PatientReasonDAO;
import com.xhs.ems.service.PatientReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:40:12
 */
@Service
public class PatientReasonServiceImpl implements PatientReasonService {
	@Autowired
	private PatientReasonDAO patientReasonDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午4:40:12
	 * @see com.xhs.ems.service.PatientReasonService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return patientReasonDAO.getData(parameter);
	}

}
