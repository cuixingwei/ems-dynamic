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

import com.xhs.ems.bean.CarWork;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.CarWorkDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午12:23:26
 */
@Repository
public class CarWorkDAOImpl implements CarWorkDAO {

	private static final Logger logger = Logger.getLogger(CarWorkDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;
	private NamedParameterJdbcTemplate npJdbcTemplateSQLServer;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql, DataSource dataSourceSQLServer) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
		this.npJdbcTemplateSQLServer = new NamedParameterJdbcTemplate(dataSourceSQLServer);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午12:23:26
	 * @see com.xhs.ems.dao.CarWorkDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT s.stationName station,et.actualSign carCode,COUNT(DISTINCT et.taskCode) outCarNumbers,	"
				+ "round(avg(TIMESTAMPDIFF(SECOND,et.createTime,et.taskDriveToTime)),0) averageOutCarTimes,"
				+ "sum(if(et.taskArriveTime is not null,1,0)) arriveSpotNumbers,	"
				+ "round(avg(TIMESTAMPDIFF(SECOND,et.createTime,et.taskArriveTime)),0) averageArriveSpotTimes	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN station s on s.stationCode=et.stationCode	"
				+ "WHERE e.eventProperty=1 and et.taskCode is not null and s.stationName is not null and e.createTime between :startTime and :endTime ";
		String sqlPause = "SELECT  s.stationName station,vpl.actualSign carCode,COUNT(*) pauseNumbers	"
				+ "from vehicle_pause_log vpl	LEFT JOIN station s on s.stationCode=vpl.stationCode "
				+ "where vpl.createTime between :startTime and :endTime  GROUP BY s.stationName,vpl.actualSign ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and et.stationCode=:station ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getCarCode())) {
			sql = sql + " and et.vehicleCode=:carCode ";
		}
		sql += " GROUP BY s.stationName,et.actualSign";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());
		paramMap.put("carCode", parameter.getCarCode());

		List<CarWork> results = this.npJdbcTemplate.query(sql, paramMap, new RowMapper<CarWork>() {
			@Override
			public CarWork mapRow(ResultSet rs, int index) throws SQLException {
				CarWork carWork = new CarWork();
				carWork.setArriveSpotNumbers(rs.getString("arriveSpotNumbers"));
				carWork.setAverageArriveSpotTimes(rs.getString("averageArriveSpotTimes"));
				carWork.setAverageOutCarTimes(rs.getString("averageOutCarTimes"));
				carWork.setCarCode(rs.getString("carCode"));
				carWork.setStation(rs.getString("station"));
				carWork.setOutCarNumbers(rs.getString("outCarNumbers"));
				return carWork;
			}
		});
		List<CarWork> resultPause = this.npJdbcTemplate.query(sqlPause, paramMap, new RowMapper<CarWork>() {
			@Override
			public CarWork mapRow(ResultSet rs, int index) throws SQLException {
				CarWork carWork = new CarWork();
				carWork.setCarCode(rs.getString("carCode"));
				carWork.setStation(rs.getString("station"));
				carWork.setPauseNumbers(rs.getString("pauseNumbers"));
				return carWork;
			}
		});
		logger.info(sql + "-------" + sqlPause);
		for (CarWork result : results) {
			for (CarWork rPause : resultPause) {
				if (result.getStation().equals(rPause.getStation())
						&& result.getCarCode().equals(rPause.getCarCode())) {
					result.setPauseNumbers(rPause.getPauseNumbers());
				}
			}
		}
		for (CarWork result : results) {
			result.setAverageArriveSpotTimes(CommonUtil.formatSecond(result.getAverageArriveSpotTimes()));
			result.setAverageOutCarTimes(CommonUtil.formatSecond(result.getAverageOutCarTimes()));
		}
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

}
