package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.EmptyCarReason;
import com.xhs.ems.dao.EmptyCarReasonDAO;
import com.xhs.ems.service.EmptyCarReasonService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午11:19:29
 */
@Service
public class EmptyCarReasonServiceImpl implements EmptyCarReasonService {
	@Autowired
	private EmptyCarReasonDAO emptyCarReason;

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 上午11:19:29
	 * @see com.xhs.ems.service.EmptyCarReasonService#getData()
	 */
	@Override
	public List<EmptyCarReason> getData() {
		return emptyCarReason.getData();
	}

}
