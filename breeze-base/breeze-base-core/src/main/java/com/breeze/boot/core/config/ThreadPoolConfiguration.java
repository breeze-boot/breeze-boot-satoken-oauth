/*
 * Copyright (c) 2023, gaoweixuan (breeze-cloud@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breeze.boot.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author gaoweixuan
 * @since 2023-04-14
 */
@Slf4j
@Configuration
public class ThreadPoolConfiguration {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        log.info("---------- 开始加载线程池 ----------");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程池数
        threadPoolTaskExecutor.setCorePoolSize(5);
        // 最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(10);
        // 队列容量
        threadPoolTaskExecutor.setQueueCapacity(15);
        // 活跃时间
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        // 主线程等待子线程执行时间
        threadPoolTaskExecutor.setAwaitTerminationSeconds(60);
        // 线程名字前缀
        threadPoolTaskExecutor.setThreadNamePrefix("breeze-thread-pool-executor-");
        // 当pool已经达到max-size的时候，如何处理新任务：由调用者所在的线程来执行
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        threadPoolTaskExecutor.initialize();
        log.info("---------- 线程池加载完成 ----------");
        return threadPoolTaskExecutor;
    }
}
