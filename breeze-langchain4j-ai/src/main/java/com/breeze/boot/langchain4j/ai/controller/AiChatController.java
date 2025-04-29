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

package com.breeze.boot.langchain4j.ai.controller;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.langchain4j.ai.model.query.HistoryChatPage;
import com.breeze.boot.langchain4j.ai.model.vo.ChatConversationMessageVO;
import com.breeze.boot.langchain4j.ai.mongdb.entity.MongoDBChatMessages;
import com.breeze.boot.langchain4j.ai.service.IAiChatService;
import com.breeze.boot.langchain4j.ai.service.IRagService;
import com.breeze.boot.xss.annotation.JumpXss;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * ai聊天控制器
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/ai/v1")
public class AiChatController {

    private final IAiChatService chatService;
    private final IRagService esLocalRagService;

    @GetMapping("/create")
    public Result<Object> create(@RequestParam Long userId) {
        return this.chatService.create(userId);
    }

    @JumpXss
    @GetMapping(value = "/{platform}/{model}/ragChat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ragChat(@PathVariable("model") String model,
                                @PathVariable("platform") String platform,
                                @RequestParam(value = "conversationId") String conversationId,
                                @RequestParam(value = "message", defaultValue = "请介绍自己") String message
    ) {
        return chatService.chat(model, platform, message, Long.valueOf(conversationId));
    }

    @GetMapping("/history")
    public Result<Page<MongoDBChatMessages>> history(HistoryChatPage historyChatPage) {
        return this.chatService.history(historyChatPage);
    }

    @GetMapping("/historyDetail")
    public Result<List<ChatConversationMessageVO>> historyDetail(@RequestParam String id) {
        return this.chatService.historyDetail(id);
    }

    @PostMapping("/importDoc")
    public void importDoc(List<MultipartFile> files) {
        this.esLocalRagService.importDoc(files);
    }

}
