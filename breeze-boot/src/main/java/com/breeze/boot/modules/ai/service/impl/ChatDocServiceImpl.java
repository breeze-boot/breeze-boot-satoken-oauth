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

package com.breeze.boot.modules.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.modules.ai.mapper.ChatDocMapper;
import com.breeze.boot.modules.ai.model.entity.ChatDoc;
import com.breeze.boot.modules.ai.model.form.ChatDocForm;
import com.breeze.boot.modules.ai.model.mappers.CharDocMapStruct;
import com.breeze.boot.modules.ai.model.vo.ChatDocVO;
import com.breeze.boot.modules.ai.service.IChatDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AI聊天知识库管理服务 Impl
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
@Service
@RequiredArgsConstructor
public class ChatDocServiceImpl extends ServiceImpl<ChatDocMapper, ChatDoc> implements IChatDocService {

    private final CharDocMapStruct charDocMapStruct;

    @Override
    public List<ChatDoc> list() {
        return null;
    }

    @Override
    public boolean saveChatDoc(ChatDocForm chatDocForm) {
        ChatDoc chatDoc = ChatDoc.builder()
                .docId(chatDocForm.getDocId())
                .docName(chatDocForm.getDocName())
                .docUrl(chatDocForm.getDocUrl()).build();
        return this.save(chatDoc);
    }

    @Override
    public ChatDocVO getInfoById(Long docId) {
        return this.charDocMapStruct.entity2VO(this.getById(docId));
    }

    @Override
    public Boolean modifyChatDoc(Long id, ChatDocForm chatDocForm) {
        ChatDoc chatDoc = this.charDocMapStruct.form2Entity(chatDocForm);
        chatDoc.setId(id);
        return this.updateById(chatDoc);
    }

    @Override
    public Boolean deleteChatDoc(List<Long> ids) {
        return null;
    }

}
