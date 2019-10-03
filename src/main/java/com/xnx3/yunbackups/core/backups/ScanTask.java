package com.xnx3.yunbackups.core.backups;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.xnx3.DateUtil;
import com.xnx3.yunbackups.core.Global;
import com.xnx3.yunbackups.core.backups.interfaces.ProgressListener;
import com.xnx3.yunbackups.core.bean.BackupsPath;

/**
 * 针对某个目录进行的文件扫描任务
 * @author 管雷鸣
 */
public class ScanTask {
	private ProgressListener listener;
	private BackupsPath backupsPath;
	
	/**** 当前进度相关 ****/
	private int allFileNumber = 0;	//当前目录下所有文件的数量
	private int scanAccordNumber = 0;	//扫描到的符合条件的，需要进行备份的文件数量
	
	private long startTime = DateUtil.timeForUnix13();	//这个开始扫描的时间，也就是当前backups开始执行的时间，13位linux时间戳
	private long scanFinishTime = DateUtil.timeForUnix13();	// 搜索扫描子文件完成的时间。接下来就是进行文件排序，也就是文件排序开始的时间
	private long sortFinishTime = DateUtil.timeForUnix13();	// 文件排序完成的时间
	
	//组合好的子文件列表，不包含文件夹，只是 file 的子文件
	private List<File> subFileList = new ArrayList<File>();
	
	/**
	 * 创建一个文件备份任务。
	 * @param backupsPath 用户可自定义设定多个备份目录，这里便是传入其中一个备份目录
	 */
	public ScanTask(BackupsPath backupsPath) {
		this.backupsPath = backupsPath;
	}
	
	/**
	 * 用户可自定义设定多个备份目录，这里便是当前备份任务所执行备份的那个备份目录。
	 * @return {@link BackupsPath}备份目录
	 */
	public BackupsPath getBackupsPath() {
		return backupsPath;
	}
	/**
	 * 文件(包含文件夹)的数量。
	 * <br/>注意，如果还在扫描文件的过程中，这里的数是一直在增加的，不是固定的。当文件扫描完毕后，这里的数值才是固定的
	 * @return 文件(包含文件夹)的数量。
	 */
	public int getAllFileNumber() {
		return allFileNumber;
	}
	
	/**
	 * 扫描到的符合条件的，需要进行备份的文件数量。
	 * <br/>注意，如果还在扫描文件的过程中，这里的数是一直在增加的，不是固定的。当文件扫描完毕后，这里的数值才是固定的
	 * @return 需要执行备份操作的文件的数量
	 */
	public int getScanAccordNumber() {
		return scanAccordNumber;
	}
	
	/**
	 * 这个目录备份任务开始扫描的时间，也就是搜索扫描当前备份目录下子文件，开始执行的时间。
	 * @return 13位linux时间戳
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * 搜索扫描子文件完成的时间。
	 * <br/>接下来就是进行文件排序，也就是此时间也是文件排序开始的时间
	 * @return 13位linux时间戳
	 */
	public long getScanFinishTime() {
		return scanFinishTime;
	}
	
	/**
	 * 文件排序完成的时间
	 * <br/>排序完成后，备份任务
	 * @return
	 */
	public long getSortFinishTime() {
		return sortFinishTime;
	}

	public List<File> getSubFileList() {
		return subFileList;
	}


	/**
	 * 创建文件扫描的进度监听
	 * @param progressListener
	 */
	public void setProgressListener(ProgressListener progressListener){
		this.listener = progressListener;
	}
	
	/**
	 * 对 sublist进行排序，最新修改的在最前面
	 */
	public void sort(){
		//排序，筛选出最近修改的文件
		Collections.sort(subFileList, new Comparator<File>() {
			public int compare(File file1, File file2) {
				if(file1 == null || file2 == null){
					return 0;
				}
				long diff = file1.lastModified() - file2.lastModified();
				if (diff > 0) {
					return -1;
				}else if (diff < 0) {
					return 1;
				}
				
				//如果等于0，那就在比较文件大小吧，小文件排前面
				long lengthDiff = file1.length() - file2.length();
				if(lengthDiff > 0){
					return -1;
				}else if(lengthDiff < 0){
					return 1;
				}
				
				//如果前两个都一样，那么最后只能判断文件路径了
				return file1.getPath().compareTo(file2.getPath());
			}
		});
		
		//排序完成时间记录
		this.sortFinishTime = DateUtil.timeForUnix13();
		
		//排序完成监听
		if(this.listener != null){
			this.listener.sortFinish(this);
		}
	}
	
	/**
	 * 扫描用户自定义要备份的目录下，有多少可备份文件
	 */
	public void scanFiles(){
		this.startTime = DateUtil.timeForUnix13();
		findSubFileList(new File(this.backupsPath.getPath()));
		this.scanFinishTime = DateUtil.timeForUnix13();
	}
	
	/**
	 * 传入一个文件夹，扫描这个文件夹内的所有文件（及文件夹）列表
	 * @param file 要扫描的文件夹
	 */
	private void findSubFileList(File file){
		if(file == null){
			return;
		}
		if(!file.exists()){
			return;
		}
		
		//获取到file下所有子文件（或文件夹）
		File[] subFiles = file.listFiles();
		if(subFiles == null){
			return;
		}
		
		for (int i = 0; i < subFiles.length; i++) {
			allFileNumber++;
			
			if(!Global.system.isHiddenFileScan()){
				//如果不扫描隐藏文件，还要先判断一下当前文件是否是隐藏的，如果是隐藏的，那么忽略
				if(subFiles[i].isHidden()){
					//如果当前文件或文件夹是隐藏的，那就忽略，继续下一个文件
					continue;
				}
			}
			
			if(subFiles[i].length() > Global.system.getFileMaxSize()){
				//文件大小超过设定的最大大小，忽略
				continue;
			}
			
			//判断文件后缀名
			String[] suffixs = subFiles[i].getName().split("\\.");
			String suffix = "";	//后缀名，另外有的文件没有后缀，那么就是空字符串
			if(suffixs.length > 1){
				suffix = suffixs[suffixs.length-1].trim();
			}
			//如果指定了要备份某些特殊后缀的文件，那么要判断一下当前文件后缀是否是在这些可备份后缀里面
			if(Global.system.getSuffixNameList().size() > 0){
				boolean kebeifen = false;	//当前文件后缀是否是可以备份的，true：可备份
				for (int j = 0; j < Global.system.getSuffixNameList().size(); j++) {
					if(suffix.equalsIgnoreCase(Global.system.getSuffixNameList().get(j))){
						kebeifen = true;	//发现了，那么这个文件就是可备份的。跳出当前for
						break;
					}
				}
				if(!kebeifen){
					//如果不是可备份的，那么就跳出，不备份这个文件
					continue;
				}
			}
			//如果指定了某些特殊后缀名不进行备份，那么判断一下当前文件后缀名是否是在不备份的后缀里面
			if(Global.system.getIgnoreSuffixNameList().size() > 0){
				boolean find = false;	//当前文件后缀是否是在不备份的后缀里面， true 发现了，当前文件是在不备份后缀里面
				for (int j = 0; j < Global.system.getIgnoreSuffixNameList().size(); j++) {
					if(suffix.equalsIgnoreCase(Global.system.getIgnoreSuffixNameList().get(j))){
						find = true;	//发现了，那么这个文件就是可备份的。跳出当前for
						break;
					}
				}
				if(find){
					//发现了这个文件后缀已经在不可备份文件的后缀里面，那么就跳出，不备份这个文件
					continue;
				}
			}
			
			if(subFiles[i].lastModified() - this.backupsPath.getLasttime() - 1 > 0){
				//如果这里就是上一次某个具体秒数的时间备份了，这次还继续备份一次，也就是最后一次备份的秒数那个时间点备份两次。一秒创建不了多少文件，顶多也就是额外多备份几个文件而已
				//时间通过，未备份过的新文件，可以进行备份
			}else{
				//在lasttime之前的，那么忽略
				continue;
			}
			
			if(subFiles[i].isFile()){
				//如果是文件，那么直接加入返回列表
				subFileList.add(subFiles[i]);
				scanAccordNumber++;
				if(this.listener != null){
					this.listener.scanAccordFile(this, subFiles[i]);
				}
			}else if (subFiles[i].isDirectory()) {
				//如果是文件夹，那么还要往下找，找到这个文件夹内的子文件
				findSubFileList(subFiles[i]);
			}
		}
	}
	
	public String toString() {
		return "ScanTask [listener=" + listener + ", backupsPath=" + backupsPath + ", allFileNumber=" + allFileNumber
				+ ", scanAccordNumber=" + scanAccordNumber + ", startTime=" + startTime + ", scanFinishTime="
				+ scanFinishTime + ", sortFinishTime=" + sortFinishTime + "]";
	}
}
