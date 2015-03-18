package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.RingToAccept;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.RingToAcceptDAO;

@Repository("ringToAcceptDAO")
public class RingToAcceptDAOImpl implements RingToAcceptDAO {

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<RingToAccept> getData(String overtimes, String dispatcher,
			String startTime, String endTime) {
		String sql = "select  m.姓名 dispatcher,convert(varchar(20),a.电话振铃时刻,120) ringTime,convert(varchar(20),a.开始受理时刻,120) callTime,"
				+ "datediff(Second,a.电话振铃时刻,a.开始受理时刻) ringDuration,a.受理台号 acceptCode,a.备注  acceptRemark "
				+ "from AuSp120.tb_AcceptDescriptV a "
				+ "left outer join AuSp120.tb_MrUser m on a.调度员编码=m.工号 "
				+ "left outer join AuSp120.tb_EventV e on a.事件编码=e.事件编码 "
				+ "where e.事件性质编码=1  and datediff(Second,a.电话振铃时刻,a.开始受理时刻)>:overtimes and  m.人员类型=0 and a.电话振铃时刻<a.开始受理时刻 and a.开始受理时刻>=:startTime and a.开始受理时刻<:endTime ";
		if (!CommonUtil.isNullOrEmpty(dispatcher)) {
			sql = sql + " and a.调度员编码=:dispatcher ";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("overtimes", overtimes);
		paramMap.put("dispatcher", dispatcher);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		
		List<RingToAccept> results = this.npJdbcTemplate.query(sql, paramMap, new RowMapper<RingToAccept>() {
			@Override
					public RingToAccept mapRow(ResultSet rs, int index)
							throws SQLException {

						return new RingToAccept(rs.getString("dispatcher"), rs
								.getString("ringTime"), rs
								.getString("callTime"), rs
								.getString("ringDuration"), rs
								.getString("acceptCode"), rs
								.getString("acceptRemark"));
					}
				});
		System.out.println(results.size());
		return results;
	}
}
