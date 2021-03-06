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
import com.xhs.ems.service.SubstationVisitService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月16日 上午10:57:59
 */
@Controller
@RequestMapping(value = "/page/base")
public class SubstationVisitController {
	private static final Logger logger = Logger.getLogger(SubstationVisitController.class);

	@Autowired
	private SubstationVisitService substationVisitService;

	@RequestMapping(value = "/getSubstationVisitDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("急救站出诊情况查询");
		return substationVisitService.getData(parameter);
	}

	@RequestMapping(value = "/exportSubstationVisitDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("导出急救站出诊情况数据到excel");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "急救站出诊情况";
		String[] parents = new String[] { "", "", "", "", "", "", "", "", "", "", "", "", "救治人数(病例)" };// 父表头数组
		String[][] children = new String[][] { new String[] { "分站名称" }, new String[] { "120派诊" },
				new String[] { "正常完成" }, new String[] { "正常完成比率" }, new String[] { "中止任务" }, new String[] { "中止任务比率" },
				new String[] { "空车" }, new String[] { "空车比率" }, new String[] { "拒绝出车" }, new String[] { "拒绝出车比率" },
				new String[] { "暂停调用" }, new String[] { "择院次数" },
				new String[] { "未治", "救后死亡(现场)", "有效", "无变化", "稳定", "救后死亡(途中)", "救后死亡(院内)", "合计" } };// 子表头数组
		String[] fields = new String[] { "station", "sendNumbers", "nomalNumbers", "nomalRate", "stopNumbers",
				"stopRate", "emptyNumbers", "emptyRate", "refuseNumbers", "refuseRate", "pauseNumbers",
				"choiseHosNumbers", "noTreat", "afterDeathSpot", "effect", "noChange", "stable", "afterDeathRoad",
				"afterDeathHopital", "treatNumbers" };
		TableData td = ExcelUtils.createTableData(substationVisitService.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(parents, children), fields);
		JsGridReportBase report = new JsGridReportBase(request, response);

		HttpSession session = request.getSession();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute("sessionInfo");
		if (null != sessionInfo) {
			report.exportToExcel(title, sessionInfo.getUser().getName(), td, parameter);
		} else {
			report.exportToExcel(title, "", td, parameter);
		}
	}

}
