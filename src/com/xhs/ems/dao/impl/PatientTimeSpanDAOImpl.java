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
import com.xhs.ems.bean.PatientTimeSpan;
import com.xhs.ems.dao.PatientTimeSpanDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 上午10:18:28
 */
@Repository
public class PatientTimeSpanDAOImpl implements PatientTimeSpanDAO {
	private static final Logger logger = Logger
			.getLogger(PatientTimeSpanDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月22日 上午10:18:46
	 * @see com.xhs.ems.dao.PatientTimeSpanDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select ddcs.NameM patientType,COUNT(*) summary,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=0 and DATENAME(HOUR,a.开始受理时刻)<1 then 1 else 0 end) span0_1,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=1 and DATENAME(HOUR,a.开始受理时刻)<2 then 1 else 0 end) span1_2,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=2 and DATENAME(HOUR,a.开始受理时刻)<3 then 1 else 0 end) span2_3,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=3 and DATENAME(HOUR,a.开始受理时刻)<4 then 1 else 0 end) span3_4,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=4 and DATENAME(HOUR,a.开始受理时刻)<5 then 1 else 0 end) span4_5,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=5 and DATENAME(HOUR,a.开始受理时刻)<6 then 1 else 0 end) span5_6,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=6 and DATENAME(HOUR,a.开始受理时刻)<7 then 1 else 0 end) span6_7,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=7 and DATENAME(HOUR,a.开始受理时刻)<8 then 1 else 0 end) span7_8,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=8 and DATENAME(HOUR,a.开始受理时刻)<9 then 1 else 0 end) span8_9,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=9 and DATENAME(HOUR,a.开始受理时刻)<10 then 1 else 0 end) span9_10,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=10 and DATENAME(HOUR,a.开始受理时刻)<11 then 1 else 0 end) span10_11,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=11 and DATENAME(HOUR,a.开始受理时刻)<12 then 1 else 0 end) span11_12,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=12 and DATENAME(HOUR,a.开始受理时刻)<13 then 1 else 0 end) span12_13,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=13 and DATENAME(HOUR,a.开始受理时刻)<14 then 1 else 0 end) span13_14,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=14 and DATENAME(HOUR,a.开始受理时刻)<15 then 1 else 0 end) span14_15,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=15 and DATENAME(HOUR,a.开始受理时刻)<16 then 1 else 0 end) span15_16,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=16 and DATENAME(HOUR,a.开始受理时刻)<17 then 1 else 0 end) span16_17,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=17 and DATENAME(HOUR,a.开始受理时刻)<18 then 1 else 0 end) span17_18,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=18 and DATENAME(HOUR,a.开始受理时刻)<19 then 1 else 0 end) span18_19,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=19 and DATENAME(HOUR,a.开始受理时刻)<20 then 1 else 0 end) span19_20,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=20 and DATENAME(HOUR,a.开始受理时刻)<21 then 1 else 0 end) span20_21,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=21 and DATENAME(HOUR,a.开始受理时刻)<22 then 1 else 0 end) span21_22,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=22 and DATENAME(HOUR,a.开始受理时刻)<23 then 1 else 0 end) span22_23,	"
				+ "SUM(case when DATENAME(HOUR,a.开始受理时刻)>=23 and DATENAME(HOUR,a.开始受理时刻)<24 then 1 else 0 end) span23_24	"
				+ "from AuSp120.tb_PatientCase pc	left outer join AuSp120.tb_Ambulance am on am.实际标识=pc.车辆标识	"
				+ "left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and am.车辆编码=t.车辆编码	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_Station s on s.分站编码=pc.分站编码	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码 left outer join AuSp120.tb_DDiseaseClassState ddcs on ddcs.Code=pc.分类统计编码	"
				+ "where e.事件性质编码=1 and pc.姓名<>'' and a.开始受理时刻 between :startTime and :endTime  "
				+ "group by ddcs.NameM  ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<PatientTimeSpan> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<PatientTimeSpan>() {
					@Override
					public PatientTimeSpan mapRow(ResultSet rs, int index)
							throws SQLException {
						return new PatientTimeSpan(rs.getString("patientType"),
								rs.getString("summary"), rs
										.getString("span0_1"), rs
										.getString("span1_2"), rs
										.getString("span2_3"), rs
										.getString("span3_4"), rs
										.getString("span4_5"), rs
										.getString("span5_6"), rs
										.getString("span6_7"), rs
										.getString("span7_8"), rs
										.getString("span8_9"), rs
										.getString("span9_10"), rs
										.getString("span10_11"), rs
										.getString("span11_12"), rs
										.getString("span12_13"), rs
										.getString("span13_14"), rs
										.getString("span14_15"), rs
										.getString("span15_16"), rs
										.getString("span16_17"), rs
										.getString("span17_18"), rs
										.getString("span18_19"), rs
										.getString("span19_20"), rs
										.getString("span20_21"), rs
										.getString("span21_22"), rs
										.getString("span22_23"), rs
										.getString("span23_24"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

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
