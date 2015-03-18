package com.xhs.ems.dao;

import java.util.List;

import com.xhs.ems.bean.RingToAccept;

public interface RingToAcceptDAO {
	/**
	 * 返回振铃到接听大于X秒的相关数据
	 * 
	 * @param overtimes
	 * @param dispatcher
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<RingToAccept> getData(String overtimes, String dispatcher,
			String startTime, String endTime);
}
