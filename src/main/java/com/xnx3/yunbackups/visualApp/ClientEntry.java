package com.xnx3.yunbackups.visualApp;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.MistSilverSkin;

import com.xnx3.swing.DialogUtil;
import com.xnx3.yunbackups.commandLineApp.config.HuaweiObsConfig;
import com.xnx3.yunbackups.core.util.SystemUtil;
import com.xnx3.yunbackups.visualApp.action.CreateTray;
import com.xnx3.yunbackups.visualApp.action.LogJPanelAction;
import com.xnx3.yunbackups.visualApp.action.VersionCheck;
import com.xnx3.yunbackups.visualApp.ui.MainJFrame;
import com.xnx3.yunbackups.visualApp.ui.JPanel.FileManageJPanel;
import com.xnx3.yunbackups.visualApp.ui.JPanel.HuaWeiConfigJPanel;
import com.xnx3.yunbackups.visualApp.ui.JPanel.LogJPanel;
import com.xnx3.yunbackups.visualApp.ui.JPanel.SystemJPanel;

/**
 * 云客户端的运行入口
 * @author 管雷鸣
 *
 */
public class ClientEntry {
	
	public static void main(String[] args) throws IOException {
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() {
				
				try{
					SubstanceLookAndFeel.setSkin(new MistSilverSkin());
				}catch(Exception e){
					e.printStackTrace();
				}
				Global.logJPanel = new LogJPanel();
				
				//加载云端配置参数
				com.xnx3.yunbackups.commandLineApp.Global.cloudConfigBean = HuaweiObsConfig.read();
				
				Global.mainJFrame = new MainJFrame();
				Global.mainJFrame.setSize(700, 540);
				Global.mainJFrame.tabbedPane.addTab("运行状态", Global.logJPanel);
				Global.mainJFrame.tabbedPane.addTab("系统参数", new SystemJPanel());
				Global.mainJFrame.tabbedPane.addTab("备份目录", new FileManageJPanel());
				Global.mainJFrame.tabbedPane.addTab("华为云配置", new HuaWeiConfigJPanel());
				Global.mainJFrame.setVisible(true);
				
				if(SystemUtil.isMacOS()){
					/*
					 * mac有些功能特别，不能用，比如：
					 * 1. 托盘创建不了
					 * 2. 最小化卡死  
					 */
					Global.mainJFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);           //禁用close功能
					Global.mainJFrame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);//使frame只剩下标题栏
					Global.mainJFrame.addWindowListener(new WindowListener() {
						public void windowOpened(WindowEvent e) {
						}
						public void windowIconified(WindowEvent e) {
						}
						public void windowDeiconified(WindowEvent e) {
						}
						public void windowDeactivated(WindowEvent e) {
						}
						public void windowClosing(WindowEvent e) {
							DialogUtil.showMessageDialog("你好，苹果系统不提供关闭功能！<br/>关闭了就没法自动备份了，<br/>您可以本窗口拖一边去就好。");
						}
						public void windowClosed(WindowEvent e) {
						}
						public void windowActivated(WindowEvent e) {
						}
					});
					
				}
				
				//判断一下是否设置过备份服务器相关参数，若已经设置了，那么自动运行
				if(com.xnx3.yunbackups.commandLineApp.Global.cloudConfigBean.getBucketName() != null && com.xnx3.yunbackups.commandLineApp.Global.cloudConfigBean.getBucketName().length() > 0){
					//判断要备份的目录是否为空，backupsPath 为空，用户还没有设置要备份哪个目录
					if(com.xnx3.yunbackups.core.Global.backupsPathMap == null || com.xnx3.yunbackups.core.Global.backupsPathMap.size() == 0){
						return;
					}
					if(com.xnx3.yunbackups.core.Global.system == null){
						return;
					}
					
					new Thread(new Runnable() {
						public void run() {
							com.xnx3.yunbackups.visualApp.Global.logJPanel.runButton.setVisible(false);
							com.xnx3.yunbackups.visualApp.Global.logJPanel.statusLabel.setText("初始化中...20秒后开启...");
							try {
								Thread.sleep(20000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							LogJPanelAction.clickRunButton();
						}
					}).start();
					
				}
				
				//新版本检测
				VersionCheck.check(Global.VERSION_CHECK_URL, Global.VERSION, "yunbackups");
			}
			
		});
		
		//创建托盘
		new CreateTray();
	}
	
	
}
