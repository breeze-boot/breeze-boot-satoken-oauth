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

package com.breeze.boot.langchain4j.ai.mongdb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * MongoDB 聊天消息
 *
 * @author gaoweixuan
 * @since 2025/04/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_messages")
public class MongoDBChatMessages {

    @Id
    private ObjectId id;

    private Object memoryId;
    /**
     * 当前聊天记录列表的json字符串
     */
    private String messages;

    private String title;
    private Long userId;
    private LocalDateTime createTime;

}