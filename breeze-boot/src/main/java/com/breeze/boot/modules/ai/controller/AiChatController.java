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

package com.breeze.boot.modules.ai.controller;

import com.breeze.boot.modules.ai.config.AiLoggingAdvisor;
import com.breeze.boot.xss.annotation.JumpXss;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

/**
 * ai聊天控制器
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
@RestController
@CrossOrigin
public class AiChatController {

    private final ChatClient chatClient;

    public AiChatController(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.defaultSystem(
                        """
                                您是清风系统管家。请以友好、乐于助人且愉快的方式来回复
                                您正在通过在线聊天系统与客户互动
                                您可以帮助用户操作系统，可以通过对话完成系统操作
                                请讲中文
                                今天的日期是 {current_date}
                                """
                )
        .defaultAdvisors(
                new PromptChatMemoryAdvisor(chatMemory),
                new AiLoggingAdvisor(),
                new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().build()) // RAG
        )
        .defaultFunctions("currentWeather")
        .build();
}

@JumpXss
@GetMapping(value = "/ai/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> chat(@RequestParam(value = "message", defaultValue = "请介绍自己") String message) {
Flux<String> content = this.chatClient.prompt()
        .user(message)
        .system(promptSystemSpec -> promptSystemSpec.param("current_date", LocalDate.now().toString()))
        .advisors(advisorSpec -> advisorSpec.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
        .stream()
        .content();
return content.concatWith(Flux.just("[complete]"));
}

}
