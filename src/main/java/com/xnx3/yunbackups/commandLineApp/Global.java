package com.xnx3.yunbackups.commandLineApp;

import java.io.File;
import com.xnx3.yunbackups.commandLineApp.bean.CloudConfigBean;
import com.xnx3.yunbackups.core.util.SystemUtil;

/**
 * 当前包的全局缓存
 * @author 管雷鸣
 *
 */
public class Global {
	//云端配置的一些参数。持久缓存
	public static CloudConfigBean cloudConfigBean; 
	
	//log日志文件的存放目录
	public static final String LOG_PATH = SystemUtil.getCurrentDir()+File.separator+"log"+File.separator;
	static{
		//如果log日志文件夹不存在，那么自动创建
		File file = new File(LOG_PATH);
		if(!file.exists()){
			file.mkdirs();
		}
	}
}