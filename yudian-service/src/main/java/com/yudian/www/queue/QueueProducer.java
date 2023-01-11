package com.yudian.www.queue;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 1、Queue是一个集合，队列的每个方法都有两种形式，一种是抛异常，另一种是返回一个特定的值。
 * 2、PriorityQueue是一个无界优先级队列，默认情况下，队列种的元素按自然顺序排序，或者根据提供的Comparator进行排序。也就是说，优先级队列种的元素都是经过排序的，排序规则可以自己指定，同时队列种的元素都必须是可排序的。
 * 3、BlockingQueue是一个阻塞队列，向已满的队列种插入元素时会阻塞，向空队列中取元素时也会阻塞；阻塞队列被设计主要用于生产者-消费者队列。
 * 4、ArrayBlockingQueue是用数组实现的有界阻塞队列，队列种的元素按FIFO（先进先出）排序。
 * 5、LinkedBlockingQueue是用链表实现的可选边界的阻塞队列。
 * 6、PriorityBlockingQueue相当于是阻塞队列和优先级队列的合体，排序规则与优先级队列相同。
 * 7、DelayQueue延时队列中的元素都有一个有效期，只有当过了有效期才可以使用该元素。
 */
@Slf4j
@Component
public class QueueProducer {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 添加阻塞队列
     *
     * @param t         实际业务参数参数
     * @param delay     延迟时间数
     * @param timeUnit  延迟时间单位
     * @param queueName 队列名
     * @param <T>       泛型
     */
    public <T> void addQueue(String queueName, long delay, TimeUnit timeUnit, T t) {
        try {
            RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
            RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
            delayedQueue.offer(t, delay, timeUnit);
            log.info("添加阻塞队列：{}, 延迟时间数：{}, 延迟时间单位：{}, 实际参数：{}", queueName, delay, timeUnit, t);
        } catch (Exception e) {
            log.error("(添加阻塞失败) {}", e.getMessage());
            throw new RuntimeException("(添加阻塞失败)");
        }
    }

    /**
     * 添加延迟队列
     *
     * @param t         队列值
     * @param delay     延迟时间
     * @param timeUnit  时间单位
     * @param queueName 队列名
     * @param <T>
     */
    public <T> void addDelayQueue(String queueName, long delay, TimeUnit timeUnit, T t) {
        try {
            RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(queueName);
            RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
            delayedQueue.offer(t, delay, timeUnit);
            log.info("添加延时队列：{}, 延迟时间数：{}, 延迟时间单位：{}, 实际参数：{}", queueName, delay, timeUnit, t);
        } catch (Exception e) {
            log.error("(添加延时队列失败) {}", e.getMessage());
            throw new RuntimeException("(添加延时队列失败)");
        }
    }

    /**
     * 获取延迟队列
     *
     * @param queueCode
     * @param <T>
     * @return
     * @throws InterruptedException
     */
    public <T> T getDelayQueue(String queueCode) throws InterruptedException {
        RBlockingDeque<Map> blockingDeque = redissonClient.getBlockingDeque(queueCode);
        T value = (T) blockingDeque.take();
        return value;
    }

    /**
     * 删除队列中的值
     *
     * @param queueName 队列名称
     * @param value     要删除的值
     * @param <T>       泛型
     * @return 是否删除成功
     */
    public <T> Boolean delValue(String queueName, Object value) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        return delayedQueue.remove(value);
    }

}


