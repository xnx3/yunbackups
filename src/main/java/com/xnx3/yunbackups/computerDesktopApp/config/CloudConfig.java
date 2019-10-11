package com.xnx3.yunbackups.computerDesktopApp.config;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.FileUtil;
import com.xnx3.StringUtil;
import com.xnx3.json.JSONUtil;
import com.xnx3.yunbackups.computerDesktopApp.bean.CloudConfigBean;
import com.xnx3.yunbackups.core.Global;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 软件配置
 * @author 管雷鸣
 *
 */
public class CloudConfig {
	/**
	 * 保存配置信息到本地
	 * @param bean {@link CloudConfigBean}配置信息
	 */
	public static void save(CloudConfigBean bean){
		JSONObject json = JSONObject.fromObject(bean);
		java.lang.System.out.println(json.toString());
		FileUtil.write(Global.CONFIG_PATH+"cloudConfig.config", StringUtil.StringToUtf8(json.toString()));
	}
	
	/**
	 * 读取登陆信息
	 */
	public static CloudConfigBean read(){
		CloudConfigBean bean = new CloudConfigBean();
		
		String content = FileUtil.read(Global.CONFIG_PATH+"cloudConfig.config");
		content = StringUtil.utf8ToString(content);
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
