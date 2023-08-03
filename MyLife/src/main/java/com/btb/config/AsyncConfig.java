package com.btb.config;

import org.eclipse.jgit.transport.SshSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * 线程池的配置
 */
@Configuration
public class AsyncConfig {

    @Bean("importAsyncTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(20);
        asyncTaskExecutor.setCorePoolSize(5);
        asyncTaskExecutor.setThreadNamePrefix("3007-async-task-thread-pool-");
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }
}