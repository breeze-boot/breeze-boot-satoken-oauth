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

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;

public class DashScopeChatClientStrategy implements ChatClientStrategy {
    private final ChatModel dashScopeChatModel;
    private final VectorStore dashscopeVectorStore;

    public DashScopeChatClientStrategy(ChatModel dashScopeChatModel, VectorStore dashscopeVectorStore) {
        this.dashScopeChatModel = dashScopeChatModel;
        this.dashscopeVectorStore = dashscopeVectorStore;
    }

    @Override
    public ChatClient.Builder buildChatClient() {
        return ChatClient.builder(dashScopeChatModel);
    }

    @Override
    public VectorStore getVectorStore() {
        return dashscopeVectorStore;
    }
}
