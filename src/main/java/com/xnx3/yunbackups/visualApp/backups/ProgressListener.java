package com.xnx3.yunbackups.visualApp.backups;

import java.io.File;
import com.xnx3.DateUtil;
import com.xnx3.exception.NotReturnValueException;
import com.xnx3.yunbackups.visualApp.Global;
import com.xnx3.yunbackups.core.backups.ScanTask;

/**
 * 进度监听
 * @author 管雷鸣
 *
 */
public class ProgressListener implements com.xnx3.yunbackups.core.backups.interfaces.ProgressListener{
	LogBackupsOneFileFinishThread logBackupsOneFileFinishThread;

	public void scanAccordFile(ScanTask scanTask, File file) {
	}

	public void scanFinish(ScanTask scanTask) {
		Global.logJPanel.currentBackupsPathLabel.setText("<html>"+scanTask.getBackupsPath().getPath());
		Global.logJPanel.allFileNumberLabel.setText(scanTask.getAllFileNumber()+" 个");
		Global.logJPanel.alreadyScanAccordNumberLabel.setText(scanTask.getScanAccordNumber()+" 个");
		
		//耗时
		Global.logJPanel.scanTimeLabel.setText((scanTask.getScanFinishTime() - scanTask.getStartTime())+" ms");
	}

	public void sortFinish(ScanTask scanTask) {
		Global.logJPanel.sortTimeLabel.setText((scanTask.getSortFinishTime() - scanTask.getScanFinishTime())+" ms");
	}

	public void backupsOnePathFinish(ScanTask scanTask) {
		Global.logJPanel.currentBackupsPathLabel.setText("<html>"+scanTask.getBackupsPath().getPath());
	}

	public void backupsOneFileFinish(ScanTask scanTask, File file, int currentRankNumber) {
		logBackupsOneFileFinishThread.currentPath = file.getPath()+"\n此目录下，已备份"+currentRankNumber+"个文件，还剩"+(scanTask.getScanAccordNumber()-currentRankNumber)+"个文件未备份";
	}

	public void backupsFinish(int allFileNumber, int alreadyScanAccordNumber, long starttime) {
		//更新一备份到哪个文件的ui刷新线程，标记为false，退出循转，结束线程
		logBackupsOneFileFinishThread.run = false;
		//备份耗时
		long usetime = DateUtil.timeForUnix13() - starttime;	
		//算出共耗时多少秒
		int secend = Math.round(usetime / 1000);
		//更新完毕的状态提示文字
		try {
			Global.logJPanel.statusLabel.setText("本次自动备份完毕！共备份"+alreadyScanAccordNumber+"个文件，耗时"+secend+"秒。下次扫描备份时间为 "+DateUtil.dateFormat(DateUtil.timeForUnix10()+com.xnx3.yunbackups.core.Global.system.getIntervalTime()*60, "hh:mm:ss"));
		} catch (NotReturnValueException e) {
			e.printStackTrace();
		}
		//隐藏备份具体进度信息
		Global.logJPanel.getProgressPanel().setVisible(false);
	}

	public void backupsBefore() {
		Global.logJPanel.getStatusLabel().setText("正在备份过程中...");
		//显示备份具体进度信息
		Global.logJPanel.getProgressPanel().setVisible(true);
		
		//开启备份文件已备份到什么成都的刷新线程
		logBackupsOneFileFinishThread = null;
		logBackupsOneFileFinishThread = new LogBackupsOneFileFinishThread();
		logBackupsOneFileFinishThread.start();
	}

	public void backupsOnePathBefore(ScanTask scanTask) {
		Global.logJPanel.currentBackupsPathLabel.setText("<html>"+scanTask.getBackupsPath().getPath());
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
				Global.logJPanel.currentBackupsFilePathTextarea.setText(currentPath);
			} catch (Exception e) {
				System.out.println("ProgressListener 85 line : "+e.getMessage());
			}
		}
	}
}