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

import com.xhs.ems.bean.Accident;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.AccidentDAO;

@Repository
public class AccidentDAOImpl implements AccidentDAO {
	private static final Logger logger = Logger
			.getLogger(AccidentDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select e.事件编码 eventCode,ac.事发时间 eventTime,e.事件名称 eventName,e.呼救电话 callPhone,"
				+ "m.姓名 dispatcher	from AuSp120.tb_AccidentEventLink ael	"
				+ "left outer join AuSp120.tb_Accident ac on ac.事故编码=ael.事故编码	"
				+ "left outer join AuSp120.tb_Event e on e.事件编码=ael.事件编码	"
				+ "left outer join AuSp120.tb_MrUser m on e.调度员编码=m.工号	"
				+ "where e.事件性质编码=1 and ac.事发时间 between :startTime and :endTime ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<Accident> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<Accident>() {
					@Override
					public Accident mapRow(ResultSet rs, int index)
							throws SQLException {

						return new Accident(rs.getString("eventCode"), rs
								.getString("eventTime"), rs
								.getString("eventName"), rs
								.getString("callPhone"), rs
								.getString("dispatcher"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

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
