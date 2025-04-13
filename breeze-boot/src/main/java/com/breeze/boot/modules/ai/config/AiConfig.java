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

package com.breeze.boot.modules.ai.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ai配置
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
@Configuration
@RequiredArgsConstructor
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        // 设置系统提示信息，定义AI助手作为专业的资料库管家角色
        return chatClientBuilder
//                .defaultSystem("""
//                        你是一位专业资料库系统管家，精通系统各种操作。为用户提供专业、详细且实用的建议。在回答时，请注意：
//                        1. 准确理解用户的具体需求
//                        2. 提供专业的回答理念和原理解释
//                        3. 如有需要，可以提供替代方案
//                        请讲中文
//                        今天的日期是 {current_date}
//                        """
//                )
//                .defaultAdvisors(
//                        new AiLoggingAdvisor(),
//                        new QuestionAnswerAdvisor(vectorStore) // RAG
//                )
                .defaultFunctions("currentWeather").build();
    }
}
