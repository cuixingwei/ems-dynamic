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
import com.xhs.ems.bean.StationMsg;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.StationMsgDAO;

/**
 * @author cuixingwei
 * @datetime 2017年1月9日下午9:30:11
 */
@Repository
public class StationMsgDAOImpl implements StationMsgDAO {
	private static final Logger logger = Logger
			.getLogger(StationMsgDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;
	private NamedParameterJdbcTemplate npJdbcTemplateSQLServer;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql,DataSource dataSourceSQLServer) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
		this.npJdbcTemplateSQLServer = new NamedParameterJdbcTemplate(dataSourceSQLServer);
	}
	/**
	 * @author cuixingwei
	 * @datetime 2017年1月9日下午9:30:29
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT s.stationName station,sum(if(et.taskCode is not null,1,0)) totalCount,	"
				+ "sum(if(TIMESTAMPDIFF(SECOND,et.createTime,et.stationAcceptTime)<=:overtimes or TIMESTAMPDIFF(SECOND,et.createTime,et.stationRefuseTime)<=:overtimes,1,0)) normalReturn,	"
				+ "sum(if(TIMESTAMPDIFF(SECOND,et.createTime,et.stationAcceptTime)>:overtimes or TIMESTAMPDIFF(SECOND,et.createTime,et.stationRefuseTime)>:overtimes,1,0)) lateReturn,	"
				+ "sum(if(et.stationAcceptTime is null and et.stationRefuseTime is null,1,0)) noReturn	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN station s on s.stationCode=et.stationCode	"
				+ "WHERE e.eventProperty=1   and eh.createTime between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and et.stationCode=:station ";
		}
		sql += "  group by s.stationName  ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("overtimes", parameter.getOvertimes());

		List<StationMsg> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<StationMsg>() {
					@Override
					public StationMsg mapRow(ResultSet rs, int index)
							throws SQLException {
						StationMsg stationMsg = new StationMsg();
						stationMsg.setLateReturn(rs.getString("lateReturn"));
						stationMsg.setNormalReturn(rs.getString("normalReturn"));
						stationMsg.setStation(rs.getString("station"));
						stationMsg.setTotalCount(rs.getString("totalCount"));
						stationMsg.setNoReturn(rs.getString("noReturn"));
						return stationMsg;
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		logger.info(sql);
		StationMsg summary = new StationMsg();
		summary.setLateReturn("0");
		summary.setNormalReturn("0");
		summary.setStation("合计");
		summary.setTotalCount("0");
		summary.setNoReturn("0");
		for (StationMsg result : results) {
			summary.setLateReturn(Integer.parseInt(result.getLateReturn())+Integer.parseInt(summary.getLateReturn())+"");
			summary.setNormalReturn(Integer.parseInt(result.getNormalReturn())+Integer.parseInt(summary.getNormalReturn())+"");
			summary.setTotalCount(Integer.parseInt(result.getTotalCount())+Integer.parseInt(summary.getTotalCount())+"");
			summary.setNoReturn(Integer.parseInt(result.getNoReturn())+Integer.parseInt(summary.getNoReturn())+"");
		}
		results.add(summary);
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
