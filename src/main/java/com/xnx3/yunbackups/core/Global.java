package com.xnx3.yunbackups.core;

import java.io.File;
import java.util.Map;
import com.xnx3.SystemUtil;
import com.xnx3.yunbackups.core.bean.BackupsPath;

/**
 * core 全局缓存
 * @author 管雷鸣
 *
 */
public class Global {
	public static final String CONFIG_PATH;	//配置文件所在的文件夹
	static{
		//判断当前系统是否是mac
		if(com.xnx3.yunbackups.core.util.SystemUtil.isMacOS()){
			//是mac
			CONFIG_PATH = System.getProperty("user.home")+File.separator+"yunbackups"+File.separator+"config"+File.separator;	//配置文件所在的文件夹
		}else {
			CONFIG_PATH = SystemUtil.getCurrentDir()+File.separator+"config"+File.separator;	//配置文件所在的文件夹
		}
		//如果配置文件目录不存在，那么自动创建
		File file = new File(CONFIG_PATH);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	// system.config 配置
	public static com.xnx3.yunbackups.core.bean.System system;
	static{
		system = com.xnx3.yunbackups.core.config.System.read();
	}
	
	// 要自动备份的文件夹列表。 key: backupsPath.path
	public static Map<String, BackupsPath> backupsPathMap;
	static{
		backupsPathMap = com.xnx3.yunbackups.core.config.BackupsPath.read();
	}
}
