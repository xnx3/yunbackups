# yunbackups
Automatically backup files to huawei cloud, ali cloud, or self-built server, FTP, etc., to ensure data security


## 结构组成
com.xnx3.yunbackups包:
core			核心代码
storage			备份存储方式，如FTP、阿里云、华为云等，此处可自由扩展！
commandLineApp	以命令行方式运行
visualApp		以可视化界面的方式运行（如果只是以命令行方式运行，这个包完全可以删除掉）

## 配置说明
使用可视化界面 (visualApp) 填好的配置，可以直接用 命令行方式 (commandLineApp) 运行，配置文件都是通用的，只不过是两种运行形态。