package com.xnx3.yunbackups.commandLineApp;

import java.io.IOException;
import com.xnx3.yunbackups.commandLineApp.backups.ExceptionListener;
import com.xnx3.yunbackups.commandLineApp.backups.ProgressListener;
import com.xnx3.yunbackups.core.backups.BackupsThread;
import com.xnx3.yunbackups.core.util.BackupsUtil;

/**
 * 云客户端的运行入口
 * @author 管雷鸣
 *
 */
public class RunEntry {
	
	public static void main(String[] args) throws IOException {
		
		//备份的主线程
		BackupsThread backupsThread = BackupsUtil.generateBackupsThread();
		
		//运行自动备份线程，执行备份操作
		if(backupsThread != null){
			//进行备份进度的监听。如果不想看到实时进度，此项完全可以省略，还能提升性能
			backupsThread.setProgressListener(new ProgressListener());
			//备份过程中遇到的异常监听。比如断网了
			backupsThread.setExceptionListener(new ExceptionListener());
			//开始执行此线程，开始备份
			backupsThread.start();			
		}

	}
	
	
}
