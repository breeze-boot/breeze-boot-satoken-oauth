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

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.breeze.boot.spring.ai.mongdb.MongoDBChatConversationRepository;
import com.breeze.boot.spring.ai.model.entity.MongoMessage;
import com.breeze.boot.spring.ai.mongdb.entity.MongoDBChatConversation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.*;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * mongo db chat内存
 *
 * @author gaoweixuan
 * @since 2025/04/13
 */
@Configuration
@RequiredArgsConstructor
public class MongoDBChatMemory implements ChatMemory {

    private final MongoDBChatConversationRepository conversationRepository;

    @Override
    public void add(String conversationId, List<Message> messages) {
        MongoDBChatConversation conversation = conversationRepository.findChatByConversationId(conversationId)
                .orElseThrow(() -> new RuntimeException("can not find the conversation , conversation id is %s".formatted(conversationId)));
        List<Message> savedMessages = this.convert(conversation);
        savedMessages.addAll(messages);
        List<MongoMessage> messageList = savedMessages.stream().map(item -> {
            return toMessage(item.getMessageType(), item.getText());
        }).map(item -> {
            return MongoMessage.builder()
                   .type(item.getMessageType())
                   .content(item.getText())
                   .build();
        }).collect(Collectors.toList());
        conversation.setMessages(JSON.toJSONString(messageList));
        this.conversationRepository.save(conversation);
    }

    public Message toMessage(MessageType type, String content) {
        return switch (type) {
            case USER -> new UserMessage(content);
            case ASSISTANT -> new AssistantMessage(content);
            case SYSTEM -> new SystemMessage(content);
            case TOOL -> throw new UnsupportedOperationException();
        };
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        return this.conversationRepository.findChatByConversationId(conversationId)
                .map(v -> convert(v).stream().limit(lastN)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    @Override
    public void clear(String conversationId) {
        this.conversationRepository.deleteByConversationId(conversationId);
    }

    private List<Message> convert(MongoDBChatConversation conversation) {
        if (StrUtil.isBlankIfStr(conversation.getMessages())) {
            return Lists.newArrayList();
        }
        List<MongoMessage> messages = JSON.parseArray(conversation.getMessages(), MongoMessage.class);
        return messages.stream()
                .map(item -> {
                    return this.toMessage(item.getType(), item.getContent());
                })
                .collect(Collectors.toList());
    }
}
