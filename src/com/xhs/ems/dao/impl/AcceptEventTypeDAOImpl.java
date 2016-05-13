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

import com.xhs.ems.bean.AcceptEventType;
import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.AcceptEventTypeDAO;

@Repository("AcceptEventTypeDAO")
public class AcceptEventTypeDAOImpl implements AcceptEventTypeDAO {

	private static final Logger logger = Logger
			.getLogger(AcceptEventTypeDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select a.调度员编码,	COUNT(*) numbersOfSendCar,"
				+ "sum(case when t.结果编码=2 then 1 else 0 end) stopTask,'' ratioStopTask,	sum(case when t.结果编码=3 then 1 else 0 end) emptyCar,'' ratioEmptyCar,"
				+ "sum(case when t.结果编码=4 then 1 else 0 end) nomalComplete,'' ratioComplete	into #temp1 "
				+ "from  AuSp120.tb_AcceptDescriptV a	left outer join AuSp120.tb_TaskV t on a.事件编码=t.事件编码 and a.受理序号=t.受理序号 "
				+ "left outer join AuSp120.tb_Event e on e.事件编码=a.事件编码	"
				+ "where e.事件性质编码=1 and a.开始受理时刻 between :startTime and :endTime 	group by a.调度员编码 "
				+ "select a.调度员编码,sum(case when a.类型编码=1 then 1 else 0 end) numbersOfNormalSendCar,"
				+ "sum(case when a.类型编码=2 then 1 else 0 end) numbersOfNormalHangUp,	"
				+ "sum(case when a.类型编码=3 then 1 else 0 end) numbersOfReinforceSendCar,"
				+ "sum(case when a.类型编码=4 then 1 else 0 end) numbersOfReinforceHangUp,	"
				+ "sum(case when a.类型编码=5 then 1 else 0 end) numbersOfStopTask,sum(case when a.类型编码=6 then 1 else 0 end) specialEvent,"
				+ "sum(case when a.类型编码=7 then 1 else 0 end) noCar,sum(case when a.类型编码=8 then 1 else 0 end) transmitCenter,	"
				+ "sum(case when a.类型编码=9 then 1 else 0 end) refuseSendCar,"
				+ "sum(case when a.类型编码=10 then 1 else 0 end) wakeSendCar	into #temp2  "
				+ "from  AuSp120.tb_AcceptDescriptV a	"
				+ "left outer join AuSp120.tb_Event e on e.事件编码=a.事件编码	left outer join AuSp120.tb_MrUser m on m.工号=a.调度员编码 "
				+ "where e.事件性质编码=1   and a.开始受理时刻 between :startTime and :endTime	group by a.调度员编码 "
				+ "select tr.调度员编码,COUNT(*) numbersOfPhone into #temp3 from AuSp120.tb_TeleRecordV tr where tr.记录类型编码 in (1,2,3,5,8) and 结果编码=4 "
				+ "and tr.产生时刻 between :startTime and :endTime group by tr.调度员编码 "
				+ "select m.姓名 dispatcher,t1.emptyCar,t1.nomalComplete,t1.numbersOfSendCar,t1.ratioComplete,t1.ratioEmptyCar,"
				+ "t1.ratioStopTask,t1.stopTask,t2.noCar,t2.numbersOfNormalHangUp,t2.numbersOfNormalSendCar,t2.numbersOfReinforceHangUp,"
				+ "t2.numbersOfReinforceSendCar,t2.numbersOfStopTask,t2.refuseSendCar,t2.specialEvent,t2.transmitCenter,"
				+ "t2.wakeSendCar,t3.numbersOfPhone from #temp2 t2 left outer join #temp1 t1 on t1.调度员编码=t2.调度员编码 "
				+ "left outer join #temp3 t3 on t2.调度员编码=t3.调度员编码 "
				+ "left outer join AuSp120.tb_MrUser m on m.工号=t2.调度员编码 where  m.人员类型=0 "
				+ "drop table #temp1,#temp2,#temp3";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());

		List<AcceptEventType> results = this.npJdbcTemplate.query(sql,
				paramMap, new RowMapper<AcceptEventType>() {
					@Override
					public AcceptEventType mapRow(ResultSet rs, int index)
							throws SQLException {
						AcceptEventType acceptEventType = new AcceptEventType();
						acceptEventType.setDispatcher(rs.getString("dispatcher"));
						acceptEventType.setEmptyCar(rs.getString("emptyCar"));
						acceptEventType.setNoCar(rs.getString("noCar"));
						acceptEventType.setNomalComplete(rs.getString("nomalComplete"));
						acceptEventType.setNumbersOfNormalHangUp(rs.getString("numbersOfNormalHangUp"));
						acceptEventType.setNumbersOfNormalSendCar(rs.getString("numbersOfNormalSendCar"));
						acceptEventType.setNumbersOfPhone(rs.getString("numbersOfPhone"));
						acceptEventType.setNumbersOfReinforceHangUp(rs.getString("numbersOfReinforceHangUp"));
						acceptEventType.setNumbersOfReinforceSendCar(rs.getString("numbersOfReinforceSendCar"));
						acceptEventType.setNumbersOfSendCar(rs.getString("numbersOfSendCar"));
						acceptEventType.setNumbersOfStopTask(rs.getString("numbersOfStopTask"));
						acceptEventType.setRatioComplete(rs.getString("ratioComplete"));
						acceptEventType.setRatioEmptyCar(rs.getString("ratioEmptyCar"));
						acceptEventType.setRatioStopTask(rs.getString("ratioStopTask"));
						acceptEventType.setRefuseSendCar(rs.getString("refuseSendCar"));
						acceptEventType.setSpecialEvent(rs.getString("specialEvent"));
						acceptEventType.setStopTask(rs.getString("stopTask"));
						acceptEventType.setTransmitCenter(rs.getString("transmitCenter"));
						acceptEventType.setWakeSendCar(rs.getString("wakeSendCar"));

						return acceptEventType;
					}
				});
		logger.info("一共有" + results.size() + "条数据");
		AcceptEventType summary = new AcceptEventType("合计", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
		for (AcceptEventType result : results) {
			summary.setEmptyCar(Integer.parseInt(result.getEmptyCar())+Integer.parseInt(summary.getEmptyCar())+"");
			summary.setNoCar(Integer.parseInt(result.getNoCar())+Integer.parseInt(summary.getNoCar())+"");
			summary.setNomalComplete(Integer.parseInt(result.getNomalComplete())+Integer.parseInt(summary.getNomalComplete())+"");
			summary.setNumbersOfNormalHangUp(Integer.parseInt(result.getNumbersOfNormalHangUp())+Integer.parseInt(summary.getNumbersOfNormalHangUp())+"");
			summary.setNumbersOfNormalSendCar(Integer.parseInt(result.getNumbersOfNormalSendCar())+Integer.parseInt(summary.getNumbersOfNormalSendCar())+"");
			summary.setNumbersOfPhone(Integer.parseInt(result.getNumbersOfPhone())+Integer.parseInt(summary.getNumbersOfPhone())+"");
			summary.setNumbersOfReinforceHangUp(Integer.parseInt(result.getNumbersOfReinforceHangUp())+Integer.parseInt(summary.getNumbersOfReinforceHangUp())+"");
			summary.setNumbersOfReinforceSendCar(Integer.parseInt(result.getNumbersOfReinforceSendCar())+Integer.parseInt(summary.getNumbersOfReinforceSendCar())+"");
			summary.setNumbersOfSendCar(Integer.parseInt(result.getNumbersOfSendCar())+Integer.parseInt(summary.getNumbersOfSendCar())+"");
			summary.setNumbersOfStopTask(Integer.parseInt(result.getNumbersOfStopTask())+Integer.parseInt(summary.getNumbersOfStopTask())+"");
			summary.setRefuseSendCar(Integer.parseInt(result.getRefuseSendCar())+Integer.parseInt(summary.getRefuseSendCar())+"");
			summary.setSpecialEvent(Integer.parseInt(result.getSpecialEvent())+Integer.parseInt(summary.getSpecialEvent())+"");
			summary.setStopTask(Integer.parseInt(result.getStopTask())+Integer.parseInt(summary.getStopTask())+"");
			summary.setTransmitCenter(Integer.parseInt(result.getTransmitCenter())+Integer.parseInt(summary.getTransmitCenter())+"");
			summary.setWakeSendCar(Integer.parseInt(result.getWakeSendCar())+Integer.parseInt(summary.getWakeSendCar())+"");
		}
		results.add(summary);
		
		for (AcceptEventType result : results) {
			result.setRatioStopTask(CommonUtil.calculateRate(
					Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getStopTask().toString())));
			result.setRatioComplete(CommonUtil.calculateRate(
					Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getNomalComplete().toString())));
			result.setRatioEmptyCar(CommonUtil.calculateRate(
					Integer.parseInt(result.getNumbersOfSendCar().toString()),
					Integer.parseInt(result.getEmptyCar().toString())));
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
