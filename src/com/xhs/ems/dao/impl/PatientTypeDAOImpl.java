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

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.PatientType;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.PatientTypeDAO;

/**
 * @datetime 2016年5月18日 下午6:34:21
 * @author 崔兴伟
 */
@Repository
public class PatientTypeDAOImpl implements PatientTypeDAO {
	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select dt.NameM patientType,COUNT(*) numbers,'' radio from AuSp120.tb_PatientCase pc  "
				+ "left outer join AuSp120.tb_DDiseaseType dt on dt.Code=pc.疾病种类编码 "
				+ "left outer join AuSp120.tb_Ambulance  am on am.实际标识=pc.车辆标识 "
				+ "left outer join AuSp120.tb_Task t on  pc.任务编码=t.任务编码 and t.车辆编码=am.车辆编码	"
				+ "	where  pc.疾病种类编码 is not null and t.生成任务时刻 between :startTime and :endTime  ";
		if (!CommonUtil.isNullOrEmpty(parameter.getPatientTypeCode())) {
			sql = sql + " and pc.疾病种类编码= :patientTypeCode ";
		}
		sql += " group by dt.NameM";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("patientTypeCode", parameter.getPatientTypeCode());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("startTime", parameter.getStartTime());

		List<PatientType> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<PatientType>() {
					@Override
					public PatientType mapRow(ResultSet rs, int index)
							throws SQLException {
						PatientType patientType = new PatientType();
						patientType.setNumbers(rs.getString("numbers"));
						patientType.setPatientType(rs.getString("patientType"));
						patientType.setRadio(rs.getString("radio"));
						return patientType;
					}
				});
		PatientType summary = new PatientType();
		summary.setPatientType("合计");
		summary.setRadio("100%");
		summary.setNumbers("0");
		Integer total = 0;
		System.out.println("记录数:" + results.size());
		for (PatientType result : results) {
			System.out.println("summary:" + summary.getNumbers() + ";result:"
					+ result.getNumbers());
			total = Integer.parseInt(result.getNumbers()) + total;
		}
		summary.setNumbers(total + "");
		for (PatientType result : results) {
			result.setRadio(CommonUtil.calculateRate(
					Integer.parseInt(summary.getNumbers()), result.getNumbers()));
		}
		results.add(summary);

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
