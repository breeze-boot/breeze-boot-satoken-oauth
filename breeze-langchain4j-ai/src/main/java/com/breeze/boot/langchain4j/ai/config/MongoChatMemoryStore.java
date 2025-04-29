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

package com.breeze.boot.langchain4j.ai.config;

import com.breeze.boot.langchain4j.ai.mongdb.MongoDBChatMessageRepository;
import com.breeze.boot.langchain4j.ai.mongdb.entity.MongoDBChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * mongo聊天记忆库
 *
 * @author gaoweixuan
 * @since 2025/04/29
 */
@Component
@RequiredArgsConstructor
public class MongoChatMemoryStore implements ChatMemoryStore {

    private final MongoDBChatMessageRepository mongoDBChatMessageRepository;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        Optional<MongoDBChatMessages> chatMessagesOptional = mongoDBChatMessageRepository.findByMemoryId(memoryId);
        if (chatMessagesOptional.isPresent()) {
            return ChatMessageDeserializer.messagesFromJson(chatMessagesOptional.get().getMessages());
        }
        return new LinkedList<>();
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        Optional<MongoDBChatMessages> chatMessagesOptional = mongoDBChatMessageRepository.findByMemoryId(memoryId);
        MongoDBChatMessages chatMessages = chatMessagesOptional.orElseGet(() -> {
            MongoDBChatMessages newChatMessages = new MongoDBChatMessages();
            newChatMessages.setMemoryId(memoryId);
            return newChatMessages;
        });
        chatMessages.setMessages(ChatMessageSerializer.messagesToJson(messages));
        mongoDBChatMessageRepository.save(chatMessages);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        mongoDBChatMessageRepository.findByMemoryId(memoryId)
                .ifPresent(this.mongoDBChatMessageRepository.deleteByMemoryId(memoryId));
    }

}