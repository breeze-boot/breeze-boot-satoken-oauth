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

package com.breeze.boot.langchain4j.ai.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.ai.model.entity.AiModel;
import com.breeze.boot.ai.model.entity.AiPlatform;
import com.breeze.boot.ai.service.AiPlatformService;
import com.breeze.boot.ai.service.impl.AiModelServiceImpl;
import com.breeze.boot.core.utils.MapperUtils;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.langchain4j.ai.assistant.QwenAssistant;
import com.breeze.boot.langchain4j.ai.model.query.HistoryChatPage;
import com.breeze.boot.langchain4j.ai.model.vo.ChatConversationMessageVO;
import com.breeze.boot.langchain4j.ai.mongdb.MongoDBChatMessageRepository;
import com.breeze.boot.langchain4j.ai.mongdb.entity.MongoDBChatMessages;
import com.breeze.boot.langchain4j.ai.service.IAiChatService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ai聊天服务impl
 *
 * @author gaoweixuan
 * @since 2025/04/13
 */
@Slf4j
@Service
public class AiChatServiceImpl implements IAiChatService {

    // 常量定义
    private static final String ERROR_MESSAGE = "[error]";
    private static final String COMPLETE_MESSAGE = "[complete]";

    private final MongoDBChatMessageRepository mongoDBChatMessageRepository;

    private final QwenAssistant qwenAssistant;
    // 声明可用模型
    private final AiModelServiceImpl aiModelService;
    // 声明可用平台
    private final AiPlatformService aiPlatformService;

    public AiChatServiceImpl(MongoDBChatMessageRepository mongoDBChatMessageRepository,
                             QwenAssistant qwenAssistant,
                             AiModelServiceImpl aiModelService,
                             AiPlatformService aiPlatformService
    ) {
        this.mongoDBChatMessageRepository = mongoDBChatMessageRepository;
        this.qwenAssistant = qwenAssistant;
        this.aiModelService = aiModelService;
        this.aiPlatformService = aiPlatformService;
    }

    /**
     * 处理聊天请求并返回流式响应
     *
     * @param model          使用的模型名称
     * @param platform       聊天平台标识
     * @param message        用户输入的聊天消息
     * @param conversationId 对话的唯一标识符
     * @return 包含聊天回复内容的 Flux 流
     */
    public Flux<String> chat(String model, String platform, String message, Long conversationId) {
        // 检查是否存在错误消息，如果存在则直接返回
        Flux<String> errorMessage = getStringFlux(model, platform);
        if (errorMessage != null) {
            return errorMessage;
        }

        log.info(" 命中 {} ......", platform);
        log.info(" 当前输入的模型为：{}", model);
        Flux<String> chat = qwenAssistant.chat(conversationId, message);
        // 启动流式响应并附加完成标记
        return chat.concatWith(Flux.just(COMPLETE_MESSAGE)); // 在流末尾添加完成标识
    }

    private Flux<String> getStringFlux(String model, String platform) {
        AiPlatform aiPlatform = this.aiPlatformService.getOne(Wrappers.<AiPlatform>lambdaQuery()
                .eq(AiPlatform::getPlatformCode, platform));
        if (aiPlatform == null) {
            return Flux.just(ERROR_MESSAGE);
        }
        AiModel aiModel = this.aiModelService.getOne(Wrappers.<AiModel>lambdaQuery()
                .eq(AiModel::getModelCode, model)
                .eq(AiModel::getPlatformCode, platform));
        if (aiModel == null) {
            return Flux.just(ERROR_MESSAGE);
        }
        return null;
    }


    @Override
    public Result<Object> create(Long userId) {
        // @formatter:off
        MongoDBChatMessages chatConversation = MongoDBChatMessages.builder()
                .memoryId(RandomUtil.randomNumbers(16))
                .title("新建对话")
                .createTime(LocalDateTime.now())
                .userId(userId)
                .messages(MapperUtils.write(Lists.newArrayList())).build();
        MongoDBChatMessages save = this.mongoDBChatMessageRepository.save(chatConversation);
        // @formatter:on
        return Result.ok(save.getMemoryId());
    }

    @Override
    public Result<Page<MongoDBChatMessages>> history(HistoryChatPage historyChatPage) {
        Pageable pageable = PageRequest.of(historyChatPage.getCurrent(), historyChatPage.getSize(),
                Sort.by(Sort.Direction.DESC, "createTime"));
        Page<MongoDBChatMessages> mongoDBChatConversatonPage
                = this.mongoDBChatMessageRepository.findByUserId(historyChatPage.getUserId(), pageable);
        return Result.ok(mongoDBChatConversatonPage);
    }

    @Override
    public Result<List<ChatConversationMessageVO>> historyDetail(String id) {
        String messages = this.mongoDBChatMessageRepository.findById(id).orElseThrow(() -> new RuntimeException("")).getMessages();
        List<ChatConversationMessageVO> chatMessageVO = JSON.parseArray(messages, ChatConversationMessageVO.class);
        return Result.ok(chatMessageVO);
    }

}
