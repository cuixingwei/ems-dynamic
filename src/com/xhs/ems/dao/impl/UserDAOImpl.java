package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.User;
import com.xhs.ems.dao.UserDAO;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	private static final Logger logger = Logger
			.getLogger(UserDAOImpl.class);
	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<User> validateMrUser(User user) {
		String sql = "select t.*,s.stationName from user as t LEFT JOIN station s on s.stationCode=t.unitCode "
				+ "where t.jobNum= :employeeId and t.deleteState= :isValid";
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(
				user);
		final List<User> results = new ArrayList<User>();
		logger.info("sql"+sql);
		this.npJdbcTemplate.query(sql, namedParameters,
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						User user = new User();
						user.setEmployeeId(rs.getString("jobNum"));
						user.setName(rs.getString("personName"));
						user.setPassword(rs.getString("password"));
						user.setStationName(rs.getString("stationName"));
						if (!(rs.getString("userType").equals("1") || rs.getString(
								"userType").equals("0")||rs.getString("userType").equals("2"))) {
							user.setIsValid(3);
						} else {
							user.setIsValid(0);
						}
						results.add(user);
					}
				});
		logger.info("list:"+results);
		return results;
	}

	@Override
	public List<User> getAvailableDispatcher() {
		String sql = "select * from user as t where t.userType= 1 and t.deleteState= 0";
		final List<User> results = new ArrayList<User>();
		this.npJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				User user = new User(rs.getString("jobNum"), rs.getString("personName"));
				results.add(user);
			}
		});
		return results;
	}

	@Override
	public int changePwd(User user) {
		String sql = "update user set password=:password where jobNum=:id";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("password", user.getPassword());
		paramMap.put("id", user.getEmployeeId());
		this.npJdbcTemplate.update(sql, paramMap);
		return this.npJdbcTemplate.update(sql, paramMap);
	}
}
