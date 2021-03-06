package com.xnx3.yunbackups.core.backups;

import java.io.File;
import java.net.UnknownHostException;
import java.util.Map;
import com.xnx3.BaseVO;
import com.xnx3.DateUtil;
import com.xnx3.yunbackups.core.Global;
import com.xnx3.yunbackups.core.backups.interfaces.ExceptionListener;
import com.xnx3.yunbackups.core.backups.interfaces.ProgressListener;
import com.xnx3.yunbackups.core.backups.interfaces.StorageInterface;
import com.xnx3.yunbackups.core.subsidiary.BackupsPathSynchronizationThread;

/**
 * 自动备份线程
 * @author 管雷鸣
 *
 */
public class BackupsThread extends Thread{
	private StorageInterface storage;	//备份实现接口
	private ProgressListener progressListener;	//文件扫描备份的进度监听
	private ExceptionListener exceptionListener;	//异常监听
	public static BackupsPathSynchronizationThread backupsPathSynchronizationThread;	//每间隔5秒钟，自动保存一次备份进度
	
	/**
	 * 自动备份线程，也就是整个自动备份的核心所在
	 * @param storage 备份的实现。将文件备份到哪里去
	 */
	public BackupsThread(StorageInterface storage) {
		this.storage = storage;
		setName("backupsThread");
		
		//创建自动保存进度的线程
		if(backupsPathSynchronizationThread == null){
			backupsPathSynchronizationThread = new BackupsPathSynchronizationThread();
			backupsPathSynchronizationThread.start();
		}
	}
	
	public void setProgressListener(ProgressListener progressListener) {
		this.progressListener = progressListener;
	}

	public void setExceptionListener(ExceptionListener exceptionListener) {
		this.exceptionListener = exceptionListener;
	}


	public void run() {
		//判断要备份的目录是否为空，backupsPath 为空，用户还没有设置要备份哪个目录
		if(Global.backupsPathMap == null || Global.backupsPathMap.size() == 0){
			if(this.exceptionListener != null){
				this.exceptionListener.runInitializeFailure(1, "开启失败，您还没有设置要备份哪个目录呢。");
			}
			return;
		}
		if(Global.system == null){
			if(this.exceptionListener != null){
				this.exceptionListener.runInitializeFailure(2, "开启失败，系统设置未读取到，您可以重启软件进行尝试");
			}
			return;
		}
		
		
		//判断通信是否正常
		boolean serviceSuccess = false;	//若通信正常，此为true
		while(!serviceSuccess){
			try {
				BaseVO vo = storage.isUsable();
				if(vo.getResult() - BaseVO.FAILURE == 0){
					//通信失败
					
					//异常触发
					if(this.exceptionListener != null){
						this.exceptionListener.serviceDisabled(vo.getInfo());
					}
					return;	 //退出执行，不是网络问题，那就是参数设置有误，既然参数没有设置好，再尝多少次都是没用的，直接退出线程运行
				}else{
					serviceSuccess = true;	//设为true，跳出while循环，进行下一步开始备份
				}
			} catch (UnknownHostException e1) {
				//异常触发
				if(this.exceptionListener != null){
					this.exceptionListener.unknownHostException(e1);
				}
				e1.printStackTrace();
				try {
					Thread.sleep(30000);	//30秒后重试
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		while(true){
			int allFileNumber = 0;	//记录本次一共扫描的文件数量，每扫描完都会附加到这里
			int backupsNumber = 0;	//本次共备份了多少个文件
			long starttime = DateUtil.timeForUnix13();	//开始时间记录
			
			if(this.progressListener != null){
				this.progressListener.backupsBefore();
			}
			
			for(Map.Entry<String, com.xnx3.yunbackups.core.bean.BackupsPath> entry : Global.backupsPathMap.entrySet()){
				com.xnx3.yunbackups.core.bean.BackupsPath backupsPath = entry.getValue();
				ScanTask scanTask = new ScanTask(backupsPath);
				scanTask.setProgressListener(this.progressListener);	//设定监听
				//ScanTask执行之前监听
				if(this.progressListener != null){
					this.progressListener.backupsOnePathBefore(scanTask);
				}
				//扫描用户自定义要备份的目录下，有多少可备份文件
				scanTask.scanFiles();
				//扫描完成监听
				if(this.progressListener != null){
					this.progressListener.scanFinish(scanTask);
				}
				scanTask.sort();
				
				int listSize = scanTask.getSubFileList().size();
				for (int i = 0; i < listSize; i++) {
					//吧应该备份的文件筛选出来
					File file = scanTask.getSubFileList().get(i);
					if(!file.exists()){
						//备份时，再判断一下文件是否存在。有这种情况，扫描时，文件可能是一个临时缓存，加入进了备份列表，但是回头备份时，临时缓存自动删除了，所以就存在文件为空的情况
						//文件如果不存在，那么继续下个文件的备份
						continue;
					}
					// 备份实现
					if(storage != null){
						backupsFile(file);
					}
					//标记此 backupsPath 最后更新记录的时间
					Global.backupsPathMap.get(backupsPath.getPath()).setLasttime(file.lastModified());
					
					//接口监听
					if(this.progressListener != null){
						this.progressListener.backupsOneFileFinish(scanTask, file, i+1);
					}
				}
				
				allFileNumber += scanTask.getAllFileNumber();
				backupsNumber += scanTask.getScanAccordNumber();
				
				//接口监听
				if(this.progressListener != null){
					this.progressListener.backupsOnePathFinish(scanTask);
				}
			}
			
			//接口监听，监听执行完毕
			if(this.progressListener != null){
				this.progressListener.backupsFinish(allFileNumber, backupsNumber, starttime);
			}
			
			try {
				Thread.sleep(Global.system.getIntervalTime() * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 对文件进行备份操作
	 * @param file 要备份的文件
	 */
	private void backupsFile(File file){
		try {
			storage.backups(file);
		} catch (java.net.UnknownHostException e1) {
			//联网异常，断网了，那么等待30秒钟重试
			int sleepTime = 30000;	//默认30秒。这里时间单位是 ms
			if(this.exceptionListener != null){
				sleepTime = this.exceptionListener.unknownHostException(e1);
			}
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e11) {
				e1.printStackTrace();
			}
			//重试
			backupsFile(file);
		} catch (java.lang.IllegalArgumentException e2){
			System.out.println("===========");
			System.out.println("===========");
			System.out.println(e2.getMessage());
			System.out.println("===========");
			System.out.println("===========");
			e2.printStackTrace();
			
			//重试
			backupsFile(file);
		}
	}
}
