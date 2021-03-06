package com.xnx3.yunbackups.visualApp.backups;

import java.net.UnknownHostException;

import com.xnx3.yunbackups.visualApp.Global;
import com.xnx3.yunbackups.visualApp.action.CreateTray;

/**
 * 异常监听
 * @author 管雷鸣
 *
 */
public class ExceptionListener implements com.xnx3.yunbackups.core.backups.interfaces.ExceptionListener{

	public int unknownHostException(UnknownHostException e) {
		System.out.println("断网了。。");
		Global.logJPanel.statusLabel.setText("<html>网络连接异常:"+e.getMessage()+"，等待30秒后再次尝试...");
		CreateTray.showTrayMessage("失败提示", "网络连接异常，等待30秒后再次尝试...");
		
		//延迟等待30秒
		return 30*1000;
	}

	public void serviceDisabled(String info) {
		System.out.println("备份服务器通信失败："+info);
		Global.logJPanel.statusLabel.setText("备份服务器通信失败："+info);
		CreateTray.showTrayMessage("失败提示", "备份服务器通信失败："+info);
	}

	public void runInitializeFailure(int type, String defaultInfo) {
		System.out.println(defaultInfo);
		Global.logJPanel.statusLabel.setText(defaultInfo);
		CreateTray.showTrayMessage("失败提示",defaultInfo);
	}

}
