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

import com.xhs.ems.bean.DoctorNurseWork;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.DoctorNurseWorkDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午4:43:45
 */
@Repository
public class DoctorNurseWorkDAOImpl implements DoctorNurseWorkDAO {
	private static final Logger logger = Logger
			.getLogger(DoctorNurseWorkDAOImpl.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void SetDataSourceTag(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月22日 下午4:43:45
	 * @see com.xhs.ems.dao.DoctorNurseWorkDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql1 = "select  pc.任务编码,pc.随车医生,pc.随车护士,pc.分站编码 into #temp1	"
				+ "from AuSp120.tb_PatientCase pc where pc.任务编码<>'' and pc.任务时刻 between :startTime and :endTime ";
		String sql2 = "select distinct pc.任务编码,pc.随车医生,pc.随车护士,pc.分站编码 into #temp2	"
				+ "from AuSp120.tb_PatientCase pc where pc.任务编码<>'' and pc.任务时刻 between :startTime and :endTime ";
		String sql3 = "select tt.分站编码,tt.随车医生,COUNT(*) doctorCureNumbers into #temp3 "
				+ "from #temp1 tt 	where tt.随车医生<>'' and tt.任务编码<>'' group by tt.分站编码,tt.随车医生  ";
		String sql4 = "select tt.分站编码,tt.随车护士,COUNT(*) nurseCureNumbers into #temp3 "
				+ "from #temp1 tt	where tt.随车护士<>'' and tt.任务编码<>'' group by tt.分站编码,tt.随车护士  ";
		String sql5 = "select t.分站编码 ,tt.随车医生 name,SUM(case when t.结果编码<>5 then 1 else 0 end) outCarNumbers,	"
				+ "SUM(case when t.结果编码=4 then 1 else 0 end) validOutCarNumbers,	"
				+ "SUM(case when t.结果编码 in (2,3) then 1 else 0 end) stopNumbers,	"
				+ "isnull(AVG(DATEDIFF(Second,t.到达现场时刻,t.完成时刻)),0) averateCureTimes into #temp4 	"
				+ "from AuSp120.tb_TaskV t left outer join #temp2 tt on t.任务编码=tt.任务编码	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "where e.事件性质编码=1 and t.结果编码 is not null and tt.随车医生<>'' and t.生成任务时刻  between :startTime and :endTime	"
				+ "group by  t.分站编码 ,tt.随车医生	order by  t.分站编码 ,tt.随车医生   ";
		String sql6 = "select t.分站编码 ,tt.随车护士 name,SUM(case when t.结果编码<>5 then 1 else 0 end) outCarNumbers,	"
				+ "SUM(case when t.结果编码=4 then 1 else 0 end) validOutCarNumbers,	"
				+ "SUM(case when t.结果编码 in (2,3) then 1 else 0 end) stopNumbers,	"
				+ "isnull(AVG(DATEDIFF(Second,t.到达现场时刻,t.完成时刻)),0) averateCureTimes into #temp4 	"
				+ "from AuSp120.tb_TaskV t left outer join #temp2 tt on t.任务编码=tt.任务编码	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "where e.事件性质编码=1 and t.结果编码 is not null and tt.随车护士<>'' and t.生成任务时刻  between :startTime and :endTime	"
				+ "group by  t.分站编码 ,tt.随车护士	order by  t.分站编码 ,tt.随车护士   ";
		String sql7 = "select s.分站名称 station,tt4.name,tt4.outCarNumbers,tt4.validOutCarNumbers,"
				+ "tt4.stopNumbers,	tt3.doctorCureNumbers curePeopleNumbers,tt4.averateCureTimes	"
				+ "from #temp3 tt3 left outer join #temp4 tt4 on tt3.分站编码=tt4.分站编码 and tt3.随车医生=tt4.name	"
				+ "left outer join AuSp120.tb_Station s on s.分站编码=tt4.分站编码";
		String sql8 = "select s.分站名称 station,tt4.name,tt4.outCarNumbers,tt4.validOutCarNumbers,"
				+ "tt4.stopNumbers,	tt3.nurseCureNumbers curePeopleNumbers,tt4.averateCureTimes	"
				+ "from #temp3 tt3 left outer join #temp4 tt4 on tt3.分站编码=tt4.分站编码 and tt3.随车护士=tt4.name	"
				+ "left outer join AuSp120.tb_Station s on s.分站编码=tt4.分站编码";
		String sqlEnd = " drop table #temp1,#temp2,#temp3,#temp4";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql7 += " where tt4.分站编码 = :station and tt4.分站编码 is not null ";
			sql8 += " where tt4.分站编码 = :station and tt4.分站编码 is not null";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());

		int page = (int) parameter.getPage();
		int rows = (int) parameter.getRows();

		String sql = "";
		String doctorOrNurse = "1";
		if (!CommonUtil.isNullOrEmpty(parameter.getDoctorOrNurse())) {
			doctorOrNurse = parameter.getDoctorOrNurse();
		}
		logger.info(doctorOrNurse);
		if ("1".equals(doctorOrNurse)) {
			sql = sql1 + sql2 + sql3 + sql5 + sql7 + sqlEnd;
			logger.info("医生");
		} else if ("2".equals(doctorOrNurse)) {
			sql = sql1 + sql2 + sql4 + sql6 + sql8 + sqlEnd;
			logger.info("护士");
		}

		List<DoctorNurseWork> results = this.namedParameterJdbcTemplate.query(
				sql, paramMap, new RowMapper<DoctorNurseWork>() {
					@Override
					public DoctorNurseWork mapRow(ResultSet rs, int index)
							throws SQLException {

						return new DoctorNurseWork(rs.getString("station"), rs
								.getString("name"), rs
								.getString("outCarNumbers"), rs
								.getString("validOutCarNumbers"), rs
								.getString("stopNumbers"), rs
								.getString("curePeopleNumbers"), rs
								.getString("averateCureTimes"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (DoctorNurseWork result : results) {
			result.setAverateCureTimes(CommonUtil.formatSecond(result
					.getAverateCureTimes()));
		}
		Grid grid = new Grid();
		int fromIndex = (page - 1) * rows;
		int toIndex = (results.size() <= page * rows && results.size() >= (page - 1)
				* rows) ? results.size() : page * rows;
		grid.setRows(results.subList(fromIndex, toIndex));
		grid.setTotal(results.size());
		return grid;
	}
}
