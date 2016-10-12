package com.xhs.ems.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.CenterAnserDailySheetDAO;
import com.xhs.ems.service.CenterAnserDailySheetService;

/**
 * @author cuixingwei
 * @datetime 2016年10月11日下午3:04:41
 */
@Service
public class CenterAnserDailySheetServiceImpl implements CenterAnserDailySheetService {
	@Autowired
	private CenterAnserDailySheetDAO centerAnserDailySheetDAO;
	/**
	 * @author cuixingwei
	 * @datetime 2016年10月11日下午3:04:41
	 */
	@Override
	public Grid getData(Parameter parameter) {
		return centerAnserDailySheetDAO.getData(parameter);
	}

}
