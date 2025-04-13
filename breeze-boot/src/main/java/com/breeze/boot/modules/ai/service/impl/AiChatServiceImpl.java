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

package com.breeze.boot.modules.ai.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.cloud.ai.advisor.RetrievalRerankAdvisor;
import com.alibaba.cloud.ai.model.RerankModel;
import com.alibaba.fastjson.JSON;
import com.breeze.boot.core.base.UserPrincipal;
import com.breeze.boot.core.utils.MapperUtils;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.ai.config.AiLoggingAdvisor;
import com.breeze.boot.modules.ai.config.ChatMessageAdvisor;
import com.breeze.boot.modules.ai.config.MongoDBChatMemory;
import com.breeze.boot.modules.ai.model.entity.MongoDBChatConversation;
import com.breeze.boot.modules.ai.model.query.HistoryChatPage;
import com.breeze.boot.modules.ai.model.vo.ChatConversationMessageVO;
import com.breeze.boot.modules.ai.mongdb.MongoDBChatConversationRepository;
import com.breeze.boot.modules.ai.service.IAiChatService;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.transformation.CompressionQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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

    private final ChatClient chatClient;

    private final MongoDBChatMemory mongoDBChatMemory;

    private final VectorStore vectorStore;

    private final MongoDBChatConversationRepository mongoDBChatConversationRepository;

    private final RerankModel rerankModel;

    private final ChatModel chatModel;

    private final MongoDBChatMemory chatMemory;

    public AiChatServiceImpl(ChatClient chatClient,
                             MongoDBChatMemory mongoDBChatMemory,
                             VectorStore vectorStore,
                             MongoDBChatConversationRepository mongoDBChatConversationRepository,
                             RerankModel rerankModel,
                             @Qualifier("dashscopeChatModel")
                             ChatModel chatModel,
                             MongoDBChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.mongoDBChatMemory = mongoDBChatMemory;
        this.vectorStore = vectorStore;
        this.mongoDBChatConversationRepository = mongoDBChatConversationRepository;
        this.rerankModel = rerankModel;
        this.chatModel = chatModel;
        this.chatMemory = chatMemory;
    }

    @Override
    public Flux<String> chat(String conversationId, String message) {
        // @formatter:off
        Flux<String> content = this.chatClient.prompt()
                .user(message)
                .system(promptSystemSpec -> promptSystemSpec.param("current_date", LocalDate.now().toString()))
                .advisors(new ChatMessageAdvisor(mongoDBChatMemory, conversationId))
                .stream().content();
        // @formatter:on
        return content.concatWith(Flux.just("[complete]"));
    }

    @SneakyThrows
    public Flux<String> chat(String message, String conversationId, String token) {
        ChatClient.Builder chatBuilder = ChatClient.builder(chatModel);
        SearchRequest searchRequest = SearchRequest.builder().query(message).build();

        /*一、查询重写 简短精简描述*/
        Query query = new Query(searchRequest.getQuery());

        // 创建查询重写转换器
        QueryTransformer rewriteQueryTransformer = RewriteQueryTransformer.builder()
                .chatClientBuilder(chatBuilder)
                .build();

        Query transformedQuery;
        try {
            // 执行查询重写
            transformedQuery = rewriteQueryTransformer.transform(query);
            // 输出重写后的查询
            log.info("\n 输出重写后的查询: {}", transformedQuery.text());
        } catch (Exception e) {
            log.error("查询重写过程中出现异常", e);
            return Flux.error(e);
        }

        /*三、创建查询转换器*/
        // QueryTransformer用于将带有上下文的查询转换为完整的独立查询
        QueryTransformer compressionQueryTransformer = CompressionQueryTransformer.builder()
                .chatClientBuilder(chatBuilder)
                .build();

        try {
            // 执行查询转换
            // 将模糊的代词引用（"这个小区"）转换为明确的实体名称（"碧海湾小区"）
            transformedQuery = compressionQueryTransformer.transform(transformedQuery);
        } catch (Exception e) {
            log.error("查询转换过程中出现异常", e);
            return Flux.error(e);
        }

        Advisor advisor = RetrievalAugmentationAdvisor.builder()
                // 配置查询增强器
                .queryAugmenter(ContextualQueryAugmenter.builder()
                        .allowEmptyContext(true)        // 允许空上下文查询
                        .build())
                // 配置文档检索器
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        .similarityThreshold(0.5)       // 相似度阈值
                        .topK(3)                        // 返回文档数量
                        .build())
                .build();


        String promptTemplate = systemResource.getContentAsString(StandardCharsets.UTF_8);
        searchRequest = SearchRequest.builder().query(transformedQuery.text()).build();

        Object loginId = StpUtil.getLoginIdByToken(token);
        SaSession saSession = StpUtil.getSessionByLoginId(loginId);
        String username = ((UserPrincipal) (saSession.getDataMap().get("userPrincipal"))).getUsername();

        Flux<String> content = chatBuilder
                .defaultSystem("""
                         你是一位专业的知识库顾问，请注意：
                         1. 准确理解用户需求
                         2. 结合参考资料
                         3. 提供专业解释
                         4. 考虑实用性
                         5. 提供替代方案
                         今日：{current_date}，您正在与{username}沟通
                        """)
                .defaultAdvisors(
                        advisor,
                        new AiLoggingAdvisor(),
                        new RetrievalRerankAdvisor(vectorStore, rerankModel, searchRequest, promptTemplate, 0.01)
                )
                .defaultFunctions("currentWeather").build()
                .prompt()
                .user(searchRequest.getQuery())
                .system(promptSystemSpec -> promptSystemSpec.param("current_date", LocalDate.now().toString()))
                .system(promptSystemSpec -> promptSystemSpec.param("username", username))
                .advisors(new ChatMessageAdvisor(chatMemory, conversationId))
                .stream().content();

        return content.concatWith(Flux.just("[complete]"));
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
        Pageable pageable = PageRequest.of(historyChatPage.getCurrent(), historyChatPage.getSize(), Sort.by(Sort.Direction.DESC, "createTime"));
        Page<MongoDBChatConversation> mongoDBChatConversatonPage = this.mongoDBChatConversationRepository.findByUserId(historyChatPage.getUserId(), pageable);
        return Result.ok(mongoDBChatConversatonPage);
    }

    @Override
    public Result<List<ChatConversationMessageVO>> historyDetail(String id) {
        String messages = this.mongoDBChatConversationRepository.findById(id).orElseThrow(() -> new RuntimeException("")).getMessages();
        List<ChatConversationMessageVO> chatMessageVO = JSON.parseArray(messages, ChatConversationMessageVO.class);
        return Result.ok(chatMessageVO);
    }
}
