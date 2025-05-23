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

package com.breeze.boot.langchain4j.ai.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * qwen 聊天代理
 *
 * @author gaoweixuan
 * @since 2025/04/29
 */
@AiService(
        wiringMode = EXPLICIT,
        streamingChatModel = "qwenStreamingChatModel",
        chatMemoryProvider = "chatMemoryMongoProvider",
        tools = "weatherTools",
        contentRetriever = "esEmbeddingStoreContentRetriever")
public interface QwenAssistant {

    /**
     * 聊天代理
     *
     * @param conversationId 聊天id
     * @param userMessage    用户消息
     */
    @SystemMessage(fromResource = "system-prompt.st")
    Flux<String> chat(@MemoryId Long conversationId, @UserMessage String userMessage);

}