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
import com.xhs.ems.bean.PatientCaseFillCount;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.PatientCaseFillCountDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午2:12:04
 */
@Repository
public class PatientCaseFillCountDAOImpl implements PatientCaseFillCountDAO {
	private static final Logger logger = Logger
			.getLogger(PatientCaseFillCountDAOImpl.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void SetDataSourceTag(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月22日 下午2:12:04
	 * @see com.xhs.ems.dao.PatientCaseFillCountDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select s.分站名称 station,COUNT(*) sendNumbers,COUNT(tt.任务编码) fillNumbers,'' rate	"
				+ "from AuSp120.tb_TaskV t left outer join AuSp120.tb_PatientCase  tt on  t.分站编码=tt.stationCode and t.任务编码=tt.任务编码 and tt.pcOrder=1 	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_Station s on s.分站编码=t.分站编码 	"
				+ "where e.事件性质编码=1 and t.结果编码=4 and t.生成任务时刻 between :startTime and :endTime	"
				+ "group by s.分站名称,s.显示顺序	order by s.显示顺序";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<PatientCaseFillCount> results = this.namedParameterJdbcTemplate
				.query(sql, paramMap, new RowMapper<PatientCaseFillCount>() {
					@Override
					public PatientCaseFillCount mapRow(ResultSet rs, int index)
							throws SQLException {

						return new PatientCaseFillCount(
								rs.getString("station"), rs
										.getString("sendNumbers"), rs
										.getString("fillNumbers"), rs
										.getString("rate"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		
		PatientCaseFillCount summary = new PatientCaseFillCount();
		summary.setStation("合计");
		summary.setRate("0%");
		summary.setSendNumbers("0");
		summary.setFillNumbers("0");
		Integer total1 = 0,total2 = 0;

		// 计算比率
		for (PatientCaseFillCount result : results) {
			result.setRate(CommonUtil.calculateRate(
					Integer.parseInt(result.getSendNumbers()),
					Integer.parseInt(result.getFillNumbers())));
			total1 = Integer.parseInt(result.getSendNumbers()) + total1;
			total2 = Integer.parseInt(result.getFillNumbers()) + total2;
		}
		
		
		summary.setSendNumbers(total1 + "");
		summary.setFillNumbers(total2 + "");
		summary.setRate(CommonUtil.calculateRate(total1, total2));
		results.add(summary);

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
