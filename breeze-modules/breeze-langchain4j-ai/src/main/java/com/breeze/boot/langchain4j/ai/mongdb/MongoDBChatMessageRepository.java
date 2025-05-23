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

package com.breeze.boot.langchain4j.ai.mongdb;

import com.breeze.boot.langchain4j.ai.mongdb.entity.MongoDBChatMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * mongodb聊天对话库
 *
 * @author gaoweixuan
 * @since 2025/03/10
 */
public interface MongoDBChatMessageRepository extends MongoRepository<MongoDBChatMessages, String> {


    // 根据 userId 进行分页查询
    Page<MongoDBChatMessages> findByUserId(Long userId, Pageable pageable);

    // 根据 memoryId 查询单个记录
    Optional<MongoDBChatMessages> findByMemoryId(Object memoryId);

    // 根据 memoryId 删除记录
    Consumer<? super MongoDBChatMessages> deleteByMemoryId(Object memoryId);

}