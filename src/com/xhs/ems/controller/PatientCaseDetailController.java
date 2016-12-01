package com.xhs.ems.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.bean.SessionInfo;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.PatientCaseDetailService;

/**
 * @datetime 2016年9月27日 下午12:17:34
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "/page/base")
public class PatientCaseDetailController {
	private static final Logger logger = Logger
			.getLogger(PatientCaseDetailController.class);

	@Autowired
	private PatientCaseDetailService patientCaseDetailService;

	@RequestMapping(value = "/getPatientCaseDetailDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("院前病历一览表");
		return patientCaseDetailService.getData(parameter);
	}

	@RequestMapping(value = "/exportPatientCaseDetailDatas", method = RequestMethod.GET)
	public @ResponseBody void export(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出院前病历一览表数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "院前病历一览表";
		String[] headers = new String[] { "接警时间", "接警地址", "到达现场时间", "患者姓名", "年龄",
				"性别", "医生诊断", "既往病史", "到达医院时刻", "治疗措施","送往医院","车牌号码" };
		String[] fields = new String[] { "alarmTime", "alarmAddr", "arriveSpotTime",
				"patientName", "age", "sex", "judgementOnPhone",
				"pastIllness", "arriveHospitalTime", "cureMeasure","sendHospital","plateNo" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(
				patientCaseDetailService.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(headers, spanCount), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		
		HttpSession session = request.getSession();
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute("sessionInfo");
		if (null != sessionInfo) {
			report.exportToExcel(title, sessionInfo.getUser().getName(), td,
					parameter);
		} else {
			report.exportToExcel(title, "", td, parameter);
		}

	}
}
