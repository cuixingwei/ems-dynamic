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
import com.xhs.ems.bean.SubstationLateVisit;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.SubstationLateVisitDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 下午8:17:04
 */
@Repository
public class SubstationLateVisitDAOImpl implements SubstationLateVisitDAO {
	private static final Logger logger = Logger
			.getLogger(SubstationLateVisitDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;
	private NamedParameterJdbcTemplate npJdbcTemplateSQLServer;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql,DataSource dataSourceSQLServer) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
		this.npJdbcTemplateSQLServer = new NamedParameterJdbcTemplate(dataSourceSQLServer);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月16日 下午8:17:04
	 * @see com.xhs.ems.dao.SubstationLateVisitDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());
		paramMap.put("carCode", parameter.getCarCode());
		String sql = "SELECT e.eventAddress siteAddress,det.`name` eventType,et.actualSign carCode,"
				+ "date_format(eh.handleBeginTime,'%Y-%c-%d %h:%i:%s') acceptTime,	date_format(et.createTime,'%Y-%c-%d %h:%i:%s') createTaskTime,"
				+ "date_format(et.taskDriveToTime,'%Y-%c-%d %h:%i:%s') outCarTime,	TIMESTAMPDIFF(SECOND,et.createTime,et.taskDriveToTime) outCarTimes,"
				+ "dtr.`name` taskResult,eh.remark,u.personName dispatcher	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN `user` u on u.jobNum=eh.operatorJobNum	LEFT JOIN define_task_result dtr on dtr.`code`=et.taskResult	"
				+ "LEFT JOIN define_event_type det on det.code=eh.handleType	"
				+ "WHERE e.eventProperty=1 and et.taskCode is not null  "
				+ "and e.createTime between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql += " and et.stationCode=:station ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getCarCode())) {
			sql += " and et.vehicleCode=:carCode ";
		}
		int second = 0;
		if (!CommonUtil.isNullOrEmpty(parameter.getOutCarTimesMin())) {
			second = 60 * Integer.parseInt(parameter.getOutCarTimesMin());
			String secondMin = second + "";
			paramMap.put("secondMin", secondMin);
			sql += " and TIMESTAMPDIFF(SECOND,et.createTime,et.taskDriveToTime)> :secondMin ";
			logger.info("outCarTimesMin:" + parameter.getOutCarTimesMin());
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getOutCarTimesMax())) {
			second = 60 * Integer.parseInt(parameter.getOutCarTimesMax());
			String secondMax = second + "";
			paramMap.put("secondMax", secondMax);
			sql += " and TIMESTAMPDIFF(SECOND,et.createTime,et.taskDriveToTime) <= :secondMax ";
			logger.info("outCarTimesMax:" + parameter.getOutCarTimesMax());
		}
		
		sql += " order by e.createTime ";

		List<SubstationLateVisit> results = this.npJdbcTemplate
				.query(sql, paramMap, new RowMapper<SubstationLateVisit>() {
					@Override
					public SubstationLateVisit mapRow(ResultSet rs, int index)
							throws SQLException {

						return new SubstationLateVisit(rs
								.getString("siteAddress"), rs
								.getString("eventType"), rs
								.getString("carCode"), rs
								.getString("acceptTime"), rs
								.getString("createTaskTime"), rs
								.getString("outCarTime"), rs
								.getString("outCarTimes"), rs
								.getString("taskResult"), rs
								.getString("remark"), rs
								.getString("dispatcher"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		for (SubstationLateVisit result : results) {
			result.setOutCarTimes(CommonUtil.formatSecond(result
					.getOutCarTimes()));
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
