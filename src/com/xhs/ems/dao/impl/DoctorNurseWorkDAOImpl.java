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
		String sql1 = "select s.分站名称 station,t.随车医生 name,COUNT(*) outCarNumbers,	"
				+ "SUM(case when t.结果编码=4 then 1 else 0 end) validOutCarNumbers,	"
				+ "SUM(case when t.结果编码 in (2,3) then 1 else 0 end) stopNumbers,	"
				+ "SUM(case when t.结果编码=4 then t.接回人数 else 0 end) curePeopleNumbers,	"
				+ "AVG(DATEDIFF(S,t.到达现场时刻,t.完成时刻)) averateCureTimes	"
				+ "from ausp120.tb_EventV e left outer join ausp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码	"
				+ "left outer join ausp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join ausp120.tb_Station s on s.分站编码=t.分站编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and t.任务编码 is not null	"
				+ "and t.出车时刻<t.到达现场时刻 and t.生成任务时刻 between :startTime and :endTime and t.随车医生<>''	 ";
		String sql2 = "select s.分站名称 station,t.随车护士 name,COUNT(*) outCarNumbers,	"
				+ "SUM(case when t.结果编码=4 then 1 else 0 end) validOutCarNumbers,	"
				+ "SUM(case when t.结果编码 in (2,3) then 1 else 0 end) stopNumbers,	"
				+ "SUM(case when t.结果编码=4 then t.接回人数 else 0 end) curePeopleNumbers,	"
				+ "AVG(DATEDIFF(S,t.到达现场时刻,t.完成时刻)) averateCureTimes	"
				+ "from ausp120.tb_EventV e left outer join ausp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码	"
				+ "left outer join ausp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join ausp120.tb_Station s on s.分站编码=t.分站编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4) and t.任务编码 is not null	"
				+ "and t.出车时刻<t.到达现场时刻 and t.生成任务时刻 between :startTime and :endTime and t.随车护士<>''	 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql1 += " and t.分站编码=:station ";
			sql2 += " and t.分站编码=:station ";
		}
		sql1 += "group by s.分站名称,t.随车医生	order by s.分站名称,t.随车医生";
		sql2 += "group by s.分站名称,t.随车护士	order by s.分站名称,t.随车护士";
		

		String sql = "";
		String doctorOrNurse = "1";
		if (!CommonUtil.isNullOrEmpty(parameter.getDoctorOrNurse())) {
			doctorOrNurse = parameter.getDoctorOrNurse();
		}
		logger.info(doctorOrNurse);
		if ("1".equals(doctorOrNurse)) {
			sql = sql1 ;
			logger.info("医生");
		} else if ("2".equals(doctorOrNurse)) {
			sql =  sql2 ;
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
