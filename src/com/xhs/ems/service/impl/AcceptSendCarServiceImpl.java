package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.AcceptSendCarDAO;
import com.xhs.ems.service.AcceptSendCarService;

/**
 * @author 崔兴伟
 * @datetime 2015年5月7日 下午3:08:07
 */
@Service
public class AcceptSendCarServiceImpl implements AcceptSendCarService {
	@Autowired
	private AcceptSendCarDAO acceptSendCarDAO;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月7日 下午3:08:07
	 * @see com.xhs.ems.service.AcceptSendCarService#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return acceptSendCarDAO.getData(parameter);
	}

}
