package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.Dictionary;
import com.xhs.ems.dao.DictionaryDAO;
import com.xhs.ems.service.DictionaryService;

/**
 * @datetime 2016年5月18日 下午5:05:40
 * @author 崔兴伟
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {
	@Autowired
	private DictionaryDAO dictionaryDAO;

	@Override
	public List<Dictionary> getPatientClass() {
		return dictionaryDAO.getPatientClass();
	}

	@Override
	public List<Dictionary> GetPatientType() {
		return dictionaryDAO.GetPatientType();
	}

}
