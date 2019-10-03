package com.xnx3.yunbackups.defaultStorage;

import java.io.File;
import java.net.UnknownHostException;

import com.xnx3.yunbackups.core.backups.interfaces.StorageInterface;
import com.xnx3.yunbackups.core.util.SystemUtil;
import com.xnx3.yunbackups.defaultStorage.huawei.OBSHandler;

/**
 * 附件上传之 华为云 OBS 
 * @author 管雷鸣
 *
 */
public class HuaweiyunOBS implements StorageInterface{
	private static OBSHandler obsHandler;	//禁用，通过getObsUtil() 获取
	private static String obsBucketName; // 当前进行操作桶的名称
	
	// 创建 accesskeyid 网址 https://console.huaweicloud.com/iam/?region=cn-north-4&locale=zh-cn#/iam/agencies  创建委托 便是
	public static String accessKeyId = "SRB26OK09GG9O23LUCYG";
	public static String accessKeySecret = "FnSy0oqrgBWcdCf221kcScD5XBolH038hw7kgIPN";
	
	public static String obsEndpoint = "obs.cn-north-1.myhuaweicloud.com";
	public static String bucketName = "testbuckup"; 
	
	/**
	 * 获取华为云OBS的操作类
	 * @author 李鑫
	 * @return 当前华为云OBS的操作类型
	 */
	public static OBSHandler getObsHander() {
		
		if(obsHandler == null) {
			obsHandler = new OBSHandler(accessKeyId,accessKeySecret,obsEndpoint);
			// 如果设置过CDN的路径测设置为CDN路径，没有设置则为桶原生的访问路径
			//obsHandler.setUrlForCDN(Global.get("ATTACHMENT_FILE_URL"));
			// 在数据库中读取进行操作的桶的明恒
			obsHandler.setObsBucketName(bucketName);
			// 对桶名称进行当前类内缓存
			obsBucketName = obsHandler.getObsBucketName();
		}
		return obsHandler;
	}
	
	/**
	 * 从本地要保存的文件中，提取要保存到备份服务器上的路径
	 * @param file 本地要保存的文件
	 * @return 保存到备份服务器上的文件路径
	 */
	private String getSavePath(File file){
		String path = file.getPath();
		if(SystemUtil.isWindowsOS()){
			//是windows系统，那么要吧 C:\\a 这样的盘符去掉,将路径格式转化为linux的路径格式
			path = path.replaceAll("\\:\\\\", "/").replaceAll("\\\\", "/");
		}
		return path;
	} 
	
	
	public void backups (File file) throws java.net.UnknownHostException, java.lang.IllegalArgumentException{
		try {
			getObsHander().putLocalFile(obsBucketName, getSavePath(file), file);
		}catch (com.obs.services.exception.ObsException obsE){
			String eStr = obsE.getCause().toString().toLowerCase();
			if(eStr.indexOf("java.net.unknownhostexception") > -1){
				throw new java.net.UnknownHostException();
			}else{
				obsE.printStackTrace();
			}
		}
		
	}

	
}
