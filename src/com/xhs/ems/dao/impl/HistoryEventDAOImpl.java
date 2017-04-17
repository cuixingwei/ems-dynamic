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
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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
		String sql = "select e.事件名称 eventName,e.受理次数 acceptCount,det.NameM eventType,den.NameM eventNature,e.呼救电话 callPhone,a.现场地址 callAddress,a.病人需求 patientNeed,"
				+ "a.初步判断 preJudgment,dis.NameM sickCondition,a.特殊要求 specialNeeds,a.救治人数 humanNumbers,ls.NameM eventSource,a.等车地址 waitAddress,"
				+ "a.患者姓名 sickName,	a.年龄 age,a.性别 gender,di.NameM identitys,a.联系人 contactMan,"
				+ "a.联系电话 contactPhone,a.分机 extension,m.姓名 thisDispatcher,	a.备注 remark,a.是否要担架 isOrNoLitter,a.受理序号 acceptOrder,"
				+ "convert(varchar(20),a.开始受理时刻,120) acceptStartTime,dat.NameM acceptType,	dhr.NameM toBeSentReason,"
				+ "convert(varchar(20),a.结束受理时刻,120) endAcceptTime,convert(varchar(20),a.派车时刻,120) sendCarTime,drr.NameM cancelReason,s.分站名称 sendStation,	"
				+ "am.实际标识 carIndentiy,dts.NameM states,convert(varchar(20),t.接受命令时刻,120) receiveOrderTime,dtr.NameM taskResult,"
				+ "isnull(dsr.NameM,'')+isnull(dtrr.NameM,'')+ISNULL(der.NameM,'') reason,	"
				+ "CONVERT(varchar(20),t.出车时刻,120) outCarTime,t.备注 taskRemark,convert(varchar(20),t.到达现场时刻,120) arriveSpotTime,t.救治人数 takeHumanNumbers,	"
				+ "isnull(a.入院人数,0) toHospitalNumbers,convert(varchar(20),t.离开现场时刻,120) leaveSpotTime,"
				+ "ISNULL(a.死亡人数,0) deathNumbers,	ISNULL(a.留观人数,0) stayHospitalNumbers,"
				+ "'0' backHospitalNumbers,convert(varchar(20),t.完成时刻,120) completeTime,m.姓名 stationDispatcher,"
				+ "t.任务序号 taskOrder,et.录音文件名 record from AuSp120.tb_AcceptDescriptV a "
				+ "left outer  join AuSp120.tb_EventV e on a.事件编码=e.事件编码 LEFT outer join AuSp120.tb_EventTele et on et.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_Task t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码	"
				+ "left outer join AuSp120.tb_DEventNature den on den.Code=e.事件性质编码	"
				+ "left outer join AuSp120.tb_DILLState dis on dis.Code=a.病情编码	"
				+ "left outer join AuSp120.tb_DLinkSource ls on ls.Code=e.联动来源编码	"
				+ "left outer join AuSp120.tb_DIdentity di on di.Code=a.身份编码	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=a.调度员编码 or m.工号=t.分站调度员编码	"
				+ "left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.类型编码	"
				+ "left outer join AuSp120.tb_DHangReason dhr on dhr.Code=a.挂起原因编码	"
				+ "left outer join AuSp120.tb_DRepealReason drr on drr.Code=a.撤消原因编码	"
				+ "left outer join AuSp120.tb_Station s on s.分站编码=t.分站编码	"
				+ "left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码	"
				+ "left outer join AuSp120.tb_DTaskState dts on dts.Code=t.状态编码	"
				+ "left outer join AuSp120.tb_DTaskResult dtr on dtr.Code=t.结果编码	"
				+ "left outer join AuSp120.tb_DEmptyReason der on der.Code=t.放空车原因编码	"
				+ "left outer join AuSp120.tb_DRefuseReason dtrr on dtrr.Code=t.拒绝出车原因编码	"
				+ "left outer join AuSp120.tb_DStopReason dsr on dsr.Code=t.中止任务原因编码	where e.事件编码   = :eventCode order by a.事件编码,a.受理序号,t.任务序号 ";
		
		
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
				historyEvent.setBackHospitalNumbers(rs.getString("backHospitalNumbers"));
				historyEvent.setCallAddress(rs.getString("callAddress"));
				historyEvent.setCancelReason(rs.getString("cancelReason"));
				historyEvent.setCarIndentiy(rs.getString("carIndentiy"));
				historyEvent.setCompleteTime(rs.getString("completeTime"));
				historyEvent.setContactMan(rs.getString("contactMan"));
				historyEvent.setContactPhone(rs.getString("contactPhone"));
				historyEvent.setDeathNumbers(rs.getString("deathNumbers"));
				historyEvent.setEndAcceptTime(rs.getString("endAcceptTime"));
				historyEvent.setEventNature(rs.getString("eventNature"));
				historyEvent.setExtension(rs.getString("extension"));
				historyEvent.setGender(rs.getString("gender"));
				historyEvent.setHumanNumbers(rs.getString("humanNumbers"));
				historyEvent.setIdentity(rs.getString("identitys"));
				historyEvent.setIsOrNoLitter(rs.getString("isOrNoLitter"));
				historyEvent.setLeaveSpotTime(rs.getString("leaveSpotTime"));
				historyEvent.setTaskOrder(rs.getString("taskOrder"));
				historyEvent.setAcceptOrder(rs.getString("acceptOrder"));
				historyEvent.setOutCarTime(rs.getString("outCarTime"));
				historyEvent.setPatientNeed(rs.getString("patientNeed"));
				historyEvent.setPreJudgment(rs.getString("preJudgment"));
				historyEvent.setReason(rs.getString("reason"));
				historyEvent.setReceiveOrderTime(rs.getString("receiveOrderTime"));
				historyEvent.setRemark(rs.getString("remark"));
				historyEvent.setSendCarTime(rs.getString("sendCarTime"));
				historyEvent.setSendStation(rs.getString("sendStation"));
				historyEvent.setSickCondition(rs.getString("sickCondition"));
				historyEvent.setSickName(rs.getString("sickName"));
				historyEvent.setSpecialNeeds(rs.getString("specialNeeds"));
				historyEvent.setState(rs.getString("states"));
				historyEvent.setStationDispatcher(rs.getString("stationDispatcher"));
				historyEvent.setStayHospitalNumbers(rs.getString("stayHospitalNumbers"));
				historyEvent.setTakeHumanNumbers(rs.getString("takeHumanNumbers"));
				historyEvent.setTaskRemark(rs.getString("taskRemark"));
				historyEvent.setTaskResult(rs.getString("taskResult"));
				historyEvent.setToBeSentReason(rs.getString("toBeSentReason"));
				historyEvent.setToHospitalNumbers(rs.getString("toHospitalNumbers"));
				historyEvent.setWaitAddress(rs.getString("waitAddress"));
				
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
