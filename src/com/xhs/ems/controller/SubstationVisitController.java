package com.xhs.ems.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xhs.ems.bean.Grid;
import com.xhs.ems.bean.Parameter;
import com.xhs.ems.excelTools.ExcelUtils;
import com.xhs.ems.excelTools.JsGridReportBase;
import com.xhs.ems.excelTools.TableData;
import com.xhs.ems.service.SubstationVisitService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 上午10:57:59
 */
@Controller
@RequestMapping(value = "/page/base")
public class SubstationVisitController {
	private static final Logger logger = Logger
			.getLogger(SubstationVisitController.class);

	@Autowired
	private SubstationVisitService substationVisitService;

	@RequestMapping(value = "/getSubstationVisitDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("急救站出诊情况查询");
		return substationVisitService.getData(parameter);
	}

	@RequestMapping(value = "/exportSubstationVisitDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("导出急救站出诊情况数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "急救站出诊情况";
		String[] headers = new String[] { "分站名称", "120派诊", "正常完成", "正常完成比率",
				"中止任务", "中止任务比率", "空车", "空车比率", "拒绝出车", "拒绝出车比率", "暂停调用",
				"救治人数" };
		String[] fields = new String[] { "station", "sendNumbers",
				"nomalNumbers", "nomalRate", "stopNumbers", "stopRate",
				"emptyNumbers", "emptyRate", "refuseNumbers", "refuseRate",
				"pauseNumbers", "treatNumbers" };
		TableData td = ExcelUtils.createTableData(substationVisitService
				.getData(parameter).getRows(), ExcelUtils
				.createTableHeader(headers), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);
		report.exportToExcel(title, "admin", td);
	}

}
