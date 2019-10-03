package com.xnx3.yunbackups.core.backups.interfaces;

/**
 * 备份过程中的异常监听
 * @author 管雷鸣
 *
 */
public interface ExceptionListener {
	
	/**
	 * 备份文件时，断网的异常监听。当断网了，触发此方法
	 * @param e 捕获的异常
	 * @return 延迟等待的时间。出现断网异常后，当前文件肯定备份失败了，等待此处返回时间后，再次进行备份此文件。这里时间单位是 ms
	 */
	public int unknownHostException(java.net.UnknownHostException e);
	
}
