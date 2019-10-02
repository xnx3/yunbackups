package com.xnx3.yunbackups.core.backups.interfaces;

import java.io.File;

/**
 * 存储相关的接口
 * @author 管雷鸣
 *
 */
public interface StorageInterface {
	
	/**
	 * 传入一个文件，进行备份操作
	 * @param file 本地的文件，要进行备份的文件
	 */
	public void backups(File file); 
	
}
