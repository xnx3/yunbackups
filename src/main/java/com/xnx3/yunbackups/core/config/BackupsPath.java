package com.xnx3.yunbackups.core.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xnx3.FileUtil;
import com.xnx3.StringUtil;
import com.xnx3.json.JSONUtil;
import com.xnx3.yunbackups.core.Global;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 备份的文件夹
 * @author 管雷鸣
 *
 */
public class BackupsPath {
	/**
	 * 保存配置信息到本地
	 * @param loginInfo 要保存的一些登陆信息
	 */
	public static void save(Map<String, com.xnx3.yunbackups.core.bean.BackupsPath> map){
		//将map转化为list
		List<com.xnx3.yunbackups.core.bean.BackupsPath> list = new ArrayList<com.xnx3.yunbackups.core.bean.BackupsPath>();
		for(Map.Entry<String, com.xnx3.yunbackups.core.bean.BackupsPath> entry : map.entrySet()){
			list.add(entry.getValue());
		}
		JSONArray json = JSONArray.fromObject(list);
		FileUtil.write(Global.CONFIG_PATH+"backupsPath.config", StringUtil.StringToUtf8(json.toString()));
	}
	
	/**
	 * 读取
	 */
	public static Map<String, com.xnx3.yunbackups.core.bean.BackupsPath> read(){
		Map<String, com.xnx3.yunbackups.core.bean.BackupsPath> map = new HashMap<String, com.xnx3.yunbackups.core.bean.BackupsPath>();
		
		String content = FileUtil.read(Global.CONFIG_PATH+"backupsPath.config");
		content = StringUtil.utf8ToString(content);
		if(content == null || content.length() == 0){
			//第一次用，还没有配置文件
		}else{
			//将文件信息转化为json
			try {
				JSONArray jsonArray = JSONArray.fromObject(content);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					com.xnx3.yunbackups.core.bean.BackupsPath backupsPath = new com.xnx3.yunbackups.core.bean.BackupsPath();
					backupsPath.setPath(JSONUtil.getString(json, "path"));
					try {
						backupsPath.setLasttime(Long.parseLong(JSONUtil.getString(json, "lasttime")));
					} catch (Exception e) {
					}
					
					map.put(backupsPath.getPath(), backupsPath);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return map;
	}
}
