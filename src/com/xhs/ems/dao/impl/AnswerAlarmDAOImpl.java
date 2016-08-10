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

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午8:37:26
	 * @see com.xhs.ems.dao.AnswerAlarmDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct t.事件编码,t.分站编码,s.分站名称 station  into #temp2 	"
				+ "from AuSp120.tb_TaskV t left outer join AuSp120.tb_Station s on s.分站编码=t.分站编码 "
				+ "select distinct e.事件编码 eventCode,m.姓名 dispatcher into #temp1	"
				+ "from AuSp120.tb_EventV e	left outer join AuSp120.tb_MrUser m on m.工号=e.调度员编码	"
				+ "where e.事件性质编码=1 and m.人员类型=0  select a.事件编码,a.受理序号,pc.姓名 into #pc 	"
				+ "from AuSp120.tb_PatientCase pc	left outer join AuSp120.tb_Ambulance am on am.实际标识=pc.车辆标识	"
				+ "left outer join AuSp120.tb_Task t on pc.任务编码=t.任务编码 and t.车辆编码=am.车辆编码	"
				+ "left outer join AuSp120.tb_AcceptDescript a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "select	事件编码,受理序号,姓名 = (stuff((select ',' + 姓名 from #pc where 事件编码 = pc.事件编码 and 受理序号=pc.受理序号 for xml path('')),1,1,''))  into #name	"
				+ "from #pc pc  group by pc.事件编码,pc.受理序号 "
				+ "select a.ID id,convert(varchar(20),a.电话振铃时刻,120) answerAlarmTime,a.呼救电话 alarmPhone,"
				+ "a.联系电话 relatedPhone,a.现场地址 siteAddress,	a.初步判断 judgementOnPhone, t2.station,"
				+ "convert(varchar(20),a.派车时刻,120) sendCarTime, t.dispatcher,et.录音文件名 recordPath,n.姓名 patientName	from #temp1 t	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on t.eventCode=a.事件编码 "
				+ "left outer join #temp2 t2 on t2.事件编码=t.eventCode	left outer join AuSp120.tb_EventTele et on et.事件编码=t.eventCode "
				+ "left outer join #name n on n.事件编码=a.事件编码 and a.受理序号=n.受理序号  "
				+ "where a.电话振铃时刻  between :startTime and :endTime  ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + "and a.调度员编码= :dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getAlarmPhone())) {
			sql += " and  a.呼救电话  like :alarmPhone ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getSiteAddress())) {
			sql += " and a.现场地址 like :siteAddress ";
		}
		sql += "order by a.电话振铃时刻  drop table #temp1,#temp2,#name,#pc";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("alarmPhone", "%" + parameter.getAlarmPhone() + "%");
		paramMap.put("siteAddress", "%" + parameter.getSiteAddress() + "%");

		logger.info(sql);

		List<AnswerAlarm> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<AnswerAlarm>() {
					@Override
					public AnswerAlarm mapRow(ResultSet rs, int index)
							throws SQLException {
						AnswerAlarm alarm = new AnswerAlarm();
						alarm.setId(rs.getString("id"));
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
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("alarmPhone", "%" + parameter.getAlarmPhone() + "%");

		logger.info(sql);

		List<AnswerAlarm> results = this.npJdbcTemplate.query(sql, paramMap,
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
