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
import com.xhs.ems.bean.SubstationLateVisit;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.SubstationLateVisitDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 下午8:17:04
 */
@Repository
public class SubstationLateVisitDAOImpl implements SubstationLateVisitDAO {
	private static final Logger logger = Logger
			.getLogger(SubstationLateVisitDAOImpl.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void SetDataSourceTag(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月16日 下午8:17:04
	 * @see com.xhs.ems.dao.SubstationLateVisitDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());
		String sql = "select a.现场地址 siteAddress,det.NameM eventType,am.实际标识 carCode,"
				+ "CONVERT(varchar(20),a.开始受理时刻,120) acceptTime,CONVERT(varchar(20),a.派车时刻,120) createTaskTime,	"
				+ "CONVERT(varchar(20),t.出车时刻,120) outCarTime,DATEDIFF(SECOND,a.派车时刻,t.出车时刻) outCarTimes,"
				+ "dtr.NameM taskResult,t.备注 remark,m.姓名 dispatcher	from AuSp120.tb_EventV e	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码	"
				+ "left outer join AuSp120.tb_DEventType det on det.Code=e.事件类型编码	"
				+ "left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码	"
				+ "left outer join AuSp120.tb_DTaskResult dtr on dtr.Code=t.结果编码	"
				+ "left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4)	"
				+ "and a.开始受理时刻 between :startTime and :endTime and a.开始受理时刻<t.出车时刻 ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql += " and t.分站编码=:station ";
		}
		int second = 0;
		if (!CommonUtil.isNullOrEmpty(parameter.getOutCarTimesMin())) {
			second = 60 * Integer.parseInt(parameter.getOutCarTimesMin());
			String secondMin = second + "";
			paramMap.put("secondMin", secondMin);
			sql += " and DATEDIFF(SECOND,a.派车时刻,t.出车时刻)> :secondMin ";
			logger.info("outCarTimesMin:" + parameter.getOutCarTimesMin());
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getOutCarTimesMax())) {
			second = 60 * Integer.parseInt(parameter.getOutCarTimesMax());
			String secondMax = second + "";
			paramMap.put("secondMax", secondMax);
			sql += " and DATEDIFF(SECOND,a.派车时刻,t.出车时刻) <= :secondMax ";
			logger.info("outCarTimesMax:" + parameter.getOutCarTimesMax());
		}
		
		sql += " order by a.开始受理时刻 ";

		List<SubstationLateVisit> results = this.namedParameterJdbcTemplate
				.query(sql, paramMap, new RowMapper<SubstationLateVisit>() {
					@Override
					public SubstationLateVisit mapRow(ResultSet rs, int index)
							throws SQLException {

						return new SubstationLateVisit(rs
								.getString("siteAddress"), rs
								.getString("eventType"), rs
								.getString("carCode"), rs
								.getString("acceptTime"), rs
								.getString("createTaskTime"), rs
								.getString("outCarTime"), rs
								.getString("outCarTimes"), rs
								.getString("taskResult"), rs
								.getString("remark"), rs
								.getString("dispatcher"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		for (SubstationLateVisit result : results) {
			result.setOutCarTimes(CommonUtil.formatSecond(result
					.getOutCarTimes()));
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
