package com.xnx3.yunbackups.storage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.UnknownHostException;
import java.util.Properties;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.xnx3.BaseVO;
import com.xnx3.yunbackups.core.backups.interfaces.StorageInterface;

/**
 * 通过SFTP备份同步
 * @author 管雷鸣
 *
 */
public class Sftp implements StorageInterface {
	private String host;		//主机，传入如 10.0.0.1
	private String username;	//登录用户名，如 root
	private String password;	//登录密码
	
	private int port = 22;
    private ChannelSftp sftp = null;
    
	/**
	 * 
	 * @param host 主机，传入如 10.0.0.1
	 * @param username 登录用户名，如 root
	 * @param password 登录密码
	 */
	public Sftp(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 获取当前可操作的SFTP对象
	 * @return {@link ChannelSftp}
	 * @throws JSchException 
	 */
	public ChannelSftp getSftp() throws UnknownHostException, JSchException{
		if(this.sftp == null || !this.sftp.isConnected()){
			//如果没有建立连接，那么重新建立连接
			reconnection();
		}
		
		return this.sftp;
	}
	
	/**
	 * sftp重新建立连接。
	 * 第一次用建立连接，或者超时了重新建立连接
	 * @throws JSchException 
	 */
	public void reconnection() throws UnknownHostException, JSchException{
        JSch jsch = new JSch();
        jsch.getSession(username, host, port);
        Session sshSession = jsch.getSession(username, host, port);
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        sftp = (ChannelSftp) channel;
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
	 * @throws JSchException 
	 */
	public void createPath(String path) throws UnknownHostException, JSchException{
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
		try {
			if(getSftp().isConnected()){
				try {
					createPath("yunbackups/usable.test");
					getSftp().put(new ByteArrayInputStream("ceshiaa".getBytes()), "yunbackups/usable.test");
				} catch (SftpException e) {
					e.printStackTrace();
					return BaseVO.failure("failure");
				}
			}
		} catch (JSchException e) {
			e.printStackTrace();
			//将其转化为小写，去空格
			String message = e.getMessage().replaceAll(" ", "").toLowerCase(); 
			System.out.println(message);
			if(message.equals("authfail")){
				return BaseVO.failure("Auth fail");
			}else if (message.equals("java.net.socketexception:networkisunreachable(connectfailed)")) {
				throw new java.net.UnknownHostException(this.host);
			}else if(message.equals("java.net.connectexception:connectionrefused(connectionrefused)")){
				throw new java.net.UnknownHostException(this.host);
			}
		}
		
		return BaseVO.success("success");
	}

	public void backups(File file) throws UnknownHostException, IllegalArgumentException {
		if(file == null){
			return;
		}
		String sftpPath = getPath(file.getAbsolutePath());	//上传到sftp的path
		try {
			createPath(sftpPath);
		} catch (JSchException e1) {
			e1.printStackTrace();
		}
		try {
			getSftp().put(file.getAbsolutePath(), sftpPath);
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SftpException, UnknownHostException, IllegalArgumentException {
		Sftp sftp = new Sftp("192.168.1.1", "root", "123123qQ");
		System.out.println(sftp.isUsable().getResult());
//		sftp.backups(new File("/Users/apple/Downloads/1.png"));
		
	}
}
