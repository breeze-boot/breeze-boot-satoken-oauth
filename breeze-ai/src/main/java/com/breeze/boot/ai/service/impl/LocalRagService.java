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

package com.breeze.boot.ai.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import com.breeze.boot.ai.service.RagService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.autoconfigure.vectorstore.elasticsearch.ElasticsearchVectorStoreProperties;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地rag服务
 *
 * @author gaoweixuan
 * @since 2025/04/13
 */
@Slf4j
@Service
public class LocalRagService implements RagService {

    private static final String TEXT_FIELD = "txt-content";
    private static final String VECTOR_FIELD = "embedding";

    private final VectorStore dashscopeVectorStore;
    private final ElasticsearchClient elasticsearchClient;
    private final ElasticsearchVectorStoreProperties options;

    public LocalRagService(VectorStore dashscopeVectorStore,
                           ElasticsearchClient elasticsearchClient,
                           ElasticsearchVectorStoreProperties options) {
        this.dashscopeVectorStore = dashscopeVectorStore;
        this.elasticsearchClient = elasticsearchClient;
        this.options = options;
    }

    @SneakyThrows
    @Override
    public void importDoc(MultipartFile file) {
        DocumentReader reader = new PagePdfDocumentReader(file.getResource());
        List<Document> documents = reader.get();
        // 读取文档
//        List<Document> documents = new TikaDocumentReader(file.getResource()).get();
        // 分割文档
        List<Document> splitDocuments = Lists.newArrayList();
        documents.forEach(document -> {
            splitDocuments.addAll(new TokenTextSplitter().split(documents.get(0)));
        });
        log.info("已分割 {} 个文档", splitDocuments.size());
        log.info("创建嵌入并保存到向量存储");
        // 若索引不存在则创建
       this.creatEsIndexIfAbsent();
        if (!splitDocuments.isEmpty()) {
            dashscopeVectorStore.add(splitDocuments);
        } else {
            // 处理文档列表为空的情况
            log.error("分割后的文档列表为空");
        }
    }

    /**
     * 若索引不存在则创建
     */
    private void creatEsIndexIfAbsent() {
        String indexName = getIndexName();
        validateIndexName(indexName);

        if (isIndexExists(indexName)) {
            log.debug("索引 {} 已存在，跳过创建", indexName);
            return;
        }

        IndexSettings indexSettings = createIndexSettings();
        Map<String, Property> properties = createIndexProperties();

        try {
            CreateIndexResponse indexResponse = createIndex(indexName, indexSettings, properties);
            if (!indexResponse.acknowledged()) {
                throw new RuntimeException("创建索引失败");
            }
            log.info("成功创建 Elasticsearch 索引 {}", indexName);
        } catch (IOException e) {
            log.error("创建索引失败", e);
            throw new RuntimeException(e);
        }
    }

    private String getIndexName() {
        return options.getIndexName();
    }

    private void validateIndexName(String indexName) {
        if (StringUtils.isBlank(indexName)) {
            throw new IllegalArgumentException("必须提供 Elasticsearch 索引名称");
        }
    }

    private boolean isIndexExists(String indexName) {
        try {
            return elasticsearchClient.indices().exists(idx -> idx.index(indexName)).value();
        } catch (IOException e) {
            log.error("检查索引是否存在时出错", e);
            throw new RuntimeException(e);
        }
    }

    private IndexSettings createIndexSettings() {
        return IndexSettings.of(settings ->
                settings.numberOfShards("1").numberOfReplicas("1"));
    }

    private Map<String, Property> createIndexProperties() {
        Integer dimsLength = options.getDimensions();
        String similarityAlgo = options.getSimilarity().name();

        // 定义索引属性
        Map<String, Property> properties = new HashMap<>();
        properties.put(VECTOR_FIELD, Property.of(property ->
                property.denseVector(DenseVectorProperty.of(dense ->
                        dense.index(true).dims(dimsLength).similarity(similarityAlgo)))));
        properties.put(TEXT_FIELD, Property.of(property -> property.text(TextProperty.of(t -> t))));

        // 定义元数据属性
        Map<String, Property> metadata = new HashMap<>();
        metadata.put("doc_ref", Property.of(property -> property.keyword(KeywordProperty.of(k -> k))));

        properties.put("metadata", Property.of(property ->
                property.object(ObjectProperty.of(op -> op.properties(metadata)))));

        return properties;
    }

    private CreateIndexResponse createIndex(String indexName, IndexSettings indexSettings, Map<String, Property> properties) throws IOException {
        return elasticsearchClient.indices().create(createIndexBuilder ->
                createIndexBuilder.index(indexName)
                        .settings(indexSettings)
                        .mappings(TypeMapping.of(mappings -> mappings.properties(properties))));
    }
}