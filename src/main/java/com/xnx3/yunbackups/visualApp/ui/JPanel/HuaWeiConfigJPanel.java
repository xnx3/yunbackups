package com.xnx3.yunbackups.visualApp.ui.JPanel;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.xnx3.yunbackups.commandLineApp.Global;
import com.xnx3.yunbackups.commandLineApp.bean.CloudConfigBean;
import com.xnx3.yunbackups.commandLineApp.config.HuaweiObsConfig;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 华为云配置
 * @author 管雷鸣
 *
 */
public class HuaWeiConfigJPanel extends JPanel {
	private JTextField accessKeyIdTextField;
	private JTextField secretAccessKeyTextField;
	private JTextField bucketNameTextField;
	private JTextField endPointTextField;

	/**
	 * Create the panel.
	 */
	public HuaWeiConfigJPanel() {
		
		JLabel lblNewLabel = new JLabel("Access Key Id");
		
		accessKeyIdTextField = new JTextField();
		accessKeyIdTextField.setColumns(10);
		
		JLabel lblSecretAccessKey = new JLabel("Secret Access Key");
		
		JLabel lblBA = new JLabel("Bucket Name");
		
		secretAccessKeyTextField = new JTextField();
		secretAccessKeyTextField.setColumns(10);
		
		bucketNameTextField = new JTextField();
		bucketNameTextField.setColumns(10);
		
		JButton btnNewButton = new JButton("保存");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CloudConfigBean bean = new CloudConfigBean();
				bean.setAccessKeyId(accessKeyIdTextField.getText().trim());
				bean.setSecretAccessKey(secretAccessKeyTextField.getText().trim());
				bean.setBucketName(bucketNameTextField.getText().trim());
				bean.setEndpoint(endPointTextField.getText().trim());
				
				//更新持久缓存
				Global.cloudConfigBean = bean;
				//保存到本地配置文件
				HuaweiObsConfig.save(bean);
			}
		});
		
		JLabel lblObsendpoint = new JLabel("Endpoint");
		
		endPointTextField = new JTextField();
		endPointTextField.setColumns(10);
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
									.addComponent(accessKeyIdTextField, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblSecretAccessKey, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(secretAccessKeyTextField, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblBA, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(bucketNameTextField, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(105)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblObsendpoint, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(endPointTextField, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(accessKeyIdTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSecretAccessKey)
						.addComponent(secretAccessKeyTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBA)
						.addComponent(bucketNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(endPointTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblObsendpoint))
					.addGap(28)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(79, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

		//数据填充，但要考虑数据泄漏危险
		accessKeyIdTextField.setText(Global.cloudConfigBean.getAccessKeyId());
		secretAccessKeyTextField.setText(Global.cloudConfigBean.getSecretAccessKey());
		bucketNameTextField.setText(Global.cloudConfigBean.getBucketName());
		endPointTextField.setText(Global.cloudConfigBean.getEndpoint());
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
}