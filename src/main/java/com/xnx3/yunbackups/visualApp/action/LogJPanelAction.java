package com.xnx3.yunbackups.visualApp.action;

import com.xnx3.yunbackups.commandLineApp.Global;
import com.xnx3.yunbackups.core.backups.BackupsThread;
import com.xnx3.yunbackups.storage.HuaweiyunOBS;
import com.xnx3.yunbackups.visualApp.backups.ExceptionListener;
import com.xnx3.yunbackups.visualApp.backups.ProgressListener;

/**
 * LogJPanel 相关动作
 * @author 管雷鸣
 *
 */
public class LogJPanelAction {
	
	/**
	 * 点击运行按钮后执行的
	 */
	public static void clickRunButton(){
		//运行自动备份线程，执行备份操作
		HuaweiyunOBS obs = new HuaweiyunOBS(Global.cloudConfigBean.getAccessKeyId(), Global.cloudConfigBean.getSecretAccessKey(), Global.cloudConfigBean.getEndpoint(), Global.cloudConfigBean.getBucketName());
		BackupsThread backupsThread = new BackupsThread(obs);
		//进行备份进度的监听。如果不想看到实时进度，此项完全可以省略，还能提升性能
		backupsThread.setProgressListener(new ProgressListener());
		//备份过程中遇到的异常监听。比如断网了
		backupsThread.setExceptionListener(new ExceptionListener());
		//开始执行此线程，开始备份
		backupsThread.start();
		
		com.xnx3.yunbackups.visualApp.Global.logJPanel.runButton.setVisible(false);
		com.xnx3.yunbackups.visualApp.Global.logJPanel.statusLabel.setText("扫描中...");
	}
	
}
