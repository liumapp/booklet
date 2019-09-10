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



## AOF

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

## 













