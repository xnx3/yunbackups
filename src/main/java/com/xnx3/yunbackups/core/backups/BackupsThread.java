package com.xnx3.yunbackups.core.backups;

import java.io.File;
import java.util.Map;
import com.xnx3.DateUtil;
import com.xnx3.yunbackups.core.Global;
import com.xnx3.yunbackups.core.backups.interfaces.ProgressListener;
import com.xnx3.yunbackups.core.backups.interfaces.StorageInterface;

/**
 * 自动备份线程
 * @author 管雷鸣
 *
 */
public class BackupsThread extends Thread{
	private StorageInterface storage;	//备份实现接口
	private ProgressListener listener;	//文件扫描备份的进度监听
	
	public BackupsThread(StorageInterface storage, ProgressListener progressListener) {
		this.storage = storage;
		this.listener = progressListener;
	}
	
	public void run() {
		while(true){
			int allFileNumber = 0;	//记录本次一共扫描的文件数量，每扫描完都会附加到这里
			int backupsNumber = 0;	//本次共备份了多少个文件
			long starttime = DateUtil.timeForUnix13();	//开始时间记录
			
			for(Map.Entry<String, com.xnx3.yunbackups.core.bean.BackupsPath> entry : Global.backupsPathMap.entrySet()){
				com.xnx3.yunbackups.core.bean.BackupsPath backupsPath = entry.getValue();
				ScanTask scanTask = new ScanTask(backupsPath);
				scanTask.setProgressListener(this.listener);	//设定监听
				//列出所有子文件
				scanTask.findSubFileList(new File(backupsPath.getPath()));
				scanTask.sort();
				
				int listSize = scanTask.getSubFileList().size()-1;
				for (int i = 0; i < listSize; i++) {
					//吧应该备份的文件筛选出来
					File file = scanTask.getSubFileList().get(i);
					// 备份实现
					if(storage != null){
						storage.backups(file);
					}
					//标记此 backupsPath 最后更新记录的时间
					Global.backupsPathMap.get(backupsPath.getPath()).setLasttime(file.lastModified());
					
					//接口监听
					if(this.listener != null){
						this.listener.backupsOneFileFinish(scanTask, file, i+1);
					}
				}
				
				allFileNumber += scanTask.getAllFileNumber();
				backupsNumber += scanTask.getScanAccordNumber();
				
				//接口监听
				if(this.listener != null){
					this.listener.backupsOnePathFinish(scanTask);
				}
			}
			
			//接口监听，监听执行完毕
			if(this.listener != null){
				this.listener.backupsFinish(allFileNumber, backupsNumber, starttime);
			}
			
			try {
				Thread.sleep(Global.system.getIntervalTime() * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
