package com.xnx3.yunbackups.computerDesktopApp;

import java.io.File;

import com.xnx3.DateUtil;
import com.xnx3.exception.NotReturnValueException;
import com.xnx3.yunbackups.core.backups.ScanTask;

/**
 * 进度监听
 * @author 管雷鸣
 *
 */
public class ProgressListener implements com.xnx3.yunbackups.core.backups.interfaces.ProgressListener{
	public void scanAccordFile(ScanTask scanTask, File file) {
		// TODO Auto-generated method stub
		
	}

	public void scanFinish(ScanTask scanTask) {
		Global.logJPanel.currentBackupsPathLabel.setText("<html>"+scanTask.getBackupsPath().getPath());
		System.out.println("scanFilish:"+scanTask);
		Global.logJPanel.allFileNumberLabel.setText(scanTask.getAllFileNumber()+" 个");
		Global.logJPanel.alreadyScanAccordNumberLabel.setText(scanTask.getScanAccordNumber()+" 个");
		
		//耗时
		Global.logJPanel.scanTimeLabel.setText((scanTask.getScanFinishTime() - scanTask.getStartTime())+" ms");
	}

	public void sortFinish(ScanTask scanTask) {
		//Global.logJPanel.currentBackupsPathLabel.setText("<html>"+scanTask.getBackupsPath().getPath());
		Global.logJPanel.currentBackupsFilePathLabel.setText("<html>123");
		Global.logJPanel.sortTimeLabel.setText((scanTask.getSortFinishTime() - scanTask.getScanFinishTime())+" ms");
		System.out.println("sortFinish:"+scanTask);
	}

	public void backupsOnePathFinish(ScanTask scanTask) {
		Global.logJPanel.currentBackupsPathLabel.setText("<html>"+scanTask.getBackupsPath().getPath());
	}

	public void backupsOneFileFinish(ScanTask scanTask, File file, int currentRankNumber) {
		Global.logJPanel.currentBackupsFilePathLabel.setText("<html>"+file.getPath());
		try {
			System.out.println("--"+file.getPath()+"   "+DateUtil.dateFormat(file.lastModified(), "yyyy-MM-dd hh:mm:ss"));
		} catch (NotReturnValueException e) {
			e.printStackTrace();
		}
	}

	public void backupsFinish(int allFileNumber, int alreadyScanAccordNumber, long starttime) {
		// TODO Auto-generated method stub
		
	}
	
}
