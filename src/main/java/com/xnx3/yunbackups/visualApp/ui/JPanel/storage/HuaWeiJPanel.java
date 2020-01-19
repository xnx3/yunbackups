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
import com.xnx3.yunbackups.core.util.StorateUtil;
import com.xnx3.yunbackups.core.util.SystemUtil;
import com.xnx3.yunbackups.storage.HuaweiyunOBS;
import com.xnx3.yunbackups.storage.Sftp;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * 华为云配置
 * @author 管雷鸣
 *
 */
public class HuaWeiJPanel extends JPanel {
	private JTextField accessKeyIdTextField;
	private JTextField secretAccessKeyTextField;
	private JTextField bucketNameTextField;
	private JTextField endPointTextField;
	private JLabel setupLabel;

	/**
	 * Create the panel.
	 */
	public HuaWeiJPanel() {
		
		JLabel lblNewLabel = new JLabel("Access Key Id");
		
		accessKeyIdTextField = new JTextField();
		accessKeyIdTextField.setColumns(10);
		
		JLabel lblSecretAccessKey = new JLabel("Secret Access Key");
		
		JLabel lblBA = new JLabel("Bucket Name");
		
		secretAccessKeyTextField = new JTextField();
		secretAccessKeyTextField.setColumns(10);
		
		bucketNameTextField = new JTextField();
		bucketNameTextField.setColumns(10);
		
		JButton btnNewButton = new JButton("保存，并使用华为云作为存储目的地");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("accessKeyId", accessKeyIdTextField.getText().trim());
				map.put("secretAccessKey", secretAccessKeyTextField.getText().trim());
				map.put("bucketName", bucketNameTextField.getText().trim());
				map.put("endpoint", endPointTextField.getText().trim());
				
				//测试是否能连接通
				StorageInterface storageInterface = new HuaweiyunOBS(accessKeyIdTextField.getText().trim(), secretAccessKeyTextField.getText().trim(), endPointTextField.getText().trim(), bucketNameTextField.getText().trim());
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
				Global.system.setStorage("huaweiobs");
				com.xnx3.yunbackups.core.config.System.save(Global.system);
				
				//更新持久缓存
				Global.storageConfigMap = map;
				//保存到本地配置文件
				StorageConfig.save(map);
				DialogUtil.showMessageDialog("测试上传正常，参数保存成功！当前已使用华为云OBS为备份目的地。");
			}
		});
		
		JLabel lblObsendpoint = new JLabel("Endpoint");
		
		endPointTextField = new JTextField();
		endPointTextField.setColumns(10);
		
		setupLabel = new JLabel("点击此处查看详细的设置步骤");
		setupLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SystemUtil.openUrl("http://www.yunbackups.com/huaweiobsSetup.html");
			}
		});
		setupLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setupLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		setupLabel.setForeground(Color.BLUE);
		setupLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(accessKeyIdTextField, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblSecretAccessKey, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(secretAccessKeyTextField, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblBA, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(bucketNameTextField, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblObsendpoint, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(endPointTextField, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(setupLabel, GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(71)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(accessKeyIdTextField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSecretAccessKey, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(secretAccessKeyTextField, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBA, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(bucketNameTextField, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(endPointTextField, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblObsendpoint, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(42)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(setupLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(43, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

		//数据填充，但要考虑数据泄漏危险
		accessKeyIdTextField.setText(StorateUtil.getStorageConfigValue(Global.storageConfigMap, "accessKeyId"));
		secretAccessKeyTextField.setText(StorateUtil.getStorageConfigValue(Global.storageConfigMap, "secretAccessKey"));
		bucketNameTextField.setText(StorateUtil.getStorageConfigValue(Global.storageConfigMap, "bucketName"));
		endPointTextField.setText(StorateUtil.getStorageConfigValue(Global.storageConfigMap, "endpoint"));
	}
	public JTextField getAccessKeyIdTextField() {
		return accessKeyIdTextField;
	}
	public JTextField getSecretAccessKeyTextField() {
		return secretAccessKeyTextField;
	}
	public JTextField getBucketNameTextField() {
		return bucketNameTextField;
	}
	public JTextField getEndPointTextField() {
		return endPointTextField;
	}
	public JLabel getSetupLabel() {
		return setupLabel;
	}
}
