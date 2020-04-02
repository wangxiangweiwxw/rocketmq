package com.test.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Description
 * @Author wxw
 * @Date 2020-04-01 16:21
 */
@Slf4j
public class MyMessageListener implements MessageListenerConcurrently  {


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for(MessageExt msg: list){
            log.info("接受的消息="+new String(msg.getBody()));
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
