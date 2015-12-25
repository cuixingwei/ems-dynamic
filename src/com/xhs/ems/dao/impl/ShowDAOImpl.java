package com.xhs.ems.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.Show;
import com.xhs.ems.dao.ShowDAO;

/**
 * @datetime 2015年12月25日 下午5:28:03
 * @author 崔兴伟
 */
@Repository
public class ShowDAOImpl implements ShowDAO {
	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<Show> getShow(Parameter parameter) {
		String sql = "(select '1' type,'值班人员' keys,AuSp120.GetOnlinePeople('1') value,'1' orders from AuSp120.tb_MrUser where 在线标志=1 "
				+ "union select '1' type,'呼入电话总数' keys,cast(COUNT(*) as varchar) value,'2' orders "
				+ "from AuSp120.tb_TeleRecord	where 记录类型编码 in (1,2,3,5,8) and 产生时刻 between :startTime and :endTime	"
				+ "union	select '1' type,'派车总数' keys,cast(COUNT(*) as varchar) value,'3' orders from AuSp120.tb_Task t	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码	"
				+ "where e.事件性质编码=1 and t.生成任务时刻 between :startTime and :endTime	union	"
				+ "select '1' type,s.分站名称 keys,cast(COUNT(*) as varchar) value,'4' orders from AuSp120.tb_Task t	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	left outer join AuSp120.tb_Station s on t.分站编码=s.分站编码	"
				+ "where e.事件性质编码=1 and t.生成任务时刻 between :startTime and :endTime	group by s.分站名称	union	"
				+ "select '2' type,dc.NameM keys,cast(COUNT(*) as varchar) value,'5' orders	from AuSp120.tb_PatientCase pc "
				+ "left outer join AuSp120.tb_DDiseaseClass dc on dc.Code=pc.分类统计编码	"
				+ "left outer join AuSp120.tb_TaskV t on t.任务序号=pc.序号 and t.任务编码=pc.任务编码	"
				+ "where dc.NameM is not null and t.生成任务时刻 between :startTime and :endTime	group by dc.NameM) order by orders";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		System.out.println("startTime:" + parameter.getStartTime()
				+ ";endTime:" + parameter.getEndTime());
		final List<Show> results = new ArrayList<Show>();
		this.npJdbcTemplate.query(sql, paramMap, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				Show show = new Show();
				show.setKeys(rs.getString("keys"));
				show.setType(rs.getString("type"));
				show.setValue(rs.getString("value"));
				results.add(show);
			}
		});
		return results;
	}
}
