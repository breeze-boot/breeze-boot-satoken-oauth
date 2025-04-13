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

package com.breeze.boot.modules.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.modules.ai.model.entity.ChatDoc;
import com.breeze.boot.modules.ai.model.form.ChatDocForm;
import com.breeze.boot.modules.ai.model.vo.ChatDocVO;

import java.util.List;

/**
 * AI聊天知识库管理服务
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
public interface IChatDocService extends IService<ChatDoc> {

    List<ChatDoc> list();

    boolean saveChatDoc(ChatDocForm chatDocForm);

    ChatDocVO getInfoById(Long docId);

    Boolean modifyChatDoc(Long id,  ChatDocForm chatDocForm);

    Boolean deleteChatDoc(List<Long> ids);

}
