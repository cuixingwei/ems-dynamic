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

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	/**
	 * @author cuixingwei
	 * @datetime 2017年1月9日下午9:30:29
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT s.分站名称 station, SUM(case when t.任务编码 is not null then 1 else 0 end) totalCount,	"
				+ "SUM(case when DATEDIFF(ss,t.生成任务时刻, m.接收时刻)> :overtimes then 1 else 0 end) lateReturn,	"
				+ "SUM(case when DATEDIFF(ss,t.生成任务时刻, m.接收时刻)<= :overtimes then 1 else 0 end) normalReturn,"
				+ "SUM(case when m.任务编码 is null then 1 else 0 end) noReturn "
				+ "FROM  AuSp120.tb_EventV AS e LEFT OUTER JOIN AuSp120.tb_TaskV AS t ON t.事件编码 = e.事件编码 AND e.事件性质编码 = 1 "
				+ "LEFT OUTER JOIN  AuSp120.tb_StationMsg AS m ON m.任务编码 = t.任务编码 LEFT OUTER JOIN   "
				+ "AuSp120.tb_Station AS s ON s.分站编码 = t.分站编码       "
				+ "where s.分站名称 is not null   and e.受理时刻 between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and t.分站编码=:station ";
		}
		sql += "  group by s.分站名称  ";
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
