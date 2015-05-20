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
import com.xhs.ems.service.CenterTaskService;

/**
 * @author CUIXINGWEI
 * @datetime 2015年3月31日 上午9:32:24
 */
@Controller
@RequestMapping(value = "/page/base")
public class CenterTaskController {
	private static final Logger logger = Logger
			.getLogger(CenterTaskController.class);
	@Autowired
	private CenterTaskService centerService;

	@RequestMapping(value = "/getCenterTaskDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("中心任务信息统计");
		return centerService.getData(parameter);
	}

	@RequestMapping(value = "/exportCenterTaskDatas", method = RequestMethod.GET)
	public @ResponseBody void export(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("中心任务信息统计导出Excel");
		response.setContentType("application/msexcel;charset=GBK");

		String title = "中心任务信息统计";
		String[] parents = new String[] { "", "", "", "", "", "", "", "", "",
				"", "", "出诊人员", "", "" };// 父表头数组
		String[][] children = new String[][] { new String[] { "姓名" },
				new String[] { "患者地址" }, new String[] { "主诉" },
				new String[] { "呼救电话" }, new String[] { "受理时间" },
				new String[] { "派车时间" }, new String[] { "出车时间" },
				new String[] { "到达时间" }, new String[] { "返院时间" },
				new String[] { "送住地点" }, new String[] { "车辆" },
				new String[] { "医生", "护士", "司机" }, new String[] { "调度员" },
				new String[] { "出车结果" } };// 子表头数组
		String[] fields = new String[] { "name", "sickAddress",
				"sickDescription", "phone", "acceptTime", "sendCarTime",
				"drivingTime", "arrivalTime", "returnHospitalTime",
				"toAddress", "carCode", "doctor", "nurse", "driver",
				"dispatcher", "taskResult" };
		TableData td = ExcelUtils.createTableData(
				centerService.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(parents, children), fields);
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
