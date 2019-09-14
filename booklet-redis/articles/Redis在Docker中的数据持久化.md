# Redis在Docker中的数据持久化

项目Github地址：[github/booklet](https://github.com/liumapp/booklet/tree/master/booklet-redis)

Redis 提供了两种不同的持久化方法来将数据存储到硬盘里面。一种方法叫快照（snapshotting，RDB），它可以将存在于某一时刻的所有数据都写入硬盘里面。

另一种方法叫只追加文件（append-only file，AOF），它会在执行写命令时，将被执行的写命令复制到硬盘里面。

这篇文章梳理了Redis两种持久化方法的知识点，并通过Docker + Docker-Compose进行环境的模拟，来进行数据的备份与恢复等操作。

至于测试数据，我通过一个python脚本批量录入三百万条key-value键值对（会消耗719.42M内存，来源于redis-cli info信息），没有python环境的同学，可以使用我在项目里准备的另一个shell脚本

python脚本代码:

````python
# -*- coding: UTF-8 -*-
import redis

r = redis.Redis(host="127.0.0.1", port=6379, db=0, password="admin123")
print("开始插入三百万条数据，每10万条数据提交一次批处理")
with r.pipeline(transaction=True) as p:
    value = 0
    while value < 3000000:
        print("开始插入" + str(value) + "条数据")
        p.sadd("key" + str(value), "value" + str(value))
        value += 1
        if (value % 100000) == 0:
            p.execute()
````

## RDB



### RDB配置说明

### RDB-Docker实操

## AOF

AOF持久化会将被执行的写命令写到 AOF 文件的末尾，以此来记录数据发生的变化。因此，Redis 只要从头到尾重新执行一次AOF 文件包含的所有写命令，就可以恢复AOF文件所记录的数据集。

要启用AOF（并关闭RDB），我们需要修改Redis的配置文件(./redis_config/redis.conf)：

````
requirepass admin123

#save 60 1000
stop-writes-on-bgsave-error no
rdbcompression no
dbfilename dump.rdb

appendonly yes
appendfsync everysec
no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb

dir /data/
````

### AOF配置说明

* appendonly: 是否启用AOF 

    * yes: 启用AOF
    
    * no: 关闭AOF
    
* appendfsync: 启用AOF后的数据同步频率

    * alaways: 每个Redis写命令都要同步写入硬盘。这样做会严重降低Redis 的速度 （不建议）
    
    * everysec: 每秒执行一次同步，显式地将多个写命令同步到硬盘 （推荐，对性能没有太大影响）
    
    * no: 让操作系统来决定应该何时进行同步。（不建议）
    
        Redis将不对AOF文件执行任何显式的同步操作，如果用户的硬盘处理写入操作的速度不够快的话，那么当缓冲区被等待写入硬盘的数据填满时，Redis的写入操作将被阻塞，并导致Redis处理命令请求的速度变慢        

* no-appendfsync-on-rewrite：在对AOF进行压缩（也被称为重写机制）的时候能否执行同步操作

    * yes: 不允许
    
    * no: 允许

* auto-aof-rewrite-percentage：多久执行一次AOF压缩，单位是百分比

* auto-aof-rewrite-min-size：需要压缩的文件达到多少时开始执行

    auto-aof-rewrite-percentage跟auto-aof-rewrite-min-size需要配套使用，比如当我们设置auto-aof-rewrite-percentage为100，设置auto-aof-rewrite-min-size为64mb时
    
    redis会在AOF产生的文件比64M大时，并且AOF文件的体积比上一次重写之后至少增大了一倍（100%）才执行BGREWRITEAOF重写命令
    
    如果觉得AOF重写执行得过于频繁，我们可以把auto-aof-rewrite-percentage设置100以上，比如200，就可以降低重写频率
    
    这里可以参考Redis的官方手册，写的非常清楚：[https://redislabs.com/ebook/part-2-core-concepts/chapter-4-keeping-data-safe-and-ensuring-performance/4-1-persistence-options/4-1-3-rewritingcompacting-append-only-files/](https://redislabs.com/ebook/part-2-core-concepts/chapter-4-keeping-data-safe-and-ensuring-performance/4-1-persistence-options/4-1-3-rewritingcompacting-append-only-files/)

* dir：备份文件存放目录

### AOF重写机制

在上面的配置中，已经通过auto-aof-rewrite-percentage和auto-aof-rewrite-min-size两个参数，简单介绍了Redis的BGREWRITEAOF重写命令

那么，为什么要用AOF重写机制呢？

因为AOF持久化是通过保存被执行的写命令来记录Redis数据库状态的，所以AOF文件随着时系统运行会越来越大

而过于庞大的AOF文件会产生以下不良影响

* 影响Redis服务性能；

* 占用服务器磁盘空间；

* AOF还原数据状态的时间增加；

所以Redis提供了一套AOF重写机制，通过创建一个新的AOF文件来替换掉旧的AOF文件，这两个文件所保存的数据状态是相同的，但新的AOF文件不会包含冗余命令，所以体积会较旧AOF文件小很多

但在实际的使用中，我们需要非常小心，不能让Redis的重写命令执行的过于频繁 **(注意：auto-aof-rewrite-percentage的单位是百分比，值越大，重写频率越低，也千万别出现0这种值)** 因为BGREWRITEAOF的工作原理和BGSAVE创建快照的工作原理非常相似：Redis会创建一个子进程，然后由子进程负责对AOF文件进行重写，因为AOF文件重写也需要用到子进程，所以快照持久化因为创建子进程而导致的性能问题和内存占用问题，在AOF持久化中也同样存在

更具体的AOF重写工作原理：

* Fork主进程，产生一个带有数据副本的子进程在后台执行

    Redis这样设计可以确保在重写过程中，不影响Redis主进程的服务正常运行，同时通过处理数据副本来保证数据的安全性**(注意，重写是针对数据副本来进行处理，而不是针对旧的AOF文件)**

* 子进程Fork完成后，Redis将启用AOF重写缓冲区，此刻开始，新的写入命令会被写入AOF缓冲区和AOF重写缓冲区中

    这里启用的AOF重写缓冲区可以确保：在执行AOF重写的过程中，任何新的写入命令产生，都不会导致新AOF文件的数据状态与Redis数据库状态不一致

* 子进程完成对AOF文件的重写后，通知父进程

* 父进程收到通知后，将AOF重写缓冲区的内容全部写入新的AOF文件中

* 父进程将新的AOF文件替换掉旧的AOF文件**(注意，这一步会造成Redis阻塞，但问题不大)**

BGREWRITEAOF的工作流程图如下所示**(绘图源代码在项目的./articles/bgrewriteaof.puml文件下)**：

![bgrewriteaof.png](https://github.com/liumapp/booklet/blob/master/booklet-redis/articles/bgrewriteaof.png?raw=true)


### AOF-Docker实操

* 通过docker-compose启动Redis容器

    docker-compose.yml配置如下
    
    ````yaml
        version: "2"
        services:
          redis:
            image: 'redis:3.2.11'
            restart: always
            hostname: redis
            container_name: redis
            ports:
              - '6379:6379'
            command: redis-server /usr/local/etc/redis/redis.conf
            volumes:
              - ./redis_config/redis.conf:/usr/local/etc/redis/redis.conf
              - ./redis_data/:/data/
    ````
    
    我将Docker容器中的redis服务所产生的备份文件，映射在宿主机的./redis_data目录下
    
* 修改redis配置文件，使AOF生效，并关闭RDB

    这里将上面的redis.conf内容复制替换到./redis_config/redis.conf文件中即可
    
* 启动redis服务，并观察redis_data目录下是否有appendonly.aof文件生成，有生成，则证明备份成功

    另外我们可以发现，3百万条数据（700M）的备份文件，其实际占用磁盘空间约为170M，这便是Redis重写机制强大的地方
    
* 数据恢复的话，我们不需要做其他操作，只要确保该appendonly.aof存在，redis便会自动去读取其中的数据 

## 总结

虽然RDB跟AOF都可以确保Redis的数据持久化，但光是这样还是不够的

对于一个需要支持可扩展的分布式平台而言，我们还需要提供一套复制备份机制，允许在一个周期内，自动将AOF或者RDB的文件备份到不同的服务器下

这种情况下，我们就需要使用Redis的复制并生成数据副本功能，具体内容我会在下一篇文章进行实操记录

## 参考链接

* https://redislabs.com/ebook/part-2-core-concepts/chapter-4-keeping-data-safe-and-ensuring-performance

