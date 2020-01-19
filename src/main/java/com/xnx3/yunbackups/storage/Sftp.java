package com.xnx3.yunbackups.storage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.UnknownHostException;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.xnx3.BaseVO;
import com.xnx3.net.SFTPUtil;
import com.xnx3.yunbackups.core.backups.interfaces.StorageInterface;

/**
 * 通过SFTP备份同步
 * @author 管雷鸣
 *
 */
public class Sftp implements StorageInterface {
	SFTPUtil sftp;
	private String host;		//主机，传入如 10.0.0.1
	private String username;	//登录用户名，如 root
	private String password;	//登录密码
	
	/**
	 * 
	 * @param host 主机，传入如 10.0.0.1
	 * @param username 登录用户名，如 root
	 * @param password 登录密码
	 */
	public Sftp(String host, String username, String password) {
		sftp = new SFTPUtil();
		this.host = host;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 获取当前可操作的SFTP对象
	 * @return {@link ChannelSftp}
	 */
	public ChannelSftp getSftp() throws UnknownHostException{
		if(this.sftp.getSftp() == null || !this.sftp.getSftp().isConnected()){
			//如果没有建立连接，那么重新建立连接
			reconnection();
		}
		
		return this.sftp.getSftp();
	}
	
	/**
	 * sftp重新建立连接。
	 * 第一次用建立连接，或者超时了重新建立连接
	 */
	public void reconnection() throws UnknownHostException{
		sftp.setHost(host);
		sftp.setUsername(username);
		sftp.setPassword(password);
		sftp.connect();
	}
	
	/**
	 * 去掉乱七八糟的字符，将windows的目录转化为linux的目录格式。
	 * 并且如果最前面有/ 会讲其去掉，变为相对路径的形式
	 * @param path
	 * @return linux格式目录形式。会用这个返回值，作为sftp存储路径
	 */
	public String getPath(String path){
		if(path.indexOf(":") > -1){
			path = path.replaceAll(":", "");
		}
		if(path.indexOf("\\") > -1){
			path = path.replaceAll("\\\\", "/");
		}
		if(path.indexOf("//") > -1){
			path = path.replaceAll("//", "/");
		}
		if(path.indexOf("/") == 0){
			path = path.substring(1,path.length());
		}
		return path; 
	}
	
	/**
	 * 创建目录
	 * @param path 要创建的目录。传入如 /user/apple/yunbackups/1.txt 则会创建 user/apple/yunbackups 这个目录
	 */
	public void createPath(String path) throws UnknownHostException{
		int lastIndex = path.lastIndexOf("/");
		if(lastIndex == -1){
			return;
		}
		String dir = path.substring(0, lastIndex);
		
		try {
			getSftp().lstat(dir);
		} catch (SftpException e) {
			if(e.getMessage().replaceAll(" ", "").toLowerCase().equals("nosuchfile")){
				//没有这个目录，需要创建。判断上一级目录是否存在
				createPath(dir);
				try {
					getSftp().mkdir(dir);
				} catch (SftpException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public BaseVO isUsable() throws UnknownHostException{
		//上传一个对象，看是否能上传成功，从而判断当前storage是否可用。
		if(getSftp().isConnected()){
			try {
				createPath("yunbackups/usable.test");
				getSftp().put(new ByteArrayInputStream("ceshiaa".getBytes()), "yunbackups/usable.test");
			} catch (SftpException e) {
				e.printStackTrace();
				return BaseVO.failure("failure");
			}
		}
		
		return BaseVO.success("success");
	}

	public void backups(File file) throws UnknownHostException, IllegalArgumentException {
		if(file == null){
			return;
		}
		String sftpPath = getPath(file.getAbsolutePath());	//上传到sftp的path
		createPath(sftpPath);
		try {
			getSftp().put(file.getAbsolutePath(), sftpPath);
		} catch (SftpException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws SftpException, UnknownHostException, IllegalArgumentException {
		Sftp sftp = new Sftp("112.126.62.205", "root", "123123qQ");
		sftp.isUsable();
		sftp.backups(new File("/Users/apple/Downloads/1.png"));
		
	}
}
