package com.yudian.www.project.queue;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisCustomer implements QueueConsumer {

    @Override
    public void execute(Object object) {
        log.info("监听到了延时队列消息：" + JSON.toJSONString(object));
        //该类监听的为CommonQueueComponent类中的queueName名称的redis中的key
        //实现延时返回后处理数据的业务
        Map<String, Object> param = (Map) object;
        Object type = param.get("type");
        if ("1".equals(type + "")) {
            log.info("监听到了延时队列消息：盲盒藏品未使用的活动库存回滚");
            Object itemId = param.get("value");
        }
    }
}

