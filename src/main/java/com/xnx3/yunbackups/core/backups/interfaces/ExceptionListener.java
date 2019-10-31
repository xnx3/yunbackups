package com.xnx3.yunbackups.core.backups.interfaces;

/**
 * 备份过程中的异常监听
 * @author 管雷鸣
 *
 */
public interface ExceptionListener {
	
	/**
	 * 备份文件时，断网的异常监听。当断网了，触发此方法
	 * @param e 捕获的异常
	 * @return 延迟等待的时间。出现断网异常后，当前文件肯定备份失败了，等待此处返回时间后，再次进行备份此文件。这里时间单位是 ms
	 */
	public int unknownHostException(java.net.UnknownHostException e);
	
	/**
	 * 将文件备份到服务器时，如果服务器通信失败，不可用时，触发此接口
	 * @param info 当通信失败不可用时，传入的原因，为什么通信失败不可用
	 */
	public void serviceDisabled(String info);
	
	/**
	 * 当点击运行按钮进行运行时，初始化判断设置的一些参数，如果有异常，设置不对的地方，就会终止执行，触发此方法
	 * @param type 初始化时，出现错误的地方、类型。包含以下：
	 * 			<ul>
	 * 				<li>1:要备份的目录为空，backupsPath 为空，用户还没有设置要备份哪个目录</li>
	 * 				<li>2:开启失败，系统设置未读取到，您可以重启软件进行尝试</li>
	 * 			</ul>
	 * @param defaultInfo 默认的错误提示信息，图省事，可以直接吧这个信息展示给用户看。
	 */
	public void runInitializeFailure(int type, String defaultInfo);
}
