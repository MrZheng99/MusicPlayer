package com.zj.musicplayer.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @description读取数据库配置
 * 
 * @author ZJ
 * @date 20200322
 */
public class RederConfig extends Properties {
	// 读取配置文件 单例 饿汉模式
	static RederConfig instance = new RederConfig();

	private RederConfig() {
		// 这么写会自动关闭
		try (InputStream is = getClass().getClassLoader().getResourceAsStream("sql_config.properties")) {
			this.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static RederConfig getInstance() {
		return instance;
	}
}
