package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.DoctorNurseWorkDAO;
import com.xhs.ems.service.DoctorNurseWorkService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午4:44:07
 */
@Service
public class DoctorNurseWorkServiceImpl implements DoctorNurseWorkService {
	@Autowired
	private DoctorNurseWorkDAO doctorNurseWorkDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月22日 下午4:44:07
	 * @see com.xhs.ems.service.DoctorNurseWorkService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return doctorNurseWorkDAO.getData(parameter);
	}

}
