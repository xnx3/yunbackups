package com.xnx3.yunbackups.visualApp.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.xnx3.yunbackups.visualApp.Global;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;

/**
 * 主界面
 * @author 管雷鸣
 *
 */
public class MainJFrame extends JFrame {

	private JPanel contentPane;
	public JTabbedPane tabbedPane;

	/**
	 * Create the frame.
	 */
	public MainJFrame() {
		if(Global.image != null){
			setIconImage(Global.image);
		}
		setTitle("yunbackups v"+Global.VERSION +"    云备份软件，保障您的数据安全！");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

}
