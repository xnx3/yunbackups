package com.xnx3.yunbackups.core.subsidiary;

import com.xnx3.yunbackups.core.Global;
import com.xnx3.yunbackups.core.config.BackupsPath;

/**
 * backupsPath 同步线程，每间隔5秒保存一次
 * @author 管雷鸣
 *
 */
public class BackupsPathSynchronizationThread extends Thread{
	public BackupsPathSynchronizationThread() {
		setName("backupsPathSynchronizationThread");
	}
	
	public void run() {
		while(true){
			BackupsPath.save(Global.backupsPathMap);
			
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
