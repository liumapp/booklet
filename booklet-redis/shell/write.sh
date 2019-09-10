#!/bin/bash

echo '============================================================='
echo '$                                                           $'
echo '$                      liumapp                              $'
echo '$                                                           $'
echo '$                                                           $'
echo '$  email:    liumapp.com@gmail.com                          $'
echo '$  homePage: http://www.liumapp.com                         $'
echo '$  Github:   https://github.com/liumapp                     $'
echo '$                                                           $'
echo '============================================================='
echo '.'

echo '假设 127.0.0.1:6379 存在redis服务'
echo '现在这个脚本将开始对redis写入三百万条数据'
echo '建议您使用python脚本进行批处理'

n=3000000
for ((i=n; i>=1; i--))
do
    echo ${i}
    redis-cli -a admin123 set key${i} value${i}
done




