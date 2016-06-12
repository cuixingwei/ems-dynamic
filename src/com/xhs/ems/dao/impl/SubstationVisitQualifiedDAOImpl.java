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

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void SetDataSourceTag(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select s.分站名称 station,COUNT(*) total,SUM(case when DATEDIFF(SECOND,a.派车时刻,t.出车时刻)<= 120 then 1 else 0 end) normal,	"
				+ "SUM(case when DATEDIFF(SECOND,a.派车时刻,t.出车时刻) > 120 then 1 else 0 end) late,'' rate	"
				+ "from AuSp120.tb_EventV e	left outer join AuSp120.tb_AcceptDescriptV a on e.事件编码=a.事件编码	"
				+ "left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码	"
				+ "where e.事件性质编码=1 and a.类型编码 not in (2,4)	and a.派车时刻<t.出车时刻 	"
				+ "and a.开始受理时刻 between :startTime and :endTime	group by s.分站名称";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<SubstationVisitQualified> results = this.namedParameterJdbcTemplate.query(sql,
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
