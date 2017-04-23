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
import com.xhs.ems.bean.HungEvent;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.HungEventDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午12:18:54
 */
@Repository
public class HungEventDAOImpl implements HungEventDAO {
	private static final Logger logger = Logger
			.getLogger(HungEventDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月15日 下午12:18:54
	 * @see com.xhs.ems.dao.HungEventDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT dsr.`name` hungReason,eh.eventAddress eventName,dht.`name` acceptType,"
				+ "date_format(eh.handleBeginTime,'%Y-%c-%d %h:%i:%s')  hungTime,	date_format(eh.handleEndTime,'%Y-%c-%d %h:%i:%s')  endTime,"
				+ "TIMESTAMPDIFF(SECOND,eh.handleBeginTime,eh.handleEndTime) hungtimes,u.personName dispatcher	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN `user` u on u.jobNum=eh.operatorJobNum	LEFT JOIN define_suspend_reason dsr on dsr.`code`=eh.suspendReason	"
				+ "LEFT JOIN define_handle_type dht on dht.code=eh.handleType	"
				+ "WHERE e.eventProperty=1 and eh.suspendReason is not null and eh.createTime  between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and eh.operatorJobNum= :dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getHungReason())) {
			sql = sql + " and eh.suspendReason = :hungReason ";
		}
		sql += " order by eh.createTime ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("hungReason", parameter.getHungReason());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("startTime", parameter.getStartTime());

		List<HungEvent> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<HungEvent>() {
					@Override
					public HungEvent mapRow(ResultSet rs, int index)
							throws SQLException {

						return new HungEvent(rs.getString("eventName"), rs
								.getString("acceptType"), rs
								.getString("dispatcher"), rs
								.getString("hungTime"), rs
								.getString("hungReason"), rs
								.getString("endTime"), rs
								.getString("hungtimes"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		for (HungEvent result : results) {
			result.setHungtimes(CommonUtil.formatSecond(result.getHungtimes()));
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
