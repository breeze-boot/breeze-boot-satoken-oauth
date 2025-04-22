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

package com.breeze.boot.ai.model.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.ai.model.entity.AiChatDoc;
import com.breeze.boot.ai.model.form.AiChatDocForm;
import com.breeze.boot.ai.model.vo.AiChatDocVO;
import org.mapstruct.Mapper;

/**
 * 聊天文档 转换器
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
@Mapper(componentModel = "spring" )
public interface AiChatDocConverter {

    Page<AiChatDocVO> page2VOPage(Page<AiChatDoc> aiChatDocPage);

    AiChatDoc form2Entity(AiChatDocForm form);

    AiChatDocVO entity2VO(AiChatDoc aiChatDoc);

}
