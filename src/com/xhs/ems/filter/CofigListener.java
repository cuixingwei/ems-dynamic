package com.xhs.ems.filter;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.xhs.ems.common.CommonUtil;
import com.xhs.ems.common.ConfigReader;

/**
 * @author 崔兴伟
 * @datetime 2015年5月23日 下午2:53:28
 */
public class CofigListener implements ServletContextListener {
	private static final Logger logger = Logger.getLogger(ConfigReader.class);

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String configPath = sce.getServletContext().getRealPath(
				"WEB-INF/conf/config.properties");
		Map<String, String> map = (new ConfigReader(configPath)).getMap();
		for (String key : map.keySet()) {
			if (CommonUtil.isNullOrEmpty(key))
				continue;
			sce.getServletContext().setAttribute(key, map.get(key));
			logger.info(key + ":" + map.get(key));
		}
	}

}
