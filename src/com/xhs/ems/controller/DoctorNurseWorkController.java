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
import com.xhs.ems.service.DoctorNurseWorkService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月22日 下午4:44:25
 */
@Controller
@RequestMapping(value = "/page/base")
public class DoctorNurseWorkController {
	private static final Logger logger = Logger
			.getLogger(DoctorNurseWorkController.class);
	@Autowired
	private DoctorNurseWorkService doctorNurseWorkService;

	@RequestMapping(value = "getDoctorNurseWorkData", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("医生护士工作情况统计");
		return doctorNurseWorkService.getData(parameter);
	}

	@RequestMapping(value = "exportDoctorNurseWorkData", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出医生护士工作情况统计到Excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title1 = "医生工作情况统计";
		String title2 = "护士工作情况统计";
		String[] headers = new String[] { "分站", "姓名", "出车数", "有效出车数", "中止数(中止、空车)","拒绝出车数",
				"救治人数", "平均救治时间" };
		String[] fields = new String[] { "station", "name", "outCarNumbers",
				"validOutCarNumbers", "stopNumbers", "refuseNumbers","curePeopleNumbers",
				"averateCureTimes" };
		int spanCount = 1; // 需要合并的列数。从第1列开始到指定列。
		TableData td = ExcelUtils.createTableData(doctorNurseWorkService
				.getData(parameter).getRows(), ExcelUtils.createTableHeader(
				headers, spanCount), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);

		HttpSession session = request.getSession();
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute("sessionInfo");
		if (null != sessionInfo) {
			if (Integer.parseInt(parameter.getDoctorOrNurse()) == 1) {
				report.exportToExcel(title1, sessionInfo.getUser().getName(),
						td, parameter);
			} else {
				report.exportToExcel(title2, sessionInfo.getUser().getName(),
						td, parameter);
			}
		} else {
			if (Integer.parseInt(parameter.getDoctorOrNurse()) == 1) {
				report.exportToExcel(title1, "", td, parameter);
			} else {
				report.exportToExcel(title2, "", td, parameter);
			}
		}
	}
}
