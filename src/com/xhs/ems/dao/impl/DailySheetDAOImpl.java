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

import com.xhs.ems.bean.DailySheet;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.DailySheetDAO;

@Repository
public class DailySheetDAOImpl implements DailySheetDAO {
	private static final Logger logger = Logger.getLogger(DailySheetDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select ve.byRobHospital,isnull(SUM(case when ve.结果编码=2 and ve.中止任务原因编码=9 then ve.救治人数  else 0 end),0) robCure "
				+ "into #temp1 from AuSp120.View_EventAcceptTaskStation ve "
				+ "where ve.事件性质编码<>2 and ve.分站名称 is not null and ve.受理时刻 between :startTime and :endTime "
				+ "group by ve.byRobHospital "
				+ "select ve.分站名称 hospital,isnull(SUM(case when ve.结果编码=4 then ve.救治人数 else 0 end),0) inHosCounts,"
				+ "isnull(SUM(case when ve.任务编码 is not null and ve.事件性质编码=3 then 1 else 0 end),0) spotCure,"
				+ "isnull(SUM(case when ve.结果编码=3 then 1 else 0 end),0) emptyTask,"
				+ "isnull(SUM(case when ve.结果编码=2 and ve.中止任务原因编码=9 then ve.救治人数 else 0 end),0) byRobCure,"
				+ "isnull(SUM(case when ve.事件类型编码=3 and ve.任务编码 is not null then 1 else 0 end),0) transferCure,"
				+ "isnull(SUM(case when ve.结果编码=2 and ve.中止任务原因编码=8 then 1 else 0 end),0) specialTask,"
				+ "isnull(SUM(case when ve.结果编码=2 and ve.中止任务原因编码=10 then 1 else 0 end),0) refuseTransferCount,"
				+ "isnull(SUM(case when ve.结果编码=2 and ve.中止任务原因编码=11 then 1 else 0 end),0) refuseNoOwnerCount,"
				+ "isnull(SUM(case when ve.任务编码 is not null then 1 else 0 end),0) totalSendCar,"
				+ "isnull(SUM(case when ve.类型编码 in (2,4) then 1 else 0 end),0) hungOn into #temp2 "
				+ "from AuSp120.View_EventAcceptTaskStation ve "
				+ "where ve.事件性质编码<>2 and ve.分站名称 is not null and ve.受理时刻 between :startTime and :endTime "
				+ "group by ve.分站名称  "
				+ "select t2.*,isnull(t1.robCure,0) robCure from #temp2 t2 left outer join #temp1 t1 on t1.byRobHospital=t2.hospital "
				+ "drop table #temp1,#temp2 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		List<DailySheet> results = this.npJdbcTemplate.query(sql, paramMap, new RowMapper<DailySheet>() {
			@Override
			public DailySheet mapRow(ResultSet rs, int index) throws SQLException {
				DailySheet dailySheet = new DailySheet();
				dailySheet.setByRobCure(rs.getString("byRobCure"));
				dailySheet.setEmptyTask(rs.getString("emptyTask"));
				dailySheet.setHospital(rs.getString("hospital"));
				dailySheet.setHungOn(rs.getString("hungOn"));
				dailySheet.setInHosCounts(rs.getString("inHosCounts"));
				dailySheet.setRefuseNoOwnerCount(rs.getString("refuseNoOwnerCount"));
				dailySheet.setRefuseTransferCount(rs.getString("refuseTransferCount"));
				dailySheet.setRobCure(rs.getString("robCure"));
				dailySheet.setSpecialTask(rs.getString("specialTask"));
				dailySheet.setSpotCure(rs.getString("spotCure"));
				dailySheet.setTotalSendCar(rs.getString("totalSendCar"));
				dailySheet.setTransferCure(rs.getString("transferCure"));
				return dailySheet;
			}
		});
		DailySheet summary = new DailySheet("合计", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");// 添加合计
		logger.info("一共有" + results.size() + "条数据");
		for (DailySheet dSheet : results) {
			summary.setByRobCure(
					(Integer.parseInt(summary.getByRobCure()) + Integer.parseInt(dSheet.getByRobCure())) + "");
			summary.setEmptyTask(
					(Integer.parseInt(summary.getEmptyTask()) + Integer.parseInt(dSheet.getEmptyTask())) + "");
			summary.setHungOn((Integer.parseInt(summary.getHungOn()) + Integer.parseInt(dSheet.getHungOn())) + "");
			summary.setInHosCounts(
					(Integer.parseInt(summary.getInHosCounts()) + Integer.parseInt(dSheet.getInHosCounts())) + "");
			summary.setRefuseNoOwnerCount((Integer.parseInt(summary.getRefuseNoOwnerCount())
					+ Integer.parseInt(dSheet.getRefuseNoOwnerCount())) + "");
			summary.setRefuseTransferCount((Integer.parseInt(summary.getRefuseTransferCount())
					+ Integer.parseInt(dSheet.getRefuseTransferCount())) + "");
			summary.setRobCure((Integer.parseInt(summary.getRobCure()) + Integer.parseInt(dSheet.getRobCure())) + "");
			summary.setSpecialTask(
					(Integer.parseInt(summary.getSpecialTask()) + Integer.parseInt(dSheet.getSpecialTask())) + "");
			summary.setSpotCure(
					(Integer.parseInt(summary.getSpotCure()) + Integer.parseInt(dSheet.getSpotCure())) + "");
			summary.setTotalSendCar(
					(Integer.parseInt(summary.getTotalSendCar()) + Integer.parseInt(dSheet.getTotalSendCar())) + "");
			summary.setTransferCure(
					(Integer.parseInt(summary.getTransferCure()) + Integer.parseInt(dSheet.getTransferCure())) + "");
		}
		results.add(summary);
		Grid grid = new Grid();
		if ((int) parameter.getPage() > 0) {
			int page = (int) parameter.getPage();
			int rows = (int) parameter.getRows();

			int fromIndex = (page - 1) * rows;
			int toIndex = (results.size() <= page * rows && results.size() >= (page - 1) * rows) ? results.size()
					: page * rows;
			grid.setRows(results.subList(fromIndex, toIndex));
			grid.setTotal(results.size());

		} else {
			grid.setRows(results);
		}
		return grid;
	}

}
