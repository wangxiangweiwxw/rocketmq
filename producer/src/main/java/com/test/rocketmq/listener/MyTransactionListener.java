package com.test.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @Description
 * @Author wxw
 * @Date 2020-04-01 22:29
 */
@Slf4j
public class MyTransactionListener implements TransactionListener {
        @Override
        public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info(" 执行 本地事务=====executeLocalTransaction");
        log.info("msg.getBody=:" + new String(msg.getBody()));
        log.info("msg.getTransactionId():" + msg.getTransactionId());
        try {
            //执行业务处理消息
        } catch (Exception e) {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        // 真正发出去的数据 可用
        return LocalTransactionState.COMMIT_MESSAGE;
    }

        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
            log.info(" Broker端回调 ，检查事务=====checkLocalTransaction");
            log.info("msg.getBody=:" + new String(msg.getBody()));
            log.info("msg.getTransactionId():" + msg.getTransactionId());
        // 事务执行成功
        return LocalTransactionState.COMMIT_MESSAGE;
        // 等会儿
        //		return LocalTransactionState.UNKNOW;
        // 回滚消息
        //		return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
