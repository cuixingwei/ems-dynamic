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
import com.xhs.ems.bean.PatientDepartment;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.PatientDepartmentDAO;

/**
 * @datetime 2016年7月27日 下午1:04:55
 * @author 崔兴伟
 */
@Repository
public class PatientDepartmentDAOImpl implements PatientDepartmentDAO {

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select dc.NameM patientDepartment,COUNT(*) numbers,'' radio from AuSp120.tb_PatientCase pc  "
				+ "left outer join AuSp120.tb_DDiseaseClass dc on dc.Code=pc.疾病科别编码 "
				+ "left outer join AuSp120.tb_Ambulance  am on am.实际标识=pc.车辆标识 "
				+ "left outer join AuSp120.tb_Task t on  pc.任务编码=t.任务编码 and t.车辆编码=am.车辆编码	"
				+ "	where  pc.疾病科别编码 is not null and t.生成任务时刻 between :startTime and :endTime  ";
		sql += " group by dc.NameM";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("startTime", parameter.getStartTime());

		List<PatientDepartment> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<PatientDepartment>() {
					@Override
					public PatientDepartment mapRow(ResultSet rs, int index)
							throws SQLException {
						PatientDepartment patientDepartment = new PatientDepartment();
						patientDepartment.setNumbers(rs.getString("numbers"));
						patientDepartment.setPatientDepartment(rs.getString("patientDepartment"));
						patientDepartment.setRadio(rs.getString("radio"));
						return patientDepartment;
					}
				});
		PatientDepartment summary = new PatientDepartment();
		summary.setPatientDepartment("合计");
		summary.setRadio("100%");
		summary.setNumbers("0");
		Integer total = 0;
		System.out.println("记录数:" + results.size());
		for (PatientDepartment result : results) {
			System.out.println("summary:" + summary.getNumbers() + ";result:"
					+ result.getNumbers());
			total = Integer.parseInt(result.getNumbers()) + total;
		}
		summary.setNumbers(total + "");
		for (PatientDepartment result : results) {
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
