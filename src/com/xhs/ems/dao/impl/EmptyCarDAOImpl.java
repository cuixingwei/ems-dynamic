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

import com.xhs.ems.bean.EmptyCar;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.EmptyCarDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月14日 上午8:55:42
 */
@Repository
public class EmptyCarDAOImpl implements EmptyCarDAO {
	private static final Logger logger = Logger
			.getLogger(EmptyCarDAOImpl.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void SetDataSourceTag(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月14日 上午8:55:42
	 * @see com.xhs.ems.dao.EmptyCarDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT der.`name` emptyReason,date_format(e.callTime,'%Y-%c-%d %h:%i:%s') acceptTime,e.eventAddress sickAddress,	"
				+ "u.personName dispatcher,TIMESTAMPDIFF(SECOND,et.taskEmptyTime,et.taskAwaitTime) emptyRunTime	"
				+ "from `event` e LEFT JOIN event_history eh on eh.eventCode=e.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN define_empty_reason der on der.`code`=et.emptyVehicleReason	"
				+ "LEFT JOIN `user` u on u.jobNum=eh.operatorJobNum	"
				+ "where e.eventProperty=1 and et.taskResult=2 and et.createTime between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql += " and et.stationCode=:dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getEmptyReason())) {
			sql += " and et.emptyVehicleReason=:emptyCarReason ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql += " and et.stationCode=:station ";
		}
		sql += " order by et.createTime ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());
		paramMap.put("emptyCarReason", parameter.getEmptyReason());

		List<EmptyCar> results = this.namedParameterJdbcTemplate.query(sql,
				paramMap, new RowMapper<EmptyCar>() {
					@Override
					public EmptyCar mapRow(ResultSet rs, int index)
							throws SQLException {

						return new EmptyCar(rs.getString("acceptTime"), rs
								.getString("sickAddress"), rs
								.getString("dispatcher"), rs
								.getString("emptyRunTimes"), rs
								.getString("emptyReason"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		for (EmptyCar result : results) {
			result.setEmptyRunTimes(CommonUtil.formatSecond(result
					.getEmptyRunTimes()));
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
