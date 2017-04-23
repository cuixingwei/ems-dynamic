package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

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

	private static final Logger logger = Logger
			.getLogger(AcceptEventTypeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql1 = "SELECT u.personName dispatcher,sum(if(eh.handleType=1,1,0)) numbersOfNormalSendCar,sum(if(eh.handleType=2,1,0)) numbersOfNormalHangUp,	"
				+ "sum(if(eh.handleType=3,1,0)) numbersOfReinforceSendCar,sum(if(eh.handleType=4,1,0)) numbersOfReinforceHangUp,	"
				+ "sum(if(eh.handleType=5,1,0)) numbersOfStopTask,sum(if(eh.handleType=6,1,0)) specialEvent,sum(if(eh.handleType=7,1,0)) noCar,	"
				+ "sum(if(eh.handleType=8,1,0)) transmitCenter,sum(if(eh.handleType=9,1,0)) refuseSendCar	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	LEFT JOIN `user` u on u.jobNum=eh.operatorJobNum	"
				+ "WHERE e.eventProperty=1  and e.createTime  between :startTime and :endTime group by u.personName ";
		String sql2 = "SELECT u.personName dispatcher,sum(if(et.taskResult=1,1,0)) stopTask,'' ratioStopTask,sum(if(et.taskResult=2,1,0)) emptyCar,'' ratioEmptyCar,	"
				+ "sum(if(et.taskResult=3,1,0)) nomalComplete,'' ratioComplete,sum(if(et.taskResult=4,1,0)) refuseCar,"
				+ " '' ratioRefuseCar,COUNT(DISTINCT et.taskCode) numbersOfSendCar	"
				+ "from `event` e LEFT JOIN event_task et on et.eventCode=e.eventCode	"
				+ "LEFT JOIN `user` u on u.jobNum=et.operatorJobNum	WHERE e.eventProperty=1  and e.createTime between :startTime and :endTime group by u.personName ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<AcceptEventType> results1 = this.npJdbcTemplate.query(sql1,
				paramMap, new RowMapper<AcceptEventType>() {
					@Override
					public AcceptEventType mapRow(ResultSet rs, int index)
							throws SQLException {
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
		List<AcceptEventType> results2 = this.npJdbcTemplate.query(sql2,
				paramMap, new RowMapper<AcceptEventType>() {
					@Override
					public AcceptEventType mapRow(ResultSet rs, int index)
							throws SQLException {
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
				if(result.getDispatcher().equals(rs.getDispatcher())){
					result.setEmptyCar(rs.getEmptyCar());
					result.setRatioEmptyCar(rs.getRatioEmptyCar());
					result.setNumbersOfSendCar(rs.getNumbersOfSendCar());
					result.setStopTask(rs.getStopTask());
					result.setRatioStopTask(rs.getRatioStopTask());
					result.setNomalComplete(rs.getNomalComplete());
					result.setRatioComplete(rs.getRatioComplete());
					result.setRefuseCar(rs.getRefuseCar());
					result.setRatioRefuseCar(rs.getRefuseSendCar());
				}
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
		summary.setNomalComplete("0");
		summary.setNumbersOfReinforceSendCar("0");
		summary.setNumbersOfSendCar("0");
		summary.setNumbersOfStopTask("0");
		summary.setRefuseSendCar("0");
		summary.setSpecialEvent("0");
		summary.setStopTask("0");
		summary.setTransmitCenter("0");
		summary.setRefuseCar("0");
		for (AcceptEventType result : results1) {
			summary.setEmptyCar(Integer.parseInt(result.getEmptyCar())+Integer.parseInt(summary.getEmptyCar())+"");
			summary.setNoCar(Integer.parseInt(result.getNoCar())+Integer.parseInt(summary.getNoCar())+"");
			summary.setNomalComplete(Integer.parseInt(result.getNomalComplete())+Integer.parseInt(summary.getNomalComplete())+"");
			summary.setNumbersOfNormalHangUp(Integer.parseInt(result.getNumbersOfNormalHangUp())+Integer.parseInt(summary.getNumbersOfNormalHangUp())+"");
			summary.setNumbersOfNormalSendCar(Integer.parseInt(result.getNumbersOfNormalSendCar())+Integer.parseInt(summary.getNumbersOfNormalSendCar())+"");
//			summary.setNumbersOfPhone(Integer.parseInt(result.getNumbersOfPhone())+Integer.parseInt(summary.getNumbersOfPhone())+"");
			summary.setNumbersOfReinforceHangUp(Integer.parseInt(result.getNumbersOfReinforceHangUp())+Integer.parseInt(summary.getNumbersOfReinforceHangUp())+"");
			summary.setNumbersOfReinforceSendCar(Integer.parseInt(result.getNumbersOfReinforceSendCar())+Integer.parseInt(summary.getNumbersOfReinforceSendCar())+"");
			summary.setNumbersOfSendCar(Integer.parseInt(result.getNumbersOfSendCar())+Integer.parseInt(summary.getNumbersOfSendCar())+"");
			summary.setNumbersOfStopTask(Integer.parseInt(result.getNumbersOfStopTask())+Integer.parseInt(summary.getNumbersOfStopTask())+"");
			summary.setRefuseSendCar(Integer.parseInt(result.getRefuseSendCar())+Integer.parseInt(summary.getRefuseSendCar())+"");
			summary.setSpecialEvent(Integer.parseInt(result.getSpecialEvent())+Integer.parseInt(summary.getSpecialEvent())+"");
			summary.setStopTask(Integer.parseInt(result.getStopTask())+Integer.parseInt(summary.getStopTask())+"");
			summary.setTransmitCenter(Integer.parseInt(result.getTransmitCenter())+Integer.parseInt(summary.getTransmitCenter())+"");
//			summary.setWakeSendCar(Integer.parseInt(result.getWakeSendCar())+Integer.parseInt(summary.getWakeSendCar())+"");
			summary.setRefuseCar(Integer.parseInt(result.getRefuseCar())+Integer.parseInt(summary.getRefuseCar())+"");
		}
		results1.add(summary);
		
		for (AcceptEventType result : results1) {
			result.setRatioStopTask(CommonUtil.calculateRate(
					Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getStopTask().toString())));
			result.setRatioComplete(CommonUtil.calculateRate(
					Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getNomalComplete().toString())));
			result.setRatioEmptyCar(CommonUtil.calculateRate(
					Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getEmptyCar().toString())));
			result.setRatioRefuseCar(CommonUtil.calculateRate(
					Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getRefuseCar().toString())));
		}

		Grid grid = new Grid();
		if ((int) parameter.getPage() > 0) {
			int page = (int) parameter.getPage();
			int rows = (int) parameter.getRows();

			int fromIndex = (page - 1) * rows;
			int toIndex = (results1.size() <= page * rows && results1.size() >= (page - 1)
					* rows) ? results1.size() : page * rows;
			grid.setRows(results1.subList(fromIndex, toIndex));
			grid.setTotal(results1.size());

		} else {
			grid.setRows(results1);
		}
		return grid;
	}
}
