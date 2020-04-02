package rocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rocketmq.listener.MyMessageListener;


/**
 * @Description
 * @Author wxw
 * @Date 2020-03-31 22:42
 */
@Slf4j
@Configuration
public class DefaultConsumer3Configure {



     @Bean
     public   DefaultMQPushConsumer getDefaultConsumer() throws  Exception{
         log.info("DefaultMQPushConsumer3  -------开始启动-----");
         DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test03ConsumerGroup");
         consumer.setNamesrvAddr("192.168.247.129:9876");
         consumer.subscribe("topic_test_001","ABCD");

         // log.info("启用广播模式");
         //consumer.setMessageModel(MessageModel.BROADCASTING);
         consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
         // 开启内部类实现监听
         consumer.registerMessageListener(new MyMessageListener());
         consumer.start();
         log.info("DefaultMQPushConsumer3  -------启动集群模式-----");
         return consumer;
     }



}