package com.xnx3.yunbackups.computerDesktopApp.backups;

import java.net.UnknownHostException;

import com.xnx3.swing.DialogUtil;
import com.xnx3.yunbackups.computerDesktopApp.action.CreateTray;

/**
 * 异常监听
 * @author 管雷鸣
 *
 */
public class ExceptionListener implements com.xnx3.yunbackups.core.backups.interfaces.ExceptionListener{

	public int unknownHostException(UnknownHostException e) {
		System.out.println("断网了。。");
		CreateTray.showTrayMessage("失败提示", "当前网络中断，等待30秒后再次尝试...");
		
		//延迟等待30秒
		return 30*1000;
	}

	public void serviceDisabled(String info) {
		System.out.println("备份服务器通信失败："+info);
		CreateTray.showTrayMessage("失败提示", "备份服务器通信失败："+info);
//		DialogUtil.showMessageDialog("备份服务器通信失败！"+info+"<br/>请确保华为云参数是否配置正确");
	}

}
