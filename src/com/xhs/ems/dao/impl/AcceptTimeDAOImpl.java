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

import com.xhs.ems.bean.AcceptTime;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AcceptTimeDAO;

@Repository("acceptTimeDAO")
public class AcceptTimeDAOImpl implements AcceptTimeDAO {

	private static final Logger logger = Logger
			.getLogger(AcceptTimeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select sl.调度员编码 工号, avg(datediff(SECOND,sl.开始时刻,sl.结束时刻)) readyTime into #temp1 "
				+ "from AuSp120.tb_SlinoLog sl	where sl.座席状态='就绪' "
				+ "and sl.开始时刻 between :startTime and :endTime group by sl.调度员编码	"
				+ "select sl.调度员编码 工号,avg(datediff(SECOND,sl.开始时刻,sl.结束时刻)) leaveTime  into #temp2 "
				+ "from AuSp120.tb_SlinoLog sl	where sl.座席状态='离席' and sl.开始时刻 between :startTime and :endTime group by sl.调度员编码	"
				+ "select tr.调度员编码 工号,avg(datediff(Second,tr.振铃时刻,tr.通话开始时刻))  averageOffhookTime into #temp3 "
				+ "from AuSp120.tb_TeleRecord tr	where tr.振铃时刻 between :startTime and :endTime group by tr.调度员编码	"
				+ "select a.调度员编码 工号,	avg(datediff(Second, a.开始受理时刻, a.派车时刻)) averageOffSendCar,	"
				+ "avg(datediff(Second, a.开始受理时刻, a.结束受理时刻)) averageAccept into #temp4	from  AuSp120.tb_AcceptDescriptV a		"
				+ "left outer join AuSp120.tb_Event e on e.事件编码=a.事件编码	where e.事件性质编码=1 "
				+ "and a.开始受理时刻 between :startTime and :endTime  group by a.调度员编码	"
				+ "select m.姓名 dispatcher,ISNULL(t1.readyTime,0) readyTime,ISNULL(t2.leaveTime,0) leaveTime,"
				+ "ISNULL(t3.averageOffhookTime,0) averageOffhookTime,ISNULL(t4.averageAccept,0) averageAccept,"
				+ "ISNULL(t4.averageOffSendCar,0) averageOffSendCar	from #temp4 t4 left outer join #temp1 t1 on t1.工号=t4.工号	"
				+ "left outer join #temp2 t2 on t4.工号=t2.工号	left outer join #temp3 t3 on t4.工号=t3.工号	"
				+ "left outer join AuSp120.tb_MrUser m on t4.工号=m.工号	where m.人员类型=0";
		String summary = "select '合计' 工号, avg(datediff(SECOND,sl.开始时刻,sl.结束时刻)) readyTime into #temp1 "
				+ "from AuSp120.tb_SlinoLog sl	where sl.座席状态='就绪' "
				+ "and sl.开始时刻 between :startTime and :endTime 	"
				+ "select '合计' 工号,avg(datediff(SECOND,sl.开始时刻,sl.结束时刻)) leaveTime  into #temp2 "
				+ "from AuSp120.tb_SlinoLog sl	where sl.座席状态='离席' and sl.开始时刻 between :startTime and :endTime 	"
				+ "select '合计' 工号,avg(datediff(Second,tr.振铃时刻,tr.通话开始时刻))  averageOffhookTime into #temp3 "
				+ "from AuSp120.tb_TeleRecord tr	where tr.振铃时刻 between :startTime and :endTime 	"
				+ "select '合计' 工号,	avg(datediff(Second, a.开始受理时刻, a.派车时刻)) averageOffSendCar,	"
				+ "avg(datediff(Second, a.开始受理时刻, a.结束受理时刻)) averageAccept into #temp4	from  AuSp120.tb_AcceptDescriptV a		"
				+ "left outer join AuSp120.tb_Event e on e.事件编码=a.事件编码	where e.事件性质编码=1 "
				+ "and a.开始受理时刻 between :startTime and :endTime  	"
				+ "select '合计' dispatcher,ISNULL(t1.readyTime,0) readyTime,ISNULL(t2.leaveTime,0) leaveTime,"
				+ "ISNULL(t3.averageOffhookTime,0) averageOffhookTime,ISNULL(t4.averageAccept,0) averageAccept,"
				+ "ISNULL(t4.averageOffSendCar,0) averageOffSendCar	from #temp4 t4 left outer join #temp1 t1 on t1.工号=t4.工号	"
				+ "left outer join #temp2 t2 on t4.工号=t2.工号	left outer join #temp3 t3 on t4.工号=t3.工号	";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and t4.工号=:dispatcher ";
		}
		sql = sql + " drop table #temp1,#temp2,#temp3,#temp4 ";
		summary = summary + " drop table #temp1,#temp2,#temp3,#temp4 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<AcceptTime> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<AcceptTime>() {
					@Override
					public AcceptTime mapRow(ResultSet rs, int index)
							throws SQLException {

						return new AcceptTime(rs.getString("dispatcher"), rs
								.getString("averageOffhookTime"), rs
								.getString("averageOffSendCar"), rs
								.getString("averageAccept"), rs
								.getString("readyTime"), rs
								.getString("leaveTime"));
					}
				});
		List<AcceptTime> summaryList = this.npJdbcTemplate.query(summary, paramMap,
				new RowMapper<AcceptTime>() {
					@Override
					public AcceptTime mapRow(ResultSet rs, int index)
							throws SQLException {

						return new AcceptTime(rs.getString("dispatcher"), rs
								.getString("averageOffhookTime"), rs
								.getString("averageOffSendCar"), rs
								.getString("averageAccept"), rs
								.getString("readyTime"), rs
								.getString("leaveTime"));
					}
				});
		for (AcceptTime sm : summaryList) {
			results.add(sm);
		}
		for (AcceptTime result : results) {
			result.setReadyTime(CommonUtil.formatSecond(result.getReadyTime()));
			result.setLeaveTime(CommonUtil.formatSecond(result.getLeaveTime()));
		}
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
