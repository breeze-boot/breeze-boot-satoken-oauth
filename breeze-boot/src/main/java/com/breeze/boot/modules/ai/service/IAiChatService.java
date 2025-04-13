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

package com.breeze.boot.modules.ai.service;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.ai.model.entity.MongoDBChatConversation;
import com.breeze.boot.modules.ai.model.query.HistoryChatPage;
import com.breeze.boot.modules.ai.model.vo.ChatConversationMessageVO;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * iai聊天服务
 *
 * @author gaoweixuan
 * @since 2025/04/13
 */
public interface IAiChatService {

    Flux<String> chat(String conversationId, String message);

    Flux<String> chat(String message, String s, String token);

    Result<String> create(Long userId);

    Result<Page<MongoDBChatConversation>> history(HistoryChatPage historyChatPage);

    Result<List<ChatConversationMessageVO>> historyDetail(String id);

}
