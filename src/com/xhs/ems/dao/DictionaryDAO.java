package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.Dictionary;

/**
 * @datetime 2016年5月18日 下午5:06:04
 * @author 崔兴伟
 */
public interface DictionaryDAO {
	/**
	 * 获取疾病分类字典表
	 * 
	 * @datetime 2016年5月18日 下午4:58:33
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> getPatientClass();
	/**
	 * 获取疾病种类字典表
	 * @datetime 2016年5月18日 下午6:17:57
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetPatientType();

}
