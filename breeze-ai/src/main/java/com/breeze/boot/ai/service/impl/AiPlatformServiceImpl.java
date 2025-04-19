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
import com.breeze.boot.ai.mapper.AiPlatformMapper;
import com.breeze.boot.ai.model.converter.AiPlatformConverter;
import com.breeze.boot.ai.model.entity.AiPlatform;
import com.breeze.boot.ai.model.form.AiPlatformForm;
import com.breeze.boot.ai.model.query.AiPlatformQuery;
import com.breeze.boot.ai.model.vo.AiPlatformVO;
import com.breeze.boot.ai.service.AiPlatformService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AI平台 impl
 *
 * @author gaoweixuan
 * @since 2025-04-19
 */
@Service
@RequiredArgsConstructor
public class AiPlatformServiceImpl extends ServiceImpl<AiPlatformMapper, AiPlatform> implements AiPlatformService {

    private final AiPlatformConverter aiPlatformConverter;

    /**
     * 列表页面
     *
     * @param query AI平台 查询
     * @return {@link Page}<{@link AiPlatformVO }>
     */
    @Override
    @DymicSql
    public Page<AiPlatformVO> listPage(@ConditionParam AiPlatformQuery query) {
        Page<AiPlatform> page = new Page<>(query.getCurrent(), query.getSize());
        Page<AiPlatform> aiPlatformpage = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .orderByDesc(AiPlatform::getCreateTime)
                .page(page);
        return this.aiPlatformConverter.page2VOPage(aiPlatformpage);
    }

    /**
     * 按id获取信息
     *
     * @param aiPlatformId AI平台 id
     * @return {@link AiPlatformVO }
     */
    @Override
    public AiPlatformVO getInfoById(Long aiPlatformId) {
        return this.aiPlatformConverter.entity2VO(this.getById(aiPlatformId));
    }

    /**
     * 修改AI平台
     *
     * @param aiPlatformId AI平台ID
     * @param form         AI平台表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyAiPlatform(Long aiPlatformId, AiPlatformForm form) {
        AiPlatform aiPlatform = this.aiPlatformConverter.form2Entity(form);
        aiPlatform.setId(aiPlatformId);
        return this.updateById(aiPlatform);
    }

    /**
     * 通过IDS删除AI平台
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeAiPlatformByIds(List<Long> ids) {
        return Result.ok(this.removeByIds(ids));
    }

}