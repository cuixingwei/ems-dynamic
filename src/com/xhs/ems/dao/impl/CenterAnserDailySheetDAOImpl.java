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

import com.xhs.ems.bean.CenterAnserDailySheet;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.dao.CenterAnserDailySheetDAO;

@Repository
public class CenterAnserDailySheetDAOImpl implements CenterAnserDailySheetDAO {
	private static final Logger logger = Logger.getLogger(CenterAnserDailySheet.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select ve.分站名称 hospital,SUM(case when ve.结果编码=4 then ve.救治人数 else 0 end) inHosCounts,	"
				+ "SUM(case when ve.任务编码 is not null and ve.事件性质编码=3 then ve.救治人数 else 0 end) spotCure,	"
				+ "SUM(case when ve.结果编码=3 then 1 else 0 end) emptyTask,	"
				+ "SUM(case when ve.结果编码=2 and ve.中止任务原因编码=9 then 1 else 0 end) byRobCure,	"
				+ "SUM(case when ve.任务编码 is not null and ve.事件性质编码=3 then 1 else 0 end) spotCure,	"
				+ "SUM(case when ve.结果编码=4 or (ve.任务编码 is not null and ve.事件性质编码=3) then ve.救治人数 else 0 end) totalSendCount	"
				+ "from AuSp120.View_EventAcceptTaskStation ve	"
				+ "where ve.事件性质编码<>2 and ve.分站名称 is not null and ve.受理时刻  between :startTime and :endTime	group by ve.分站名称";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<CenterAnserDailySheet> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<CenterAnserDailySheet>() {
					@Override
					public CenterAnserDailySheet mapRow(ResultSet rs, int index) throws SQLException {
						CenterAnserDailySheet centerAnserDailySheet = new CenterAnserDailySheet();
						centerAnserDailySheet.setByRobCure(rs.getString("byRobCure"));
						centerAnserDailySheet.setEmptyTask(rs.getString("emptyTask"));
						centerAnserDailySheet.setHospital(rs.getString("hospital"));
						centerAnserDailySheet.setInHosCounts(rs.getString("inHosCounts"));
						centerAnserDailySheet.setSpotCure(rs.getString("spotCure"));
						centerAnserDailySheet.setTotalSendCount(rs.getString("totalSendCount"));
						return centerAnserDailySheet;
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		CenterAnserDailySheet summary = new CenterAnserDailySheet("合计", "0", "0", "0", "0", "0");// 添加合计
		for (CenterAnserDailySheet dSheet : results) {
			summary.setByRobCure(
					(Integer.parseInt(summary.getByRobCure()) + Integer.parseInt(dSheet.getByRobCure())) + "");
			summary.setEmptyTask(
					(Integer.parseInt(summary.getEmptyTask()) + Integer.parseInt(dSheet.getEmptyTask())) + "");
			summary.setInHosCounts(
					(Integer.parseInt(summary.getInHosCounts()) + Integer.parseInt(dSheet.getInHosCounts())) + "");
			summary.setSpotCure(
					(Integer.parseInt(summary.getSpotCure()) + Integer.parseInt(dSheet.getSpotCure())) + "");
			summary.setTotalSendCount(
					(Integer.parseInt(summary.getTotalSendCount()) + Integer.parseInt(dSheet.getTotalSendCount()))
							+ "");
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
