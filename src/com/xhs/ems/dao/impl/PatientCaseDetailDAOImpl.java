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

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.PatientCaseDetail;
import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.dao.PatientCaseDetailDAO;

/**
 * @datetime 2016年9月27日 下午12:38:39
 * @author 崔兴伟
 */
@Repository
public class PatientCaseDetailDAOImpl implements PatientCaseDetailDAO {

	private static final Logger logger = Logger
			.getLogger(PatientCaseDetailDAOImpl.class);

	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public Grid getData(Parameter parameter) {
		String sql = "select CONVERT(varchar(20),a.开始受理时刻,120) alarmTime,a.现场地址 alarmAddr,CONVERT(varchar(20),t.到达现场时刻,120) arriveSpotTime,pc.姓名 patientName,	"
				+ "pc.性别 sex,pc.年龄 age,pc.医生诊断 judgementOnPhone,pc.既往病史 pastIllness,CONVERT(varchar(20),t.到达医院时刻,120) arriveHospitalTime,	"
				+ "case when pc.送往地点='' then s.分站名称 else pc.送往地点 end sendHospital,am.车牌号码 plateNo,	"
				+ "cureMeasure = (stuff((select ',' + NameM from  AuSp120.tb_CureMeasure cm left outer join AuSp120.tb_DMeasure dm on dm.Code=cm.救治措施编码 where cm.任务编码 = pc.任务编码 and cm.病例序号=pc.序号 for xml path('')),1,1,'')) "
				+ "from AuSp120.tb_PatientCase pc	left outer join AuSp120.tb_Ambulance am on am.实际标识=pc.车辆标识	"
				+ "left outer join AuSp120.tb_TaskV t on t.任务编码=pc.任务编码 and am.车辆编码=t.车辆编码	"
				+ "left outer join AuSp120.tb_AcceptDescriptV a on a.事件编码=t.事件编码 and a.受理序号=t.受理序号	"
				+ "left outer join AuSp120.tb_Station s on s.分站编码=pc.分站编码	"
				+ "left outer join AuSp120.tb_EventV e on e.事件编码=t.事件编码	"
				+ "where e.事件性质编码=1 and pc.姓名<>'' and a.开始受理时刻 between :startTime and :endTime  ";
		Map<String, String> paramMap = new HashMap<String, String>();
		if(!CommonUtil.isNullOrEmpty(parameter.getDoctorDiagnosis())){
			paramMap.put("doctorDiagnosis", "%"+parameter.getDoctorDiagnosis()+"%");
			sql += " and pc.医生诊断 like :doctorDiagnosis ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getIllDepartment())){
			paramMap.put("illDepartment", parameter.getIllDepartment());
			sql += " and pc.疾病科别编码=:illDepartment ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getIllClass())){
			paramMap.put("illClass", parameter.getIllClass());
			sql += " and pc.分类统计编码=:illClass ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getIllState())){
			paramMap.put("illState", parameter.getIllState());
			sql += " and pc.病情编码=:illState ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getAidResult())){
			paramMap.put("aidResult", parameter.getAidResult());
			sql += " and pc.救治结果编码=:aidResult ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getDeathProof())){
			paramMap.put("deathProof", parameter.getDeathProof());
			sql += " and pc.死亡证明编码=:deathProof ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getSendAddrType())){
			paramMap.put("sendAddrType", parameter.getSendAddrType());
			sql += " and pc.送往地点类型编码=:sendAddrType ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getSpotAddrType())){
			paramMap.put("spotAddrType", parameter.getSpotAddrType());
			sql += " and pc.现场地点类型编码=:spotAddrType ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getIllReason())){
			paramMap.put("illReason", parameter.getIllReason());
			sql += " and pc.病因编码=:illReason ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getCarPlate())){
			paramMap.put("carPlate", parameter.getCarPlate());
			sql += " and t.车辆编码=:carPlate ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getOutCome())){
			paramMap.put("outCome", parameter.getOutCome());
			sql += " and pc.转归编码=:outCome ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getPatientCooperation())){
			paramMap.put("patientCooperation", parameter.getPatientCooperation());
			sql += " and pc.病家合作编码=:patientCooperation ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getSex())){
			paramMap.put("sex", parameter.getSex());
			sql += " and pc.性别=:sex ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getIdentity())){
			paramMap.put("identity", parameter.getIdentity());
			sql += " and pc.身份编码=:identity ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getProfession())){
			paramMap.put("profession", parameter.getProfession());
			sql += " and pc.职业编码=:profession ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getDoctor())){
			paramMap.put("doctor", "%"+parameter.getDoctor()+"%");
			sql += " and pc.随车医生 like :doctor ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getNurse())){
			paramMap.put("nurse", "%"+parameter.getNurse()+"%");
			sql += " and pc.随车护士 like :nurse ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getDriver())){
			paramMap.put("driver", "%"+parameter.getDriver()+"%");
			sql += " and pc.司机 like :driver ";
		}
		if(!CommonUtil.isNullOrEmpty(parameter.getPatientName())){
			paramMap.put("patientName", "%"+parameter.getPatientName()+"%");
			sql += " and pc.姓名 like :patientName ";
		}
		paramMap.put("startTime", parameter.getStartTime());
		paramMap.put("endTime", parameter.getEndTime());
		sql += " order by a.开始受理时刻";
		

		List<PatientCaseDetail> results = this.npJdbcTemplate.query(sql, paramMap,
				new RowMapper<PatientCaseDetail>() {
					@Override
					public PatientCaseDetail mapRow(ResultSet rs, int index)
							throws SQLException {
						PatientCaseDetail patientCaseDetail = new PatientCaseDetail();
						patientCaseDetail.setAge(rs.getString("age"));
						patientCaseDetail.setAlarmAddr(rs.getString("alarmAddr"));
						patientCaseDetail.setAlarmTime(rs.getString("alarmTime"));
						patientCaseDetail.setArriveHospitalTime(rs.getString("arriveHospitalTime"));
						patientCaseDetail.setArriveSpotTime(rs.getString("arriveSpotTime"));
						patientCaseDetail.setCureMeasure(rs.getString("cureMeasure"));
						patientCaseDetail.setJudgementOnPhone(rs.getString("judgementOnPhone"));
						patientCaseDetail.setPastIllness(rs.getString("pastIllness"));
						patientCaseDetail.setPatientName(rs.getString("patientName"));
						patientCaseDetail.setPlateNo(rs.getString("plateNo"));
						patientCaseDetail.setSendHospital(rs.getString("sendHospital"));
						patientCaseDetail.setSex(rs.getString("sex"));
						return patientCaseDetail;
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
