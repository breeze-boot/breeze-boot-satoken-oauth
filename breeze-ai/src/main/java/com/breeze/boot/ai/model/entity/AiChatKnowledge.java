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

package com.breeze.boot.ai.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.model.IdBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 聊天文档 实体
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ai_chat_knowledge")
@Schema(description = "聊天文档 实体")
public class AiChatKnowledge extends IdBaseModel<AiChatKnowledge> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文档文件ID
     */
    private String docId;
    /**
     * 文档标题
     */
    private String docName;
    /**
     * 文档地址
     */
    private String docUrl;
    /**
     * 向量数据库ID
     */
    private String refDocId;

}