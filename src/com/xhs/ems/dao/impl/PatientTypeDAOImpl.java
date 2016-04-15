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
import com.xhs.ems.dao.PatientTypeDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午5:01:13
 */
@Repository
public class PatientTypeDAOImpl implements PatientTypeDAO {

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午5:01:13
	 * @see com.xhs.ems.dao.PatientTypeDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select s.分站名称 station,SUM(case when pc.分类统计编码=1 then 1 else 0 end) type1,	"
				+ "SUM(case when pc.分类统计编码=2 then 1 else 0 end) type2,"
				+ "SUM(case when pc.分类统计编码=3 then 1 else 0 end) type3,	"
				+ "SUM(case when pc.分类统计编码=4 then 1 else 0 end) type4,"
				+ "SUM(case when pc.分类统计编码=5 then 1 else 0 end) type5,	"
				+ "SUM(case when pc.分类统计编码=6 then 1 else 0 end) type6,"
				+ "SUM(case when pc.分类统计编码=7 then 1 else 0 end) type7,	"
				+ "SUM(case when pc.分类统计编码=8 then 1 else 0 end) type8,"
				+ "SUM(case when pc.分类统计编码=9 then 1 else 0 end) type9,	"
				+ "SUM(case when pc.分类统计编码=10 then 1 else 0 end) type10,"
				+ "SUM(case when pc.分类统计编码=11 then 1 else 0 end) type11,	"
				+ "SUM(case when pc.分类统计编码=12 then 1 else 0 end) type12,"
				+ "SUM(case when pc.分类统计编码=13 then 1 else 0 end) type13,	"
				+ "SUM(case when pc.分类统计编码=14 then 1 else 0 end) type14,"
				+ "SUM(case when pc.分类统计编码=15 then 1 else 0 end) type15,	"
				+ "SUM(case when pc.分类统计编码=16 then 1 else 0 end) type16,"
				+ "SUM(case when pc.分类统计编码=17 then 1 else 0 end) type17,	"
				+ "SUM(case when pc.分类统计编码=18 then 1 else 0 end) type18,"
				+ "SUM(case when pc.分类统计编码=19 then 1 else 0 end) type19,	"
				+ "SUM(case when pc.分类统计编码=20 then 1 else 0 end) type20,"
				+ "SUM(case when pc.分类统计编码=21 then 1 else 0 end) type21,	COUNT(*) total	"
				+ "from AuSp120.tb_PatientCase pc	"
				+ "left outer join AuSp120.tb_Ambulance  am on am.实际标识=pc.车辆标识 "
				+ "left outer join AuSp120.tb_Task t on  pc.任务编码=t.任务编码 and pc.车辆标识=am.实际标识	"
				+ "left outer join AuSp120.tb_DDiseaseClassState ddcs on ddcs.Code=pc.分类统计编码	"
				+ "left outer join AuSp120.tb_Station s on s.分站编码=pc.分站编码	"
				+ "where t.生成任务时刻 between :startTime and :endTime	group by s.分站名称 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<PatientType> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<PatientType>() {
					@Override
					public PatientType mapRow(ResultSet rs, int index)
							throws SQLException {
						PatientType patientType = new PatientType();
						patientType.setStation(rs.getString("station"));
						patientType.setTotal(rs.getString("total"));
						patientType.setType1(rs.getString("type1"));
						patientType.setType2(rs.getString("type2"));
						patientType.setType3(rs.getString("type3"));
						patientType.setType4(rs.getString("type4"));
						patientType.setType5(rs.getString("type5"));
						patientType.setType6(rs.getString("type6"));
						patientType.setType7(rs.getString("type7"));
						patientType.setType8(rs.getString("type8"));
						patientType.setType9(rs.getString("type9"));
						patientType.setType10(rs.getString("type10"));
						patientType.setType11(rs.getString("type11"));
						patientType.setType12(rs.getString("type12"));
						patientType.setType13(rs.getString("type13"));
						patientType.setType14(rs.getString("type14"));
						patientType.setType15(rs.getString("type15"));
						patientType.setType16(rs.getString("type16"));
						patientType.setType17(rs.getString("type17"));
						patientType.setType18(rs.getString("type18"));
						patientType.setType19(rs.getString("type19"));
						patientType.setType20(rs.getString("type20"));
						patientType.setType21(rs.getString("type21"));
						return patientType;
					}
				});

		PatientType sumary = new PatientType("合计", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
		sumary.setStation("合计");
		for (PatientType result : results) {
			sumary.setTotal(Integer.toString(Integer.parseInt(result.getTotal())+Integer.parseInt(sumary.getTotal())));
			sumary.setType1(Integer.toString(Integer.parseInt(result.getType1())+Integer.parseInt(sumary.getType1())));
			sumary.setType2(Integer.toString(Integer.parseInt(result.getType2())+Integer.parseInt(sumary.getType2())));
			sumary.setType3(Integer.toString(Integer.parseInt(result.getType3())+Integer.parseInt(sumary.getType3())));
			sumary.setType4(Integer.toString(Integer.parseInt(result.getType4())+Integer.parseInt(sumary.getType4())));
			sumary.setType5(Integer.toString(Integer.parseInt(result.getType5())+Integer.parseInt(sumary.getType5())));
			sumary.setType6(Integer.toString(Integer.parseInt(result.getType6())+Integer.parseInt(sumary.getType6())));
			sumary.setType7(Integer.toString(Integer.parseInt(result.getType7())+Integer.parseInt(sumary.getType7())));
			sumary.setType8(Integer.toString(Integer.parseInt(result.getType8())+Integer.parseInt(sumary.getType8())));
			sumary.setType9(Integer.toString(Integer.parseInt(result.getType9())+Integer.parseInt(sumary.getType9())));
			sumary.setType10(Integer.toString(Integer.parseInt(result.getType10())+Integer.parseInt(sumary.getType10())));
			sumary.setType11(Integer.toString(Integer.parseInt(result.getType11())+Integer.parseInt(sumary.getType11())));
			sumary.setType12(Integer.toString(Integer.parseInt(result.getType12())+Integer.parseInt(sumary.getType12())));
			sumary.setType13(Integer.toString(Integer.parseInt(result.getType13())+Integer.parseInt(sumary.getType13())));
			sumary.setType14(Integer.toString(Integer.parseInt(result.getType14())+Integer.parseInt(sumary.getType14())));
			sumary.setType15(Integer.toString(Integer.parseInt(result.getType15())+Integer.parseInt(sumary.getType15())));
			sumary.setType16(Integer.toString(Integer.parseInt(result.getType16())+Integer.parseInt(sumary.getType16())));
			sumary.setType17(Integer.toString(Integer.parseInt(result.getType17())+Integer.parseInt(sumary.getType17())));
			sumary.setType18(Integer.toString(Integer.parseInt(result.getType18())+Integer.parseInt(sumary.getType18())));
			sumary.setType19(Integer.toString(Integer.parseInt(result.getType19())+Integer.parseInt(sumary.getType19())));
			sumary.setType20(Integer.toString(Integer.parseInt(result.getType20())+Integer.parseInt(sumary.getType20())));
			sumary.setType21(Integer.toString(Integer.parseInt(result.getType21())+Integer.parseInt(sumary.getType21())));
		}
		results.add(sumary);
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
