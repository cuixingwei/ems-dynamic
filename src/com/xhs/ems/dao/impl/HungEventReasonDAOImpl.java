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
import com.xhs.ems.bean.HungEventReason;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.HungEventReasonDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午2:09:40
 */
@Repository
public class HungEventReasonDAOImpl implements HungEventReasonDAO {
	private static final Logger logger = Logger
			.getLogger(HungEventReasonDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午2:09:40
	 * @see com.xhs.ems.dao.HungEventReasonDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT dsr.`name` reason,COUNT(DISTINCT eh.eventCode) times,'' rate	from `event` e "
				+ "LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN define_suspend_reason dsr on dsr.`code`=eh.suspendReason	"
				+ "WHERE e.eventProperty=1 and eh.suspendReason is not null and eh.createTime	";
		if (!CommonUtil.isNullOrEmpty(parameter.getHungReason())) {
			sql += " and eh.suspendReason =:hungReason";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql += " and eh.operatorJobNum=:dispatcher";
		}
		sql += " group by dsr.`name` ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("hungReason", parameter.getHungReason());
		paramMap.put("dispatcher", parameter.getDispatcher());

		List<HungEventReason> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<HungEventReason>() {
					@Override
					public HungEventReason mapRow(ResultSet rs, int index)
							throws SQLException {
						return new HungEventReason(rs.getString("reason"), rs
								.getString("times"), rs.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		int totaltimes = 0;
		// 计算总终止次数
		for (HungEventReason result : results) {
			totaltimes += Integer.parseInt(result.getTimes());
		}
		// 计算比率
		for (HungEventReason result : results) {
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
