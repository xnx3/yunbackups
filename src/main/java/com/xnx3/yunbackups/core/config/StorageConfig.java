package com.xnx3.yunbackups.core.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.xnx3.FileUtil;
import com.xnx3.json.JSONUtil;
import com.xnx3.yunbackups.core.Global;
import com.xnx3.yunbackups.core.util.StorateUtil;

import net.sf.json.JSONObject;

/**
 * 存储方式相关的配置，如华为云存储、sftp存储等，其相关参数的配置
 * 存储信息都保存在Map<String, String> 中，其中 key=storage 为当前使用存储方式的类型，传入如 huaweiobs 、 sftp 等。
 * @author 管雷鸣
 */
public class StorageConfig {
	/**
	 * 保存配置信息到本地
	 * @param map 配置信息
	 */
	public static void save(Map<String, String> map){
		JSONObject json = JSONObject.fromObject(map);
		FileUtil.write(Global.CONFIG_PATH + StorateUtil.getStorageConfigFileName(), json.toString());
	}
	
	/**
	 * 读取配置信息
	 * @return 配置信息都在map中
	 */
	public static Map<String, String> read(){
		Map<String, String> map = new HashMap<String, String>();
		
		String content = FileUtil.read(Global.CONFIG_PATH + StorateUtil.getStorageConfigFileName());
		if(content == null || content.length() == 0){
			//第一次用，还没有配置文件
		}else{
			//将文件信息转化为json
			try {
				JSONObject json = JSONObject.fromObject(content);
				Iterator<String> iterator = json.keys();
		        while (iterator.hasNext()) {
		            String key = iterator.next();
		            map.put(key, JSONUtil.getString(json, key));
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return map;
	}
	
}
