package com.yudian.www;

import com.yudian.common.web.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 项目免费开源
 * 本项目不得用于商业用途，仅做学习交流
 */
@EnableAsync
@EnableScheduling
@ServletComponentScan
@SpringBootApplication(scanBasePackages = "com.yudian.*")
public class AppApplication {

    private final static Logger logger = LoggerFactory.getLogger(AppApplication.class);

    public static void main(String[] args) {
        Environment env = SpringApplication.run(AppApplication.class, args).getEnvironment();
        logger.info(
                "\n----------------------------------------------------------"
                        + "\n\t Application '{}' is running! Access URLs:"
                        + "\n\t Local: \t http://{}:{}{}"
                        + "\n\t Local: \t http://{}:{}{}"
                        + "\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                "127.0.0.1",
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path"),
                IpUtils.getInnetIp(),
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path")
        );
    }


    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //线程池创建的核心线程数，线程池维护线程的最少数量，即使没有任务需要执行，也会一直存活
        //如果设置allowCoreThreadTimeout=true（默认false）时，核心线程会超时关闭直到关闭为0
        executor.setCorePoolSize(8);
        // 缓存队列（阻塞队列）当核心线程数达到最大时，新任务会放在队列中排队等待执行。
        // 需注意，如果设置的过大可能造成服务的oom
        executor.setQueueCapacity(1024);
        // 最大线程池数量，当线程数>=corePoolSize，且阻塞队列已满时。线程池会创建新线程来处理任务
        // 有任务进来时：优先使用核心线程池(CorePoolSize)>>然后进入阻塞队列(QueueCapacity)>>然后开启新的线程(maxPoolSize)>>进入拒绝策略
        executor.setMaxPoolSize(64);
        //当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize
        //允许线程空闲时间30秒，当maxPoolSize的线程在空闲时间到达的时候销毁
        executor.setKeepAliveSeconds(30);
        // spring 提供的 ThreadPoolTaskExecutor 线程池，是有setThreadNamePrefix() 方法的。
        // jdk 提供的ThreadPoolExecutor 线程池是没有 setThreadNamePrefix() 方法的
        executor.setThreadNamePrefix("customize_async_task");
        // 优雅退出，在程序关闭时是否等待计划任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy()：交由调用方线程运行，比如 main 线程；如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行
        // AbortPolicy()：该策略是线程池的默认策略，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
        // DiscardPolicy()：如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常
        // DiscardOldestPolicy()：丢弃队列中最老的任务，队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}