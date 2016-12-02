package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.StationTransfer;
import com.xhs.ems.dao.StationTransferDAO;

/**
 * @datetime 2016年4月19日 下午8:07:57
 * @author 崔兴伟
 */
@Repository
public class StationTransferDAOImpl implements StationTransferDAO{
	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "(select s.分站名称+'出车总数' station,count(*) total 	 "
				+ "from AuSp120.tb_PatientCase pc	left outer join AuSp120.tb_Ambulance am on am.实际标识=pc.actualSign	"
				+ "left outer join AuSp120.tb_Station s on s.分站编码=pc.stationCode	"
				+ "left outer join AuSp120.tb_Task t on am.车辆编码=t.车辆编码 and pc.任务编码=t.任务编码	"
				+ "where t.生成任务时刻 between :startTime and :endTime	group by s.分站名称) union	"
				+ "(select s.分站名称+'转'+pc.toAddr station,count(*) total	 from AuSp120.tb_PatientCase pc	"
				+ "left outer join AuSp120.tb_Ambulance am on am.实际标识=pc.actualSign and pc.pcOrder=1	"
				+ "left outer join AuSp120.tb_Station s on s.分站编码=pc.stationCode	"
				+ "left outer join AuSp120.tb_Task t on am.车辆编码=t.车辆编码 and pc.任务编码=t.任务编码	"
				+ "where t.生成任务时刻 between :startTime and :endTime	group by s.分站名称,pc.toAddr)";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("endTime", parameter.getEndTime());
		paramMap.put("startTime", parameter.getStartTime());

		List<StationTransfer> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<StationTransfer>() {
					@Override
					public StationTransfer mapRow(ResultSet rs, int index)
							throws SQLException {
						StationTransfer stationTransfer = new StationTransfer();
						stationTransfer.setName(rs.getString("station"));
						stationTransfer.setNumbers(rs.getString("total"));
						return stationTransfer;
					}
				});


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
