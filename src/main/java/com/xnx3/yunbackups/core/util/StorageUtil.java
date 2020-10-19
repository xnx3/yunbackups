package com.xnx3.yunbackups.core.util;

import java.util.Map;

import com.xnx3.yunbackups.core.Global;

/**
 * 存储方式的工具类
 * @author 管雷鸣
 *
 */
public class StorageUtil {
	
	/**
	 * 从map中，取storage配置文件的名字。如果取不到，返回 "storage_.config"
	 * @param map 当前存储方式的配置信息
	 * @return 如果取不到，返回 "" 空字符串。如果取到，返回如 storage_sftp.config 、 storage_huaweiobs.config 等
	 */
	public static String getStorageConfigFileName(){
		return "storage_"+Global.system.getStorage()+".config";
	}
	
	/**
	 * 获取 map 配置的内容
	 * @param map 当前存储方式的配置信息
	 * @param key 要取的配置名字
	 * @return 如果为null，则返回空字符串
	 */
	public static String getStorageConfigValue(Map<String, String> map, String key){
		if(map == null){
			return "";
		}
		if(map.get(key) == null){
			return "";
		}
		return map.get(key).toString();
	}
}
