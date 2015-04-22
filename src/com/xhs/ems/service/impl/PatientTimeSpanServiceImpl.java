package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.PatientTimeSpanDAO;
import com.xhs.ems.service.PatientTimeSpanService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 上午9:52:32
 */
@Service
public class PatientTimeSpanServiceImpl implements PatientTimeSpanService {
	@Autowired
	private PatientTimeSpanDAO patientTimeSpanDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月22日 上午9:52:32
	 * @see com.xhs.ems.service.PatientTimeSpanService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return patientTimeSpanDAO.getData(parameter);
	}

}
