package com.breeze.boot.ai.config;

import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestClient;
import org.springframework.ai.autoconfigure.vectorstore.elasticsearch.ElasticsearchVectorStoreProperties;
import org.springframework.ai.embedding.BatchingStrategy;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStoreOptions;
import org.springframework.ai.vectorstore.observation.VectorStoreObservationConvention;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@RequiredArgsConstructor
public class EsVectorStoreAutoConfiguration {

    private final ElasticsearchVectorStoreProperties properties;

    @Bean
    ElasticsearchVectorStore openAiVectorStore(RestClient restClient,
                                               OpenAiEmbeddingModel embeddingModel, ObjectProvider<ObservationRegistry> observationRegistry,
                                               ObjectProvider<VectorStoreObservationConvention> customObservationConvention,
                                               BatchingStrategy batchingStrategy) {
        ElasticsearchVectorStoreOptions elasticsearchVectorStoreOptions = new ElasticsearchVectorStoreOptions();

        if (StringUtils.hasText(properties.getIndexName())) {
            elasticsearchVectorStoreOptions.setIndexName(properties.getIndexName());
        }
        if (properties.getDimensions() != null) {
            elasticsearchVectorStoreOptions.setDimensions(properties.getDimensions());
        }
        if (properties.getSimilarity() != null) {
            elasticsearchVectorStoreOptions.setSimilarity(properties.getSimilarity());
        }

        return ElasticsearchVectorStore.builder(restClient, embeddingModel)
                .options(elasticsearchVectorStoreOptions)
                .initializeSchema(properties.isInitializeSchema())
                .observationRegistry(observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP))
                .customObservationConvention(customObservationConvention.getIfAvailable(() -> null))
                .batchingStrategy(batchingStrategy)
                .build();
    }

    @Bean
    ElasticsearchVectorStore dashscopeVectorStore(RestClient restClient,
                                                  DashScopeEmbeddingModel embeddingModel, ObjectProvider<ObservationRegistry> observationRegistry,
                                                  ObjectProvider<VectorStoreObservationConvention> customObservationConvention,
                                                  BatchingStrategy batchingStrategy) {
        ElasticsearchVectorStoreOptions elasticsearchVectorStoreOptions = new ElasticsearchVectorStoreOptions();

        if (StringUtils.hasText(properties.getIndexName())) {
            elasticsearchVectorStoreOptions.setIndexName(properties.getIndexName());
        }
        if (properties.getDimensions() != null) {
            elasticsearchVectorStoreOptions.setDimensions(properties.getDimensions());
        }
        if (properties.getSimilarity() != null) {
            elasticsearchVectorStoreOptions.setSimilarity(properties.getSimilarity());
        }

        return ElasticsearchVectorStore.builder(restClient, embeddingModel)
                .options(elasticsearchVectorStoreOptions)
                .initializeSchema(properties.isInitializeSchema())
                .observationRegistry(observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP))
                .customObservationConvention(customObservationConvention.getIfAvailable(() -> null))
                .batchingStrategy(batchingStrategy)
                .build();
    }


    @Bean
    ElasticsearchVectorStore ollamaVectorStore(RestClient restClient,
                                               OllamaEmbeddingModel embeddingModel, ObjectProvider<ObservationRegistry> observationRegistry,
                                               ObjectProvider<VectorStoreObservationConvention> customObservationConvention,
                                               BatchingStrategy batchingStrategy) {
        ElasticsearchVectorStoreOptions elasticsearchVectorStoreOptions = new ElasticsearchVectorStoreOptions();

        if (StringUtils.hasText(properties.getIndexName())) {
            elasticsearchVectorStoreOptions.setIndexName(properties.getIndexName());
        }
        if (properties.getDimensions() != null) {
            elasticsearchVectorStoreOptions.setDimensions(properties.getDimensions());
        }
        if (properties.getSimilarity() != null) {
            elasticsearchVectorStoreOptions.setSimilarity(properties.getSimilarity());
        }

        return ElasticsearchVectorStore.builder(restClient, embeddingModel)
                .options(elasticsearchVectorStoreOptions)
                .initializeSchema(properties.isInitializeSchema())
                .observationRegistry(observationRegistry.getIfUnique(() -> ObservationRegistry.NOOP))
                .customObservationConvention(customObservationConvention.getIfAvailable(() -> null))
                .batchingStrategy(batchingStrategy)
                .build();
    }
}
