package com.xnx3.yunbackups.computerDesktopApp;

import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.NebulaSkin;
import com.xnx3.yunbackups.computerDesktopApp.ui.MainJFrame;
import com.xnx3.yunbackups.computerDesktopApp.ui.JPanel.LogJPanel;
import com.xnx3.yunbackups.core.backups.BackupsThread;
import com.xnx3.yunbackups.core.subsidiary.BackupsPathSynchronizationThread;
import com.xnx3.yunbackups.defaultStorage.HuaweiyunOBS;

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
				MainJFrame frame = new MainJFrame();
				frame.setSize(1000, 600);
				Global.logJPanel = new LogJPanel();
				frame.setVisible(true);
				
				frame.setContentPane(Global.logJPanel);	
				frame.setVisible(true);
				
				//运行自动备份线程，执行备份操作
				new BackupsThread(new HuaweiyunOBS(),new ProgressListener()).start();
				
			}
		});

		
		//运行辅助线程-自动同步当前备份进度
		//new BackupsPathSynchronizationThread().start();
		
	}
	
	
}
