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

import cn.hutool.core.util.StrUtil;
import com.breeze.boot.ai.service.AiChatKnowledgeService;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.model.FileInfo;
import com.breeze.boot.spring.ai.service.IRagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * 本地rag服务
 *
 * @author gaoweixuan
 * @since 2025/04/13
 */
@Slf4j
@Service("redisLocalRagService")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.ai.vector-store.redis", name = "enabled", havingValue = "true")
public class RedisLocalRagServiceImpl implements IRagService {

    private final RedisVectorStore redisDashScopeVectorStore;

    private final AiChatKnowledgeService aiChatDocService;

    public void importDoc(List<MultipartFile> files) {
        log.info("start import data");
        for (MultipartFile file : files) {
            processFile(file);
        }
    }

    @Async
    public void processFile(MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new BreezeBizException(ResultCode.FAIL);
        }
        String originalFilename = file.getOriginalFilename();
        if (Objects.isNull(originalFilename) || StrUtil.isBlankIfStr(originalFilename)) {
            throw new BreezeBizException(ResultCode.FAIL);
        }
        List<Document> splitDocuments;
        if (originalFilename.endsWith(".pdf")) {
            // 读取文档
            DocumentReader reader = new PagePdfDocumentReader(file.getResource());
            List<Document> documents = reader.get();
            // 分割文档
            splitDocuments = new TokenTextSplitter().apply(documents);
            log.info("已分割 {} 个文档", splitDocuments.size());
        } else if (originalFilename.endsWith(".md")) {
            Long id = this.saveFileInfo(originalFilename);
            // 读取文档
            DocumentReader reader = new MarkdownDocumentReader(file.getResource(),
                    MarkdownDocumentReaderConfig.builder()
                            .withAdditionalMetadata("refId", id)
                            .withAdditionalMetadata("year", "2025")
                            .withAdditionalMetadata("name", "私有知识库")
                            .build());
            List<Document> documents = reader.get();
            // 分割文档
            splitDocuments = new TokenTextSplitter().apply(documents);
            log.info("已分割 {} 个文档", splitDocuments.size());
        } else {
            log.error("不支持的文件类型");
            throw new BreezeBizException(ResultCode.FAIL);
        }
        if (splitDocuments.isEmpty()) {
            log.error("分割后的文档列表为空");
            throw new BreezeBizException(ResultCode.FAIL);
        }
        this.redisDashScopeVectorStore.add(splitDocuments);
    }

    private Long saveFileInfo(String originalFilename) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(originalFilename);
        Long id = aiChatDocService.saveAiChatKnowledge(fileInfo);
        return id;
    }

}