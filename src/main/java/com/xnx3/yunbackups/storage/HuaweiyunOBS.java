package com.xnx3.yunbackups.storage;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import com.obs.services.exception.ObsException;
import com.xnx3.BaseVO;
import com.xnx3.yunbackups.core.backups.interfaces.StorageInterface;
import com.xnx3.yunbackups.core.util.SystemUtil;
import com.xnx3.yunbackups.storage.huawei.OBSHandler;

import net.sf.json.JSONObject;

/**
 * 通过华为云 OBS备份同步 
 * @author 管雷鸣
 *
 */
public class HuaweiyunOBS implements StorageInterface{
	private OBSHandler obsHandler;	//禁用，通过getObsUtil() 获取
	
	// 创建 accesskeyid 网址 https://console.huaweicloud.com/iam/?region=cn-north-4&locale=zh-cn#/iam/agencies  创建委托 便是
	public String accessKeyId;
	public String accessKeySecret;
	public String obsEndpoint;	//格式如 obs.cn-north-1.myhuaweicloud.com
	public String bucketName; 
	
	/**
	 * 创建华为云存储
	 * @param accessKeyId   https://console.huaweicloud.com/iam/?region=cn-east-2&locale=zh-cn#/iam/users    进入安全设置，新增访问密钥，得到  Access Key Id 、 Secret Access Key
	 * @param accessKeySecret
	 * @param obsEndpoint 所属区域
	 * @param bucketName 桶的名字
	 */
	public HuaweiyunOBS(String accessKeyId, String accessKeySecret, String obsEndpoint, String bucketName) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.obsEndpoint = obsEndpoint;
		this.bucketName = bucketName;
	}
	
	/**
	 * 获取华为云OBS的操作类
	 * @author 李鑫
	 * @return 当前华为云OBS的操作类型
	 */
	public OBSHandler getObsHander() {
		
		if(obsHandler == null) {
			obsHandler = new OBSHandler(accessKeyId,accessKeySecret,obsEndpoint);
			// 如果设置过CDN的路径测设置为CDN路径，没有设置则为桶原生的访问路径
			//obsHandler.setUrlForCDN(Global.get("ATTACHMENT_FILE_URL"));
			// 在数据库中读取进行操作的桶的明恒
			obsHandler.setObsBucketName(bucketName);
			// 对桶名称进行当前类内缓存
			bucketName = obsHandler.getObsBucketName();
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
			getObsHander().putLocalFile(bucketName, getSavePath(file), file);
		}catch (com.obs.services.exception.ObsException obsE){
			String eStr = obsE.getCause().toString().toLowerCase();
			if(eStr.indexOf("java.net.unknownhostexception") > -1){
				throw new java.net.UnknownHostException();
			}else{
				obsE.printStackTrace();
			}
		}
		
	}

	public BaseVO isUsable() throws UnknownHostException {
		BaseVO vo = new BaseVO();
		
		try {
			//上传一个对象，看是否能上传成功，从而判断当前storage是否可用。
			getObsHander().putStringFile(bucketName, "yunbackups/usable.test", "this is test file.", "UTF-8");
			vo.setBaseVO(BaseVO.SUCCESS, "success");
		} catch (ObsException e) {
			Throwable throwable = e.getCause();
			if(throwable != null) {
				String throwString = throwable.getMessage().toLowerCase();	//全部小写
				if(throwString.indexOf("java.net.unknownhostexception") > -1){
					java.net.UnknownHostException host = (UnknownHostException) throwable;
					throw host;
				}
			}else{
				String code = e.getErrorCode();
				if(code != null){
					if(code.equalsIgnoreCase("InvalidAccessKeyId")){
						//Secret Access Key 错误
						vo.setBaseVO(BaseVO.FAILURE, "Access Key Id 错误，不存在");
						return vo;
					}
					if(code.equalsIgnoreCase("SignatureDoesNotMatch")){
						//Secret Access Key 错误
						vo.setBaseVO(BaseVO.FAILURE, "Secret Access Key 错误");
						return vo;
					}
				}
				
				String responseStatus = e.getResponseStatus();
				if(responseStatus != null && responseStatus.contentEquals("Not Found")){
					vo.setBaseVO(BaseVO.FAILURE, "Bucket Name 不存在");
					return vo;
				}
			}
			
			vo.setBaseVO(BaseVO.FAILURE, e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			vo.setBaseVO(BaseVO.FAILURE, e.getMessage());
			e.printStackTrace();
		}
		
		return vo;
	}

	
}
