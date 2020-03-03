# yunbackups

云备份软件，英文名 yunbackups ，采用开放式架构，可以有用户自由扩展开发对接自己的存储系统，无任何限制！用户可以将自己电脑、或手机、或服务器等，将任何来源的文件，备份到任何地方！

# 软件成品下载使用体验
下载地址： http://www.yunbackups.com/
云备份软件成品软件，支持Windows 64位、 Mac OS 系统，可以前往下载，双击直接打开使用。

# 云备份软件分两种形式运行
1. 适用于电脑小白，双击即可运行，有可视化界面，可以通过可视化界面进行方便的配置。另外实时运行日志也会在可视化界面中显示。
2. 适用于服务器、或对资源占用要求比较高的场景，提供命令行运行方式，将cpu等占用降到最低！适用于技术人员配置及使用。配置时需要手动打开json格式的配置文件进行配置，另外运行日志也是保存到 /log 目录下，看日志时也需要手动以命令行方式进行查看。
下面就介绍两种运行方式的具体使用步骤：

# 可视化界面方式使用说明
#### 1. 运行状态
![](https://github.com/xnx3/yunbackups/blob/master/images/yunxingzhuangtai.png?raw=true)
软件当前备份的实时状态，是否正在备份、正在备份的目录、当前目录扫描文件的数量及耗时、筛选出要进行备份的文件、数量及耗时、以及筛选完毕后当前备份的进度等。
软件开启后会自动进行备份，便是会看到当前进度状态。 

#### 2.系统参数
![](https://github.com/xnx3/yunbackups/blob/master/images/xitongcanshu.png?raw=true)
对软件进行系统性的设置。具体设置项如下：
1. **自动备份时间间隔**：可以设置自动备份完毕后，等待多长时间开始下一次备份，这样软件后台运行过程中，会自动循环备份最新文件。
1. **最大文件大小**：可以设置可备份文件的最大大小，超过这个大小就不会进行备份。例如设置为10MB，那么会自动备份不超过10MB的文件。超过10MB的不会备份。
1. **要备份的文件后缀名**：设置格式如： jpg,png,xls,txt  每个后缀中间用,分割。如果设置上了，那么备份时只会备份当前所设定的后缀名的文件。如果不设置，留空，那么会备份所有的文件。
1. **不备份文件的后缀名**：设置格式如： exe,bat,txt,xml  每个后缀名中间用,分割。如果设置上，那么备份时，此后缀的文件忽略掉，不会进行自动备份。
1. **是否备份隐藏文件**：如果设置上备份，那么文件属性为隐藏的文件、文件夹也会一起进行备份。

#### 3.备份目录
![](https://github.com/xnx3/yunbackups/blob/master/images/beifenmulu.png?raw=true)
可以设定多个目录进行备份。凡事设定好的目录，都会自动进行备份，保障目录内的文件安全。


#### 4.华为云配置
![](https://github.com/xnx3/yunbackups/blob/master/images/huaweiyunconfig.png?raw=true)
配置华为云的参数，配置好后，将会同步本地设置的备份目录的文件，自动同步到华为云上面进行备份。

## 命令行方式使用说明

命令行方式，适用于对资源占用要求极低的场景，不想让云备份软件占用太大的cpu。命令行方式运行，对CPU占用极低，甚至可以达到忽略的程度。
以命令行方式运行，需要配置 /config/  目录下的配置文件。这里说明一下其中的配置文件：

#### system.config	
系统配置，比如配置没间隔多长时间自动备份、备份什么后缀的文件、是否备份隐藏文件等。
#### backupsPath.config		
备份的目录，要对那几个目录进行自动备份 
#### storage_huaweiobs.config		
备份到哪里。这个配置文件是将本地文件自动备份到华为云上。
#### storage_aliyunoss.config		
备份到阿里云上。
#### storage_ftp.config		
备份到FTP上。通过提供的ftp地址、账号、密码进行备份。

其中  system.config  、 backupsPath.config  这两个是必须配置的，  storage_huaweiobs.config 是备份服务器的配置，如果你要将本地文件自动备份到华为云上，那么就要配置好这个配置文件。如果你要将本地文件自动备份到阿里云上，那么你需要配置 storage_aliyunoss.config 这个配置文件。
备份服务器的配置文件，前缀都是以 storage_ 开头。

这里，选用华为云，将本地文件自动备份到华为云上，配置文件如何配置，做一下说明。
备份到华为云，那么需要配置的文件有这三个：
system.config
backupsPath.config 
storage_huaweiobs.config
配置文件的格式都是 json 格式，以文本方式打开进行编辑

#### system.config 配置说明

````
{
    "fileMaxSize":1048576000,
    "hiddenFileScan":false,
    "ignoreSuffixNameList":[],
    "intervalTime":1,
    "suffixNameList":[]
}
````

fileMaxSize : 可以设置可备份文件的最大大小，超过这个大小就不会进行备份。单位是 B 。
例如你要备份文件大小在1KB以内的文件，这里就可以填写 1024
要备份文件大小在1GB以内的文件，可填写1073741824 （1B * 1024 = 1KB * 1024 = 1MB  * 1024 = 1GB ）

hiddenFileScan : 是否备份隐藏文件，如果为 true ，那么就是备份文件属性为隐藏的文件、文件夹也会一起进行备份。
如果为false，便是不备份隐藏的文件或文件夹。遇到隐藏文件自动忽略，不进行备份。

ignoreSuffixNameList : 忽略的文件后缀，不备份文件的后缀名。设置格式如： 

````
"ignoreSuffixNameList":[
	"exe",
	"bat",
	"dll"
]
````

每个后缀名中间用,分割。如果设置上，那么备份时，此后缀的文件忽略掉，不会进行自动备份。

intervalTime : 自动备份的时间间隔，自动备份完毕后，等待多长时间开始下一次备份，这样软件后台运行过程中，会自动循环备份最新文件。
单位是分钟。比如设置 1 则是进行备份完毕，等待1分钟后，在此开始下一次扫描备份。
比如要1小时备份一次，这里就可以设置成 60
suffixNameList : 要备份的文件后缀名，设置格式同上面的 ignoreSuffixNameList  ，例如： 
````
"suffixNameList":[
		"txt",
		"doc",
		"xls"
	]
````
每个后缀中间用,分割。如果设置上了，那么备份时只会备份当前所设定的后缀名的文件。如果不设置，留空，那么会备份所有的文件。


#### backupsPath.config 配置说明
````
[
    {
        "lasttime":1571128173000,
        "path":"/Users/apple/git"
    },
    {
        "lasttime":1571105410000,
        "path":"/Users/apple/Downloads"
    },
    {
        "lasttime":1567769785000,
        "path":"/Users/apple/Desktop"
    }
]
````
这里可以有多个备份目录，其中的每一个
````
{
	"lasttime":1571128173000,
	"path":"/Users/apple/git"
}
````
便是一个备份目录。
lasttime : 最后一次备份此目录的时间，13位时间戳。如果是刚开始使用时进行配置，这里填0即可。软件运行时便会将这个目录下所有的文件都进行备份。
path : 备份的目录，一定填写要备份目录的绝对路径。
正常运行过程中，系统会自动将最新同步的时间进行自动保存，也就是 backupsPath.config 这个文件会不断自动进行更新，没间隔几秒就会更新一次，自动更新后，为了避免path出现中文乱码，整个 path 都是以 utf8 编码的方式进行保存。
也就是path，无论是utf8转码的还是正常无任何转码的，云备份软件都能正常识别出路径。


#### storage_huaweiobs.config 配置说明
````
{
    "accessKeyId":"4PBSAVC........",
    "bucketName":"yunbackups",
    "endpoint":"obs.cn-north-4.myhuaweicloud.com",
    "secretAccessKey":"d8GbpsKDy7QG5L........."
}
````


# 代码的结构
### com.xnx3.yunbackups.core 
自动备份的核心代码所在，拥有无限扩展的可能。

1. 可扩展存储方式 ，用户可以根据自己喜好，来实现相应接口方法，定做自己的备份服务器，而不在仅仅只是局限于云服务、FTP等方式。
2. 可扩展其他的存储方式进行备份。 com.xnx3.yunbackups.core.backups.interfaces.StorageInterface
可扩展进度监听，用户可以根据自己需求，监听当前进度，用来进行日志记录，或实时显示。 com.xnx3.yunbackups.core.backups.interfaces.ProgressListener
3. 可扩展异常监听。备份过程中出现什么异常情况，比如备份服务器连接不上无法通信了、或者断网了等，都可以通过异常监听进行捕获，自由定制。 com.xnx3.yunbackups.core.backups.interfaces.ExceptionListener

### com.xnx3.yunbackups.storage 
此包下，用来实现存储的方式。比如FTP存储、阿里云OSS存储、华为云OBS存储等。可以任意扩展。

1. com.xnx3.yunbackups.storage.HuaweiyunOBS 以华为云OBS作为备份存储方式
2. com.xnx3.yunbackups.storage.AliyunOSS 以阿里云OSS作为备份存储方式
3. com.xnx3.yunbackups.storage.FTP 以FTP作为备份存储对接方式 

### com.xnx3.yunbackups.commandLineApp 
以命令行方式运行云备份软件，可用于linux服务器，或对资源占用要求低的场景。 此种方式运行，实时日志在当前文件夹下的log文件夹中查看。
1. progress.log  实时进度日志，记录最后一次的日志记录，也就是扩展了进度监听接口 com.xnx3.yunbackups.core.backups.interfaces.ProgressListener 所记录的日志 
2. exception.log  记录异常日志，也就是扩展了异常监听接口 com.xnx3.yunbackups.core.backups.interfaces.ExceptionListener 所记录的日志。如果没有异常，则不会生成这个文件。 
### com.xnx3.yunbackups.visualApp 
可视化界面方式运行云备份软件。以用户比较接受比较友好的可视化界面的方式来运行，比如配置备份文件最大的大小、是否备份隐藏文件、以及配置备份的目录等，都是在可视化状态下进行设置。
 另外 com.xnx3.yunbackups.commandLineApp 命令行运行的前提，也是要运行可视化界面的方式，先配置好，再进行命令行方式运行。

# 如果你现有的项目想增加此功能，该项目可以完美嵌入您的项目中去！您只需吧jar包导入，一二十行代码，即可变成你自己项目中的功能！

实现方式参考：  https://gitee.com/leimingyun/yunbackups_demo
