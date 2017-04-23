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

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.HistoryEvent;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.HistoryEventDAO;

/**
 * @author cuixingwei
 * @datetime 2017年1月5日下午5:41:55
 */
@Repository
public class HistoryEventDAOImpl implements HistoryEventDAO {

	private static final Logger logger = Logger.getLogger(HistoryEventDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
	}

	/**
	 * @author cuixingwei
	 * @datetime 2017年1月5日下午5:42:02
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select e.eventAddress eventName,e.handleTimes acceptCount,e.incomingCall callPhone,e.eventCode,DATE_FORMAT(e.createTime,'%Y-%m-%d') acceptStartTime,	"
				+ "u.personName thisDispatcher,det.name eventType,des.name eventSource,der.name eventResult	"
				+ "from `event` e LEFT JOIN user u on e.operatorJobNum=u.jobNum	LEFT JOIN define_event_type det on det.`code`=e.eventType	"
				+ "LEFT JOIN define_event_source des on des.`code`=e.eventSource left join define_event_result der on der.code=e.eventResult	"
				+ "where e.eventProperty=1     and e.createTime between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getEventName())) {
			sql = sql + " and e.eventAddress like :eventName ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getAlarmPhone())) {
			sql = sql + " and e.incomingCall  like :alarmPhone ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getEventCode())) {
			sql = sql + " and e.eventCode  = :eventCode ";
		}
		sql += " order by e.createTime  ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("eventName", "%" + parameter.getEventName() + "%");
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("eventCode", parameter.getEventCode());
		paramMap.put("alarmPhone", "%" + parameter.getAlarmPhone() + "%");
		logger.info(sql);
		List<HistoryEvent> results = this.npJdbcTemplate.query(sql, paramMap, new RowMapper<HistoryEvent>() {
			@Override
			public HistoryEvent mapRow(ResultSet rs, int index) throws SQLException {
				HistoryEvent historyEvent = new HistoryEvent();
				historyEvent.setAcceptCount(rs.getString("acceptCount"));
				historyEvent.setAcceptStartTime(rs.getString("acceptStartTime"));
				historyEvent.setThisDispatcher(rs.getString("thisDispatcher"));
				historyEvent.setEventName(rs.getString("eventName"));
				historyEvent.setEventSource(rs.getString("eventSource"));
				historyEvent.setEventType(rs.getString("eventType"));
				historyEvent.setCallPhone(rs.getString("callPhone"));
				historyEvent.setEventCode(rs.getString("eventCode"));
				return historyEvent;
			}
		});
		logger.info("一共有" + results.size() + "条数据");
		Grid grid = new Grid();
		if ((int) parameter.getPage() > 0) {
			int page = (int) parameter.getPage();
			int rows = (int) parameter.getRows();

			int fromIndex = (page - 1) * rows;
			int toIndex = (results.size() <= page * rows && results.size() >= (page - 1) * rows) ? results.size()
					: page * rows;
			grid.setRows(results.subList(fromIndex, toIndex));
			grid.setTotal(results.size());

		} else {
			grid.setRows(results);
		}
		return grid;
	}

	/**
	 * @author cuixingwei
	 * @datetime 2017年1月5日下午5:42:02
	 */
	@Override
	public List<HistoryEvent> getDetail(String eventCode, HttpServletRequest request) {
		String sql = "select * from v_event_detail	where eventCode  = :eventCode order by eventCode,acceptCount,taskCode ";
		
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("eventCode", eventCode);
		ServletContext sct = request.getServletContext();
		String recordIP = (String) sct.getAttribute("RecordServerIP");
		List<HistoryEvent> results = this.npJdbcTemplate.query(sql, paramMap, new RowMapper<HistoryEvent>() {
			@Override
			public HistoryEvent mapRow(ResultSet rs, int index) throws SQLException {
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
//				historyEvent.setBackHospitalNumbers(rs.getString("backHospitalNumbers"));
//				historyEvent.setCallAddress(rs.getString("callAddress"));
				historyEvent.setCancelReason(rs.getString("cancelReason"));
				historyEvent.setCarIndentiy(rs.getString("carIndentiy"));
				historyEvent.setCompleteTime(rs.getString("completeTime"));
				historyEvent.setContactMan(rs.getString("contactMan"));
				historyEvent.setContactPhone(rs.getString("contactPhone"));
//				historyEvent.setDeathNumbers(rs.getString("deathNumbers"));
				historyEvent.setEndAcceptTime(rs.getString("endAcceptTime"));
				historyEvent.setEventNature(rs.getString("eventNature"));
//				historyEvent.setExtension(rs.getString("extension"));
				historyEvent.setGender(rs.getString("gender"));
				historyEvent.setHumanNumbers(rs.getString("humanNumbers"));
				historyEvent.setIdentity(rs.getString("identitys"));
				historyEvent.setIsOrNoLitter(rs.getString("isOrNoLitter"));
				historyEvent.setLeaveSpotTime(rs.getString("leaveSpotTime"));
//				historyEvent.setTaskOrder(rs.getString("taskOrder"));
				historyEvent.setAcceptOrder(rs.getString("acceptOrder"));
				historyEvent.setOutCarTime(rs.getString("outCarTime"));
//				historyEvent.setPatientNeed(rs.getString("patientNeed"));
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
//				historyEvent.setStationDispatcher(rs.getString("stationDispatcher"));
//				historyEvent.setStayHospitalNumbers(rs.getString("stayHospitalNumbers"));
//				historyEvent.setTakeHumanNumbers(rs.getString("takeHumanNumbers"));
//				historyEvent.setTaskRemark(rs.getString("taskRemark"));
				historyEvent.setTaskResult(rs.getString("taskResult"));
//				historyEvent.setToBeSentReason(rs.getString("toBeSentReason"));
//				historyEvent.setToHospitalNumbers(rs.getString("toHospitalNumbers"));
				historyEvent.setWaitAddress(rs.getString("waitAddress"));
				historyEvent.setCancelReason(rs.getString("cancelReason"));
				historyEvent.setSuspendReason(rs.getString("suspendReason"));
				historyEvent.setTaskAwaitTime(rs.getString("taskAwaitTime"));
				historyEvent.setWeatherState(rs.getString("weatherState"));
				historyEvent.setRoadState(rs.getString("roadState"));
				historyEvent.setSendTarget(rs.getString("sendTarget"));
				historyEvent.setStopTaskReason(rs.getString("stopTaskReason"));
				historyEvent.setEmptyVehicleReason(rs.getString("emptyVehicleReason"));
				
				String record = rs.getString("record");
				String year, day, month;
				if (record != null) {
					String recordName = record.trim();
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
						historyEvent.setRecord(recordPath);
					}
				}
				return historyEvent;
			}
		});
		logger.info("一共有" + results.size() + "条数据");
		return results;
	}

}
