/*
 * Copyright (c) 2025, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.spring.ai.config;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

/**
 * 日志记录
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
@Slf4j
public class AiLoggingAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    @NotNull
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 实现环绕通知逻辑，在调用目标方法前和后执行自定义操作。
     *
     * @param advisedRequest 被拦截的请求对象，包含原始调用参数和上下文信息
     * @param chain          顾问链对象，用于继续执行后续的顾问或目标方法
     * @return 处理后的响应对象，可能包含被修改的返回值或增强后的结果
     */
    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        // 记录请求进入时的上下文信息
        log.info("BEFORE: {}", advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
        // 记录方法执行后的最终结果
        log.info("AFTER: {}", advisedResponse);
        return advisedResponse;
    }


    /**
     * 处理流式请求的环绕通知方法。在请求处理前后分别记录日志，并通过聚合器处理响应流。
     *
     * @param advisedRequest 包含原始请求信息的AdvisedRequest对象
     * @param chain          处理链对象，用于调用后续的环绕通知处理器
     * @return 经过所有环绕通知处理后的AdvisedResponse响应流
     */
    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        // 记录请求处理前的日志信息
        log.info("请求之前BEFORE: {}", advisedRequest);
        Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);
        // 聚合响应流并执行后置处理（记录每个响应）
        return new MessageAggregator().aggregateAdvisedResponse(advisedResponses,
                advisedResponse -> {
                    log.info("请求之后AFTER: {}", advisedResponse.adviseContext());
                    log.info("请求之后AFTER: {}", advisedResponse.response());
                });
    }


}

