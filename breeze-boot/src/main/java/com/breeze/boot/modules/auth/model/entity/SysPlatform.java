/*
 * Copyright (c) 2023, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.modules.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.model.IdBaseModel;
import com.breeze.boot.mybatis.annotation.Audit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


/**
 * 系统平台实体
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Data
@Audit
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_platform")
@Schema(description = "系统平台实体")
public class SysPlatform extends IdBaseModel<SysPlatform> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 平台编码
     */
    @Schema(description = "平台编码")
    private String platformCode;

    /**
     * 平台名称
     */
    @Schema(description = "平台名称")
    private String platformName;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

}
