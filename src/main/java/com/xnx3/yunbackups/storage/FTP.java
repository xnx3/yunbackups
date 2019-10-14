package com.xnx3.yunbackups.storage;

import java.io.File;
import java.net.UnknownHostException;
import com.xnx3.BaseVO;
import com.xnx3.yunbackups.core.backups.interfaces.StorageInterface;

/**
 * 通过FTP备份同步
 * @author 管雷鸣
 *
 */
public class FTP implements StorageInterface {

	public BaseVO isUsable() {
		// TODO Auto-generated method stub
		return null;
	}

	public void backups(File file) throws UnknownHostException, IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

}
