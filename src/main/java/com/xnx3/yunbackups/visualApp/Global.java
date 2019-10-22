package com.xnx3.yunbackups.visualApp;

import java.awt.Image;
import java.awt.Toolkit;

import com.xnx3.yunbackups.visualApp.ui.MainJFrame;
import com.xnx3.yunbackups.visualApp.ui.JPanel.LogJPanel;

/**
 * 当前包的全局缓存
 * @author 管雷鸣
 *
 */
public class Global {
	//主界面
	public static MainJFrame mainJFrame;
	
	//日志面板
	public static LogJPanel logJPanel;
	
	//当前桌面app的版本
	public static final String VERSION = "1.0";
	
	//图标
	public static Image image;
	static{
		try {
			image = Toolkit.getDefaultToolkit().getImage(Global.class.getResource("/res/icon.png"));
		} catch (Exception e) {
			//System.out.println("如果是吧这个包导出来的，加入到其他项目中用，这里会有异常，忽略即可");
			//e.printStackTrace();
		}
	}
}
