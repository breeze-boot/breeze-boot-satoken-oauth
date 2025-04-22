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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.ai.mapper.AiChatDocMapper;
import com.breeze.boot.ai.model.entity.AiChatDoc;
import com.breeze.boot.ai.model.form.AiChatDocForm;
import com.breeze.boot.ai.model.converter.AiChatDocConverter;
import com.breeze.boot.ai.model.query.AiChatDocQuery;
import com.breeze.boot.ai.model.vo.AiChatDocVO;
import com.breeze.boot.ai.service.AiChatDocService;
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
public class AiChatDocServiceImpl extends ServiceImpl<AiChatDocMapper, AiChatDoc> implements AiChatDocService {

    private final AiChatDocConverter aiChatDocConverter;

    /**
     * 列表页面
     *
     * @param query 聊天文档查询
     * @return {@link Page}<{@link AiChatDocVO }>
     */
    @Override
    @DymicSql
    public Page<AiChatDocVO> listPage(@ConditionParam AiChatDocQuery query) {
        Page<AiChatDoc> page = new Page<>(query.getCurrent(), query.getSize());
        Page<AiChatDoc> aiChatDocpage = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                    .orderByDesc(AiChatDoc::getCreateTime)
                    .page(page);
        return this.aiChatDocConverter.page2VOPage(aiChatDocpage);
    }

    /**
     * 按id获取信息
     *
     * @param aiChatDocId 聊天文档 id
     * @return {@link AiChatDocVO }
     */
    @Override
    public AiChatDocVO getInfoById(Long aiChatDocId) {
        return this.aiChatDocConverter.entity2VO(this.getById(aiChatDocId));
    }

    /**
     * 保存聊天文档
     *
     * @param form 平台形式
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveAiChatDoc(AiChatDocForm form) {
        return this.save(aiChatDocConverter.form2Entity(form));
    }

    /**
     * 修改聊天文档
     *
     * @param aiChatDocId 聊天文档ID
     * @param form 聊天文档表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifyAiChatDoc(Long aiChatDocId, AiChatDocForm form) {
        AiChatDoc aiChatDoc = this.aiChatDocConverter.form2Entity(form);
        aiChatDoc.setId(aiChatDocId);
        return this.updateById(aiChatDoc);
    }

    /**
     * 通过IDS删除聊天文档
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeAiChatDocByIds(List<Long> ids) {
        return Result.ok(this.removeByIds(ids));
    }

}