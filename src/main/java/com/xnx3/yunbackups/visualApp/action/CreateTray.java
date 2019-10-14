package com.xnx3.yunbackups.visualApp.action;

import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import com.xnx3.swing.DialogUtil;
import com.xnx3.swing.TrayUtil;
import com.xnx3.yunbackups.visualApp.Global;

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
		java.awt.MenuItem menuItemAbout=new java.awt.MenuItem("关于我们");	//关于按钮
		java.awt.MenuItem menuItemExit=new java.awt.MenuItem("退出程序");	//退出按钮
		
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogUtil.showMessageDialog("自动备份 - 雷鸣云");
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
		ImageIcon icon = new ImageIcon("res/trayicon.png");// 创建图片对象
		tray = TrayUtil.createTray(icon, "yunbackups", popupMenu);
		
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
}
