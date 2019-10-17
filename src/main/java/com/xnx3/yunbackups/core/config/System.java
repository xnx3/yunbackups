package com.xnx3.yunbackups.core.config;

import java.util.ArrayList;
import java.util.List;
import com.xnx3.FileUtil;
import com.xnx3.json.JSONUtil;
import com.xnx3.yunbackups.core.Global;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 软件配置
 * @author 管雷鸣
 *
 */
public class System {
	/**
	 * 保存配置信息到本地
	 * @param loginInfo 要保存的一些登陆信息
	 */
	public static void save(com.xnx3.yunbackups.core.bean.System system){
		JSONObject json = JSONObject.fromObject(system);
		FileUtil.write(Global.CONFIG_PATH+"system.config", json.toString());
	}
	
	/**
	 * 读取登陆信息
	 */
	public static com.xnx3.yunbackups.core.bean.System read(){
		com.xnx3.yunbackups.core.bean.System system = new com.xnx3.yunbackups.core.bean.System();
		
		String content = FileUtil.read(Global.CONFIG_PATH+"system.config");
		if(content == null || content.length() == 0){
			//第一次用，还没有配置文件
		}else{
			//将文件信息转化为json
			try {
				JSONObject json = JSONObject.fromObject(content);
				
				long size = 0;	
				try {
					size = Long.parseLong(JSONUtil.getString(json, "fileMaxSize"));
				} catch (Exception e) {
					size = com.xnx3.yunbackups.core.bean.System.DEFAULT_FILE_MAX_SIZE;
					e.printStackTrace();
				}
				system.setFileMaxSize(size);
				
				system.setIntervalTime(JSONUtil.getInt(json, "intervalTime"));
				
				if(json.get("suffixNameList") != null){
					List<String> list = new ArrayList<String>();
					JSONArray jsonArray = json.getJSONArray("suffixNameList");
					for (int i = 0; i < jsonArray.size(); i++) {
						String suffix = jsonArray.getString(i);
						if(suffix.length() > 0){
							list.add(suffix);
						}
					}
					system.setSuffixNameList(list);
				}
				
				if(json.get("ignoreSuffixNameList") != null){
					List<String> list = new ArrayList<String>();
					JSONArray jsonArray = json.getJSONArray("ignoreSuffixNameList");
					for (int i = 0; i < jsonArray.size(); i++) {
						String suffix = jsonArray.getString(i);
						if(suffix.length() > 0){
							list.add(suffix);
						}
					}
					system.setIgnoreSuffixNameList(list);
				}
				
				if(json.get("hiddenFileScan") != null){
					system.setHiddenFileScan(JSONUtil.getString(json, "hiddenFileScan").equalsIgnoreCase("true"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return system;
	}
	
	
}
