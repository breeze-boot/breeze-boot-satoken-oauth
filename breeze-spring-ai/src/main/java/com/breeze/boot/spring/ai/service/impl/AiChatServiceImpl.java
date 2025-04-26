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

import cn.hutool.core.util.RandomUtil;
import com.alibaba.cloud.ai.advisor.RetrievalRerankAdvisor;
import com.alibaba.cloud.ai.model.RerankModel;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.ai.model.entity.AiModel;
import com.breeze.boot.ai.model.entity.AiPlatform;
import com.breeze.boot.ai.service.AiPlatformService;
import com.breeze.boot.ai.service.impl.AiModelServiceImpl;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.MapperUtils;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.spring.ai.config.AiLoggingAdvisor;
import com.breeze.boot.spring.ai.config.ChatMessageAdvisor;
import com.breeze.boot.spring.ai.config.MongoDBChatMemory;
import com.breeze.boot.spring.ai.model.query.HistoryChatPage;
import com.breeze.boot.spring.ai.model.vo.ChatConversationMessageVO;
import com.breeze.boot.spring.ai.mongdb.MongoDBChatConversationRepository;
import com.breeze.boot.spring.ai.mongdb.entity.MongoDBChatConversation;
import com.breeze.boot.spring.ai.service.IAiChatService;
import com.breeze.boot.spring.ai.strategy.ChatClientStrategy;
import com.breeze.boot.spring.ai.strategy.ChatClientStrategyFactory;
import com.breeze.boot.spring.ai.tools.LocalDateTimeTools;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.rag.preretrieval.query.transformation.CompressionQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    @Value("classpath:/prompts/system-prompt.st")
    private Resource systemResource;

    // 常量定义
    private static final String ERROR_MESSAGE = "[error]";
    private static final String COMPLETE_MESSAGE = "[complete]";

    private final MongoDBChatConversationRepository mongoDBChatConversationRepository;

    private final RerankModel rerankModel;

    private final MongoDBChatMemory chatMemory;

    // 声明可用模型
    private final AiModelServiceImpl aiModelService;
    // 声明可用平台
    private final AiPlatformService aiPlatformService;

    private final ChatClientStrategyFactory esChatClientStrategyFactory;
    private final ChatClientStrategyFactory redisChatClientStrategyFactory;

    public AiChatServiceImpl(@Qualifier("esDashscopeVectorStore") VectorStore esDashscopeVectorStore,
                             @Qualifier("esOllamaVectorStore") VectorStore esOllamaVectorStore,
                             @Qualifier("redisDashScopeVectorStore") VectorStore redisDashScopeVectorStore,
                             @Qualifier("redisOllamaVectorStore") VectorStore redisOllamaVectorStore,
                             MongoDBChatConversationRepository mongoDBChatConversationRepository,
                             RerankModel rerankModel,
                             @Qualifier("dashscopeChatModel") ChatModel dashScopeChatModel,
                             @Qualifier("ollamaChatModel") ChatModel ollamaChatModel,
                             MongoDBChatMemory chatMemory,
                             AiModelServiceImpl aiModelService,
                             AiPlatformService aiPlatformService
    ) {
        this.mongoDBChatConversationRepository = mongoDBChatConversationRepository;
        this.rerankModel = rerankModel;
        this.chatMemory = chatMemory;
        this.aiModelService = aiModelService;
        this.aiPlatformService = aiPlatformService;
        this.esChatClientStrategyFactory = new ChatClientStrategyFactory(
                dashScopeChatModel, ollamaChatModel,
                esDashscopeVectorStore, esOllamaVectorStore
        );
        this.redisChatClientStrategyFactory = new ChatClientStrategyFactory(
                dashScopeChatModel, ollamaChatModel,
                redisDashScopeVectorStore, redisOllamaVectorStore
        );
    }

    /**
     * 处理聊天请求并返回流式响应
     *
     * @param model          使用的模型名称
     * @param platform       聊天平台标识
     * @param message        用户输入的聊天消息
     * @param conversationId 对话的唯一标识符
     * @param token          访问令牌
     * @return 包含聊天回复内容的 Flux 流
     */
    public Flux<String> chat(String model, String platform, String message, String conversationId, String token) {
        // 检查是否存在错误消息，如果存在则直接返回
        Flux<String> errorMessage = getStringFlux(model, platform);
        if (errorMessage != null) {
            return errorMessage;
        }

        // 使用策略模式构建 ChatClient 和获取 VectorStore
        // 根据平台信息从策略工厂中获取相应的策略
        ChatClientStrategy strategy = this.redisChatClientStrategyFactory.getStrategy(platform);
        // 使用策略构建 ChatClient 的构建器
        ChatClient.Builder chatBuilder = strategy.buildChatClient();
        // 使用策略获取向量存储
        VectorStore vectorStore = strategy.getVectorStore();
        log.info(" 命中 {} ......", platform);
        log.info(" 当前输入的模型为：{}", model);

        // 2. 准备模板和变量
        String systemTemplate;
        try {
            systemTemplate = this.systemResource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new BreezeBizException(ResultCode.NOT_FOUND);
        }

        // 构建压缩查询转换器
        CompressionQueryTransformer queryTransformer = CompressionQueryTransformer.builder()
                .chatClientBuilder(chatBuilder.build().mutate())
                .build();

        // 构建搜索请求
        SearchRequest searchRequest = SearchRequest.builder()
                .topK(4) // 设置返回的文档数量
                .query(message) // 设置查询的消息
                .build();

        // 构建向量存储文档检索器
        VectorStoreDocumentRetriever documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .similarityThreshold(0.50) // 设置相似度阈值
                .build();

        // 构建 ChatClient 并配置默认顾问
        ChatClient chatClient = chatBuilder
                .defaultAdvisors(
                        // 检索增强顾问，用于增强检索结果
                        RetrievalAugmentationAdvisor.builder()
                                .documentRetriever(documentRetriever)
                                .queryTransformers(queryTransformer)
                                .build(),
                        // 知识库检索重排顾问，用于对检索结果进行重排
                        new RetrievalRerankAdvisor(
                                vectorStore,
                                this.rerankModel,
                                // 使用用户原始问题 message 作为检索查询（Query）
                                searchRequest,
                                systemTemplate,
                                0.1
                        ),
                        // 日志顾问，用于记录聊天过程中的日志信息
                        new AiLoggingAdvisor()
                ).build();

        // 构建 ChatClient 请求规格
        ChatClient.ChatClientRequestSpec chatClientRequestSpec = chatClient
                .prompt(new Prompt(systemTemplate)) // 设置系统提示模板
                .options(ChatOptions.builder()
                        .model(model) // 设置使用的模型
                        .temperature(0.2) // 设置温度参数，控制生成结果的随机性
                        .topP(0.9) // 设置核采样参数，控制生成结果的多样性
                        .build())
                .user(message) // 设置用户输入的消息
                .advisors(new ChatMessageAdvisor(this.chatMemory, conversationId)) // 设置聊天消息顾问，用于处理对话上下文
                .tools(new LocalDateTimeTools()); // 设置工具，例如日期时间工具

        // 流式返回聊天回复内容，并在最后添加完成消息
        return chatClientRequestSpec
                .stream()
                .content()
                .concatWith(Flux.just(COMPLETE_MESSAGE));
    }

    @Nullable
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
    public Result<String> create(Long userId) {
        // @formatter:off
        MongoDBChatConversation chatConversation = MongoDBChatConversation.builder()
                .conversationId(RandomUtil.randomString(10))
                .title("新建对话")
                .createTime(LocalDateTime.now())
                .userId(userId)
                .messages(MapperUtils.write(Lists.newArrayList())).build();
        MongoDBChatConversation save = this.mongoDBChatConversationRepository.save(chatConversation);
        // @formatter:on
        return Result.ok(save.getConversationId());
    }

    @Override
    public Result<Page<MongoDBChatConversation>> history(HistoryChatPage historyChatPage) {
        Pageable pageable = PageRequest.of(historyChatPage.getCurrent(), historyChatPage.getSize(),
                Sort.by(Sort.Direction.DESC, "createTime"));
        Page<MongoDBChatConversation> mongoDBChatConversatonPage
                = this.mongoDBChatConversationRepository.findByUserId(historyChatPage.getUserId(), pageable);
        return Result.ok(mongoDBChatConversatonPage);
    }

    @Override
    public Result<List<ChatConversationMessageVO>> historyDetail(String id) {
        String messages = this.mongoDBChatConversationRepository.findById(id).orElseThrow(() -> new RuntimeException("")).getMessages();
        List<ChatConversationMessageVO> chatMessageVO = JSON.parseArray(messages, ChatConversationMessageVO.class);
        return Result.ok(chatMessageVO);
    }
}
