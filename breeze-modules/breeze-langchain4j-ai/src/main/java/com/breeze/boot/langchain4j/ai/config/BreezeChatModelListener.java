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

import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 聊天模型监听器
 *
 * @author gaoweixuan
 * @since 2025/04/29
 */
@Slf4j
public class BreezeChatModelListener implements ChatModelListener {

    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        log.info("onRequest(): {}", requestContext.request().messages());
    }

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        log.info("onResponse(): {}", responseContext.response().aiMessage());
    }

    @Override
    public void onError(ChatModelErrorContext errorContext) {
        log.info("onError(): {}", errorContext.error().getMessage());
    }
}