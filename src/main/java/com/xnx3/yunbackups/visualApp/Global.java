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
		image = Toolkit.getDefaultToolkit().getImage(MainJFrame.class.getResource("/res/icon.png"));
	}
}
