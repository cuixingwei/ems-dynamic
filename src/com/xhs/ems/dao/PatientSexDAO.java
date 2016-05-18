package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.PatientSex;

/**
 * @datetime 2016年5月18日 下午5:24:24
 * @author 崔兴伟
 */
public interface PatientSexDAO {
	/**
	 * 
	 * @datetime 2016年5月18日 下午5:25:35
	 * @author 崔兴伟
	 * @param patientClass
	 * @return
	 */
	public List<PatientSex> getData(Parameter parameter);
}
