package com.xnx3.yunbackups.visualApp.ui.JPanel;

import java.awt.EventQueue;
import javax.swing.JPanel;
import com.xnx3.Lang;
import com.xnx3.yunbackups.core.Global;
import com.xnx3.yunbackups.core.config.System;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * 系统设置
 * @author 管雷鸣
 *
 */
public class SystemJPanel extends JPanel {
	private JTextField intervalTimeTextField;
	private JTextField fileMaxSizeTextField;
	private JTextArea suffixTextArea;
	private JTextArea ignoreSuffixTextArea;
	private JComboBox intervalTimeUnitComboBox;
	private JComboBox fileMaxSizeUnitComboBox;
	private JComboBox hiddenFileScanComboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SystemJPanel frame = new SystemJPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SystemJPanel() {
		JLabel lblNewLabel = new JLabel("自动备份时间间隔");
		
		intervalTimeTextField = new JTextField();
		intervalTimeTextField.setColumns(10);
		
		intervalTimeUnitComboBox = new JComboBox();
		intervalTimeUnitComboBox.setModel(new DefaultComboBoxModel(new String[] {"分钟", "小时"}));
		
		JLabel label = new JLabel("最大文件大小");
		
		fileMaxSizeTextField = new JTextField();
		fileMaxSizeTextField.setColumns(10);
		
		fileMaxSizeUnitComboBox = new JComboBox();
		fileMaxSizeUnitComboBox.setModel(new DefaultComboBoxModel(new String[] {"KB", "MB"}));
		
		JLabel lblNewLabel_1 = new JLabel("上次自动备份完成时，等待这段时间，会再次进行自动备份");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblNewLabel_1.setForeground(Color.GRAY);
		
		JLabel lblmbmbmb = new JLabel("自动备份文件的大小，例如设置为10MB，那么会自动备份不超过10MB的文件。超过10MB的不会备份");
		lblmbmbmb.setForeground(Color.GRAY);
		lblmbmbmb.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		
		JLabel label_1 = new JLabel("要备份的文件后缀名");
		
		suffixTextArea = new JTextArea();
		
		JLabel lblJpgpngxlstxt = new JLabel("<html>设置格式如： jpg,png,xls,txt  每个后缀中间用,分割。如果设置上了，那么备份时只会备份当前所设定的后缀名的文件。<br/>\n如果不设置，留空，那么会备份所有的文件。");
		lblJpgpngxlstxt.setVerticalAlignment(SwingConstants.TOP);
		lblJpgpngxlstxt.setForeground(Color.GRAY);
		lblJpgpngxlstxt.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		
		JLabel label_3 = new JLabel("不备份的文件后缀名");
		
		ignoreSuffixTextArea = new JTextArea();
		
		JLabel lblExebattxtxml = new JLabel("<html>设置格式如： exe,bat,txt,xml  每个后缀名中间用,分割。如果设置上，那么备份时，此后缀的文件忽略掉，不会进行自动备份。");
		lblExebattxtxml.setVerticalAlignment(SwingConstants.TOP);
		lblExebattxtxml.setForeground(Color.GRAY);
		lblExebattxtxml.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		
		JButton btnNewButton = new JButton("保存设置");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				com.xnx3.yunbackups.core.bean.System system = new com.xnx3.yunbackups.core.bean.System();
				
				String fileMaxStr = fileMaxSizeTextField.getText();
				int fileMaxSize = Lang.stringToInt(fileMaxStr, 0);
				if(fileMaxSize < 1){
					fileMaxSize = com.xnx3.yunbackups.core.bean.System.DEFAULT_FILE_MAX_SIZE;
				}else{
					//判断选择的是MB，还是KB，如果是MB，那么还要先变为KB
					if(fileMaxSizeUnitComboBox.getSelectedItem().toString().equals("MB")){
						fileMaxSize = fileMaxSize * 1024;
					}
					//再将KB转化为B
					fileMaxSize = fileMaxSize * 1024;
				}
				system.setFileMaxSize(fileMaxSize);
				
				String intervalStr = intervalTimeTextField.getText();
				int intervalTime = Lang.stringToInt(intervalStr, 0);
				if(intervalTime < 0){
					intervalTime = com.xnx3.yunbackups.core.bean.System.DEFAULT_INTERVALTIME;
				}else{
					//判断选择的是分钟，还是小时，如果是小时，那么还要变为分钟
					if(intervalTimeUnitComboBox.getSelectedItem().toString().equals("小时")){
						intervalTime = intervalTime * 60;
					}
				}
				system.setIntervalTime(intervalTime);
				
				if(suffixTextArea.getText().trim().length() > 0){
					String[] suffixs = suffixTextArea.getText().trim().split(",|，");
					List<String> list = new ArrayList<String>();
					for (int i = 0; i < suffixs.length; i++) {
						String suffix = suffixs[i].trim();
						if(suffix.length() > 0){
							list.add(suffix);
						}
					}
					system.setSuffixNameList(list);
				}
				
				if(ignoreSuffixTextArea.getText().trim().length() > 0){
					String[] suffixs = ignoreSuffixTextArea.getText().trim().split(",|，");
					List<String> list = new ArrayList<String>();
					for (int i = 0; i < suffixs.length; i++) {
						String suffix = suffixs[i].trim();
						if(suffix.length() > 0){
							list.add(suffix);
						}
					}
					system.setIgnoreSuffixNameList(list);
				}
				
				system.setHiddenFileScan(hiddenFileScanComboBox.getSelectedIndex() == 0);
				
				Global.system = system;
				System.save(system);
			}
		});
		
		JLabel label_2 = new JLabel("是否备份隐藏文件");
		
		hiddenFileScanComboBox = new JComboBox();
		hiddenFileScanComboBox.setModel(new DefaultComboBoxModel(new String[] {"备份", "不备份"}));
		hiddenFileScanComboBox.setSelectedIndex(1);
		
		JLabel label_4 = new JLabel("<html>是否备份隐藏的文件与文件夹。一般为默认的不备份即可");
		label_4.setVerticalAlignment(SwingConstants.TOP);
		label_4.setForeground(Color.GRAY);
		label_4.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		GroupLayout gl_contentPane = new GroupLayout(this);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblExebattxtxml, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblJpgpngxlstxt, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblmbmbmb, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(intervalTimeTextField, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(intervalTimeUnitComboBox, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(fileMaxSizeTextField, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(fileMaxSizeUnitComboBox, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(173)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(ignoreSuffixTextArea, GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(suffixTextArea, GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(hiddenFileScanComboBox, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_4, GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(intervalTimeTextField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(intervalTimeUnitComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(fileMaxSizeTextField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(fileMaxSizeUnitComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblmbmbmb, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(30)
							.addComponent(label_1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(suffixTextArea, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblJpgpngxlstxt, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_3)
						.addComponent(ignoreSuffixTextArea, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblExebattxtxml, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(hiddenFileScanComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
		);
		setLayout(gl_contentPane);
		
		
		/***** 初始化加载信息 ******/
		
		long fileMaxSize = Global.system.getFileMaxSize();
		//判断一下它的单位，是KB还是MB。那也就是除出来，是否有余
		//先将它转化为KB
		fileMaxSize = fileMaxSize / 1024;
		//判断一下如果转化为MB，是否有余数
		if(fileMaxSize % 1024 > 0){
			//有余数，那单位是KB
			fileMaxSizeUnitComboBox.setSelectedIndex(0);
			fileMaxSizeTextField.setText(fileMaxSize+"");
		}else{
			//没有余数，那单位是MB
			fileMaxSizeUnitComboBox.setSelectedIndex(1);
			fileMaxSizeTextField.setText((fileMaxSize/1024)+"");
		}
		
		
		long intervalTime = Global.system.getIntervalTime();
		//判断一下它的单位，是分钟还是小时
		if(intervalTime % 60 > 0){
			//有余数，那单位是分钟
			intervalTimeUnitComboBox.setSelectedIndex(0);
			intervalTimeTextField.setText(intervalTime+"");
		}else{
			//没有余数，那单位是小时
			intervalTimeUnitComboBox.setSelectedIndex(1);
			intervalTimeTextField.setText((intervalTime/60)+"");
		}
		
		
		String suffixStr = "";	//不算多，就不用stringbuffer了
		for (int i = 0; i < Global.system.getSuffixNameList().size(); i++) {
			String suffix = Global.system.getSuffixNameList().get(i);
			if(suffix.trim().length() > 0){
				if(suffixStr.length() > 0){
					suffixStr = suffixStr + ",";
				}
				suffixStr = suffixStr + suffix;
			}
		}
		suffixTextArea.setText(suffixStr);
	
		String ignoreSuffixStr = "";	//不算多，就不用stringbuffer了
		for (int i = 0; i < Global.system.getSuffixNameList().size(); i++) {
			String suffix = Global.system.getSuffixNameList().get(i);
			if(suffix.trim().length() > 0){
				if(ignoreSuffixStr.length() > 0){
					ignoreSuffixStr = ignoreSuffixStr + ",";
				}
				ignoreSuffixStr = ignoreSuffixStr + suffix;
			}
		}
		ignoreSuffixTextArea.setText(ignoreSuffixStr);
		
		hiddenFileScanComboBox.setSelectedIndex(Global.system.isHiddenFileScan()? 0:1);
	}
	public JComboBox getIntervalTimeUnitComboBox() {
		return intervalTimeUnitComboBox;
	}
	public JComboBox getFileMaxSizeUnitComboBox() {
		return fileMaxSizeUnitComboBox;
	}
	public JComboBox getHiddenFileScanComboBox() {
		return hiddenFileScanComboBox;
	}
}
