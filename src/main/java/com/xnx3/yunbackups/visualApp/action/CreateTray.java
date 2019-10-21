package com.xnx3.yunbackups.visualApp.action;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import com.xnx3.swing.DialogUtil;
import com.xnx3.swing.TrayUtil;
import com.xnx3.yunbackups.core.util.SystemUtil;
import com.xnx3.yunbackups.visualApp.Global;
import com.xnx3.yunbackups.visualApp.ui.MainJFrame;

/**
 * 创建当前程序的托盘
 * @author 管雷鸣
 *
 */
public class CreateTray {
	public static TrayIcon tray;
	
	public CreateTray(){
		//添加右键弹出按钮
		PopupMenu popupMenu=new PopupMenu();
		java.awt.MenuItem menuItemUI=new java.awt.MenuItem("打开主界面");	//关于按钮
		java.awt.MenuItem menuItemAbout=new java.awt.MenuItem("访问官网");	//关于按钮
		java.awt.MenuItem menuItemExit=new java.awt.MenuItem("退出程序");	//退出按钮
		
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemUtil.openUrl("http://www.yunbackups.com");
			}
		});
		menuItemUI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Global.mainJFrame.setVisible(true);
			}
		});
		
		popupMenu.add(menuItemUI);
		popupMenu.add(menuItemAbout);
		popupMenu.add(menuItemExit);
		
		//创建托盘
    	SystemTray sysTray= SystemTray.getSystemTray();// 获得当前操作系统的托盘对象  
    	tray = new TrayIcon(Global.image, "云备份软件", popupMenu);
    	
    	tray.setImageAutoSize(true);
        if(sysTray == null){
        	System.out.println("SystemTray is null !");
        	return;
        }
        if(tray == null){
        	System.out.println("tray is null !");
        	return;
        }
        try {
			sysTray.add(tray);		//将托盘添加到操作系统的托盘
		} catch (AWTException e1) {
			//mac 有这个问题
			try {
				java.awt.SystemTray.getSystemTray().add(new java.awt.TrayIcon(java.awt.Toolkit.getDefaultToolkit().getImage("res/icon.png")));
			} catch (AWTException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		}
		
		//创建托盘完毕后，拿到的tray对象可以进行在创建的托盘上弹出文字提示
//		tray.displayMessage("标题", "内容", MessageType.INFO);
	}
	
	/**
	 * 托盘弹出的消息提醒
	 * @param caption 消息标题
	 * @param text 消息内容
	 */
	public static void showTrayMessage(String caption, String text){
		tray.displayMessage(caption, text, MessageType.INFO);
	}
	public static void main(String[] args) {
		
	}
}
