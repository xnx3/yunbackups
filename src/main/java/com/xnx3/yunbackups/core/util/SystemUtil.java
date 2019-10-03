package com.xnx3.yunbackups.core.util;

/**
 * 操作系统相关工具类
 * @author 管雷鸣
 *
 */
public class SystemUtil extends com.xnx3.SystemUtil{
	private static String osname = null;	//当前操作系统名字
	/**
	 * 获取当前操作系统名字
	 * @return 返回如： mac os x
	 */
	public static String getOSName(){
		if(osname == null){
			osname = System.getProperty("os.name").toLowerCase();
		}
		return osname;
	}
	
	/**
	 * 获取当前系统是否是 windows os
	 * @return true：是
	 */
	public static boolean isWindowsOS(){
		return getOSName().indexOf("window") > -1;
	}
	
	public static void main(String[] args) {
		System.out.println(getOSName());
	}
	
}
