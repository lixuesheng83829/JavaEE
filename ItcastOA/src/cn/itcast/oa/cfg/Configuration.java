package cn.itcast.oa.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 对应配置的对象（对应default.properties）
 * @author icelee
 *
 */

public class Configuration {
	
	/**
	 * 
	 * 对应配置的对象（对应default.properties）
	 * 
	 */
	private static int pageSize;
	
	static{
		//读取配置default.properties文件，并初始化所有配置
		InputStream in = Configuration.class.getClassLoader().getResourceAsStream("default.properties");
		Properties props = new Properties();
		try {
			//1.读取配置文件
			props.load(in);
			//2.初始化配置 
			pageSize = Integer.parseInt(props.getProperty("pageSize"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static int getPageSize() {
		return pageSize;
	}
	
}
