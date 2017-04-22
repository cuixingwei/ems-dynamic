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
import com.xhs.ems.bean.StopTaskReason;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.StopTaskReasonDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午2:58:06
 */
@Repository
public class StopTaskReasonDAOImpl implements StopTaskReasonDAO {
	private static final Logger logger = Logger
			.getLogger(StopTaskReasonDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日 下午2:58:06
	 * @see com.xhs.ems.dao.StopTaskReasonDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT dstr.`name` reason,COUNT(DISTINCT et.taskCode) times,'' rate from event_task et LEFT JOIN `event` e on e.eventCode=et.eventCode	"
				+ "LEFT JOIN define_stop_task_reason dstr on dstr.`code`=et.stopTaskReason	"
				+ "where e.eventProperty=1 and et.taskResult=1 and et.createTime  between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + "and et.stationCode=:station ";
		}
		sql = sql + " group by dstr.`name` ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());

		List<StopTaskReason> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<StopTaskReason>() {
					@Override
					public StopTaskReason mapRow(ResultSet rs, int index)
							throws SQLException {
						return new StopTaskReason(rs.getString("reason"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		int totaltimes = 0;
		// 计算总终止次数
		for (StopTaskReason result : results) {
			totaltimes += Integer.parseInt(result.getTimes());
		}
		// 计算比率
		for (StopTaskReason result : results) {
			result.setRate(CommonUtil.calculateRate(totaltimes,
					Integer.parseInt(result.getTimes())));
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
