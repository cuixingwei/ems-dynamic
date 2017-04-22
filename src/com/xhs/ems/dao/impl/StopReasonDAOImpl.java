package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.StopReason;
import com.xhs.ems.dao.StopReasonDAO;

/**
 * @author CUIXINGWEI
 * @datetime 2015年4月10日 下午2:57:53
 */
@Repository
public class StopReasonDAOImpl implements StopReasonDAO {

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.dao.StopReasonDAO#getData()
	 * @datetime 2015年4月10日 下午2:57:53
	 */
	@Override
	public List<StopReason> getData() {
		String sql = "select * from define_stop_task_reason ";
		final List<StopReason> results = new ArrayList<StopReason>();
		this.npJdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				StopReason stopReason = new StopReason(rs.getString("code"), rs
						.getString("name"));
				results.add(stopReason);
			}
		});
		results.add(0, new StopReason("", "--请选择--"));
		return results;
	}
}
