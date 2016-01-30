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
import com.xhs.ems.bean.PatientReason;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.PatientReasonDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:41:13
 */
@Repository
public class PatientReasonDAOImpl implements PatientReasonDAO {
	private static final Logger logger = Logger
			.getLogger(PatientReasonDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午4:41:14
	 * @see com.xhs.ems.dao.PatientReasonDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct t.生成任务时刻,t.任务编码 into #task from AuSp120.tb_Task t  "
				+ "select ddr.NameM patientReason,COUNT(*) receivePeopleNumbers,'' rate 	"
				+ "from AuSp120.tb_DDiseaseReason ddr	"
				+ "left outer join AuSp120.tb_PatientCase pc  on ddr.Code=pc.病因编码 "
				+ "left outer join #task t on  pc.任务编码=t.任务编码 "
				+ "where t.生成任务时刻 between :startTime and :endTime	"
				+ "group by  ddr.NameM  drop table #task";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<PatientReason> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<PatientReason>() {
					@Override
					public PatientReason mapRow(ResultSet rs, int index)
							throws SQLException {
						return new PatientReason(rs.getString("patientReason"),
								rs.getString("receivePeopleNumbers"), rs
										.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		int totaltimes = 0;
		// 计算总终止次数
		for (PatientReason result : results) {
			totaltimes += Integer.parseInt(result.getReceivePeopleNumbers());
		}
		// 计算比率
		for (PatientReason result : results) {
			result.setRate(CommonUtil.calculateRate(totaltimes,
					Integer.parseInt(result.getReceivePeopleNumbers())));
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
