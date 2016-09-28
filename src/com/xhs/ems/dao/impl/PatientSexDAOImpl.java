package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.PatientSex;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.PatientSexDAO;

/**
 * @datetime 2016年5月18日 下午5:28:07
 * @author 崔兴伟
 */
@Repository
public class PatientSexDAOImpl implements PatientSexDAO {

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<PatientSex> getData(Parameter parameter) {
		String sql = "select ddc.NameM class,'' typeName,DATENAME(YEAR,a.开始受理时刻) year,pc.性别 into #temp1	"
				+ "from AuSp120.tb_PatientCase pc	left outer join AuSp120.tb_Ambulance am on am.实际标识=pc.车辆标识	"
				+ "left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and am.车辆编码=t.车辆编码	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_EventV e on t.事件编码=e.事件编码	"
				+ "left outer join AuSp120.tb_DDiseaseClassState ddc on ddc.Code=pc.分类统计编码	"
				+ "where pc.分类统计编码 is not null and e.事件性质编码=1 and a.开始受理时刻 between :startTime and :endTime ";
		Map<String, String> paramMap = new HashMap<String, String>();
		if (!CommonUtil.isNullOrEmpty(parameter.getPatientClassCode())) {
			sql = sql + " and pc.分类统计编码= :patientClass";
			paramMap.put("patientClass", parameter.getPatientClassCode());
		}
		sql += " select tt.year+'年' time,SUM(case when tt.性别='男' then 1 else 0 end) male,"
				+ "SUM(case when tt.性别='女' then 1 else 0 end) female,	COUNT(*) total,'' radio   	"
				+ "from #temp1 tt	group by tt.year	order by tt.year ; drop table #temp1";
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		final List<PatientSex> results = new ArrayList<PatientSex>();
		this.npJdbcTemplate.query(sql, paramMap, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				PatientSex patientSex = new PatientSex();
				patientSex.setFemale(rs.getString("female"));
				patientSex.setMale(rs.getString("male"));
				patientSex.setRadio(rs.getString("radio"));
				patientSex.setTime(rs.getString("time"));
				patientSex.setTotal(rs.getString("total"));
				results.add(patientSex);
			}
		});
		for(PatientSex data : results){
			data.setRadio(CommonUtil.calculateToRate(data.getMale(), data.getFemale()));
		}
		return results;
	}

}
