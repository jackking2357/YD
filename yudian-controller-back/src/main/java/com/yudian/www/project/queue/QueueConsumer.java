package com.yudian.www.project.queue;

public interface QueueConsumer<T> {

    /**
     * 延迟队列任务执行方法，实现该方法执行具体业务
     *
     * @param t 具体任务参数
     */
    void execute(T t);
}

