package com.xnx3.yunbackups.core.util;

import com.xnx3.StringUtil;
import com.xnx3.yunbackups.core.Global;
import com.xnx3.yunbackups.core.backups.BackupsThread;
import com.xnx3.yunbackups.core.config.StorageConfig;
import com.xnx3.yunbackups.storage.HuaweiyunOBS;
import com.xnx3.yunbackups.storage.Sftp;

/**
 * 备份
 */
public class BackupsUtil {
	
	/**
	 * 生成要运行的 {@link BackupsThread} 备份进程。
	 * @return 如果返回null，则是不能备份，有某项未填写完。返回 {@link BackupsThread} 则是可以直接拿来进行开启备份
	 */
	public static BackupsThread generateBackupsThread(){
		//备份的主线程
		BackupsThread backupsThread = null;
		
		//判断一下使用的是哪种备份方式
		if(Global.system.getStorage().equals("sftp")){
			//判断一下是否设置过备份服务器相关参数，若已经设置了，那么可运行
			String host = StorateUtil.getStorageConfigValue(Global.storageConfigMap, "host");
			String username = StorateUtil.getStorageConfigValue(Global.storageConfigMap, "username");
			String password = StorateUtil.getStorageConfigValue(Global.storageConfigMap, "password");
			if(host.equals("") || username.equals("") || password.equals("")){
				return null;
			}
			Sftp sftp = new Sftp(host, username, password);
			backupsThread = new BackupsThread(sftp);
		}else{
			//默认是华为云的，适配之前的版本
			
			//判断一下是否设置过备份服务器相关参数，若已经设置了，那么可运行
			String accessKeyId = StorateUtil.getStorageConfigValue(Global.storageConfigMap, "accessKeyId");
			String secretAccessKey = StorateUtil.getStorageConfigValue(Global.storageConfigMap, "secretAccessKey");
			String endpoint = StorateUtil.getStorageConfigValue(Global.storageConfigMap, "endpoint");
			String bucketName = StorateUtil.getStorageConfigValue(Global.storageConfigMap, "bucketName");
			if(accessKeyId.equals("") || secretAccessKey.equals("") || endpoint.equals("") || bucketName.equals("")){
				return null;
			}
			HuaweiyunOBS obs = new HuaweiyunOBS(accessKeyId, secretAccessKey, endpoint, bucketName);
			backupsThread = new BackupsThread(obs);
		}
		
		return backupsThread;
	}
	
}
