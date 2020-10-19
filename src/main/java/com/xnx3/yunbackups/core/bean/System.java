package com.xnx3.yunbackups.core.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统设置
 * @author 管雷鸣
 *
 */
public class System {
	public static final int DEFAULT_INTERVALTIME = 60 * 60;	//默认备份时间间隔是1小时
	public static final int DEFAULT_FILE_MAX_SIZE = 1 * 1024 * 1024 * 10;	//1B * 1024 (kb) * 1024 (mb) * 10 (10mb) ，默认10MB
	
	private int intervalTime;	//两次备份的间隔时间,单位是秒（1.2升级到1.3， 单位有分钟变为秒）
	private List<String> suffixNameList;	//进行备份的后缀名。全小写，格式如 png 、jpg 
	private List<String> ignoreSuffixNameList;	//忽略的后缀名，在这里面的后缀名，不会进行备份。全小写，格式如 png、 jpg 
	private long fileMaxSize;	//允许备份的文件最大大小。单位是字节(B)
	private boolean hiddenFileScan=false;	//是否扫描备份隐藏的文件或文件夹，true：是，false：不扫描(那也就是不备份了)
	private String storage;		//备份方式，如 sftp 、 huaweiobs
	
	public System() {
		this.intervalTime = 60*60;	//默认1小时
		this.fileMaxSize = 524288000;	//默认 500MB
		this.storage = "huaweiobs";	//默认华为obs，兼容1.1版本，不至于升级后，配置丢了
	}
	
	public int getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}
	public List<String> getSuffixNameList() {
		if(suffixNameList == null){
			this.suffixNameList = new ArrayList<String>();
		}
		return suffixNameList;
	}
	public void setSuffixNameList(List<String> suffixNameList) {
		this.suffixNameList = suffixNameList;
	}
	public List<String> getIgnoreSuffixNameList() {
		if(ignoreSuffixNameList == null){
			this.ignoreSuffixNameList = new ArrayList<String>();
		}
		return ignoreSuffixNameList;
	}
	public void setIgnoreSuffixNameList(List<String> ignoreSuffixNameList) {
		this.ignoreSuffixNameList = ignoreSuffixNameList;
	}
	public long getFileMaxSize() {
		return fileMaxSize;
	}
	
	public void setFileMaxSize(long fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}
	
	public boolean isHiddenFileScan() {
		return hiddenFileScan;
	}
	public void setHiddenFileScan(boolean hiddenFileScan) {
		this.hiddenFileScan = hiddenFileScan;
	}

		
	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	@Override
	public String toString() {
		return "System [intervalTime=" + intervalTime + ", suffixNameList=" + suffixNameList + ", ignoreSuffixNameList="
				+ ignoreSuffixNameList + ", fileMaxSize=" + fileMaxSize + ", hiddenFileScan=" + hiddenFileScan
				+ ", storage=" + storage + "]";
	}

	
}
