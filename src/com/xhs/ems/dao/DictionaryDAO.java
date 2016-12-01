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
	
	/**
	 * 获得疾病科别字典表
	 * @datetime 2016年9月27日 下午4:51:47
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetPatientDepartment();
	/**
	 * 获得疾病原因字典表
	 * @datetime 2016年9月27日 下午4:56:45
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetPatientReason();
	/**
	 * 获得病情字典表
	 * @datetime 2016年9月27日 下午5:00:31
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetIllState();
	/**
	 * 救治结果编码
	 * @datetime 2016年9月27日 下午5:04:29
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetAidResult();
	/**
	 * 死亡证明编码
	 * @datetime 2016年9月27日 下午5:06:55
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetDeathProve();
	/**
	 * 送往地点类型
	 * @datetime 2016年9月27日 下午5:06:55
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetTakenPlaceType();
	/**
	 * 现场地点类型
	 * @datetime 2016年9月27日 下午5:06:55
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetLocaleType();
	/**
	 * 转归编码
	 * @datetime 2016年9月27日 下午5:11:35
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetOutCome();
	/**
	 * 病家合作
	 * @datetime 2016年9月27日 下午5:11:35
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetCooperate();
	/**
	 * 身份编码
	 * @datetime 2016年9月27日 下午5:11:35
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetIdentity();
	/**
	 * 职业编码
	 * @datetime 2016年9月27日 下午5:11:35
	 * @author 崔兴伟
	 * @return
	 */
	List<Dictionary> GetProfession();

}
