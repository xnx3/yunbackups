package com.xnx3.yunbackups.core.bean;

/**
 * 要自动备份的目录
 * @author 管雷鸣
 *
 */
public class BackupsPath {
	private String path;	//要自动备份的目录，绝对路径
	private long lasttime;	//最后一次备份的文件的时间戳，13位。下次在备份时，会从这个时间戳开始寻找
	
	public BackupsPath() {
		this.lasttime = 0;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public long getLasttime() {
		return lasttime;
	}
	public void setLasttime(long lasttime) {
		this.lasttime = lasttime;
	}
	
	
}
