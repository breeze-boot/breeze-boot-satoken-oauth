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

package com.breeze.boot.spring.ai.config;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.breeze.boot.spring.ai.config.properties.BreezeRedisVectorStoreProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.ai.vectorstore.redis", name = "enabled", havingValue = "true")
public class RedisVectorStoreAutoConfiguration {

    private final BreezeRedisVectorStoreProperties properties;

    @Bean
    public JedisPooled jedisPooled() {
        log.info("Redis host: {}, port: {}", properties.getHost(), properties.getPort());
        return new JedisPooled(properties.getHost(), properties.getPort(), "default", properties.getPassword());
    }

    @Bean
    public TokenCountBatchingStrategy batchingStrategy() {
        return new TokenCountBatchingStrategy();
    }

    @Bean
    @Qualifier("redisDashScopeVectorStore")
    public RedisVectorStore redisDashScopeVectorStore(JedisPooled jedisPooled, DashScopeEmbeddingModel embeddingModel, TokenCountBatchingStrategy batchingStrategy) {
        log.info("create redis DashScope vector store");
        return RedisVectorStore.builder(jedisPooled, embeddingModel)
                .indexName(properties.getIndex())
                .prefix(properties.getPrefix()) //
                .batchingStrategy(batchingStrategy)
                .metadataFields(
                        RedisVectorStore.MetadataField.tag("name"),
                        RedisVectorStore.MetadataField.numeric("year")
                )
                .initializeSchema(properties.isInitializeSchema())
                .batchingStrategy(new TokenCountBatchingStrategy())
                .build();
    }

    @Bean
    @Qualifier("redisOllamaVectorStore")
    public RedisVectorStore redisOllamaVectorStore(JedisPooled jedisPooled, OllamaEmbeddingModel embeddingModel, TokenCountBatchingStrategy batchingStrategy) {
        log.info("create redis Ollama vector store");
        return RedisVectorStore.builder(jedisPooled, embeddingModel)
                .indexName(properties.getIndex())
                .prefix(properties.getPrefix()) //
                .batchingStrategy(batchingStrategy)
                .metadataFields(
                        RedisVectorStore.MetadataField.tag("name"),
                        RedisVectorStore.MetadataField.numeric("year")
                )
                .initializeSchema(properties.isInitializeSchema())
                .batchingStrategy(new TokenCountBatchingStrategy())
                .build();
    }

}
