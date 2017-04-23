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

	private static final Logger logger = Logger.getLogger(AcceptTimeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;
	private NamedParameterJdbcTemplate npJdbcTemplateSQLServer;

	@Autowired
	public void setDataSource(DataSource dataSourceMysql,DataSource dataSourceSQLServer) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSourceMysql);
		this.npJdbcTemplateSQLServer = new NamedParameterJdbcTemplate(dataSourceSQLServer);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql1 = "SELECT u.personName dispatcher, IFNULL(avg(TIMESTAMPDIFF(SECOND,eh.handleBeginTime,eh.handleEndTime)),0) averageAccept,	"
				+ "IFNULL(avg(TIMESTAMPDIFF(SECOND,eh.handleBeginTime,et.createTime)),0) averageOffSendCar	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN `user` u on u.jobNum=et.operatorJobNum	WHERE e.eventProperty=1 "
				+ "and u.personName is not null and e.createTime between :startTime and :endTime ";
		String sql2 = "select m.姓名 dispatcher,avg(datediff(Second,tr.振铃时刻,tr.通话开始时刻))  averageOffhookTime  "
				+ "from AuSp120.tb_TeleRecord tr left outer join AuSp120.tb_MrUser m on tr.调度员编码=m.工号	where tr.振铃时刻  between :startTime and :endTime 	";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql1 = sql1 + " and et.operatorJobNum=:dispatcher ";
			sql2 = sql2 + " and tr.调度员编码=:dispatcher ";
		}
		String summary1 =  "SELECT IFNULL(avg(TIMESTAMPDIFF(SECOND,eh.handleBeginTime,eh.handleEndTime)),0) averageAccept,	"
				+ "IFNULL(avg(TIMESTAMPDIFF(SECOND,eh.handleBeginTime,et.createTime)),0) averageOffSendCar	"
				+ "from `event` e LEFT JOIN event_history eh on e.eventCode=eh.eventCode	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN `user` u on u.jobNum=et.operatorJobNum	WHERE e.eventProperty=1 "
				+ "and u.personName is not null and e.createTime between :startTime and :endTime ";
		String summary2 = "select avg(datediff(Second,tr.振铃时刻,tr.通话开始时刻))  averageOffhookTime  "
				+ "from AuSp120.tb_TeleRecord tr left outer join AuSp120.tb_MrUser m on tr.调度员编码=m.工号	where tr.振铃时刻  between :startTime and :endTime";
		String group = " GROUP BY u.personName";
		String groupSQLServer = " group by m.姓名 ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		List<AcceptTime> results = this.npJdbcTemplate.query(sql1 + group, paramMap, new RowMapper<AcceptTime>() {
			@Override
			public AcceptTime mapRow(ResultSet rs, int index) throws SQLException {
				AcceptTime acceptTime = new AcceptTime();
				acceptTime.setAverageAccept(rs.getString("averageAccept"));
				acceptTime.setAverageOffSendCar(rs.getString("averageOffSendCar"));
				acceptTime.setDispatcher(rs.getString("dispatcher"));
				return acceptTime;
			}
		});
		List<AcceptTime> results1 = this.npJdbcTemplateSQLServer.query(sql2 + groupSQLServer, paramMap, new RowMapper<AcceptTime>() {
			@Override
			public AcceptTime mapRow(ResultSet rs, int index) throws SQLException {
				AcceptTime acceptTime = new AcceptTime();
				acceptTime.setDispatcher(rs.getString("dispatcher"));
				acceptTime.setAverageOffhookTime(rs.getString("averageOffhookTime"));
				return acceptTime;
			}
		});
		List<AcceptTime> summaryList = this.npJdbcTemplate.query(summary1, paramMap, new RowMapper<AcceptTime>() {
			@Override
			public AcceptTime mapRow(ResultSet rs, int index) throws SQLException {
				AcceptTime acceptTime = new AcceptTime();
				acceptTime.setAverageAccept(rs.getString("averageAccept"));
				acceptTime.setAverageOffSendCar(rs.getString("averageOffSendCar"));
				acceptTime.setDispatcher("合计");
				return acceptTime;
			}
		});
		List<AcceptTime> summaryList1 = this.npJdbcTemplateSQLServer.query(summary2, paramMap, new RowMapper<AcceptTime>() {
			@Override
			public AcceptTime mapRow(ResultSet rs, int index) throws SQLException {
				AcceptTime acceptTime = new AcceptTime();
				acceptTime.setAverageOffhookTime(rs.getString("averageOffhookTime"));
				acceptTime.setDispatcher("合计");
				return acceptTime;
			}
		});
		
		for (AcceptTime rs : results) {
			for (AcceptTime rs1 : results1) {
				if(rs.getDispatcher().equals(rs1.getDispatcher())){
					rs.setAverageOffhookTime(rs1.getAverageOffhookTime());
				}
			}
		}
		
		for (AcceptTime sm : summaryList) {
			for (AcceptTime sm1 : summaryList1) {
				if(sm.getDispatcher().equals(sm1.getDispatcher())){
					sm.setAverageOffhookTime(sm1.getAverageOffhookTime());
				}
			}
		}
		for (AcceptTime sm : summaryList) {
			results.add(sm);
		}
		for (AcceptTime result : results) {
			// result.setReadyTime(CommonUtil.formatSecond(result.getReadyTime()));
			// result.setLeaveTime(CommonUtil.formatSecond(result.getLeaveTime()));
		}
		logger.info("一共有" + results.size() + "条数据");

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