package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.AcceptEventType;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AcceptEventTypeDAO;

@Repository("AcceptEventTypeDAO")
public class AcceptEventTypeDAOImpl implements AcceptEventTypeDAO {

	private static final Logger logger = Logger.getLogger(AcceptEventTypeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql1 = "SELECT u.personName dispatcher,ifnull(sum(if(eh.handleType=1,1,0)),0) numbersOfNormalSendCar,ifnull(sum(if(eh.handleType=2,1,0)),0) numbersOfNormalHangUp,	"
				+ "ifnull(sum(if(eh.handleType=3,1,0)),0) numbersOfReinforceSendCar,ifnull(sum(if(eh.handleType=4,1,0)),0) numbersOfReinforceHangUp,	"
				+ "ifnull(sum(if(eh.handleType=5,1,0)),0) numbersOfStopTask,ifnull(sum(if(eh.handleType=6,1,0)),0) specialEvent,ifnull(sum(if(eh.handleType=7,1,0)),0) noCar,	"
				+ "ifnull(sum(if(eh.handleType=8,1,0)),0) transmitCenter,ifnull(sum(if(eh.handleType=9,1,0)),0) refuseSendCar	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	LEFT JOIN `user` u on u.jobNum=eh.operatorJobNum	"
				+ "WHERE e.eventProperty=1 and u.personName is not null  and e.createTime  between :startTime and :endTime group by u.personName ";
		String sql2 = "SELECT u.personName dispatcher,ifnull(sum(if(et.taskResult=1,1,0)),0) stopTask,'' ratioStopTask,ifnull(sum(if(et.taskResult=2,1,0)),0) emptyCar,'' ratioEmptyCar,	"
				+ "ifnull(sum(if(et.taskResult=3,1,0)),0) nomalComplete,'' ratioComplete,ifnull(sum(if(et.taskResult=4,1,0)),0) refuseCar,"
				+ " '' ratioRefuseCar,ifnull(COUNT(DISTINCT et.taskCode),0) numbersOfSendCar	"
				+ "from `event` e LEFT JOIN event_task et on et.eventCode=e.eventCode	"
				+ "LEFT JOIN `user` u on u.jobNum=et.operatorJobNum	WHERE e.eventProperty=1 and u.personName is not null  and e.createTime between :startTime and :endTime group by u.personName ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<AcceptEventType> results1 = this.npJdbcTemplate.query(sql1, paramMap, new RowMapper<AcceptEventType>() {
			@Override
			public AcceptEventType mapRow(ResultSet rs, int index) throws SQLException {
				AcceptEventType acceptEventType = new AcceptEventType();
				acceptEventType.setDispatcher(rs.getString("dispatcher"));
				acceptEventType.setNumbersOfNormalHangUp(rs.getString("numbersOfNormalHangUp"));
				acceptEventType.setNumbersOfNormalSendCar(rs.getString("numbersOfNormalSendCar"));
				acceptEventType.setNumbersOfReinforceHangUp(rs.getString("numbersOfReinforceHangUp"));
				acceptEventType.setNumbersOfReinforceSendCar(rs.getString("numbersOfReinforceSendCar"));
				acceptEventType.setNumbersOfStopTask(rs.getString("numbersOfStopTask"));
				acceptEventType.setRefuseSendCar(rs.getString("refuseSendCar"));
				acceptEventType.setSpecialEvent(rs.getString("specialEvent"));
				acceptEventType.setTransmitCenter(rs.getString("transmitCenter"));
				acceptEventType.setNoCar(rs.getString("noCar"));
				return acceptEventType;
			}
		});

		List<AcceptEventType> results2 = this.npJdbcTemplate.query(sql2, paramMap, new RowMapper<AcceptEventType>() {
			@Override
			public AcceptEventType mapRow(ResultSet rs, int index) throws SQLException {
				AcceptEventType acceptEventType = new AcceptEventType();
				acceptEventType.setDispatcher(rs.getString("dispatcher"));
				acceptEventType.setNumbersOfSendCar(rs.getString("numbersOfSendCar"));
				acceptEventType.setEmptyCar(rs.getString("emptyCar"));
				acceptEventType.setNomalComplete(rs.getString("nomalComplete"));
				acceptEventType.setRatioComplete(rs.getString("ratioComplete"));
				acceptEventType.setRatioEmptyCar(rs.getString("ratioEmptyCar"));
				acceptEventType.setRatioStopTask(rs.getString("ratioStopTask"));
				acceptEventType.setStopTask(rs.getString("stopTask"));
				acceptEventType.setRefuseCar(rs.getString("refuseCar"));
				acceptEventType.setRatioRefuseCar(rs.getString("ratioRefuseCar"));
				return acceptEventType;
			}
		});

		for (AcceptEventType result : results1) {
			for (AcceptEventType rs : results2) {
				if (result.getDispatcher().equals(rs.getDispatcher())) {
					result.setEmptyCar(rs.getEmptyCar());
					result.setNumbersOfSendCar(rs.getNumbersOfSendCar());
					result.setStopTask(rs.getStopTask());
					result.setNomalComplete(rs.getNomalComplete());
					result.setRefuseCar(rs.getRefuseCar());
				}
			}
		}

		for (AcceptEventType result : results2) {
			for (AcceptEventType rs : results1) {
				if (result.getDispatcher().equals(rs.getDispatcher())) {
					result.setNumbersOfNormalHangUp(rs.getNumbersOfNormalHangUp());
					result.setNumbersOfNormalSendCar(rs.getNumbersOfNormalSendCar());
					result.setNumbersOfReinforceHangUp(rs.getNumbersOfReinforceHangUp());
					result.setNumbersOfReinforceSendCar(rs.getNumbersOfReinforceSendCar());
					result.setNumbersOfStopTask(rs.getNumbersOfStopTask());
					result.setRefuseSendCar(rs.getRefuseSendCar());
					result.setSpecialEvent(rs.getSpecialEvent());
					result.setTransmitCenter(rs.getTransmitCenter());
					result.setNoCar(rs.getNoCar());
				}
			}
		}

		List<String> dispatcherList = new ArrayList<String>();
		List<AcceptEventType> acceptEventTypeList = new ArrayList<AcceptEventType>();

		for (AcceptEventType rs : results1) {
			if (!dispatcherList.contains(rs.getDispatcher())) {
				dispatcherList.add(rs.getDispatcher());
				acceptEventTypeList.add(rs);
			}
		}

		for (AcceptEventType rs : results2) {
			if (!dispatcherList.contains(rs.getDispatcher())) {
				dispatcherList.add(rs.getDispatcher());
				acceptEventTypeList.add(rs);
			}
		}

		AcceptEventType summary = new AcceptEventType();
		summary.setDispatcher("合计");
		summary.setEmptyCar("0");
		summary.setNoCar("0");
		summary.setNomalComplete("0");
		summary.setNumbersOfNormalHangUp("0");
		summary.setNumbersOfNormalSendCar("0");
		summary.setNumbersOfReinforceHangUp("0");
		summary.setNumbersOfSendCar("0");
		summary.setNumbersOfReinforceSendCar("0");
		summary.setNumbersOfStopTask("0");
		summary.setRefuseSendCar("0");
		summary.setSpecialEvent("0");
		summary.setStopTask("0");
		summary.setTransmitCenter("0");
		summary.setRefuseCar("0");
		String intTemp;
		for (AcceptEventType result : results1) {
			if (StringUtils.isEmpty(result.getEmptyCar())) {
				intTemp = "0";
			} else {
				intTemp = result.getEmptyCar();
			}
			summary.setEmptyCar(Integer.parseInt(intTemp) + Integer.parseInt(summary.getEmptyCar()) + "");

			if (StringUtils.isEmpty(result.getNoCar())) {
				intTemp = "0";
			} else {
				intTemp = result.getNoCar();
			}
			summary.setNoCar(Integer.parseInt(intTemp) + Integer.parseInt(summary.getNoCar()) + "");

			if (StringUtils.isEmpty(result.getNomalComplete())) {
				intTemp = "0";
			} else {
				intTemp = result.getNomalComplete();
			}
			summary.setNomalComplete(Integer.parseInt(intTemp) + Integer.parseInt(summary.getNomalComplete()) + "");

			if (StringUtils.isEmpty(result.getNumbersOfNormalHangUp())) {
				intTemp = "0";
			} else {
				intTemp = result.getNumbersOfNormalHangUp();
			}
			summary.setNumbersOfNormalHangUp(
					Integer.parseInt(intTemp) + Integer.parseInt(summary.getNumbersOfNormalHangUp()) + "");

			if (StringUtils.isEmpty(result.getNumbersOfNormalSendCar())) {
				intTemp = "0";
			} else {
				intTemp = result.getNumbersOfNormalSendCar();
			}
			summary.setNumbersOfNormalSendCar(
					Integer.parseInt(intTemp) + Integer.parseInt(summary.getNumbersOfNormalSendCar()) + "");

			if (StringUtils.isEmpty(result.getNumbersOfReinforceHangUp())) {
				intTemp = "0";
			} else {
				intTemp = result.getNumbersOfReinforceHangUp();
			}
			summary.setNumbersOfReinforceHangUp(
					Integer.parseInt(intTemp) + Integer.parseInt(summary.getNumbersOfReinforceHangUp()) + "");

			if (StringUtils.isEmpty(result.getNumbersOfReinforceSendCar())) {
				intTemp = "0";
			} else {
				intTemp = result.getNumbersOfReinforceSendCar();
			}
			summary.setNumbersOfReinforceSendCar(
					Integer.parseInt(intTemp) + Integer.parseInt(summary.getNumbersOfReinforceSendCar()) + "");

			if (StringUtils.isEmpty(result.getNumbersOfSendCar())) {
				intTemp = "0";
			} else {
				intTemp = result.getNumbersOfSendCar();
			}
			summary.setNumbersOfSendCar(
					Integer.parseInt(intTemp) + Integer.parseInt(summary.getNumbersOfSendCar()) + "");

			if (StringUtils.isEmpty(result.getNumbersOfStopTask())) {
				intTemp = "0";
			} else {
				intTemp = result.getNumbersOfStopTask();
			}
			summary.setNumbersOfStopTask(
					Integer.parseInt(intTemp) + Integer.parseInt(summary.getNumbersOfStopTask()) + "");

			if (StringUtils.isEmpty(result.getRefuseSendCar())) {
				intTemp = "0";
			} else {
				intTemp = result.getRefuseSendCar();
			}
			summary.setRefuseSendCar(Integer.parseInt(intTemp) + Integer.parseInt(summary.getRefuseSendCar()) + "");

			if (StringUtils.isEmpty(result.getSpecialEvent())) {
				intTemp = "0";
			} else {
				intTemp = result.getSpecialEvent();
			}
			summary.setSpecialEvent(Integer.parseInt(intTemp) + Integer.parseInt(summary.getSpecialEvent()) + "");

			if (StringUtils.isEmpty(result.getStopTask())) {
				intTemp = "0";
			} else {
				intTemp = result.getStopTask();
			}
			summary.setStopTask(Integer.parseInt(intTemp) + Integer.parseInt(summary.getStopTask()) + "");

			if (StringUtils.isEmpty(result.getTransmitCenter())) {
				intTemp = "0";
			} else {
				intTemp = result.getTransmitCenter();
			}
			summary.setTransmitCenter(Integer.parseInt(intTemp) + Integer.parseInt(summary.getTransmitCenter()) + "");

			if (StringUtils.isEmpty(result.getRefuseCar())) {
				intTemp = "0";
			} else {
				intTemp = result.getRefuseCar();
			}
			summary.setRefuseCar(Integer.parseInt(intTemp) + Integer.parseInt(summary.getRefuseCar()) + "");
		}
		acceptEventTypeList.add(summary);

		for (AcceptEventType result : acceptEventTypeList) {
			if(StringUtils.isEmpty(result.getNoCar())){
				result.setNoCar("0");
			}
			
			if(StringUtils.isEmpty(result.getNomalComplete())){
				result.setNomalComplete("0");
			}
			
			if(StringUtils.isEmpty(result.getNumbersOfNormalHangUp())){
				result.setNumbersOfNormalHangUp("0");
			}
			
			if(StringUtils.isEmpty(result.getNumbersOfNormalSendCar())){
				result.setNumbersOfNormalSendCar("0");
			}
			
			if(StringUtils.isEmpty(result.getNumbersOfReinforceHangUp())){
				result.setNumbersOfReinforceHangUp("0");
			}
			
			if(StringUtils.isEmpty(result.getNumbersOfReinforceSendCar())){
				result.setNumbersOfReinforceSendCar("0");
			}
			
			if(StringUtils.isEmpty(result.getNumbersOfSendCar())){
				result.setNumbersOfSendCar("0");
			}
			
			if(StringUtils.isEmpty(result.getNumbersOfStopTask())){
				result.setNumbersOfStopTask("0");
			}
			
			if(StringUtils.isEmpty(result.getRefuseCar())){
				result.setRefuseCar("0");
			}
			
			if(StringUtils.isEmpty(result.getSpecialEvent())){
				result.setSpecialEvent("0");
			}
			
			if(StringUtils.isEmpty(result.getStopTask())){
				result.setStopTask("0");
			}
			
			if(StringUtils.isEmpty(result.getTransmitCenter())){
				result.setTransmitCenter("0");
			}
			
			if(StringUtils.isEmpty(result.getRefuseSendCar())){
				result.setRefuseSendCar("0");
			}
			
			if(StringUtils.isEmpty(result.getEmptyCar())){
				result.setEmptyCar("0");
			}
			
			
			result.setRatioStopTask(CommonUtil.calculateRate(Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getStopTask().toString())));
			result.setRatioComplete(CommonUtil.calculateRate(Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getNomalComplete().toString())));
			result.setRatioEmptyCar(CommonUtil.calculateRate(Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getEmptyCar().toString())));
			result.setRatioRefuseCar(CommonUtil.calculateRate(Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getRefuseCar().toString())));
		}

		Grid grid = new Grid();
		if ((int) parameter.getPage() > 0) {
			int page = (int) parameter.getPage();
			int rows = (int) parameter.getRows();

			int fromIndex = (page - 1) * rows;
			int toIndex = (acceptEventTypeList.size() <= page * rows && acceptEventTypeList.size() >= (page - 1) * rows)
					? acceptEventTypeList.size() : page * rows;
			grid.setRows(acceptEventTypeList.subList(fromIndex, toIndex));
			grid.setTotal(acceptEventTypeList.size());

		} else {
			grid.setRows(acceptEventTypeList);
		}
		return grid;
	}
}
