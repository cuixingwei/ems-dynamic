package com.xhs.ems.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class CommonUtil {
	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static String getSystemTime() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mDateTime = formatter.format(cal.getTime());
		return mDateTime;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNullOrEmpty(String text) {
		if (text == null || text.length() == 0 || text.equals("")) {
			return true;
		}
		return false;
	}

	public static String readContentFromFile(File file) {
		StringBuilder builder = new StringBuilder();
		if (file.exists() && file.isFile()) {
			try {
				InputStreamReader reader = new InputStreamReader(
						new FileInputStream(file), "utf-8");
				BufferedReader bufferedReader = new BufferedReader(reader);
				String temp = null;
				while ((temp = bufferedReader.readLine()) != null) {
					builder.append(temp);
				}
				reader.close();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return builder.toString();
	}
}
