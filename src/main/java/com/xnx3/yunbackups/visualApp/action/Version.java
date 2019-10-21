package com.xnx3.yunbackups.visualApp.action;

import javax.swing.SwingUtilities;
import com.xnx3.SystemUtil;
import com.xnx3.swing.DialogUtil;
import com.xnx3.version.VersionUtil;
import com.xnx3.version.VersionVO;
import com.xnx3.yunbackups.visualApp.Global;

/**
 * 版本检测
 * @author 管雷鸣
 *
 */
public class Version {
	public static final String VERSION_CHECK_URL = "http://version.xnx3.com/yunbackups.html";	//版本更新检测的url
	
	/**
	 * 检测新版本，若有最新版本，则弹出更新提示
	 */
	public static void check() {
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() {
				VersionVO vo = VersionUtil.cloudContrast(VERSION_CHECK_URL, Global.VERSION);
				if(vo.isFindNewVersion()){
					//有新版本
					if(DialogUtil.showConfirmDialog("发现新版本 v"+vo.getNewVersion()+" ，是否更新新版本？") == DialogUtil.CONFIRM_YES){
						SystemUtil.openUrl(vo.getPreviewUrl());
					}
				}
			}
		});
	}
}
