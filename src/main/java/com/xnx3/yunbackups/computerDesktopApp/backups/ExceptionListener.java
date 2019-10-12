package com.xnx3.yunbackups.computerDesktopApp.backups;

import java.net.UnknownHostException;

/**
 * 异常监听
 * @author 管雷鸣
 *
 */
public class ExceptionListener implements com.xnx3.yunbackups.core.backups.interfaces.ExceptionListener{

	public int unknownHostException(UnknownHostException e) {
		System.out.println("断网了。。");
		
		//延迟等待30秒
		return 30*1000;
	}

	public void serviceDisabled(String info) {
		System.out.println("备份服务器通信失败："+info);
	}

}
