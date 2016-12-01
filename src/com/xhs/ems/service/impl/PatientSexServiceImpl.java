package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.PatientSex;
import com.xhs.ems.dao.PatientSexDAO;
import com.xhs.ems.service.PatientSexService;

/**
 * @datetime 2016年5月18日 下午5:26:50
 * @author 崔兴伟
 */
@Service
public class PatientSexServiceImpl implements PatientSexService {
	@Autowired
	private PatientSexDAO patientSexDAO;

	@Override
	public List<PatientSex> getData(Parameter parameter) {
		return patientSexDAO.getData(parameter);
	}

}
