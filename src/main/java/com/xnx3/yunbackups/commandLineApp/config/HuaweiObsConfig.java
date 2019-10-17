package com.xnx3.yunbackups.commandLineApp.config;

import com.xnx3.FileUtil;
import com.xnx3.json.JSONUtil;
import com.xnx3.yunbackups.commandLineApp.bean.CloudConfigBean;
import com.xnx3.yunbackups.core.Global;
import net.sf.json.JSONObject;

/**
 * 华为云配置
 * @author 管雷鸣
 *
 */
public class HuaweiObsConfig {
	//配置文件的名字
	public static final String FILE_NAME = "storage_huaweiobs.config";
	
	/**
	 * 保存配置信息到本地
	 * @param bean {@link CloudConfigBean}配置信息
	 */
	public static void save(CloudConfigBean bean){
		JSONObject json = JSONObject.fromObject(bean);
		FileUtil.write(Global.CONFIG_PATH+FILE_NAME, json.toString());
	}
	
	/**
	 * 读取登陆信息
	 */
	public static CloudConfigBean read(){
		CloudConfigBean bean = new CloudConfigBean();
		String content = FileUtil.read(Global.CONFIG_PATH+FILE_NAME);
		if(content == null || content.length() == 0){
			//第一次用，还没有配置文件
		}else{
			//将文件信息转化为json
			try {
				JSONObject json = JSONObject.fromObject(content);
				bean.setAccessKeyId(JSONUtil.getString(json, "accessKeyId"));
				bean.setSecretAccessKey(JSONUtil.getString(json, "secretAccessKey"));
				bean.setBucketName(JSONUtil.getString(json, "bucketName"));
				bean.setEndpoint(JSONUtil.getString(json, "endpoint"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return bean;
	}
	
}
