package com.xnx3.yunbackups.commandLineApp;

import java.io.IOException;
import com.xnx3.yunbackups.commandLineApp.backups.ExceptionListener;
import com.xnx3.yunbackups.commandLineApp.backups.ProgressListener;
import com.xnx3.yunbackups.commandLineApp.config.HuaweiObsConfig;
import com.xnx3.yunbackups.core.backups.BackupsThread;
import com.xnx3.yunbackups.core.subsidiary.BackupsPathSynchronizationThread;
import com.xnx3.yunbackups.storage.HuaweiyunOBS;

/**
 * 云客户端的运行入口
 * @author 管雷鸣
 *
 */
public class RunEntry {
	
	public static void main(String[] args) throws IOException {
		
		//加载云端配置参数
		Global.cloudConfigBean = HuaweiObsConfig.read();
				
		//判断一下是否设置过备份服务器相关参数，若已经设置了，那么自动运行
		if(Global.cloudConfigBean.getBucketName() != null && Global.cloudConfigBean.getBucketName().length() > 0){
			System.out.println("run... ");
		}else{
			System.out.println("please set cloud backups params");
			return;
		}
		
		//运行自动备份线程，执行备份操作
		HuaweiyunOBS obs = new HuaweiyunOBS(Global.cloudConfigBean.getAccessKeyId(), Global.cloudConfigBean.getSecretAccessKey(), Global.cloudConfigBean.getEndpoint(), Global.cloudConfigBean.getBucketName());
		BackupsThread backupsThread = new BackupsThread(obs);
		//进行备份进度的监听。如果不想看到实时进度，此项完全可以省略，还能提升性能
		backupsThread.setProgressListener(new ProgressListener());
		//备份过程中遇到的异常监听。比如断网了
		backupsThread.setExceptionListener(new ExceptionListener());
		//开始执行此线程，开始备份
		backupsThread.start();
	}
	
	
}
