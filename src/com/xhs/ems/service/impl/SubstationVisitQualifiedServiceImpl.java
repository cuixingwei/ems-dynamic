package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.SubstationVisitQualifiedDAO;
import com.xhs.ems.service.SubstationVisitQualifiedService;

/**
 * @datetime 2016年6月12日 下午6:25:03
 * @author 崔兴伟
 */
@Service
public class SubstationVisitQualifiedServiceImpl implements
		SubstationVisitQualifiedService {
	@Autowired
	private SubstationVisitQualifiedDAO substationVisitQualifiedDAO;

	@Override
	public Grid getData(Parameter parameter) {
		return substationVisitQualifiedDAO.getData(parameter);
	}

}
