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

	@Override
	public List<Dictionary> GetPatientDepartment() {
		String sql = "select * from AuSp120.tb_DDiseaseClass";
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
	public List<Dictionary> GetPatientReason() {
		String sql = "select * from AuSp120.tb_DDiseaseReason";
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
	public List<Dictionary> GetIllState() {
		String sql = "select * from AuSp120.tb_DILLState";
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
	public List<Dictionary> GetAidResult() {
		String sql = "select * from AuSp120.tb_DResult";
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
	public List<Dictionary> GetDeathProve() {
		String sql = "select * from AuSp120.tb_DDeathProve";
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
	public List<Dictionary> GetTakenPlaceType() {
		String sql = "select * from AuSp120.tb_DTakenPlaceType";
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
	public List<Dictionary> GetLocaleType() {
		String sql = "select * from AuSp120.tb_DLocaleType";
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
	public List<Dictionary> GetOutCome() {
		String sql = "select * from AuSp120.tb_DOutCome";
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
	public List<Dictionary> GetCooperate() {
		String sql = "select * from AuSp120.tb_DCooperate";
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
	public List<Dictionary> GetIdentity() {
		String sql = "select * from AuSp120.tb_DIdentity";
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
	public List<Dictionary> GetProfession() {
		String sql = "select * from AuSp120.tb_DProfession";
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
