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



