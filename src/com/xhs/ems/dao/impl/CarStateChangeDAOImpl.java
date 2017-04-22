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

import com.xhs.ems.bean.CarStateChange;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.CarStateChangeDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月13日 下午7:44:12
 */
@Repository
public class CarStateChangeDAOImpl implements CarStateChangeDAO {
	private static final Logger logger = Logger
			.getLogger(CarStateChangeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月13日 下午7:44:12
	 * @see com.xhs.ems.dao.CarStateChangeDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "SELECT e.eventAddress eventName,vl.actualSign carCode,dvs.`name` carState,u.personName dispatcher,"
				+ "date_format(vl.createTime,'%Y-%c-%d %h:%i:%s') recordTime,vl.reason,ot.`name` recordClass	"
				+ "from vehicle_log vl LEFT JOIN  `event` e on e.eventCode=vl.eventCode	"
				+ "LEFT JOIN define_vehicle_state dvs on dvs.code=vl.vehicleState	"
				+ "LEFT JOIN operatortype ot on ot.`code`=vl.operatorType	"
				+ "LEFT JOIN `user` u on u.jobNum=vl.operatorJobNum	"
				+ "WHERE e.eventProperty=1 and vl.createTime  between :startTime and :endTime ";
		if (!CommonUtil.isNullOrEmpty(parameter.getCarCode())) {
			sql = sql + " and vl.vehicleCode=:carCode ";
		}
		if (!CommonUtil.isNullOrEmpty(parameter.getEventName())) {
			sql = sql + " and e.eventAddress  like :eventName ";
		}
		sql += " order by e.eventAddress,vl.createTime,vl.actualSign ";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("carCode", parameter.getCarCode());
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("eventName", "%" + parameter.getEventName() + "%");

		List<CarStateChange> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<CarStateChange>() {
					@Override
					public CarStateChange mapRow(ResultSet rs, int index)
							throws SQLException {
						CarStateChange carStateChange = new CarStateChange();
						carStateChange.setCarCode(rs.getString("carCode"));
						carStateChange.setCarState(rs.getString("carState"));
						carStateChange.setDispatcher(rs.getString("dispatcher"));
						carStateChange.setEventName(rs.getString("eventName"));
						carStateChange.setReason(rs.getString("reason"));
						carStateChange.setRecordClass(rs.getString("recordClass"));
						carStateChange.setRecordTime(rs.getString("recordTime"));
						return carStateChange;
					}
				});
		logger.info("一共有" + results.size() + "条数据");

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
