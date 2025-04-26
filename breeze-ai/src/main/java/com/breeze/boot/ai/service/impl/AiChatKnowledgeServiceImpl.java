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

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.ai.mapper.AiChatKnowledgeMapper;
import com.breeze.boot.ai.model.converter.AiChatKnowledgeConverter;
import com.breeze.boot.ai.model.entity.AiChatKnowledge;
import com.breeze.boot.ai.model.query.AiChatKnowledgeQuery;
import com.breeze.boot.ai.model.vo.AiChatKnowledgeVO;
import com.breeze.boot.ai.service.AiChatKnowledgeService;
import com.breeze.boot.core.model.FileInfo;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 聊天文档 impl
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
@Service
@RequiredArgsConstructor
public class AiChatKnowledgeServiceImpl extends ServiceImpl<AiChatKnowledgeMapper, AiChatKnowledge> implements AiChatKnowledgeService {

    private final AiChatKnowledgeConverter aiChatKnowledgeConverter;

    /**
     * 列表页面
     *
     * @param query 聊天文档查询
     * @return {@link Page}<{@link AiChatKnowledgeVO }>
     */
    @Override
    @DymicSql
    public Page<AiChatKnowledgeVO> listPage(@ConditionParam AiChatKnowledgeQuery query) {
        Page<AiChatKnowledge> page = new Page<>(query.getCurrent(), query.getSize());
        Page<AiChatKnowledge> aiChatKnowledgepage = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                    .orderByDesc(AiChatKnowledge::getCreateTime)
                    .page(page);
        return this.aiChatKnowledgeConverter.page2VOPage(aiChatKnowledgepage);
    }

    /**
     * 按id获取信息
     *
     * @param aiChatKnowledgeId 聊天文档 id
     * @return {@link AiChatKnowledgeVO }
     */
    @Override
    public AiChatKnowledgeVO getInfoById(Long aiChatKnowledgeId) {
        return this.aiChatKnowledgeConverter.entity2VO(this.getById(aiChatKnowledgeId));
    }

    /**
     * 保存聊天文档
     *
     * @param fileInfo 平台表单
     * @return {@link Long }
     */
    @Override
    public Long saveAiChatKnowledge(FileInfo fileInfo) {
        AiChatKnowledge aiChatKnowledge = new AiChatKnowledge();
        aiChatKnowledge.setDocUrl(fileInfo.getUrl());
        aiChatKnowledge.setDocName(fileInfo.getName());
        this.save(aiChatKnowledge);
        return aiChatKnowledge.getId();
    }

    /**
     * 通过IDS删除聊天文档
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeAiChatKnowledgeByIds(List<Long> ids) {
        return Result.ok(this.removeByIds(ids));
    }

}