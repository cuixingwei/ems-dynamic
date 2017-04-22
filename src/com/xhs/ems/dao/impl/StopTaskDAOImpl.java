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

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.StopTask;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.StopTaskDAO;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午3:20:46
 */
@Repository
public class StopTaskDAOImpl implements StopTaskDAO {
	private static final Logger logger = Logger
			.getLogger(StopTaskDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.dao.StopTaskDAO#getData(com.xhs.ems.bean.Parameter)
	 * @datetime 2015年4月10日 下午3:20:46
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT date_format(e.callTime,'%Y-%c-%d %h:%i:%s') acceptTime,e.eventAddress sickAddress,e.incomingCall phone,u.personName dispatcher,et.actualSign carCode,	"
				+ "date_format(et.createTime,'%Y-%c-%d %h:%i:%s') drivingTime,s.stationName staion,dttr.name stopReason,eh.remark,"
				+ "TIMESTAMPDIFF(SECOND,et.taskStopTime,et.taskAwaitTime) emptyRunTime	"
				+ "from `event` e LEFT JOIN event_history eh on eh.eventCode=e.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN `user` u on u.jobNum=eh.operatorJobNum	LEFT JOIN define_stop_task_reason dttr on dttr.`code`=et.stopTaskReason	"
				+ "LEFT JOIN station s on s.stationCode=et.stationCode	"
				+ "where e.eventProperty=1 and et.taskResult=1 and et.createTime  between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and eh.operatorJobNum = :dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getStopReason())) {
			sql = sql + " and et.stopTaskReason = :stopReason ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and et.stationCode =: station ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getCarCode())) {
			sql = sql + " and et.vehicleCode=:carCode ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getEmptyRunTime())) {
			sql = sql + " and TIMESTAMPDIFF(SECOND,et.taskStopTime,et.taskAwaitTime)>:emptyRunTime ";
		}
		sql += " order by et.createTime ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("station", parameter.getStation());
		paramMap.put("stopReason", parameter.getStopReason());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("carCode", parameter.getCarCode());

		List<StopTask> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<StopTask>() {
					@Override
					public StopTask mapRow(ResultSet rs, int index)
							throws SQLException {

						return new StopTask(rs.getString("acceptTime"), rs
								.getString("sickAddress"), rs
								.getString("phone"),
								rs.getString("dispatcher"), rs
										.getString("carCode"), rs
										.getString("drivingTime"), rs
										.getString("emptyRunTime"), rs
										.getString("staion"), rs
										.getString("stopReason"), rs
										.getString("remark"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		for (StopTask result : results) {
			result.setEmptyRunTime(CommonUtil.formatSecond(result
					.getEmptyRunTime()));
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
