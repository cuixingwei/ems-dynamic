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
		String sql = "select ddr.NameM patientReason,COUNT(*) receivePeopleNumbers,'' rate "
				+ "from AuSp120.tb_PatientCase pc	left outer join AuSp120.tb_Ambulance am on am.实际标识=pc.actualSign	"
				+ "left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and am.车辆编码=t.车辆编码	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_DDiseaseReason ddr on ddr.Code=pc.patientReasonCode	"
				+ "where pc.patientReasonCode is not null and e.事件性质编码=1 and a.开始受理时刻 between :startTime and :endTime group by ddr.NameM ";
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
