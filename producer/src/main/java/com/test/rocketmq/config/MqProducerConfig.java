package com.test.rocketmq.config;

import com.test.rocketmq.listener.MyTransactionListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;


/**
 * @Description
 * @Author wxw
 * @Date 2020-03-30 22:28
 */
@Slf4j
@Configuration
public class MqProducerConfig {


    /**
     * 默认的 DefaultMQProducer
     * @return
     * @throws Exception
     */
    @Bean(name = "defaultMQProducer")
    public DefaultMQProducer getDefaultMQProducer() throws  Exception{
        log.info("defaultProducer 正在创建---------------------------------------");
        DefaultMQProducer producer = new DefaultMQProducer("test01ProducerGroup");
        producer.setNamesrvAddr("192.168.247.129:9876");
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);
        producer.start();
        log.info("defaultProducer 创建成功---------------------------------------");
        return  producer;
    }


    /**
     * 默认的 DefaultMQProducer
     * @return
     * @throws Exception
     */
    @Bean(name = "transactionMQProducer")
    public TransactionMQProducer getTransactionMQProducer() throws  Exception{
        log.info("TransactionMQProducer 正在创建---------------------------------------");
        TransactionMQProducer producer = new TransactionMQProducer("test02ProducerGroup");
        producer.setNamesrvAddr("192.168.247.129:9876");
        producer.setTransactionListener(new MyTransactionListener() );
        producer.start();
        log.info("TransactionMQProducer 创建成功---------------------------------------");
        return  producer;
    }


}
