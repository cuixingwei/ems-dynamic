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

import com.xhs.ems.bean.Dictionary;
import com.xhs.ems.dao.DictionaryDAO;

/**
 * @datetime 2016年5月18日 下午5:07:44
 * @author 崔兴伟
 */
@Repository
public class dictionaryDAOImpl implements DictionaryDAO {
	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<Dictionary> getPatientClass() {
		String sql = "select * from AuSp120.tb_DDiseaseClassState";
		final List<Dictionary> results = new ArrayList<Dictionary>();
		this.npJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				Dictionary dictionary = new Dictionary(rs.getString("Code"), rs
						.getString("NameM"));
				results.add(dictionary);
			}
		});
		results.add(0, new Dictionary("", "--请选择--"));
		return results;
	}

	@Override
	public List<Dictionary> GetPatientType() {
		String sql = "select * from AuSp120.tb_DDiseaseType";
		final List<Dictionary> results = new ArrayList<Dictionary>();
		this.npJdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				Dictionary dictionary = new Dictionary(rs.getString("Code"), rs
						.getString("NameM"));
				results.add(dictionary);
			}
		});
		results.add(0, new Dictionary("", "--请选择--"));
		return results;
	}

}
