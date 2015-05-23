/**
 * @author 崔兴伟
 * @datetime 2015年5月23日 下午3:02:17
 */
package com.xhs.ems.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 崔兴伟
 * @datetime 2015年5月23日 下午3:02:17
 */
public class ConfigReader {
	private Map<String, String> map = new HashMap<String, String>();

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public ConfigReader(String configPath) {
		Properties prop = new Properties();
		try {
			File f = new java.io.File(configPath);
			FileInputStream fis = new FileInputStream(f);
			prop.load(fis);
			fis.close();
			if (map.isEmpty()) {
				Enumeration<?> enums = prop.propertyNames();
				while (enums.hasMoreElements()) {
					String key = (String) enums.nextElement();
					this.map.put(key, prop.getProperty(key));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
