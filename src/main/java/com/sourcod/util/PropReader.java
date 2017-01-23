package com.sourcod.util;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取配置文件
 * @author htc
 *
 */
public class PropReader {
	
	private static final Logger logger = Logger.getLogger(PropReader.class);
	
	static Properties prop = new Properties();
	// 是否开启开发模式
	private static boolean developMode = true;
	static {
		try {
			logger.info("开始加载配置文件！");
			// 获取config配置文件夹路径
			File file= new File(PropReader.class.getResource("/config").getPath());
			for(File f :file.listFiles()){
				// 只加载properties配置文件
				if(f.getName().indexOf("properties") != -1){
					InputStream in = PropReader.class.getClassLoader().getResourceAsStream("config/" + f.getName());
					prop.load(in);
					logger.info("加载配置文件" + f.getName() + "成功！");
				}
			}

		} catch (Exception e) {
			System.out.println("读取配置文件出错！");
			e.printStackTrace();
		}

		if ("true".equals(prop.getProperty("DEVELOP_MODE")))
			developMode = true;
		else
			developMode = false;
	}

	public static String getValueByKey(String key) {
		if (key == null)
			return null;

		return prop.getProperty(key);
	}

	/**
	 * @Title: isDevelopMode
	 * @Description: 是否开启了开发模式，默认为true
	 * @return boolean
	 */
	public static boolean isDevelopMode() {
		return developMode;
	}
}
