package com.xnx3.yunbackups.core.backups.interfaces;

import java.io.File;
import com.xnx3.BaseVO;

/**
 * 存储相关的接口
 * @author 管雷鸣
 *
 */
public interface StorageInterface {
	
	/**
	 * 判断当前备份是否是可用的
	 * @return 根据 getResult() 结果来判断当前备份的服务器那边，通信是否可用。
	 * 		<ul>
	 * 			<li>result = BaseVO.SUCCESS ，可用，正常通信，可以正常进行备份操作</li>
	 * 			<li>result = BaseVO.FAILURE ，不可用，通信失败，不能进行备份！这是 getInfo() 可获取失败的具体原因。</li>
	 * 		</ul>
	 */
	public BaseVO isUsable();
	
	/**
	 * 传入一个文件，进行备份操作
	 * @param file 本地的文件，要进行备份的文件
	 * @throws java.net.UnknownHostException 断网情况下会抛出此异常
	 * @throws java.lang.IllegalArgumentException 有的文件执行备份时可能抛出此异常，导致此文件备份失败
	 */
	public void backups(File file) throws java.net.UnknownHostException, java.lang.IllegalArgumentException; 
	
}
