# Redis常用操作命令

> 直接使用Console操作Redis的常用命令记录

### 远程连接Redis服务

* command: ```redis-cli -h {redis_host} -p {redis_port} -a {redis_password}```

* redis_host: 远程redis服务地址

* redis_port: 远程redis监听端口号

* redis_password: 远程redis连接密码（没有密码则不需要传）

这条命令除了连接远程服务器上的redis之外，还可以用来在本地使用：比如我们可以用它来访问部署在docker等容器中的redis服务

### Redis可视化管理工具

推荐： https://github.com/caoxinyu/RedisClient/


