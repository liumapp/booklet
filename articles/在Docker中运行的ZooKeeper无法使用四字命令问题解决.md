> 前阵子写过一篇文章，用来记录如何在docker中启动ZooKeeper的单机或者集群节点，最近收到一个issue，大意是说项目启动后，测试ZK的四字命令无效，这篇文章记录一下解决办法

# ZK四字命令

四字命令其实就是通过类似  ``` echo stat | nc 127.0.0.1 2181   ``` 这样的指令，来与zk节点交互，获取或者设置数据的指令

zk四字命令列表：

| 命令 | 示范 | 解释 |
| ---- |  ---- | ---- |
| conf | echo conf \| nc 127.0.0.1 2181 | 输出相关服务配置的详细信息。比如端口、zk数据及日志配置路径、最大连接数，session超时时间、serverId等 |
| cons | echo cons \| nc 127.0.0.1 2181 | 列出所有连接到这台服务器的客户端连接/会话的详细信息 包括“接受/发送”的包数量、session id 、操作延迟、最后的操作执行等信息 |
| crst | echo crst \| nc 127.0.0.1 2181 | 重置当前这台服务器所有连接/会话的统计信息 | 
| dump | echo dump \| nc 127.0.0.1 2181 | 列出未经处理的会话和临时节点（只在leader上有效）|
| envi | echo envi \| nc 127.0.0.1 2181 | 输出关于服务器的环境详细信息（不同于conf命令），比如host.name、java.version、java.home、user.dir=/data/zookeeper-3.4.6/bin之类信息 |
| ruok |  echo ruok \| nc 127.0.0.1 2181 | 测试服务是否处于正确运行状态。如果正常返回"imok"，否则返回空 |
| srst | echo srst \| nc 127.0.0.1 2181 | 重置服务器的统计信息 |
| srvr | echo srvr \| nc 127.0.0.1 2181 | (New in 3.3.0)输出服务器的详细信息。zk版本、接收/发送包数量、连接数、模式（leader/follower）、节点总数 |
| stat | echo stat \| nc 127.0.0.1 2181 | 输出服务器的详细信息：接收/发送包数量、连接数、模式（leader/follower）、节点总数、延迟。 所有客户端的列表 |
| wchs | echo wchs \| nc 127.0.0.1 2181 | 列出服务器watches的简洁信息：连接总数、watching节点总数和watches总数  |
| wchc | echo wchc \| nc 127.0.0.1 2181 | 通过session分组，列出watch的所有节点，它的输出是一个与 watch 相关的会话的节点列表。如果watches数量很大的话，将会产生很大的开销，会影响性能，小心使用 |
| wchp | echo wchp \| nc 127.0.0.1 2181 | 通过路径分组，列出所有的 watch 的session id信息。它输出一个与 session 相关的路径。如果watches数量很大的话，将会产生很大的开销，会影响性能，小心使用 | 
| mntr | echo mntr \| nc 127.0.0.1 2181 | 列出集群的健康状态。包括“接受/发送”的包数量、操作延迟、当前服务模式（leader/follower）、节点总数、watch总数、临时节点总数 |

# Docker容器中的ZooKeeper不响应四字命令

随着ZooKeeper的官方Docker镜像推出，大家也都纷纷按照官方介绍的那样，用Docker来运行ZooKeeper，开始用着很爽，直到有人发现四字命令出问题：“输了命令没有响应”

百度谷歌各家论坛也没有找到解决办法，直到认真翻了一遍ZooKeeper的官方文档：[zookeeper-doc](https://docs.docker.com/samples/library/zookeeper/)

才发现，使用Docker镜像启动的ZK容器，默认是没有配置四字命令白名单列表的，说白了，你只有在这个docker容器内部才能使用四字命令，而到了宿主机上，则会被禁止掉，所以就会出现无响应这种问题

那么解决办法也很简单，我们可以利用volumes指令，在宿主机上配置好zk镜像，再映射到容器中来启动zk即可解决

具体操作办法：

* 编写配置文件zoo.cfg，并保存在./config目录下，内容如下所示：

	````properties
	dataDir=/data
	dataLogDir=/datalog
	tickTime=2000
	initLimit=5
	syncLimit=2
	autopurge.snapRetainCount=3
	autopurge.purgeInterval=0
	maxClientCnxns=60
	standaloneEnabled=true
	admin.enableServer=true
	server.1=localhost:2888:3888;2181
	4lw.commands.whitelist=*
	````

* 编写docker-compose，配置volumes，添加以下内容：

````yaml
volumes:
      - ./config:/conf
````

* 启动测试，解决

如果这篇文章没有帮助您解决问题的话，欢迎来：[docker-compose-zookeeper](https://github.com/liumapp/docker-compose-zookeeper) 发布issue
	



