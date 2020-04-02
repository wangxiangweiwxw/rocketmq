package com.test.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description
 * @Author wxw
 * @Date 2020-04-01 15:34
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
public class TestProducer {

    @Qualifier("defaultMQProducer")
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Qualifier("transactionMQProducer")
    @Autowired
    private DefaultMQProducer transactionMQProducer;

    /**
     * 消息队列模式--同步发送
     */
    @Test
    public void test01()throws  Exception {
        for(int i=1 ; i<10 ;i++){
            String msg ="hello word ---第"+ i +"消息";
            Message message = new Message("topic_test_001",msg.getBytes());
            SendResult send = defaultMQProducer.send(message);
            log.info("发送消息=返回结果"+send.toString());
        }

    }

    /**
     * 消息队列模式--异步发送
     */
    @Test
    public void test02() throws  Exception{
            String msg ="hello word --异步消息-----";
            Message message = new Message("topic_test_001",msg.getBytes());
           defaultMQProducer.send(message ,new SendCallback() {
                public void onSuccess(SendResult sendResult) {
                    log.info("消息发送成功。。。发送消息=返回结果"+sendResult);
                }
                public void onException(Throwable e) {
                    // 如果发生异常 case 异常，尝试重投
                    e.printStackTrace();
                    log.info("消息发送异常。。。");
                }
           });
        Thread.sleep(1000*30);

        System.out.println("结束------------");
    }

    /**
     * 消息队列模式--oneway方式，只管发送，不在意是否成功，日志处理一般这样
     */
    @Test
    public void test03()throws  Exception {
        for(int i=1 ; i<10 ;i++){
            String msg ="hello word ---oneway方式----第"+ i +"消息";
            Message message = new Message("topic_test_001",msg.getBytes());
            defaultMQProducer.sendOneway(message);
            log.info("oneway方式-----------------操作结束");
        }
    }



    /**
     * 消息延迟发送  不能精确设置延迟发送
     * 时间 ：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"
     * 级别：1  2   3  4   5   . . . . .
     *
     */
    @Test
    public void test04()throws  Exception {
        String msg ="hello word ---延迟10秒发送------------------";
        Message message = new Message("topic_test_001",msg.getBytes());
        message.setDelayTimeLevel(3);
        SendResult send = defaultMQProducer.send(message);
        log.info("发送消息=返回结果"+send.toString());
    }



    /**
     * 有事务的消息处理
     * Tcc 2阶段提交
     */
    @Test
    public void test05()throws  Exception {
        Message message =  new Message("topic_test_001", "测试！这是事务消息".getBytes());
        TransactionSendResult sendResult = transactionMQProducer.sendMessageInTransaction(message , null);
        log.info("发送有事务消息=返回结果"+sendResult);
    }


    /**
     * 消息队列模式--过滤分组
     */
    @Test
    public void test06()throws  Exception {
        for(int i=1 ; i<10 ;i++){
            String msg ="hello word --tag分组-第"+ i +"消息";
            // tag 是用来过滤消息，消息分组
            Message message = new Message("topic_test_001","ABC",msg.getBytes());
            SendResult send = defaultMQProducer.send(message);
            log.info("发送消息=返回结果"+send.toString());
        }

    }



}
