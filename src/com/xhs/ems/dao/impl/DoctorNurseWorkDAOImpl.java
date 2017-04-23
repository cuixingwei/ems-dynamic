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

import com.xhs.ems.bean.DoctorNurseWork;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.DoctorNurseWorkDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午4:43:45
 */
@Repository
public class DoctorNurseWorkDAOImpl implements DoctorNurseWorkDAO {
	private static final Logger logger = Logger
			.getLogger(DoctorNurseWorkDAOImpl.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void SetDataSourceTag(DataSource dataSourceMysql) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSourceMysql);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月22日 下午4:43:45
	 * @see com.xhs.ems.dao.DoctorNurseWorkDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql1 = " SELECT s.stationName station,et.doctorName name,COUNT(DISTINCT et.taskCode) outCarNumbers,	"
				+ "avg(TIMESTAMPDIFF(SECOND,et.taskArriveTime,et.finishTime)) averateCureTimes,sum(if(et.taskResult in (1,2),1,0)) stopNumbers,	"
				+ "sum(if(et.taskResult=3,1,0)) validOutCarNumbers,sum(if(et.taskResult=4,1,0)) refuseNumbers,	"
				+ "sum(if(eh.patientNum is not null or eh.patientNum<>'',eh.patientNum,0)) curePeopleNumbers	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN station s on s.stationCode=et.stationCode	"
				+ "WHERE e.eventProperty=1 and et.taskCode is not null and s.stationName is not null "
				+ "and e.createTime between :startTime and :endTime ";
		String sql2 = " SELECT s.stationName station,et.nurseName name,COUNT(DISTINCT et.taskCode) outCarNumbers,	"
				+ "avg(TIMESTAMPDIFF(SECOND,et.taskArriveTime,et.finishTime)) averateCureTimes,sum(if(et.taskResult in (1,2),1,0)) stopNumbers,	"
				+ "sum(if(et.taskResult=3,1,0)) validOutCarNumbers,sum(if(et.taskResult=4,1,0)) refuseNumbers,	"
				+ "sum(if(eh.patientNum is not null or eh.patientNum<>'',eh.patientNum,0)) curePeopleNumbers	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN station s on s.stationCode=et.stationCode	"
				+ "WHERE e.eventProperty=1 and et.taskCode is not null and s.stationName is not null "
				+ "and e.createTime between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql1 += " where et.stationCode = :station  ";
			sql2 += " where et.stationCode = :station  ";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());

		String sql = "";
		String doctorOrNurse = "1";
		if (!CommonUtil.isNullOrEmpty(parameter.getDoctorOrNurse())) {
			doctorOrNurse = parameter.getDoctorOrNurse();
		}
		logger.info(doctorOrNurse);
		if ("1".equals(doctorOrNurse)) {
			sql = sql1 + " GROUP BY s.stationName,et.doctorName order by s.stationName,et.doctorName";
			logger.info("医生");
		} else if ("2".equals(doctorOrNurse)) {
			sql = sql2 + " GROUP BY s.stationName,et.nurseName order by s.stationName,et.nurseName";
			logger.info("护士");
		}

		List<DoctorNurseWork> results = this.namedParameterJdbcTemplate.query(
				sql, paramMap, new RowMapper<DoctorNurseWork>() {
					@Override
					public DoctorNurseWork mapRow(ResultSet rs, int index)
							throws SQLException {

						return new DoctorNurseWork(rs.getString("station"), rs
								.getString("name"), rs
								.getString("outCarNumbers"), rs
								.getString("validOutCarNumbers"), rs
								.getString("stopNumbers"), rs
								.getString("curePeopleNumbers"), rs
								.getString("averateCureTimes"),rs.getString("refuseNumbers"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (DoctorNurseWork result : results) {
			result.setAverateCureTimes(CommonUtil.formatSecond(result
					.getAverateCureTimes()));
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
