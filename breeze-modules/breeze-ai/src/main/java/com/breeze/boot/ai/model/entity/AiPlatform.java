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
 * AI平台 实体
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "ai_platform")
@Schema(description = "AI平台 实体")
public class AiPlatform extends IdBaseModel<AiPlatform> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ai平台编码
     */
    private String platformCode;
    /**
     * ai平台名称
     */
    private String platformName;
    /**
     * 描述
     */
    private String description;
    
}