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
		String sql = "SELECT e.patientName name,e.eventAddress sickAddress,e.prejudge sickDescription,e.incomingCall phone,"
				+ "date_format(eh.handleBeginTime,'%Y-%c-%d %h:%i:%s') acceptTime,	"
				+ "date_format(et.createTime,'%Y-%c-%d %h:%i:%s') sendCarTime,date_format(et.taskDriveToTime,'%Y-%c-%d %h:%i:%s') drivingTime,	"
				+ "date_format(et.taskArriveTime,'%Y-%c-%d %h:%i:%s') arrivalTime,date_format(et.taskLeaveTime,'%Y-%c-%d %h:%i:%s') returnHospitalTime,	"
				+ "et.actualSign carCode,et.doctorName doctor,et.nurseName nurse,et.driverName driver,u.personName dispatcher,dtr.`name` taskResult	"
				+ "from `event` e LEFT JOIN event_history eh on eh.eventCode=e.eventCode 	"
				+ "LEFT JOIN event_task et on et.eventCode=eh.eventCode and eh.handleTimes=et.handleTimes	"
				+ "LEFT JOIN `user` u on u.jobNum=et.operatorJobNum	"
				+ "LEFT JOIN define_task_result dtr on dtr.`code`=et.taskResult"
				+ " where e.eventProperty=1 and e.createTime between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and et.stationCode=:station ";
		}
		sql += " order by e.createTime";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());

		List<CenterTask> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<CenterTask>() {
					@Override
					public CenterTask mapRow(ResultSet rs, int index)
							throws SQLException {
						CenterTask centerTask = new CenterTask();
						centerTask.setAcceptTime(rs.getString("acceptTime"));
						centerTask.setArrivalTime(rs.getString("arrivalTime"));
						centerTask.setCarCode(rs.getString("carCode"));
						centerTask.setDispatcher(rs.getString("dispatcher"));
						centerTask.setDoctor(rs.getString("doctor"));
						centerTask.setName(rs.getString("name"));
						centerTask.setNurse(rs.getString("nurse"));
						centerTask.setDriver(rs.getString("driver"));
						centerTask.setPhone(rs.getString("phone"));
						centerTask.setReturnHospitalTime(rs.getString("returnHospitalTime"));
						centerTask.setTaskResult(rs.getString("taskResult"));
						centerTask.setSendCarTime(rs.getString("sendCarTime"));
						centerTask.setSickAddress(rs.getString("sickAddress"));
						centerTask.setSickDescription(rs.getString("sickDescription"));
						return centerTask;

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