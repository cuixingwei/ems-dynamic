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
		String sql1 = "select  pc.姓名 name,病人主诉 sickDescription,送往地点 toAddress,随车医生 doctor,随车护士 nurse,司机 driver,事件编码,任务编码,家庭住址 sickAddress	into #temp1 	from AuSp120.tb_PatientCase pc "
				+ "left outer join AuSp120.tb_MrUser on 分站调度员编码=工号"
				+ " where 人员类型<>1 and 任务时刻 between :startTime and  :endTime ";
		String sql2= "select  e.呼救电话 phone,convert(varchar(20),e.受理时刻,120) acceptTime,convert(varchar(20),t.生成任务时刻,120) sendCarTime,	convert(varchar(20),t.出车时刻,120) drivingTime,convert(varchar(20),t.到达现场时刻,120) arrivalTime,"
				+ "convert(varchar(20),t.到达医院时刻,120) returnHospitalTime,	am.实际标识 carCode,m.姓名 dispatcher,dtr.NameM taskResult,t.任务编码,e.事件编码  into #temp2   	from  AuSp120.tb_TaskV t "
				+ "left outer join AuSp120.tb_Event e on t.事件编码=e.事件编码	left outer join AuSp120.tb_MrUser m on t.调度员编码=m.工号	"
				+ "left outer join AuSp120.tb_DTaskResult dtr on t.结果编码=dtr.Code	left outer join AuSp120.tb_Ambulance am on t.车辆编码=am.车辆编码	"
				+ "where e.事件性质编码=1  and m.人员类型<>1 and t.生成任务时刻 between :startTime and :endTime ";
		String sql3="select * from "
				+ "(select  name,sickAddress,sickDescription,phone,acceptTime,sendCarTime,drivingTime,arrivalTime,returnHospitalTime,toAddress,carCode,doctor,nurse,driver,dispatcher,taskResult	"
				+ "from #temp1 a left outer join  #temp2 b on a.事件编码=b.事件编码 and a.任务编码=b.任务编码   "
				+ "union   "
				+ "select  name,sickAddress,sickDescription,phone,acceptTime,sendCarTime,drivingTime,arrivalTime,returnHospitalTime,toAddress,carCode,doctor,nurse,driver,dispatcher,taskResult 	"
				+ "from #temp2 b left outer join #temp1 a on a.事件编码=b.事件编码 and a.任务编码=b.任务编码) t    "
				+ "where t.acceptTime is not null  order by t.acceptTime   "
				+ "drop table #temp1,#temp2";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql1 = sql1 + " and 分站编码=:station ";
			sql2 = sql2 + " and t.分站编码=:station ";
		}
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("station", parameter.getStation());

		int page = (int) parameter.getPage();
		int rows = (int) parameter.getRows();

		List<CenterTask> results = this.npJdbcTemplate.query(sql1+sql2+sql3, paramMap,
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

		Grid grid = new Grid();
		int fromIndex = (page - 1) * rows;
		int toIndex = (results.size() <= page * rows && results.size() >= (page - 1)
				* rows) ? results.size() : page * rows;
		grid.setRows(results.subList(fromIndex, toIndex));
		grid.setTotal(results.size());
		return grid;
	}
}
