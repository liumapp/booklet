# Redis在Docker中的数据持久化

Redis 提供了两种不同的持久化方法来将数据存储到硬盘里面。一种方法叫快照（snapshotting，RDB），它可以将存在于某一时刻的所有数据都写入硬盘里面。

另一种方法叫只追加文件（append-only file，AOF），它会在执行写命令时，将被执行的写命令复制到硬盘里面。

这篇文章梳理了Redis两种持久化方法的知识点，并通过Docker + Docker-Compose进行环境的模拟，来进行数据的备份与恢复等操作。

至于测试数据，我通过一个python脚本批量录入三百万条key-value键值对（会消耗719.42M内存，来源于redis-cli info信息），没有python环境的同学，可以使用我在项目里准备的另一个shell脚本

python脚本代码:

````python
# -*- coding: UTF-8 -*-
# file write.py
# author liumapp 
# github https://github.com/liumapp
# email liumapp.com@gmail.com
# homepage http://www.liumapp.com 
# date 2019/9/9
#
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

AOF 持久化会将被执行的写命令写到 AOF 文件的末尾，以此来记录数据发生的变化。因此，Redis 只要从头到尾重新执行一次AOF 文件包含的所有写命令，就可以恢复AOF文件所记录的数据集。

要启用AOF（并关闭RDB），我们需要修改Redis的配置文件(./redis_config/redis.conf)：

````
save 60 1000
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

    auto-aof-rewrite-percentage跟auto-aof-rewrite-min-size需要配套使用，比如当我们设置auto-aof-rewrite-percentage为100，设置auto-aof-rewrite-min-size为64mb时，redis会在AOF产生的文件比64M大时，才执行压缩
    
    这里可以参考Redis的官方手册，写的非常清楚：[https://redislabs.com/ebook/part-2-core-concepts/chapter-4-keeping-data-safe-and-ensuring-performance/4-1-persistence-options/4-1-3-rewritingcompacting-append-only-files/](https://redislabs.com/ebook/part-2-core-concepts/chapter-4-keeping-data-safe-and-ensuring-performance/4-1-persistence-options/4-1-3-rewritingcompacting-append-only-files/)

* dir：备份文件存放目录

### AOF-Docker实操



## 总结




## 参考链接

* https://redislabs.com/ebook/part-2-core-concepts/chapter-4-keeping-data-safe-and-ensuring-performance

