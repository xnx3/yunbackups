package com.xnx3.yunbackups.core.backups.interfaces;

import java.io.File;
import java.net.UnknownHostException;

/**
 * 存储相关的接口
 * @author 管雷鸣
 *
 */
public interface StorageInterface {
	
	/**
	 * 传入一个文件，进行备份操作
	 * @param file 本地的文件，要进行备份的文件
	 * @throws java.net.UnknownHostException 断网情况下会抛出此异常
	 * @throws java.lang.IllegalArgumentException 有的文件执行备份时可能抛出此异常，导致此文件备份失败
	 */
	public void backups(File file) throws java.net.UnknownHostException, java.lang.IllegalArgumentException; 
	
}
