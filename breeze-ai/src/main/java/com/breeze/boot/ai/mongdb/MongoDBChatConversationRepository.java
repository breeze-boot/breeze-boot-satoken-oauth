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

package com.breeze.boot.ai.mongdb;

import com.breeze.boot.ai.model.entity.MongoDBChatConversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * mongodb聊天对话库
 *
 * @author gaoweixuan
 * @since 2025/03/10
 */
public interface MongoDBChatConversationRepository extends MongoRepository<MongoDBChatConversation, String> {

    // 根据 userId 进行分页查询
    Page<MongoDBChatConversation> findByUserId(Long userId, Pageable pageable);

    // 根据 conversationId 查询单个记录
    Optional<MongoDBChatConversation> findChatByConversationId(String conversationId);

    // 根据 conversationId 删除记录
    void deleteByConversationId(String conversationId);
}