# Redis在Docker中的数据持久化选项

## 创建模拟环境

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













