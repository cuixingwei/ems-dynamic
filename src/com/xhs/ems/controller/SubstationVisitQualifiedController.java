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
import com.xhs.ems.service.SubstationVisitQualifiedService;

/**
 * @datetime 2016年6月12日 下午6:19:57
 * @author 崔兴伟
 */
@Controller
@RequestMapping(value = "/page/base")
public class SubstationVisitQualifiedController {
	private static final Logger logger = Logger
			.getLogger(SubstationVisitQualifiedController.class);

	@Autowired
	private SubstationVisitQualifiedService substationVisitQualifiedService;

	@RequestMapping(value = "/getSubstationVisitQualifiedDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("急救站出诊合格率");
		return substationVisitQualifiedService.getData(parameter);
	}

	@RequestMapping(value = "/exportSubstationVisitQualifiedDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出急救站出诊合格率统计数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "急救站出诊合格率";
		String[] headers = new String[] { "分站名称", "出车总次数", "正常出诊次数", "晚出诊次数(2分钟)",
				"出诊合格率" };
		String[] fields = new String[] { "station", "total",
				"normal", "late", "rate" };
		TableData td = ExcelUtils.createTableData(substationVisitQualifiedService
				.getData(parameter).getRows(), ExcelUtils
				.createTableHeader(headers), fields);
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
