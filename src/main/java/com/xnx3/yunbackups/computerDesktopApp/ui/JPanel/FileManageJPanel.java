package com.xnx3.yunbackups.computerDesktopApp.ui.JPanel;

import javax.swing.JPanel;
import java.util.Map;
import java.util.Vector;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import com.xnx3.DateUtil;
import com.xnx3.exception.NotReturnValueException;
import com.xnx3.swing.DialogUtil;
import com.xnx3.yunbackups.computerDesktopApp.ui.JPanel.fileManage.FileEditJDialog;
import com.xnx3.yunbackups.computerDesktopApp.ui.JPanel.fileManage.MyButtonRenderer;
import com.xnx3.yunbackups.core.Global;
import com.xnx3.yunbackups.core.bean.BackupsPath;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 备份的文件夹管理
 * @author 管雷鸣
 *
 */
public class FileManageJPanel extends JPanel {
	private JTable table;
	public DefaultTableModel model;

	/**
	 * Create the panel.
	 */
	public FileManageJPanel() {
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnNewButton = new JButton("添加一个要备份的文件夹");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editBackupsPath("");
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
					.addGap(292))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
		);
		
		Vector vData = new Vector();
		Vector vName = new Vector();
		vName.add("文件路径");
		vName.add("最后备份时间");
		vName.add("");
		vName.add("");
		
		model = new DefaultTableModel(vData, vName){
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 1){
					return true;
				}
				return false;
			}
		};
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
			    int row=table.getSelectedRow();
			    int col=table.getSelectedColumn();
			    String path = table.getValueAt(row, 0).toString();
			    
			    if(col == 2){
			    	//修改
			    	editBackupsPath(path); 
			    }else if(col == 3){
			    	//删除
			    	if(DialogUtil.showConfirmDialog("确定要删除吗?<br/>"+path) - DialogUtil.CONFIRM_YES == 0){
			    		Global.backupsPathMap.remove(path);
			    		com.xnx3.yunbackups.core.config.BackupsPath.save(Global.backupsPathMap);
			    		refreshTable();
			    	}
			    }
			}
		});
		table.setModel(model);
		table.setRowHeight(35);
		
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
//		table.getColumn(2).setCellEditor(  new TableEditor(new JCheckBox()));
		
		/*********/
		refreshTable();
		
		table.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(160);
		table.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(160);
		
//		table.getColumnModel().getColumn(2).setCellEditor( new MyButtonEditor());
        table.getColumnModel().getColumn(2).setCellRenderer(  new MyButtonRenderer());
        table.getColumnModel().getColumn(3).setCellRenderer(  new MyButtonRenderer());
        table.setRowSelectionAllowed(false);
	}
	
	public void refreshTable(){
		model.setRowCount(0);	//清除所有数据
		
		//加载table数据
		for(Map.Entry<String, com.xnx3.yunbackups.core.bean.BackupsPath> entry : Global.backupsPathMap.entrySet()){
			com.xnx3.yunbackups.core.bean.BackupsPath backupsPath = entry.getValue();
			
			Vector vRow = new Vector();
			vRow.add(backupsPath.getPath());
			String time = "尚未备份";
			try {
				time = DateUtil.dateFormat(backupsPath.getLasttime(), "yyyy-MM-dd hh:mm");
			} catch (NotReturnValueException e) {
			}
			vRow.add(time);
			vRow.add("修改");
			vRow.add("删除");
			
			model.addRow(vRow);
		}
		
		//刷新table UI
		table.setVisible(true);
	}
	
	public void editBackupsPath(String path){
		FileEditJDialog dialog = new FileEditJDialog(path);
		dialog.setModal(true);//设置模式 dialog关闭后才能获取path的值
		dialog.setVisible(true);
		System.out.println(dialog.newPath);
		if(dialog.newPath != null){
			//有过改动保存
			BackupsPath backupsPath = new BackupsPath();
			backupsPath.setPath(dialog.newPath);
			Global.backupsPathMap.put(backupsPath.getPath(), backupsPath);
			
			//判断一下是修改的，还是新增的
			if(dialog.oldPath.length() > 0){
				//有旧目录，是修改的，那么将就目录从map删除掉
				Global.backupsPathMap.remove(dialog.oldPath);
			}
			
			//保存配置文件
			com.xnx3.yunbackups.core.config.BackupsPath.save(Global.backupsPathMap);
			
			refreshTable();
		}
		
	}
}

