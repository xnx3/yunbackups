package com.xnx3.yunbackups.commandLineApp.backups;

import java.net.UnknownHostException;
import com.xnx3.DateUtil;
import com.xnx3.FileUtil;
import com.xnx3.yunbackups.commandLineApp.Global;

/**
 * 异常监听
 * @author 管雷鸣
 *
 */
public class ExceptionListener implements com.xnx3.yunbackups.core.backups.interfaces.ExceptionListener{
	public static final String ERROR_NAME = "exception.log";	//错误日志文件的文件名
	
	public int unknownHostException(UnknownHostException e) {
		log("网络异常，链接失败,30秒后重试！"+e.getMessage());
		
		//延迟等待30秒
		return 30*1000;
	}

	public void serviceDisabled(String info) {
		log("备份服务器通信失败！"+info);
	}
	
	/**
	 * 保存更新错误日志。保存到错误日志文件中去
	 * @param content 错误日志的信息
	 */
	private void log(String content){
		//写出到文件
		FileUtil.write(Global.LOG_PATH+ERROR_NAME, content+"    "+DateUtil.currentDate("yyyy-MM-dd HH:mm:ss"));
	}

	public void runInitializeFailure(int type, String defaultInfo) {
		log(defaultInfo);
	}

}
