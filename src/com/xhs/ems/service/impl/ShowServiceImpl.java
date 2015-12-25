package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.Show;
import com.xhs.ems.dao.ShowDAO;
import com.xhs.ems.service.ShowService;

/**
 * @datetime 2015年12月25日 下午5:26:50
 * @author 崔兴伟
 */
@Service
public class ShowServiceImpl implements ShowService {

	@Autowired
	private ShowDAO showDAO;

	@Override
	public List<Show> getShow(Parameter parameter) {
		return showDAO.getShow(parameter);
	}

}
