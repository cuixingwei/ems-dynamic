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

import com.xhs.ems.bean.CenterTask;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.CenterTaskDAO;

/**
 * @author CUIXINGWEI
 * @datetime 2015年3月31日 上午9:09:04
 */
@Repository
public class CenterTaskDAOImpl implements CenterTaskDAO {
	private static final Logger logger = Logger
			.getLogger(CenterTaskDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author CUIXINGWEI
	 * @see com.xhs.ems.dao.CenterTaskDAO#getData(com.xhs.ems.bean.Parameter)
	 * @datetime 2015年3月31日 上午9:09:04
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select pc.姓名 name,pc.家庭住址 sickAddress,pc.病人主诉 sickDescription,e.呼救电话 phone,"
				+ "convert(varchar(20),e.受理时刻,120) acceptTime, convert(varchar(20),t.生成任务时刻,120) sendCarTime,"
				+ "convert(varchar(20),t.出车时刻,120) drivingTime,convert(varchar(20),t.到达现场时刻,120) arrivalTime,"
				+ " convert(varchar(20),t.到达医院时刻,120) returnHospitalTime,pc.送往地点 toAddress,am.实际标识 carCode,"
				+ "pc.随车医生 doctor,pc.随车护士 nurse, pc.司机 driver,m.姓名 dispatcher,tr.NameM taskResult from AuSp120.tb_EventV  e "
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=e.事件编码 "
				+ "left outer join AuSp120.tb_Task t on t.事件编码=a.事件编码 and a.受理序号=t.受理序号 "
				+ "left outer join AuSp120.tb_Ambulance am on t.车辆编码=am.车辆编码 "
				+ "left outer join AuSp120.tb_MrUser m on m.工号=t.调度员编码 "
				+ "left outer join AuSp120.tb_DTaskResult tr on tr.Code=t.结果编码 "
				+ "full outer join AuSp120.tb_PatientCase pc on am.实际标识=pc.车辆标识 and t.任务编码=pc.任务编码"
				+ " where e.事件性质编码=1 and a.开始受理时刻 between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and t.分站编码=:station ";
		}
		sql += " order by a.开始受理时刻";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());

		List<CenterTask> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<CenterTask>() {
					@Override
					public CenterTask mapRow(ResultSet rs, int index)
							throws SQLException {

						return new CenterTask(rs.getString("name"), rs
								.getString("sickAddress"), rs
								.getString("sickDescription"), rs
								.getString("phone"),
								rs.getString("acceptTime"), rs
										.getString("sendCarTime"), rs
										.getString("drivingTime"), rs
										.getString("arrivalTime"), rs
										.getString("returnHospitalTime"), rs
										.getString("toAddress"), rs
										.getString("carCode"), rs
										.getString("doctor"), rs
										.getString("nurse"), rs
										.getString("driver"), rs
										.getString("dispatcher"), rs
										.getString("taskResult"));
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		logger.info(sql);

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