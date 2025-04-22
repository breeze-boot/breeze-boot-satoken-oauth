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
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.ai.model.entity.AiChatDoc;
import com.breeze.boot.ai.model.form.AiChatDocForm;
import com.breeze.boot.ai.model.query.AiChatDocQuery;
import com.breeze.boot.ai.model.vo.AiChatDocVO;

import java.util.List;

/**
 * 聊天文档 服务
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
public interface AiChatDocService extends IService<AiChatDoc> {

    /**
     * 列表页面
     *
     * @param query 聊天文档查询
     * @return {@link Page}<{@link AiChatDocVO }>
     */
    Page<AiChatDocVO> listPage(AiChatDocQuery query);

    /**
     * 按id获取信息
     *
     * @param aiChatDocId 聊天文档id
     * @return {@link AiChatDocVO }
     */
    AiChatDocVO getInfoById(Long aiChatDocId);

    /**
     * 保存聊天文档
     *
     * @param form 平台形式
     * @return {@link Boolean }
     */
    Boolean saveAiChatDoc(AiChatDocForm form);

    /**
     * 修改聊天文档
     *
     * @param aiChatDocId 聊天文档ID
     * @param form 聊天文档表单
     * @return {@link Boolean }
     */
    Boolean modifyAiChatDoc(Long aiChatDocId, AiChatDocForm form);

    /**
     * 通过IDS删除聊天文档
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    Result<Boolean> removeAiChatDocByIds(List<Long> ids);

}