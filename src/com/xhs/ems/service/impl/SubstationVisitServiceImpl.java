package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.SubstationVisitDAO;
import com.xhs.ems.service.SubstationVisitService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 上午10:56:38
 */
@Service
public class SubstationVisitServiceImpl implements SubstationVisitService {
	@Autowired
	private SubstationVisitDAO substationVisitDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月16日 上午10:56:50
	 * @see com.xhs.ems.service.SubstationVisitService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return substationVisitDAO.getData(parameter);
	}

}
