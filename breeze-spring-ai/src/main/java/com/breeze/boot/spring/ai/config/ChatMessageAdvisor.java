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
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;

/**
 * 聊天消息顾问
 *
 * @author gaoweixuan
 * @since 2025/04/13
 */
@Slf4j
public class ChatMessageAdvisor extends MessageChatMemoryAdvisor {

    public ChatMessageAdvisor(ChatMemory chatMemory, String conversationId) {
        super(chatMemory, conversationId, 4000);
        log.info("ChatMessageAdvisor init");
    }
}
