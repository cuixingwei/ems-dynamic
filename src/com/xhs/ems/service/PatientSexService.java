package com.xhs.ems.service;

import java.util.List;

import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.PatientSex;

/**
 * @datetime 2016年5月18日 下午5:26:16
 * @author 崔兴伟
 */
public interface PatientSexService {
	/**
	 * 
	 * @datetime 2016年5月18日 下午5:25:35
	 * @author 崔兴伟
	 * @param patientClass
	 * @return
	 */
	public List<PatientSex> getData(Parameter parameter);
}
