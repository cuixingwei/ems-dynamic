package com.xhs.ems.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
import com.xhs.ems.service.AnswerAlarmService;

/**
 * @author 崔兴伟
 * @datetime 2015年4月15日 下午8:22:33
 */
@Controller
@RequestMapping(value = "/page/base")
public class AnswerAlarmController {
	private static final Logger logger = Logger
			.getLogger(AnswerAlarmController.class);

	@Autowired
	private AnswerAlarmService answerAlarmService;

	@RequestMapping(value = "/getAnswerAlarmDatas", method = RequestMethod.POST)
	public @ResponseBody Grid getData(Parameter parameter) {
		logger.info("中心接警统计");
		return answerAlarmService.getData(parameter);
	}

	@RequestMapping(value = "/getPhoneRecord", method = RequestMethod.POST)
	public @ResponseBody Grid getPhoneRecord(Parameter parameter) {
		logger.info("通话记录统计");
		return answerAlarmService.getPhoneRecord(parameter);
	}

	@RequestMapping(value = "/exportRecordFile", method = RequestMethod.GET)
	public @ResponseBody void exportRecordFile(HttpServletResponse response,
			String recordPath, String fileName, HttpServletRequest request) throws IOException {
		logger.info("fileName:" + fileName + ";recordPath:" + recordPath);
		HttpURLConnection httpUrl = null;
		URL url = null;
		InputStream inputStream = null;
		OutputStream out = null;
		try {
			// 建立链接
			url = new URL(recordPath);
			httpUrl = (HttpURLConnection) url.openConnection();
			// 设置超时间为3秒
			httpUrl.setConnectTimeout(3 * 1000);
			// 防止屏蔽程序抓取而返回403错误
			httpUrl.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 得到输入流
			inputStream = httpUrl.getInputStream();
			// 获取自己数组
			byte[] getData = readInputStream(inputStream);
			// 设置文件MIME类型
			response.setContentType(request.getServletContext().getMimeType(
					fileName));
			// 设置Content-Disposition
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName + ".wav");
			out = response.getOutputStream();
			out.write(getData);
			out.flush();
			out.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("2");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从输入流中获取字节数组
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	@RequestMapping(value = "/exportAnswerAlarmDatas", method = RequestMethod.GET)
	public void export(Parameter parameter, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("中心接警统计");
		response.setContentType("application/msexcel;charset=UTF-8");
		String title = "中心接警统计";
		String[] headers = new String[] { "接诊时间", "报警电话", "相关电话", "报警地址",
				"电话判断", "出车急救站", "派车时间", "调度员","患者姓名" };
		String[] fields = new String[] { "answerAlarmTime", "alarmPhone",
				"relatedPhone", "siteAddress", "judgementOnPhone", "station",
				"sendCarTime", "dispatcher" ,"patientName"};
		TableData td = ExcelUtils.createTableData(
				answerAlarmService.getData(parameter).getRows(),
				ExcelUtils.createTableHeader(headers), fields);
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
