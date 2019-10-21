package com.xnx3.yunbackups.commandLineApp.backups;

import java.io.File;
import com.xnx3.DateUtil;
import com.xnx3.FileUtil;
import com.xnx3.yunbackups.commandLineApp.Global;
import com.xnx3.yunbackups.core.backups.ScanTask;

/**
 * 进度监听
 * @author 管雷鸣
 *
 */
public class ProgressListener implements com.xnx3.yunbackups.core.backups.interfaces.ProgressListener{
	LogBackupsOneFileFinishThread logBackupsOneFileFinishThread;
	public static ScanTask scanTask;	//当前备份到的任务
	public static final String LOG_NAME = "progress.log";	//进度日志文件的文件名
	

	public void scanAccordFile(ScanTask scanTask, File file) {
	}

	public void scanFinish(ScanTask scanTask) {
		ProgressListener.scanTask = scanTask;
		StringBuffer sb = new StringBuffer();
		//scanTask
		sb.append("当前备份的目录:"+scanTask.getBackupsPath().getPath());
		sb.append("\r\n当前目录扫描的文件数量:"+scanTask.getAllFileNumber()+"    耗时:"+(scanTask.getScanFinishTime() - scanTask.getStartTime())+" ms");
		sb.append("\r\n当前目录要备份的文件数量正在计算中...");
		log(sb.toString());
	}

	public void sortFinish(ScanTask scanTask) {
		ProgressListener.scanTask = scanTask;
		
		StringBuffer sb = new StringBuffer();
		//scanTask
		sb.append("当前备份的目录:"+scanTask.getBackupsPath().getPath());
		sb.append("\r\n当前目录扫描的文件数量:"+scanTask.getAllFileNumber()+"    耗时:"+(scanTask.getScanFinishTime() - scanTask.getStartTime())+" ms");
		sb.append("\r\n当前目录要备份的文件数量:"+scanTask.getScanAccordNumber()+"    耗时:"+(scanTask.getSortFinishTime() - scanTask.getScanFinishTime())+" ms");
		sb.append("\r\n\r\n正在准备备份文件...");
		log(sb.toString());
	}

	public void backupsOnePathFinish(ScanTask scanTask) {
		ProgressListener.scanTask = scanTask;
		
		StringBuffer sb = new StringBuffer();
		//scanTask
		sb.append("当前备份的目录:"+scanTask.getBackupsPath().getPath());
		sb.append("\r\n当前目录扫描的文件数量:"+scanTask.getAllFileNumber()+"    耗时:"+(scanTask.getScanFinishTime() - scanTask.getStartTime())+" ms");
		sb.append("\r\n当前目录要备份的文件数量:"+scanTask.getScanAccordNumber()+"    耗时:"+(scanTask.getSortFinishTime() - scanTask.getScanFinishTime())+" ms");
		sb.append("\r\n\r\n当前目录已备份完毕！");
		log(sb.toString());
	}

	public void backupsOneFileFinish(ScanTask scanTask, File file, int currentRankNumber) {
		ProgressListener.scanTask = scanTask;
		logBackupsOneFileFinishThread.currentPath = file.getPath()+"\r\n此目录下，已备份"+currentRankNumber+"个文件，还剩"+(scanTask.getScanAccordNumber()-currentRankNumber)+"个文件未备份";
	}

	public void backupsFinish(int allFileNumber, int alreadyScanAccordNumber, long starttime) {
		//更新一备份到哪个文件的ui刷新线程，标记为false，退出循转，结束线程
		logBackupsOneFileFinishThread.run = false;
		//备份耗时
		long usetime = DateUtil.timeForUnix13() - starttime;	
		//算出共耗时多少秒
		int secend = Math.round(usetime / 1000);
		
		//延迟2秒，为了等待 LogBackupsOneFileFinishThread 线程结束
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		log("本次自动备份完毕！共备份"+alreadyScanAccordNumber+"个文件，耗时"+secend+"秒。正在等待下一次自动扫描备份...");
	}

	public void backupsBefore() {
		//开启备份文件已备份到什么成都的刷新线程
		logBackupsOneFileFinishThread = null;
		logBackupsOneFileFinishThread = new LogBackupsOneFileFinishThread();
		logBackupsOneFileFinishThread.start();
		
		log("开启备份，准备中...");
	}
	
	public static void log(String content){
		FileUtil.write(Global.LOG_PATH+LOG_NAME, content+"\r\n当前日志更新时间:"+DateUtil.currentDate("yyyy-MM-dd HH:mm:ss")+"\r\n");
	}

	public void backupsOnePathBefore(ScanTask scanTask) {
		ProgressListener.scanTask = scanTask;
		
		StringBuffer sb = new StringBuffer();
		//scanTask
		sb.append("当前准备备份的目录:"+scanTask.getBackupsPath().getPath());
		log(sb.toString());
	}
	
}
/**
 * 在ui界面上显示当前备份的进度，但是不能备份一个文件就在ui更新，会卡死主进程的，所以单独开一个线程，定时500秒刷新一次ui
 * @author 管雷鸣
 *
 */
class LogBackupsOneFileFinishThread extends Thread{
	public String currentPath = "";	//当前备份到的目录
	public boolean run = true;	//true：运行，false不运行，跳出循环停止
	
	public void run() {
		while (run) {
			//1秒更新一次ui
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				StringBuffer sb = new StringBuffer();
				//scanTask
				if(ProgressListener.scanTask != null){
					sb.append("当前备份的目录:"+ProgressListener.scanTask.getBackupsPath().getPath());
					sb.append("\r\n当前目录扫描的文件数量:"+ProgressListener.scanTask.getAllFileNumber()+"    耗时:"+(ProgressListener.scanTask.getScanFinishTime() - ProgressListener.scanTask.getStartTime())+" ms");
					sb.append("\r\n当前目录要备份的文件数量:"+ProgressListener.scanTask.getScanAccordNumber()+"    耗时:"+(ProgressListener.scanTask.getSortFinishTime() - ProgressListener.scanTask.getScanFinishTime())+" ms");
				}
				sb.append("\r\n\r\n当前已备份到文件:"+currentPath);
				ProgressListener.log(sb.toString());
			} catch (Exception e) {
				System.out.println("ProgressListener 85 line : "+e.getMessage());
			}
		}
	}
}