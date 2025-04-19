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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.ai.mapper.ChatDocMapper;
import com.breeze.boot.ai.model.converter.CharDocConverter;
import com.breeze.boot.ai.model.entity.AiChatDoc;
import com.breeze.boot.ai.model.form.ChatDocForm;
import com.breeze.boot.ai.model.query.AiChatDocQuery;
import com.breeze.boot.ai.model.vo.AiChatDocVO;
import com.breeze.boot.ai.service.IAiChatDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * AI聊天知识库管理服务 Impl
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
@Service
@RequiredArgsConstructor
public class AIChatDocServiceImpl extends ServiceImpl<ChatDocMapper, AiChatDoc> implements IAiChatDocService {

    private final CharDocConverter charDocConverter;

    /**
     * 列表页面
     */
    @Override
    public Page<AiChatDocVO> listPage(AiChatDocQuery query) {
        Page<AiChatDoc> page = new Page<>(query.getCurrent(), query.getSize());
        Page<AiChatDoc> aiChatDocPage = this.page(page, Wrappers.<AiChatDoc>lambdaQuery());
        return this.charDocConverter.entity2VOPage(aiChatDocPage);
    }

    @Override
    public boolean saveChatDoc(ChatDocForm form) {
        AiChatDoc chatDoc = AiChatDoc.builder()
                .docName(form.getDocName())
                .docUrl(form.getDocUrl()).build();
        return this.save(chatDoc);
    }

    @Override
    public AiChatDocVO getInfoById(Long docId) {
        return this.charDocConverter.entity2VO(this.getById(docId));
    }

    @Override
    public Boolean modifyChatDoc(Long id, ChatDocForm form) {
        AiChatDoc chatDoc = this.charDocConverter.form2Entity(form);
        chatDoc.setId(id);
        return this.updateById(chatDoc);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteChatDoc(List<Long> ids) {
        return this.removeBatchByIds(ids);
    }

}
