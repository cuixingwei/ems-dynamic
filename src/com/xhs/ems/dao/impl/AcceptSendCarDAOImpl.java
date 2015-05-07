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

import com.xhs.ems.bean.AcceptSendCar;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AcceptSendCarDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年5月7日 下午3:09:30
 */
@Repository
public class AcceptSendCarDAOImpl implements AcceptSendCarDAO {
	private static final Logger logger = Logger
			.getLogger(AcceptSendCarDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年5月7日 下午3:09:31
	 * @see com.xhs.ems.dao.AcceptSendCarDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select m.姓名  dispatcher,CONVERT(varchar(20),a.开始受理时刻,120) startAcceptTime,"
				+ "CONVERT(varchar(20),a.派车时刻,120) sendCarTime,	 "
				+ "dat.NameM acceptType,a.呼救电话  ringPhone,DATEDIFF(SECOND,a.开始受理时刻,a.派车时刻) sendCarTimes,a.备注  remark from AuSp120.tb_Task t	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号 "
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "left outer join AuSp120.tb_MrUser m on t.调度员编码=m.工号	"
				+ "left outer join AuSp120.tb_DAcceptDescriptType dat on dat.Code=a.类型编码	"
				+ "where e.事件性质编码=1  and m.人员类型=0 and a.开始受理时刻<a.派车时刻  and a.开始受理时刻 between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getDispatcher())) {
			sql = sql + " and t.调度员编码=:dispatcher ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getOvertimes())) {
			sql = sql + " and DATEDIFF(SECOND,a.开始受理时刻,a.派车时刻)>=:overtimes ";
		}
		sql += " order by m.姓名";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("dispatcher", parameter.getDispatcher());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("overtimes", parameter.getOvertimes());

		int page = (int) parameter.getPage();
		int rows = (int) parameter.getRows();

		List<AcceptSendCar> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<AcceptSendCar>() {
					@Override
					public AcceptSendCar mapRow(ResultSet rs, int index)
							throws SQLException {

						return new AcceptSendCar(rs.getString("dispatcher"), rs
								.getString("startAcceptTime"), rs
								.getString("sendCarTime"), rs
								.getString("acceptType"), rs
								.getString("ringPhone"), rs
								.getString("sendCarTimes"), rs
								.getString("remark"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		for (AcceptSendCar result : results) {
			result.setSendCarTimes(CommonUtil.formatSecond(result
					.getSendCarTimes()));
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
