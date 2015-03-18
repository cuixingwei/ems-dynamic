package com.xhs.ems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhs.ems.bean.RingToAccept;
import com.xhs.ems.dao.RingToAcceptDAO;
import com.xhs.ems.service.RingToAcceptService;
@Service
public class RingToAcceptServiceImpl implements RingToAcceptService {
	
	@Autowired
	private RingToAcceptDAO ringToAcceptDAO;
	
	@Override
	public List<RingToAccept> getData(String overtimes, String dispatcher,
			String startTime, String endTime) {
		return ringToAcceptDAO.getData(overtimes, dispatcher, startTime, endTime);
	}

}
