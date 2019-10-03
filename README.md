# yunbackups
Automatically backup files to huawei cloud, ali cloud, or self-built server, FTP, etc., to ensure data security

## 包结构
````
com.xnx3.yunbackups.core	核心代码，如果你想定制轻量级的，备份方式及进度监听方式自己定义，那么只需要这一个就可以！
com.xnx3.yunbackups.defaultStorage	默认带的备份存储方式，备份到哪里
com.xnx3.yunbackups.defaultProgressListener	默认带的进度监听，自己实现时可以参考
com.xnx3.yunbackups.computerDesktopApp	电脑桌面应用的实现
````
	