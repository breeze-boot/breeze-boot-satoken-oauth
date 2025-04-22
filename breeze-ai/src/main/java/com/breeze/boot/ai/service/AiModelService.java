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
import com.breeze.boot.ai.model.entity.AiModel;
import com.breeze.boot.ai.model.form.AiModelForm;
import com.breeze.boot.ai.model.query.AiModelQuery;
import com.breeze.boot.ai.model.vo.AiModelVO;

import java.util.List;

/**
 * AI模型 服务
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
public interface AiModelService extends IService<AiModel> {

    /**
     * 列表页面
     *
     * @param query AI模型查询
     * @return {@link Page}<{@link AiModelVO }>
     */
    Page<AiModelVO> listPage(AiModelQuery query);

    /**
     * 按id获取信息
     *
     * @param modelId AI模型id
     * @return {@link AiModelVO }
     */
    AiModelVO getInfoById(Long modelId);

    /**
     * 保存AI模型
     *
     * @param form 平台形式
     * @return {@link Boolean }
     */
    Boolean saveAiModel(AiModelForm form);

    /**
     * 修改AI模型
     *
     * @param aiModelId AI模型ID
     * @param form AI模型表单
     * @return {@link Boolean }
     */
    Boolean modifyAiModel(Long aiModelId, AiModelForm form);

    /**
     * 通过IDS删除AI模型
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    Result<Boolean> removeAiModelByIds(List<Long> ids);

}