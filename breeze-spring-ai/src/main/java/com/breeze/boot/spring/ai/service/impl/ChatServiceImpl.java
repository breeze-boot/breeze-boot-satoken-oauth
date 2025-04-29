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
import com.breeze.boot.spring.ai.tools.WeatherTools;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
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
public class ChatServiceImpl implements IAiChatService {

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

    public ChatServiceImpl(@Qualifier("esDashscopeVectorStore") VectorStore esDashscopeVectorStore,
                           @Qualifier("esOllamaVectorStore") VectorStore esOllamaVectorStore,
                           @Qualifier("redisDashScopeVectorStore") VectorStore redisDashScopeVectorStore,
                           @Qualifier("redisOllamaVectorStore") VectorStore redisOllamaVectorStore,
                           @Qualifier("dashscopeChatModel") ChatModel dashScopeChatModel,
                           @Qualifier("ollamaChatModel") ChatModel ollamaChatModel,
                           MongoDBChatConversationRepository mongoDBChatConversationRepository,
                           AiPlatformService aiPlatformService,
                           AiModelServiceImpl aiModelService,
                           MongoDBChatMemory chatMemory,
                           RerankModel rerankModel
    ) {
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
        this.mongoDBChatConversationRepository = mongoDBChatConversationRepository;
    }

    /**
     * 处理聊天请求并返回流式响应
     *
     * @param model          使用的模型名称（如"qwen-max"）
     * @param platform       聊天平台标识（如"dashscope"或"ollama"）
     * @param message        用户输入的原始聊天消息内容
     * @param conversationId 对话的唯一标识符（用于关联多轮对话）
     * @return 包含逐字流式返回的聊天回复内容及最终完成标记的Flux流
     */
    public Flux<String> chat(String model, String platform, String message, String conversationId) {
        // 检查参数或配置错误，若存在直接返回错误信息流
        Flux<String> errorMessage = this.check(model, platform);
        if (errorMessage != null) {
            return errorMessage;
        }

        // 使用策略模式根据平台选择对应实现，构建聊天客户端和向量存储
        ChatClientStrategy strategy = this.redisChatClientStrategyFactory.getStrategy(platform);
        ChatClient.Builder chatBuilder = strategy.buildChatClient();
        VectorStore vectorStore = strategy.getVectorStore();
        log.info(" 命中 {} ......", platform);
        log.info(" 当前输入的模型为：{}", model);

        RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                // 创建向量数据库检索器，设置0.5相似度阈值过滤相关文档
                .documentRetriever(
                        VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore) // 设置向量存储器
                                .similarityThreshold(0.50) // 设置相似度阈值，过滤掉相似度低于0.5的文档
                                .topK(3) // 设置返回相关文档数量
                                .build()
                )
                // 构建查询压缩转换器和检索增强顾问，用于优化检索结果
                .queryTransformers(CompressionQueryTransformer.builder()
                        .chatClientBuilder(chatBuilder.build().mutate())
                        .build()
                )
                .build();

        // 创建知识库重排序顾问，使用重排序模型对检索结果重新评分
        SearchRequest searchRequest = SearchRequest.builder()
                .topK(1) // 设置返回相关文档数量
                .similarityThresholdAll() // 设置相似度阈值，过滤掉相似度低于0.5的文档
                .query(message) // 设置查询内容
                .build();
        RetrievalRerankAdvisor retrievalRerankAdvisor = new RetrievalRerankAdvisor(
                vectorStore, // 设置向量存储器，用于获取相关文档
                this.rerankModel, // 设置重排序模型，用于对检索结果重新评分
                searchRequest, // 设置检索请求，用于获取相关文档
                message, // 设置用户输入的提示信息，用于重排序
                0.1 // 设置重排序阈值，过滤
        );

        // 配置聊天客户端核心组件：系统提示模板和多个顾问
        ChatClient chatClient = chatBuilder
                .defaultSystem(this.getSystemTemplate()) // 应用预设系统指令模板
                .defaultAdvisors(
                        retrievalAugmentationAdvisor, // 检索增强
                        retrievalRerankAdvisor,       // 结果重排序
                        new AiLoggingAdvisor()        // 日志记录
                ).build();

        // 构建具体的聊天请求配置，包含模型参数和扩展功能
        ChatOptions chatOptions = ChatOptions.builder()
                .model(model)
                .build();
        ChatClient.ChatClientRequestSpec chatClientRequestSpec = chatClient
                .prompt()
                .options(chatOptions)
                .user(message)
                .system(promptSystemSpec -> promptSystemSpec.param("currentDate", LocalDateTime.now())) // 注入当前时间参数
                .advisors(new ChatMessageAdvisor(this.chatMemory, conversationId)) // 对话上下文持久化
                .tools(new WeatherTools()); // 集成天气查询工具

        // 启动流式响应并附加完成标记
        return chatClientRequestSpec
                .stream()
                .content()
                .concatWith(Flux.just(COMPLETE_MESSAGE)); // 在流末尾添加完成标识
    }


    @NotNull
    private String getSystemTemplate() {
        String systemTemplate;
        try {
            systemTemplate = this.systemResource.getContentAsString(StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new BreezeBizException(ResultCode.NOT_FOUND);
        }
        return systemTemplate;
    }

    @Nullable
    private Flux<String> check(String model, String platform) {
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
                .conversationId(RandomUtil.randomNumbers(16))
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
