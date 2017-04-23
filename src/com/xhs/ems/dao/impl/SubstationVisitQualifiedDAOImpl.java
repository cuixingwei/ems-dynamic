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
import com.xhs.ems.bean.SubstationVisitQualified;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.SubstationVisitQualifiedDAO;

/**
 * @datetime 2016年6月12日 下午6:26:28
 * @author 崔兴伟
 */
@Repository
public class SubstationVisitQualifiedDAOImpl implements
		SubstationVisitQualifiedDAO {
	private static final Logger logger = Logger
			.getLogger(SubstationVisitQualifiedDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;
	private NamedParameterJdbcTemplate npJdbcTemplateSQLServer;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql,DataSource dataSourceSQLServer) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
		this.npJdbcTemplateSQLServer = new NamedParameterJdbcTemplate(dataSourceSQLServer);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT s.stationName station,COUNT(DISTINCT et.taskCode) total,"
				+ "sum(if(TIMESTAMPDIFF(SECOND,et.createTime,et.taskDriveToTime)>120,1,0)) late,	"
				+ "sum(if(TIMESTAMPDIFF(SECOND,et.createTime,et.taskDriveToTime)<=120,1,0)) normal,'' rate	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN station s on s.stationCode=et.stationCode	"
				+ "WHERE e.eventProperty=1 and et.taskCode is not null and s.stationName is not null "
				+ "and e.createTime between :startTime and :endTime	group by s.stationName ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<SubstationVisitQualified> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<SubstationVisitQualified>() {
					@Override
					public SubstationVisitQualified mapRow(ResultSet rs, int index)
							throws SQLException {
						SubstationVisitQualified substationVisitQualified = new SubstationVisitQualified();
						substationVisitQualified.setLate(rs.getString("late"));
						substationVisitQualified.setNormal(rs.getString("normal"));
						substationVisitQualified.setRate(rs.getString("rate"));
						substationVisitQualified.setStation(rs.getString("station"));
						substationVisitQualified.setTotal(rs.getString("total"));
						return substationVisitQualified;
					}
				});
		logger.info(sql);

		// 添加合计
		SubstationVisitQualified summary = new SubstationVisitQualified("合计", "0", "0", "0", "100%");

		// 计算比率
		for (SubstationVisitQualified result : results) {
			result.setRate(CommonUtil.calculateRate(
					Integer.parseInt(result.getTotal()),
					Integer.parseInt(result.getNormal())));
			summary.setLate((Integer.parseInt(result.getLate())+Integer.parseInt(summary.getLate()))+"");
			summary.setNormal((Integer.parseInt(result.getNormal())+Integer.parseInt(summary.getNormal()))+"");
			summary.setTotal((Integer.parseInt(result.getTotal())+Integer.parseInt(summary.getTotal()))+"");
		}
		summary.setRate(CommonUtil.calculateRate(
				Integer.parseInt(summary.getTotal()),
				Integer.parseInt(summary.getNormal())));
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
