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

package com.breeze.boot.spring.ai.config.memory;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.breeze.boot.spring.ai.service.IMysqlDBChatConversationService;
import com.breeze.boot.spring.ai.model.entity.MysqlDBChatConversation;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.*;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * mysql-db聊天存储器
 *
 * @author gaoweixuan
 * @since 2025/04/13
 */
@Configuration
@RequiredArgsConstructor
public class MysqlDbChatMemory implements ChatMemory {

    private final IMysqlDBChatConversationService chatSessionService;

    private MysqlDBChatConversation getChatConversationByConversationId(String conversationId) {
        LambdaQueryWrapper<MysqlDBChatConversation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MysqlDBChatConversation::getConversationId, conversationId);
        return chatSessionService.getOne(queryWrapper);
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        MysqlDBChatConversation chatConversation = this.getChatConversationByConversationId(conversationId);
        if (chatConversation == null) {
            throw new RuntimeException("can not find the conversation , conversation id is %s".formatted(conversationId));
        }

        JSONArray content = chatConversation.getContent();
        for (Message message : messages) {
            content.add(this.createMessageJson(message));
        }
        chatConversation.setContent(content);

        if (chatConversation.getId() == null) {
            chatSessionService.save(chatConversation);
        } else {
            chatSessionService.updateById(chatConversation);
        }
    }

    private JSONObject createMessageJson(Message message) {
        JSONObject messageJson = new JSONObject();
        messageJson.put("content", message.getText());
        messageJson.put("type", message.getMessageType().name());
        return messageJson;
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        MysqlDBChatConversation chatConversation = getChatConversationByConversationId(conversationId);
        List<Message> messages = new ArrayList<>();

        if (chatConversation != null) {
            JSONArray content = chatConversation.getContent();
            int startIndex = Math.max(0, content.size() - lastN);
            for (int i = startIndex; i < content.size(); i++) {
                JSONObject messageJson = content.getJSONObject(i);
                Message message = convertJsonToMessage(messageJson);
                messages.add(message);
            }
        }

        return messages;
    }

    private Message convertJsonToMessage(JSONObject messageJson) {
        String msgContent = messageJson.getString("content");
        return toMessage(MessageType.valueOf(messageJson.getString("type")), msgContent);
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
    public void clear(String conversationId) {
        LambdaQueryWrapper<MysqlDBChatConversation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MysqlDBChatConversation::getConversationId, conversationId);
        chatSessionService.remove(queryWrapper);
    }

}
