package com.xnx3.yunbackups.visualApp;

import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.NebulaSkin;
import com.xnx3.yunbackups.commandLineApp.config.CloudConfig;
import com.xnx3.yunbackups.core.subsidiary.BackupsPathSynchronizationThread;
import com.xnx3.yunbackups.visualApp.action.CreateTray;
import com.xnx3.yunbackups.visualApp.action.LogJPanelAction;
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
					SubstanceLookAndFeel.setSkin(new NebulaSkin());
				}catch(Exception e){
					e.printStackTrace();
				}
				Global.logJPanel = new LogJPanel();
				
				//加载云端配置参数
				com.xnx3.yunbackups.commandLineApp.Global.cloudConfigBean = CloudConfig.read();
				
				Global.mainJFrame = new MainJFrame();
				Global.mainJFrame.setSize(1000, 600);
				Global.mainJFrame.tabbedPane.addTab("运行状态", Global.logJPanel);
				Global.mainJFrame.tabbedPane.addTab("系统参数", new SystemJPanel());
				Global.mainJFrame.tabbedPane.addTab("备份目录", new FileManageJPanel());
				Global.mainJFrame.tabbedPane.addTab("华为云配置", new HuaWeiConfigJPanel());
				Global.mainJFrame.setVisible(true);
				
				//判断一下是否设置过备份服务器相关参数，若已经设置了，那么自动运行
				if(com.xnx3.yunbackups.commandLineApp.Global.cloudConfigBean.getBucketName() != null && com.xnx3.yunbackups.commandLineApp.Global.cloudConfigBean.getBucketName().length() > 0){
					System.out.println("run...");
					LogJPanelAction.clickRunButton();
				}
			}
			
		});

		//运行辅助线程-自动同步当前备份进度
		new BackupsPathSynchronizationThread().start();
		
		//创建托盘
		new CreateTray();
	}
	
	
}
