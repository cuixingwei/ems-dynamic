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

import com.xhs.ems.bean.DriverWork;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.DriverWorkDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:01:17
 */
@Repository
public class DriverWorkDAOImpl implements DriverWorkDAO {

	private static final Logger logger = Logger
			.getLogger(DriverWorkDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午4:01:17
	 * @see com.xhs.ems.dao.DriverWorkDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT s.stationName station,et.driverName driver,COUNT(DISTINCT et.taskCode) outCarNumbers,	"
				+ "avg(TIMESTAMPDIFF(SECOND,et.createTime,et.taskDriveToTime)) averageOutCarTimes,sum(if(et.taskResult=1,1,0)) stopNumbers,	"
				+ "avg(TIMESTAMPDIFF(SECOND,et.createTime,et.taskArriveTime)) averageArriveSpotTimes,sum(if(et.taskResult=2,1,0)) emptyNumbers,	"
				+ "sum(if(et.taskResult=3,1,0)) nomalNumbers,sum(if(et.taskResult=4,1,0)) refuseNumbers	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN station s on s.stationCode=et.stationCode	"
				+ "WHERE e.eventProperty=1 and et.taskCode is not null and s.stationName is not null "
				+ "and e.createTime  between :startTime and :endTime  ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and et.stationCode=:station ";
		}
		
		sql += " GROUP BY s.stationName,et.driverName";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("station", parameter.getStation());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("startTime", parameter.getStartTime());
		logger.info(sql);

		List<DriverWork> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<DriverWork>() {
					@Override
					public DriverWork mapRow(ResultSet rs, int index)
							throws SQLException {
						DriverWork driverWork = new DriverWork();
						driverWork.setAverageArriveSpotTimes(rs.getString("averageArriveSpotTimes"));
						driverWork.setAverageOutCarTimes(rs.getString("averageOutCarTimes"));
						driverWork.setDriver(rs.getString("driver"));
						driverWork.setEmptyNumbers(rs.getString("emptyNumbers"));
						driverWork.setNomalNumbers(rs.getString("nomalNumbers"));
						driverWork.setOutCarNumbers(rs.getString("outCarNumbers"));
//						driverWork.setPauseNumbers(rs.getString("pauseNumbers"));
						driverWork.setRefuseNumbers(rs.getString("refuseNumbers"));
						driverWork.setStation(rs.getString("station"));
						driverWork.setStopNumbers(rs.getString("stopNumbers"));
						return driverWork;
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		for (DriverWork result : results) {
			result.setAverageArriveSpotTimes(CommonUtil.formatSecond(result
					.getAverageArriveSpotTimes()));
			result.setAverageOutCarTimes(CommonUtil.formatSecond(result
					.getAverageOutCarTimes()));
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
