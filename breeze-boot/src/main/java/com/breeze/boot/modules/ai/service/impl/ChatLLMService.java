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

package com.breeze.boot.modules.ai.service.impl;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.ai.model.entity.ChatLLM;
import com.breeze.boot.modules.ai.service.IChatLLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AI聊天模型管理服务 Impl
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
@Service
@RequiredArgsConstructor
public class ChatLLMService implements IChatLLMService {
    @Override
    public List<ChatLLM> list() {
        return null;
    }

    @Override
    public Result<?> saveChatLLM(ChatLLM chatLLM) {
        return null;
    }

    @Override
    public Object getInfoById(Long projectId) {
        return null;
    }

    @Override
    public Object modifyChatLLM(Long id, ChatLLM chatProject) {
        return null;
    }

    @Override
    public Boolean deleteChatLLM(List<Long> ids) {
        return null;
    }
}
