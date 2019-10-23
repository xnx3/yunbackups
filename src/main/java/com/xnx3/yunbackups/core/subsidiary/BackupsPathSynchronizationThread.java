package com.xnx3.yunbackups.core.subsidiary;

import com.xnx3.yunbackups.core.Global;
import com.xnx3.yunbackups.core.config.BackupsPath;

/**
 * backupsPath 最新备份进度的同步线程，每间隔5秒保存一次，保存当前备份到哪个时间点了
 * @author 管雷鸣
 *
 */
public class BackupsPathSynchronizationThread extends Thread{
	public BackupsPathSynchronizationThread() {
		setName("backupsPathSynchronizationThread");
	}
	
	public void run() {
		while(true){
			if(Global.backupsPathMap != null){
				BackupsPath.save(Global.backupsPathMap);
			}
			
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
