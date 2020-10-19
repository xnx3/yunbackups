package com.xnx3.yunbackups.visualApp.ui.JPanel.storage;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.xnx3.BaseVO;
import com.xnx3.swing.DialogUtil;
import com.xnx3.yunbackups.core.Global;
import com.xnx3.yunbackups.core.backups.interfaces.StorageInterface;
import com.xnx3.yunbackups.core.config.StorageConfig;
import com.xnx3.yunbackups.core.util.StorageUtil;
import com.xnx3.yunbackups.storage.Sftp;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

public class SftpJPanel extends JPanel {
	private JTextField hostTextField;
	private JTextField usernameTextField;
	private JLabel label;
	private JTextField passwordTextField;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel lblSFT;
	private JLabel lblSFT_1;

	/**
	 * Create the panel.
	 */
	public SftpJPanel() {
		
		JLabel lblNewLabel = new JLabel("IP");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		hostTextField = new JTextField();
		hostTextField.setColumns(10);
		
		usernameTextField = new JTextField();
		usernameTextField.setColumns(10);
		
		label = new JLabel("用户名");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		passwordTextField = new JTextField();
		passwordTextField.setColumns(10);
		
		label_1 = new JLabel("密码");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		label_2 = new JLabel("填入如：192.168.1.2");
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblSFT = new JLabel("sftp登录的用户名");
		lblSFT.setHorizontalAlignment(SwingConstants.LEFT);
		lblSFT.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		lblSFT_1 = new JLabel("sftp登录的密码");
		lblSFT_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblSFT_1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		
		JButton btnsftp = new JButton("保存，并使用SFTP作为存储目的地");
		btnsftp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("host", hostTextField.getText().trim());
				map.put("username", usernameTextField.getText().trim());
				map.put("password", passwordTextField.getText().trim());
				
				//测试是否能连接通
				StorageInterface storageInterface = new Sftp(hostTextField.getText().trim(), usernameTextField.getText().trim(), passwordTextField.getText().trim());
				BaseVO vo;
				try {
					vo = storageInterface.isUsable();
					if(vo.getResult() - BaseVO.FAILURE == 0){
						DialogUtil.showMessageDialog("保存失败！"+vo.getInfo());
						return;
					}
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
					DialogUtil.showMessageDialog("保存失败，连接测试时，网络异常！请重试");
					return;
				}
				
				//保存system配置文件
				Global.system.setStorage("sftp");
				com.xnx3.yunbackups.core.config.System.save(Global.system);
				
				//更新持久缓存
				Global.storageConfigMap = map;
				//保存到本地配置文件
				StorageConfig.save(map);
				
				DialogUtil.showMessageDialog("测试上传正常，参数保存成功！当前已使用sftp为备份目的地。");
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(hostTextField, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(usernameTextField, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblSFT, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(passwordTextField, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblSFT_1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(60)
							.addComponent(btnsftp, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(40, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(16)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(hostTextField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(usernameTextField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblSFT, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(3)
									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(passwordTextField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblSFT_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(22)
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)))
					.addGap(40)
					.addComponent(btnsftp, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(88, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		//数据填充
		hostTextField.setText(StorageUtil.getStorageConfigValue(Global.storageConfigMap, "host"));
		usernameTextField.setText(StorageUtil.getStorageConfigValue(Global.storageConfigMap, "username"));
		passwordTextField.setText(StorageUtil.getStorageConfigValue(Global.storageConfigMap, "password"));
	}
}
