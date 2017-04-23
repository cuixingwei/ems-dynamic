package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.AcceptSendCar;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.HistoryEvent;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AcceptSendCarDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月7日 下午3:09:30
 */
@Repository
public class AcceptSendCarDAOImpl implements AcceptSendCarDAO {
	private static final Logger logger = Logger
			.getLogger(AcceptSendCarDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月7日 下午3:09:31
	 * @see com.xhs.ems.dao.AcceptSendCarDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT et.id,dht.`name` acceptType,date_format(eh.handleBeginTime,'%Y-%c-%d %h:%i:%s')  startAcceptTime,"
				+ "eh.incomingCall ringPhone,date_format(et.createTime,'%Y-%c-%d %h:%i:%s')  sendCarTime,eh.remark,"
				+ "TIMESTAMPDIFF(SECOND,eh.handleBeginTime,et.createTime) sendCarTimes,u.personName dispatcher	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN `user` u on u.jobNum=eh.operatorJobNum	LEFT JOIN define_handle_type dht on dht.code=eh.handleType	"
				+ "WHERE e.eventProperty=1 and eh.createTime between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and eh.operatorJobNum=:dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getOvertimes())) {
			sql = sql + " and TIMESTAMPDIFF(SECOND,eh.handleBeginTime,et.createTime) >= :overtimes ";
		}
		sql += " order by u.personName,eh.createTime ";
		logger.info("sql:"+sql);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("overtimes", parameter.getOvertimes());

		List<AcceptSendCar> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<AcceptSendCar>() {
					@Override
					public AcceptSendCar mapRow(ResultSet rs, int index)
							throws SQLException {

						return new AcceptSendCar(rs.getString("id"), rs
								.getString("dispatcher"), rs
								.getString("startAcceptTime"), rs
								.getString("sendCarTime"), rs
								.getString("acceptType"), rs
								.getString("ringPhone"), rs
								.getString("sendCarTimes"), rs
								.getString("remark"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (AcceptSendCar result : results) {
			result.setSendCarTimes(CommonUtil.formatSecond(result
					.getSendCarTimes()));
		}
		Grid grid = new Grid();
		if ((int) parameter.getPage() > 0) {
			int page = (int) parameter.getPage();
			int rows = (int) parameter.getRows();

			int fromIndex = (page - 1) * rows;
			int toIndex = (results.size() <= page * rows && results.size() >= (page - 1)
					* rows) ? results.size() : page * rows;
			grid.setRows(results.subList(fromIndex, toIndex));
			grid.setTotal(results.size());

		} else {
			grid.setRows(results);
		}
		return grid;
	}

	@Override
	public HistoryEvent getDetail(String id, HttpServletRequest request) {
		String sql = "select * from v_event_detail	where id= :id ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		logger.info("id:" + id);
		logger.info(sql);
		HistoryEvent result = this.npJdbcTemplate.queryForObject(sql,
				paramMap, new RowMapper<HistoryEvent>() {

					@Override
					public HistoryEvent mapRow(ResultSet rs, int index)
							throws SQLException {
						HistoryEvent historyEvent = new HistoryEvent();
						historyEvent.setAcceptCount(rs.getString("acceptCount"));
						historyEvent.setAcceptStartTime(rs.getString("acceptStartTime"));
						historyEvent.setThisDispatcher(rs.getString("thisDispatcher"));
						historyEvent.setEventName(rs.getString("eventName"));
						historyEvent.setEventSource(rs.getString("eventSource"));
						historyEvent.setEventType(rs.getString("eventType"));
						historyEvent.setCallPhone(rs.getString("callPhone"));
						historyEvent.setAcceptOrder(rs.getString("acceptOrder"));
						historyEvent.setAcceptType(rs.getString("acceptType"));
						historyEvent.setAge(rs.getString("age"));
						historyEvent.setArriveSpotTime(rs.getString("arriveSpotTime"));
						historyEvent.setCancelReason(rs.getString("cancelReason"));
						historyEvent.setCarIndentiy(rs.getString("carIndentiy"));
						historyEvent.setCompleteTime(rs.getString("completeTime"));
						historyEvent.setContactMan(rs.getString("contactMan"));
						historyEvent.setContactPhone(rs.getString("contactPhone"));
						historyEvent.setEndAcceptTime(rs.getString("endAcceptTime"));
						historyEvent.setEventNature(rs.getString("eventNature"));
						historyEvent.setGender(rs.getString("gender"));
						historyEvent.setHumanNumbers(rs.getString("humanNumbers"));
						historyEvent.setIdentity(rs.getString("identitys"));
						historyEvent.setIsOrNoLitter(rs.getString("isOrNoLitter"));
						historyEvent.setLeaveSpotTime(rs.getString("leaveSpotTime"));
						historyEvent.setAcceptOrder(rs.getString("acceptOrder"));
						historyEvent.setOutCarTime(rs.getString("outCarTime"));
						historyEvent.setPreJudgment(rs.getString("preJudgment"));
						historyEvent.setReason(rs.getString("reason"));
						historyEvent.setReceiveOrderTime(rs.getString("receiveOrderTime"));
						historyEvent.setRemark(rs.getString("remark"));
						historyEvent.setSendCarTime(rs.getString("sendCarTime"));
						historyEvent.setSendStation(rs.getString("sendStation"));
						historyEvent.setSickCondition(rs.getString("sickCondition"));
						historyEvent.setSickName(rs.getString("sickName"));
						historyEvent.setSpecialNeeds(rs.getString("specialNeeds"));
						historyEvent.setState(rs.getString("state"));
						historyEvent.setTaskResult(rs.getString("taskResult"));
						historyEvent.setWaitAddress(rs.getString("waitAddress"));
						historyEvent.setCancelReason(rs.getString("cancelReason"));
						historyEvent.setSuspendReason(rs.getString("suspendReason"));
						historyEvent.setTaskAwaitTime(rs.getString("taskAwaitTime"));
						historyEvent.setWeatherState(rs.getString("weatherState"));
						historyEvent.setRoadState(rs.getString("roadState"));
						historyEvent.setSendTarget(rs.getString("sendTarget"));
						historyEvent.setStopTaskReason(rs.getString("stopTaskReason"));
						historyEvent.setEmptyVehicleReason(rs.getString("emptyVehicleReason"));
						
						return historyEvent;
					}
				});
		ServletContext sct = request.getServletContext();
		String recordIP = (String) sct.getAttribute("RecordServerIP");
		String year, day, month;
		if (result.getRecord() != null) {
			String recordName = result.getRecord().trim();
			int n = recordName.indexOf("_");
			if (n != -1) {
				String[] name = recordName.subSequence(0, n).toString()
						.split("-");
				year = name[0];
				month = name[1];
				day = name[2];
				String recordPath = recordIP +year + month + "/" + year
						+ month + day + "/" + recordName; 
				logger.info("录音文件绝对路径为:" + recordPath);
				result.setRecord(recordPath);
			}
		}
		return result;
	}
}
