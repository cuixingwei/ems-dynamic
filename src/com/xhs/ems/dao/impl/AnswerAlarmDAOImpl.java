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

import com.xhs.ems.bean.AnswerAlarm;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AnswerAlarmDAO;
import com.xhs.ems.filter.CofigListener;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午8:36:42
 */
@Repository
public class AnswerAlarmDAOImpl implements AnswerAlarmDAO {
	private static final Logger logger = Logger
			.getLogger(AnswerAlarmDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;
	private NamedParameterJdbcTemplate npJdbcTemplateSQLServer;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql,DataSource dataSourceSQLServer) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
		this.npJdbcTemplateSQLServer = new NamedParameterJdbcTemplate(dataSourceSQLServer);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午8:37:26
	 * @see com.xhs.ems.dao.AnswerAlarmDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT date_format(eh.createTime,'%Y-%c-%d %h:%i:%s') answerAlarmTime,eh.incomingCall alarmPhone,eh.contactPhone relatedPhone,"
				+ "eh.eventAddress siteAddress,eh.prejudge judgementOnPhone,s.stationName station,date_format(et.createTime,'%Y-%c-%d %h:%i:%s') sendCarTime,"
				+ "u.personName dispatcher,eh.patientName	from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN `user` u on u.jobNum=eh.operatorJobNum	LEFT JOIN station s on s.stationCode=et.stationCode	"
				+ "WHERE e.eventProperty=1 and eh.createTime  between :startTime and :endTime  ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + "and eh.operatorJobNum= :dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getAlarmPhone())) {
			sql += " and  eh.incomingCall like :alarmPhone ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getSiteAddress())) {
			sql += " and eh.eventAddress like :siteAddress ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql += " and et.stationCode = :station ";
		}
		sql += "order by eh.createTime ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("station",parameter.getStation());
		paramMap.put("alarmPhone", "%" + parameter.getAlarmPhone() + "%");
		paramMap.put("siteAddress", "%" + parameter.getSiteAddress() + "%");

		logger.info(sql);

		List<AnswerAlarm> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<AnswerAlarm>() {
					@Override
					public AnswerAlarm mapRow(ResultSet rs, int index)
							throws SQLException {
						AnswerAlarm alarm = new AnswerAlarm();
						alarm.setAlarmPhone(rs.getString("alarmPhone"));
						alarm.setAnswerAlarmTime(rs.getString("answerAlarmTime"));
						alarm.setRelatedPhone(rs.getString("relatedPhone"));
						alarm.setJudgementOnPhone(rs.getString("judgementOnPhone"));
						alarm.setSendCarTime(rs.getString("sendCarTime"));
						alarm.setStation(rs.getString("station"));
						alarm.setDispatcher(rs.getString("dispatcher"));
						alarm.setPatientName(rs.getString("patientName"));
						alarm.setSiteAddress(rs.getString("siteAddress"));
						return alarm;
					}
				});
		logger.info("一共有" + results.size() + "条数据");
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
	public Grid getPhoneRecord(Parameter parameter) {
		String sql = "select CONVERT(varchar(20),tr.振铃时刻,120) answerAlarmTime,tr.电话号码 alarmPhone,m.姓名 dispatcher,"
				+ "	tr.录音文件名 recordPath,dtrr.NameM result,dtr.NameM recordType	from AuSp120.tb_TeleRecordV tr	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=tr.调度员编码	"
				+ "left outer join AuSp120.tb_DTeleRecordType dtr on dtr.Code=tr.记录类型编码	"
				+ "left outer join AuSp120.tb_DTeleRecordResult dtrr on dtrr.Code=tr.结果编码	"
				+ "where tr.产生时刻  between :startTime and :endTime  ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + "and tr.调度员编码= :dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getAlarmPhone())) {
			sql += " and  tr.电话号码  like :alarmPhone";
		}
		sql += " order by tr.产生时刻";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("alarmPhone", "%" + parameter.getAlarmPhone() + "%");

		logger.info(sql);

		List<AnswerAlarm> results = this.npJdbcTemplateSQLServer.query(sql, paramMap,
				new RowMapper<AnswerAlarm>() {
					@Override
					public AnswerAlarm mapRow(ResultSet rs, int index)
							throws SQLException {
						AnswerAlarm alarm = new AnswerAlarm();
						alarm.setAlarmPhone(rs.getString("alarmPhone"));
						alarm.setAnswerAlarmTime(rs
								.getString("answerAlarmTime"));
						alarm.setDispatcher(rs.getString("dispatcher"));
						alarm.setResult(rs.getString("result"));
						alarm.setRecordType(rs.getString("recordType"));
						alarm.setRecordPath(rs.getString("recordPath"));
						return alarm;
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (AnswerAlarm reAlarm : results) {
			String recordIP = CofigListener.recordIP;
			String year, day, month;
			if (reAlarm.getRecordPath() != null) {
				String recordName = reAlarm.getRecordPath().trim();
				int n = recordName.indexOf("_");
				if (n != -1) {
					String[] name = recordName.subSequence(0, n).toString()
							.split("-");
					logger.info("name:"
							+ recordName.subSequence(0, n).toString()
							+ ";数组的长度:" + name.length);
					if (name.length == 4) {
						year = name[1];
						month = name[2];
						day = name[3];
					} else {
						year = name[0];
						month = name[1];
						day = name[2];
					}
					String recordPath = recordIP + year + month + "/" + year
							+ month + day + "/" + recordName; 
					logger.info("录音文件绝对路径为:" + recordPath);
					reAlarm.setRecordPath(recordPath);
				}
			}
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

}
