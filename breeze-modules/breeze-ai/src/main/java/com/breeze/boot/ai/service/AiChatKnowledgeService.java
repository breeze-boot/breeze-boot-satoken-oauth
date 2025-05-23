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

package com.breeze.boot.ai.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.ai.model.entity.AiChatKnowledge;
import com.breeze.boot.ai.model.vo.AiChatKnowledgeVO;
import com.breeze.boot.core.model.FileInfo;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.ai.model.query.AiChatKnowledgeQuery;

import java.util.List;

/**
 * 聊天文档 服务
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
public interface AiChatKnowledgeService extends IService<AiChatKnowledge> {

    /**
     * 列表页面
     *
     * @param query 聊天文档查询
     * @return {@link Page}<{@link AiChatKnowledgeVO }>
     */
    Page<AiChatKnowledgeVO> listPage(AiChatKnowledgeQuery query);

    /**
     * 按id获取信息
     *
     * @param aiChatKnowledgeId 聊天文档id
     * @return {@link AiChatKnowledgeVO }
     */
    AiChatKnowledgeVO getInfoById(Long aiChatKnowledgeId);

    /**
     * 保存聊天文档
     *
     * @param form 平台表单
     * @return {@link Long }
     */
    Long saveAiChatKnowledge(FileInfo form);

    /**
     * 通过IDS删除聊天文档
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    Result<Boolean> removeAiChatKnowledgeByIds(List<Long> ids);

}