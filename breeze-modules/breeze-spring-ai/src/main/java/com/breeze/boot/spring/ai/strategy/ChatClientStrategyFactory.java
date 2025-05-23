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

package com.breeze.boot.spring.ai.strategy;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.HashMap;
import java.util.Map;

public class ChatClientStrategyFactory {
    private final Map<String, ChatClientStrategy> strategies;

    public ChatClientStrategyFactory(ChatModel dashScopeChatModel, ChatModel ollamaChatModel,
                                     VectorStore dashscopeVectorStore, VectorStore ollamaVectorStore) {
        this.strategies = new HashMap<>();
        this.strategies.put("dashscope", new DashScopeChatClientStrategy(dashScopeChatModel, dashscopeVectorStore));
        this.strategies.put("ollama", new OllamaChatClientStrategy(ollamaChatModel, ollamaVectorStore));
    }

    public ChatClientStrategy getStrategy(String platform) {
        // 默认返回 dashscope 策略
        return strategies.getOrDefault(platform, strategies.get("dashscope"));
    }
}
