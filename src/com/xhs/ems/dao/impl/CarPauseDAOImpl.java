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

import com.xhs.ems.bean.CarPause;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.CarPauseDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午4:27:14
 */
@Repository
public class CarPauseDAOImpl implements CarPauseDAO {
	private static final Logger logger = Logger
			.getLogger(CarPauseDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;
	private NamedParameterJdbcTemplate npJdbcTemplateSQLServer;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql,DataSource dataSourceSQLServer) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
		this.npJdbcTemplateSQLServer = new NamedParameterJdbcTemplate(dataSourceSQLServer);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日 下午4:27:14
	 * @see com.xhs.ems.dao.CarPauseDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT dpr.name pauseReason,vpl.actualSign carCode,vpl.operatorName dispatcher,"
				+ "date_format(vpl.createTime,'%Y-%c-%d %h:%i:%s') pauseTime,vpl.operatorType operatorType ,	date_format(vpl.stateChangeTime,'%Y-%c-%d %h:%i:%s') endTime,"
				+ "TIMESTAMPDIFF(SECOND,vpl.createTime,vpl.stateChangeTime) pauseTimes,vpl.driverName driver	from vehicle_pause_log vpl "
				+ "LEFT JOIN define_pause_reason dpr  on vpl.pauseReasonCode=dpr.`code` "
				+ "where vpl.createTime between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + "and vpl.operatorJobNum= :dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and vpl.stationCode=:station ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getCarCode())) {
			sql = sql + " and vpl.vehicleCode=:carCode ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getPauseReason())) {
			sql = sql + " and vpl.pauseReasonCode=:pauseReason ";
		}
		sql += " order by vpl.createTime ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("carCode", parameter.getCarCode());
		paramMap.put("pauseReason", parameter.getPauseReason());

		List<CarPause> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<CarPause>() {
					@Override
					public CarPause mapRow(ResultSet rs, int index)
							throws SQLException {
						CarPause carPause = new CarPause();
						carPause.setCarCode(rs.getString("carCode"));
						carPause.setDispatcher(rs.getString("dispatcher"));
						carPause.setEndTime(rs.getString("endTime"));
						carPause.setOperatorType(rs.getString("operatorType"));
						carPause.setPauseReason(rs.getString("pauseReason"));
						carPause.setDriver(rs.getString("driver"));
						carPause.setPauseTime(rs.getString("pauseTime"));
						carPause.setPauseTimes(rs.getString("pauseTimes"));
						return carPause;
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (CarPause result : results) {
			result.setPauseTimes(CommonUtil.formatSecond(result.getPauseTimes()));
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
