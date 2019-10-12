package com.xnx3.yunbackups.computerDesktopApp.ui.JPanel;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.xnx3.yunbackups.computerDesktopApp.Global;
import com.xnx3.yunbackups.computerDesktopApp.backups.ExceptionListener;
import com.xnx3.yunbackups.computerDesktopApp.backups.ProgressListener;
import com.xnx3.yunbackups.core.backups.BackupsThread;
import com.xnx3.yunbackups.defaultStorage.HuaweiyunOBS;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 实时日志
 * @author 管雷鸣
 *
 */
public class LogJPanel extends JPanel {
	public JLabel allFileNumberLabel;
	public JLabel alreadyScanAccordNumberLabel;
	public JLabel currentBackupsFilePathLabel;
	public JLabel currentBackupsPathLabel;
	public JLabel scanTimeLabel;
	public JLabel sortTimeLabel;
	public JButton runButton;
	private JLabel statusLabel;
	private JPanel progressPanel;
	
	/**
	 * Create the panel.
	 */
	public LogJPanel() {
		
		statusLabel = new JLabel("");
		
		progressPanel = new JPanel();
		progressPanel.setVisible(false);
		
		runButton = new JButton("运行");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//运行自动备份线程，执行备份操作
				HuaweiyunOBS obs = new HuaweiyunOBS(Global.cloudConfigBean.getAccessKeyId(), Global.cloudConfigBean.getSecretAccessKey(), Global.cloudConfigBean.getEndpoint(), Global.cloudConfigBean.getBucketName());
				BackupsThread backupsThread = new BackupsThread(obs);
				//进行备份进度的监听。如果不想看到实时进度，此项完全可以省略，还能提升性能
				backupsThread.setProgressListener(new ProgressListener());
				//备份过程中遇到的异常监听。比如断网了
				backupsThread.setExceptionListener(new ExceptionListener());
				//开始执行此线程，开始备份
				backupsThread.start();
				
				runButton.setVisible(false);
				statusLabel.setText("扫描中...");
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(progressPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(runButton, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(statusLabel, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(statusLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(runButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(progressPanel, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(48, Short.MAX_VALUE))
		);
		
		JLabel label = new JLabel("本次已扫描文件数量：");
		
		allFileNumberLabel = new JLabel("计算中...");
		
		JLabel label_1 = new JLabel("即将进行备份的文件数量：");
		
		alreadyScanAccordNumberLabel = new JLabel("计算中...");
		
		JLabel lblNewLabel_2 = new JLabel("当前进行备份的目录：");
		
		currentBackupsPathLabel = new JLabel("加载中...");
		
		JLabel label_3 = new JLabel("耗时：");
		
		scanTimeLabel = new JLabel("计算中 ...");
		
		JLabel label_4 = new JLabel("耗时：");
		
		sortTimeLabel = new JLabel("计算中 ...");
		
		JLabel label_2 = new JLabel("当前已备份的文件：");
		
		currentBackupsFilePathLabel = new JLabel("等待扫描...");
		currentBackupsFilePathLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		GroupLayout gl_progressPanel = new GroupLayout(progressPanel);
		gl_progressPanel.setHorizontalGroup(
			gl_progressPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_progressPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_progressPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_progressPanel.createSequentialGroup()
							.addGroup(gl_progressPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_progressPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(currentBackupsPathLabel, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
								.addGroup(gl_progressPanel.createSequentialGroup()
									.addGroup(gl_progressPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(alreadyScanAccordNumberLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
										.addComponent(allFileNumberLabel, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_progressPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_progressPanel.createSequentialGroup()
											.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(scanTimeLabel, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_progressPanel.createSequentialGroup()
											.addGap(2)
											.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(sortTimeLabel, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))))))
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_progressPanel.createSequentialGroup()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(currentBackupsFilePathLabel, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_progressPanel.setVerticalGroup(
			gl_progressPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_progressPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_progressPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(currentBackupsPathLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_progressPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_progressPanel.createSequentialGroup()
							.addGroup(gl_progressPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(allFileNumberLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_progressPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(alreadyScanAccordNumberLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_progressPanel.createSequentialGroup()
							.addGroup(gl_progressPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(scanTimeLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_progressPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(sortTimeLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
					.addGroup(gl_progressPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(currentBackupsFilePathLabel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		progressPanel.setLayout(gl_progressPanel);
		setLayout(groupLayout);

	}
	public JLabel getStatusLabel() {
		return statusLabel;
	}
	public JPanel getProgressPanel() {
		return progressPanel;
	}
}
