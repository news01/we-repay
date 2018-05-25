package com.we.repay.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @ClassName: PropertyUtil
 * @version 1.0
 * @Desc: 读取配置文件
 * @author John Gu
 * @date 2016年1月5日下午4:23:32
 * @history v1.0
 *
 */
public class PropertyUtil {

	// 记录日志
	private static Logger logger = Logger.getLogger(PropertyUtil.class);
	
	private static PropertyUtil INSTANCE;
	
    public static PropertyUtil getInstance(){
        if (INSTANCE == null) {
            synchronized (PropertyUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PropertyUtil();
                }
            }
        }
        return INSTANCE;
    }

	/**
	 * 描述：获取配置文件对象
	 * @author tianzhongshan 
	 * @date 2017年9月15日上午11:42:34
	 * @param proPath 配置文件路径
	 * @return
	 */
	public  Properties getProperties(String proPath) {
		InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(proPath);
			Properties properties = new Properties();
			properties.load(in);
			in.close();
			return properties;
		} catch (IOException e) {
			logger.error("加载配置文件发生了异常",e);
		} finally {
		}
		return null;
	}

}
