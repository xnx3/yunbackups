package com.xnx3.yunbackups.visualApp.action;

import java.io.File;
import java.io.IOException;
import com.xnx3.SystemUtil;
import com.xnx3.net.HttpResponse;
import com.xnx3.net.HttpUtil;
import com.xnx3.swing.DialogUtil;
import com.xnx3.yunbackups.visualApp.Global;

/**
 * 版本更新检测。
 * windows系统自动下载更新
 * 其他系统需要到官网手动下载最新的
 * @author 管雷鸣
 */
public class VersionCheck {
	public static void main(String[] args) throws IOException {
		check("http://version.xnx3.com/yunerclient.html", Global.VERSION);
	}
	
	/**
	 * 版本检测，最好将其放在一个线程中
	 * @param versionCheckUrl 版本更新检测的url
	 * @param currentVersion 当前最新版本，如 1.0
	 */
	public static void check(String versionCheckUrl, String currentVersion){
		//读取最新版本
		HttpUtil http = new HttpUtil();
		HttpResponse hr = http.get(versionCheckUrl);
		String text = hr.getContent();
		if(text == null){
			System.out.println("text is null");
			return;
		}
		
		String s[] = text.split("\\|");
		if(s.length < 2){
			return;
		}
		
		//有新版本
		String newVersion = s[0];	//新版本的版本号
		String url = s[1];	//更新url，这个是打开一个网页的方式，用户先看网页，再点下载
		String win32ExeUrl = s[2];	//windows 32位系统的exe下载地址，格式如 http://xxxx.com/xxx.exe
		String win64ExeUrl = s[3];	//windows 64位系统的exe下载地址，格式如 http://xxxx.com/xxx.exe
		
		if(!newVersion.equals(currentVersion)){
			//有新版本
			if(DialogUtil.showConfirmDialog("发现新版本 v"+newVersion+"， 是否更新最新版本？") == DialogUtil.CONFIRM_YES){
				if(com.xnx3.yunbackups.core.util.SystemUtil.isWindowsOS()){
					//windows系统版本更新，直接更新exe文件就好，自动下载覆盖
					//打开jre/bin/versionUpdate.jar，进行更新
					String currentDir = SystemUtil.getCurrentDir()+File.separator;	//当前xxxx.exe运行文件所在的路径
					String javaPath = currentDir+"jre"+File.separator+"bin"+File.separator;	//java.exe的路径
					String cmd = javaPath+"java.exe -cp "+javaPath+"versionUpdate.jar com.xnx3.version.WindowsVersionUpdate appPath="+currentDir+" appName=yuner.exe javaPath="+javaPath;
					if(System.getProperty("os.arch").indexOf("64") > 0){
						//64位
						cmd = cmd + " downUrl="+win64ExeUrl;
					}else{
						//32位
						cmd = cmd + " downUrl="+win32ExeUrl;
					}
					System.out.println(cmd);
					try {
						Runtime.getRuntime().exec(cmd);
						
						//退出当前程序
						System.exit(0);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
				
				//其他系统，让他们到官网进行更新
				com.xnx3.yunbackups.core.util.SystemUtil.openUrl(url);
			}
		}
	}
}
