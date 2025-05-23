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
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.ai.mapper.AiModelMapper;
import com.breeze.boot.ai.model.entity.AiModel;
import com.breeze.boot.ai.model.form.AiModelForm;
import com.breeze.boot.ai.model.converter.AiModelConverter;
import com.breeze.boot.ai.model.query.AiModelQuery;
import com.breeze.boot.ai.model.vo.AiModelVO;
import com.breeze.boot.ai.service.AiModelService;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AI模型 impl
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
@Service
@RequiredArgsConstructor
public class AiModelServiceImpl extends ServiceImpl<AiModelMapper, AiModel> implements AiModelService {

    private final AiModelConverter aiModelConverter;

    /**
     * 列表页面
     *
     * @param query AI模型查询
     * @return {@link Page}<{@link AiModelVO }>
     */
    @Override
    @DymicSql
    public Page<AiModelVO> listPage(@ConditionParam AiModelQuery query) {
        Page<AiModel> page = new Page<>(query.getCurrent(), query.getSize());
        Page<AiModel> aiModelpage = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                    .orderByDesc(AiModel::getCreateTime)
                    .page(page);
        return this.aiModelConverter.page2VOPage(aiModelpage);
    }

    /**
     * 按id获取信息
     *
     * @param modelId AI模型 id
     * @return {@link AiModelVO }
     */
    @Override
    public AiModelVO getInfoById(Long modelId) {
        return this.aiModelConverter.entity2VO(this.getById(modelId));
    }

    /**
     * 保存AI模型
     *
     * @param form 平台表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveAiModel(AiModelForm form) {
        return this.save(aiModelConverter.form2Entity(form));
    }

    /**
     * 修改AI模型
     *
     * @param aiModelId AI模型ID
     * @param form AI模型表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyAiModel(Long aiModelId, AiModelForm form) {
        AiModel aiModel = this.aiModelConverter.form2Entity(form);
        aiModel.setId(aiModelId);
        return this.updateById(aiModel);
    }

    /**
     * 通过IDS删除AI模型
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeAiModelByIds(List<Long> ids) {
        return Result.ok(this.removeByIds(ids));
    }

}