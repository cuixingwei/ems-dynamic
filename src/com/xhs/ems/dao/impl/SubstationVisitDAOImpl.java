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
import com.xhs.ems.bean.SubstationVisit;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.SubstationVisitDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 上午11:11:34
 */
@Repository
public class SubstationVisitDAOImpl implements SubstationVisitDAO {
	private static final Logger logger = Logger
			.getLogger(SubstationVisitDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月16日 上午11:11:34
	 * @see com.xhs.ems.dao.SubstationVisitDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select 分站编码,count( p.车辆编码) as 暂停次数  into #temp1 "
				+ "from AuSp120.tb_RecordPauseReason p left join AuSp120.tb_Ambulance a on p.车辆编码=a.车辆编码 "
				+ " where p.操作时刻 between :startTime and :endTime group by (分站编码) "
				+ "select 任务编码,分站编码,结果编码 into #temp2 from AuSp120.tb_TaskV t "
				+ "left outer join AuSp120.tb_Event e on t.事件编码=e.事件编码 "
				+ " where e.事件性质编码=1 and t.生成任务时刻 between :startTime and :endTime  "
				+ "select 分站编码,count(*) as 救治人数  into #temp3  "
				+ "from AuSp120.tb_PatientCase  "
				+ "where 任务编码 in (select 任务编码 from  #temp2)  group by 分站编码  "
				+ "select s.分站名称 station,sum(case when t2.任务编码 is not null then 1 else 0 end) sendNumbers,	"
				+ "sum(case when t2.结果编码=4 then 1 else 0 end) nomalNumbers,'' nomalRate,	"
				+ "sum(case when t2.结果编码=2 then 1 else 0 end) stopNumbers, '' stopRate,	"
				+ "sum(case when t2.结果编码=3 then 1 else 0 end) emptyNumbers, '' emptyRate,	"
				+ "sum(case when t2.结果编码=5 then 1 else 0 end) refuseNumbers, '' refuseRate,	"
				+ "isnull(t1.暂停次数,0) as pauseNumbers,	isnull(t3.救治人数,0) as treatNumbers  "
				+ "from AuSp120.tb_Station s  left outer join #temp2 t2 on t2.分站编码=s.分站编码  "
				+ "left outer join	#temp1 t1 on t1.分站编码=s.分站编码  left outer join	#temp3 t3 on t3.分站编码=s.分站编码  "
				+ "group by s.分站名称,t1.暂停次数,t3.救治人数,s.显示顺序  order by 显示顺序 "
				+ " drop table #temp1, #temp2,#temp3";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		int page = (int) parameter.getPage();
		int rows = (int) parameter.getRows();

		List<SubstationVisit> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<SubstationVisit>() {
					@Override
					public SubstationVisit mapRow(ResultSet rs, int index)
							throws SQLException {
						return new SubstationVisit(rs.getString("station"), rs
								.getString("sendNumbers"), rs
								.getString("nomalNumbers"), rs
								.getString("nomalRate"), rs
								.getString("stopNumbers"), rs
								.getString("stopRate"), rs
								.getString("emptyNumbers"), rs
								.getString("emptyRate"), rs
								.getString("refuseNumbers"), rs
								.getString("refuseRate"), rs
								.getString("pauseNumbers"), rs
								.getString("treatNumbers"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		// 计算比率
		for (SubstationVisit result : results) {
			result.setEmptyRate(CommonUtil.calculateRate(
					Integer.parseInt(result.getSendNumbers()),
					Integer.parseInt(result.getEmptyNumbers())));
			result.setRefuseRate(CommonUtil.calculateRate(
					Integer.parseInt(result.getSendNumbers()),
					Integer.parseInt(result.getRefuseNumbers())));
			result.setStopRate(CommonUtil.calculateRate(
					Integer.parseInt(result.getSendNumbers()),
					Integer.parseInt(result.getStopNumbers())));
			result.setNomalRate(CommonUtil.calculateRate(
					Integer.parseInt(result.getSendNumbers()),
					Integer.parseInt(result.getNomalNumbers())));
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