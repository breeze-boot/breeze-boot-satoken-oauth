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


public class OpenAiChatClientStrategy implements ChatClientStrategy {
    private final ChatModel openAiChatModel;
    private final VectorStore openAiVectorStore;

    public OpenAiChatClientStrategy(ChatModel openAiChatModel, VectorStore openAiVectorStore) {
        this.openAiChatModel = openAiChatModel;
        this.openAiVectorStore = openAiVectorStore;
    }

    @Override
    public ChatClient.Builder buildChatClient() {
        return ChatClient.builder(openAiChatModel);
    }

    @Override
    public VectorStore getVectorStore() {
        return openAiVectorStore;
    }
}