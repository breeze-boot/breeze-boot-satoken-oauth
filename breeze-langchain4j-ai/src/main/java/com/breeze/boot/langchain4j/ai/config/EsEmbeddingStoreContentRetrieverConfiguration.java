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

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchEmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * es嵌入存储内容检索器配置
 *
 * @author gaoweixuan
 * @since 2025/04/29
 */
@Configuration
@RequiredArgsConstructor
public class EsEmbeddingStoreContentRetrieverConfiguration {

    private final ElasticsearchEmbeddingStore esEmbeddingStore;

    private final EmbeddingModel qwenEmbeddingModel;

    @Bean
    ContentRetriever esEmbeddingStoreContentRetriever() {
        // 创建一个 EmbeddingStoreContentRetriever 对象，用于从嵌入存储中检索内容
        return EmbeddingStoreContentRetriever.builder()
                // 设置用于生成嵌入向量的嵌入模型
                .embeddingModel(qwenEmbeddingModel)
                // 指定要使用的嵌入存储
                .embeddingStore(esEmbeddingStore)
                // 设置最大检索结果数量，这里表示最多返回 5 条匹配结果
                .maxResults(5)
                // 设置最小得分阈值，只有得分大于等于 0.5 的结果才会被返回
                .minScore(0.5)
                // 构建最终的 EmbeddingStoreContentRetriever 实例
                .build();
    }
}
