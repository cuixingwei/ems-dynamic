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
	public void setDataSource(DataSource dataSourceMysql) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月16日 上午11:11:34
	 * @see com.xhs.ems.dao.SubstationVisitDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT s.stationName station,COUNT(DISTINCT et.taskCode) sendNumbers,sum(if(et.taskResult=1,1,0))stopNumbers,'' stopRate,	"
				+ "sum(if(et.taskResult=2,1,0)) emptyNumbers,'' emptyRate,	sum(if(et.taskResult=3,1,0)) nomalNumbers,'' nomalRate,	"
				+ "sum(if(et.taskResult=4,1,0)) refuseNumbers,'' refuseRate,sum(if(eh.patientNum is null or eh.patientNum='',0,1)) treatNumbers	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN station s on s.stationCode=et.stationCode	"
				+ "WHERE e.eventProperty=1 and et.taskCode is not null and eh.createTime between :startTime and :endTime group by s.stationName ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<SubstationVisit> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<SubstationVisit>() {
					@Override
					public SubstationVisit mapRow(ResultSet rs, int index)
							throws SQLException {
						SubstationVisit substationVisit= new SubstationVisit();
						substationVisit.setEmptyNumbers(rs.getString("emptyNumbers"));
						substationVisit.setEmptyRate(rs.getString("emptyRate"));
						substationVisit.setNomalNumbers(rs.getString("nomalNumbers"));
						substationVisit.setNomalRate(rs.getString("nomalRate"));
						substationVisit.setRefuseNumbers(rs.getString("refuseNumbers"));
						substationVisit.setRefuseRate(rs.getString("refuseRate"));
						substationVisit.setSendNumbers(rs.getString("sendNumbers"));
						substationVisit.setStation(rs.getString("station"));
						substationVisit.setStopNumbers(rs.getString("stopNumbers"));
						substationVisit.setTreatNumbers(rs.getString("treatNumbers"));
						substationVisit.setStopRate(rs.getString("stopRate"));
						return substationVisit;
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		logger.info(sql);

		// 添加合计
		SubstationVisit summary = new SubstationVisit("合计", "0", "0", "0", "0", "0", "0", "0", "0", "0","0", "0", "0");

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
//			summary.setChoiseHosNumbers((Integer.parseInt(result.getChoiseHosNumbers())+Integer.parseInt(summary.getChoiseHosNumbers()))+"");
			summary.setEmptyNumbers((Integer.parseInt(result.getEmptyNumbers())+Integer.parseInt(summary.getEmptyNumbers()))+"");
			summary.setNomalNumbers((Integer.parseInt(result.getNomalNumbers())+Integer.parseInt(summary.getNomalNumbers()))+"");
//			summary.setPauseNumbers((Integer.parseInt(result.getPauseNumbers())+Integer.parseInt(summary.getPauseNumbers()))+"");
			summary.setRefuseNumbers((Integer.parseInt(result.getRefuseNumbers())+Integer.parseInt(summary.getRefuseNumbers()))+"");
			summary.setSendNumbers((Integer.parseInt(result.getSendNumbers())+Integer.parseInt(summary.getSendNumbers()))+"");
			summary.setStopNumbers((Integer.parseInt(result.getStopNumbers())+Integer.parseInt(summary.getStopNumbers()))+"");
			summary.setTreatNumbers((Integer.parseInt(result.getTreatNumbers())+Integer.parseInt(summary.getTreatNumbers()))+"");
		}
		summary.setEmptyRate(CommonUtil.calculateRate(
				Integer.parseInt(summary.getSendNumbers()),
				Integer.parseInt(summary.getEmptyNumbers())));
		summary.setRefuseRate(CommonUtil.calculateRate(
				Integer.parseInt(summary.getSendNumbers()),
				Integer.parseInt(summary.getRefuseNumbers())));
		summary.setStopRate(CommonUtil.calculateRate(
				Integer.parseInt(summary.getSendNumbers()),
				Integer.parseInt(summary.getStopNumbers())));
		summary.setNomalRate(CommonUtil.calculateRate(
				Integer.parseInt(summary.getSendNumbers()),
				Integer.parseInt(summary.getNomalNumbers())));
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
