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

	@Override
	public List<Dictionary> GetPatientDepartment() {
		return dictionaryDAO.GetPatientDepartment();
	}

	@Override
	public List<Dictionary> GetPatientReason() {
		return dictionaryDAO.GetPatientReason();
	}

	@Override
	public List<Dictionary> GetIllState() {
		return dictionaryDAO.GetIllState();
	}

	@Override
	public List<Dictionary> GetAidResult() {
		return dictionaryDAO.GetAidResult();
	}

	@Override
	public List<Dictionary> GetDeathProve() {
		return dictionaryDAO.GetDeathProve();
	}

	@Override
	public List<Dictionary> GetTakenPlaceType() {
		return dictionaryDAO.GetTakenPlaceType();
	}

	@Override
	public List<Dictionary> GetLocaleType() {
		return dictionaryDAO.GetLocaleType();
	}

	@Override
	public List<Dictionary> GetOutCome() {
		return dictionaryDAO.GetOutCome();
	}

	@Override
	public List<Dictionary> GetCooperate() {
		return dictionaryDAO.GetCooperate();
	}

	@Override
	public List<Dictionary> GetIdentity() {
		return dictionaryDAO.GetIdentity();
	}

	@Override
	public List<Dictionary> GetProfession() {
		return dictionaryDAO.GetProfession();
	}

}
