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

package com.breeze.boot.spring.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.spring.ai.mapper.ChatConversationMapper;
import com.breeze.boot.spring.ai.model.entity.MysqlDBChatConversation;
import com.breeze.boot.spring.ai.service.IMysqlDBChatConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AI聊天模型管理服务 Impl
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
@Service
@RequiredArgsConstructor
public class MysqlDBChatConversationServiceImpl extends ServiceImpl<ChatConversationMapper, MysqlDBChatConversation> implements IMysqlDBChatConversationService {

}
