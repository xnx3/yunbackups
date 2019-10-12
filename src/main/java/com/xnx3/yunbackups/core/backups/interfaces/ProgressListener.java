package com.xnx3.yunbackups.core.backups.interfaces;

import java.io.File;
import com.xnx3.yunbackups.core.backups.ScanTask;

/**
 * 备份任务执行过程中的进度监听
 * @author 管雷鸣
 */
public interface ProgressListener {
	
	/**
	 * 备份开始，跟 {@link #backupsFinish(int, int, long)} 相对。
	 *  backupsFinish(...) 备份完成后，等待多长时间，便会重新开始备份操作，扫描设定的多个备份目录内，可以进行备份的文件。本接口便是在开始备份，要扫描可备份的文件之前，进行触发
	 */
	public void backupsStart();
	
	/**
	 * ScanTask 进行扫描的过程中，当发现一个可备份的文件时，便会触发此方法。
	 * <br/>用户可以设定多个备份文件夹，一个 backupsPath 便是一个用户设定的备份文件夹。此接口便是其中一个 buckupsPath 进行扫描时的监听
	 * <br/>每当发下一个可备份文件，都会触发此方法。
	 * <br/>这个只是 单个 BackupsPath 的监听
	 * @param scanTask 当前的扫描任务
	 * @param file 当前扫描到的可备份的文件
	 */
	public void scanAccordFile(ScanTask scanTask, File file);
	
	/**
	 * 扫描完成某个用户自己所设定的 backupsPath 备份目录 的所有子文件时，触发。
	 * <br/>用户可以设定多个备份文件夹，一个 backupsPath 便是一个用户设定的备份目录。此接口便是其中一个 buckupsPath 进行扫描完当前备份目录下，有多少本次要备份文件时的监听
	 * @param scanTask 当前的扫描任务
	 */
	public void scanFinish(ScanTask scanTask);
	
	/**
	 * 扫描某个用户自己所设定的 backupsPath 备份目录，对其扫描到的所有子文件排序完成时，触发。
	 * @param scanTask 当前的扫描任务
	 */
	public void sortFinish(ScanTask scanTask);
	
	/**
	 * 备份完一个用户设定的备份目录时触发。
	 * <br/>用户可能会设定多个备份目录，这里是每当备份完其中一个备份目录都会触发一次。
	 * @param scanTask 当前的扫描任务
	 */
	public void backupsOnePathFinish(ScanTask scanTask);
	
	/**
	 * 当某个文件备份完成时进行触发。
	 * <br/>每当有一个文件备份完成都会触发此接口。
	 * @param scanTask 当前的扫描任务
	 * @param file 当前备份完成的文件
	 * @param currentRankNumber 当前备份完成的文件，在 scanTask 扫描到的备份文件列表中，排列第几。也就是备份到了第多少个。
	 * @param starttime 记录本次备份 backupsPath 这个文件目录的开始时间，也就是当前目录文件扫描开始的时间，13位时间戳
	 */
	public void backupsOneFileFinish(ScanTask scanTask, File file, int currentRankNumber);
	
	/**
	 * 当前备份执行完毕所触发的接口。
	 * <br/>用户自己所设置的备份目录，全部备份完成之后触发。
	 * @param allFileNumber 一共扫描的文件数量
	 * @param alreadyScanAccordNumber 共符合条件的，已经扫描出来备份的文件的数量
	 * @param starttime 备份开始的时间，13位时间戳
	 */
	public void backupsFinish(int allFileNumber, int alreadyScanAccordNumber, long starttime);
}
