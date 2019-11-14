package com.liumapp.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.errors.RetriableException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 *
 * 最简单的kafka生产者产生任务
 *
 * file ProducerTest.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2019/11/12
 */
@Slf4j
public class ProducerTest {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.102.74:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "-1");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 323840);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 3000);

        Producer<String, String> producer = new KafkaProducer<>(props);
//        for (int i = 0; i < 100; i++) {
//            producer.send(new ProducerRecord<>("my_topic", Integer.toString(i), Integer.toString(i)));
//        }

        String testData = "{\"activityType\":82,\"busId\":3611000100135261,\"deviceNo\":\"9676576\",\"execTime\":\"2019-11-14 11:36:42\",\"merchantId\":36110001,\"routeId\":3611000100100059,\"seq\":\"20462\"}";
        producer.send(new ProducerRecord<>("IDS_EVENT_APPLICATION_36110001", testData), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    log.info("消息发送成功");
                } else {
                    if (e instanceof RetriableException) {
                        // 处理可重试瞬时异常
                        log.error("消息发送失败，准备进行重试", e);
                    } else {
                        //处理不可重试异常
                        log.error("消息发送失败，无法重试", e);
                    }

                }
            }
        });

        producer.close();
    }

}
