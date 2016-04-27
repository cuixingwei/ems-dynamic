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

import com.xhs.ems.bean.DriverWork;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.DriverWorkDAO;

/**
 * @author 崔兴伟
 * @datetime 2015年4月17日 下午4:01:17
 */
@Repository
public class DriverWorkDAOImpl implements DriverWorkDAO {

	private static final Logger logger = Logger
			.getLogger(DriverWorkDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * @author 崔兴伟
	 * @datetime 2015年4月17日 下午4:01:17
	 * @see com.xhs.ems.dao.DriverWorkDAO#getData(com.xhs.ems.bean.Parameter)
	 */
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select distinct 任务编码,司机,车辆标识 into #pc from AuSp120.tb_PatientCase "
				+ "select t.生成任务时刻,t.出车时刻,t.到达现场时刻,结果编码,pc.司机,t.分站编码 into #temp1 	"
				+ "from AuSp120.tb_Task t	left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码 "
				+ "left outer join AuSp120.tb_Ambulance am on am.车辆编码=t.车辆编码 "
				+ "	left outer join #pc	pc on pc.车辆标识=am.实际标识 and pc.任务编码=t.任务编码	"
				+ "where e.事件性质编码=1 and pc.司机<>'' 	and t.生成任务时刻 between :startTime and :endTime  ";
		if (!CommonUtil.isNullOrEmpty(parameter.getStation())) {
			sql = sql + " and t.分站编码=:station ";
		}
		sql += " select 分站编码,p.司机,count(p.司机) as pauseNumbers into #temp2 	"
				+ "from AuSp120.tb_RecordPauseReason p	left join AuSp120.tb_Ambulance a on p.车辆编码=a.车辆编码 	"
				+ "where p.操作时刻 between :startTime and :endTime and p.司机<>''	group by (分站编码),(p.司机) "
				+ "select t.分站编码,t.司机 driver,SUM(case when t.生成任务时刻 is not null then 1 else 0 end) outCarNumbers,	"
				+ "SUM(case when t.结果编码=4 then 1 else 0 end) nomalNumbers,	"
				+ "SUM(case when t.结果编码=3 then 1 else 0 end) emptyNumbers,	"
				+ "SUM(case when t.结果编码=2 then 1 else 0 end) stopNumbers,	"
				+ "SUM(case when t.结果编码=5 then 1 else 0 end) refuseNumbers into #temp3	"
				+ "from #temp1 t group by t.分站编码,t.司机  "
				+ "select t.分站编码,t.司机 driver,AVG(DATEDIFF(Second,t.生成任务时刻,t.出车时刻)) averageOutCarTimes 	into #temp4	"
				+ "from #temp1 t "
				+ "where t.生成任务时刻<t.出车时刻 group by t.分站编码,t.司机  select t.分站编码,t.司机 driver,"
				+ "isnull(AVG(DATEDIFF(Second,t.出车时刻,t.到达现场时刻)),0) averageArriveSpotTimes	into #temp5	"
				+ "from #temp1 t "
				+ "where t.出车时刻<t.到达现场时刻 and t.出车时刻 is not null group by t.分站编码,t.司机  "
				+ "select s.分站名称 station,t3.driver,outCarNumbers,nomalNumbers,stopNumbers,emptyNumbers,isnull(refuseNumbers,0) refuseNumbers,	"
				+ "isnull(pauseNumbers,0) pauseNumbers,averageOutCarTimes,isnull(averageArriveSpotTimes,0) averageArriveSpotTimes	"
				+ "from AuSp120.tb_Station s left outer join #temp3 t3 on t3.分站编码=s.分站编码	"
				+ "left outer join #temp2 t2 on t3.分站编码=t2.分站编码 and t3.driver=t2.司机	"
				+ "left outer join #temp4 t4 on t3.分站编码=t4.分站编码 and t3.driver=t4.driver	"
				+ "left outer join #temp5 t5 on t3.分站编码=t5.分站编码 and t3.driver=t5.driver	"
				+ "where t3.driver<>''	order by s.显示顺序  "
				+ "drop table #temp1,#temp2,#temp3,#temp4,#temp5,#pc";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("station", parameter.getStation());
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("startTime", parameter.getStartTime());
		logger.info(sql);

		List<DriverWork> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<DriverWork>() {
					@Override
					public DriverWork mapRow(ResultSet rs, int index)
							throws SQLException {
						DriverWork driverWork = new DriverWork();
						driverWork.setAverageArriveSpotTimes(rs.getString("averageArriveSpotTimes"));
						driverWork.setAverageOutCarTimes(rs.getString("averageOutCarTimes"));
						driverWork.setDriver(rs.getString("driver"));
						driverWork.setEmptyNumbers(rs.getString("emptyNumbers"));
						driverWork.setNomalNumbers(rs.getString("nomalNumbers"));
						driverWork.setOutCarNumbers(rs.getString("outCarNumbers"));
						driverWork.setPauseNumbers(rs.getString("pauseNumbers"));
						driverWork.setRefuseNumbers(rs.getString("refuseNumbers"));
						driverWork.setStation(rs.getString("station"));
						driverWork.setStopNumbers(rs.getString("stopNumbers"));
						return driverWork;
					}
				});
		logger.info("一共有" + results.size() + "条数据");

		for (DriverWork result : results) {
			result.setAverageArriveSpotTimes(CommonUtil.formatSecond(result
					.getAverageArriveSpotTimes()));
			result.setAverageOutCarTimes(CommonUtil.formatSecond(result
					.getAverageOutCarTimes()));
		}

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
