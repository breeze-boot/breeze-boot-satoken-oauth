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

package com.breeze.boot.system.model.query;

import com.breeze.boot.core.model.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 系统参数配置表 查询参数
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统参数配置表查询参数")
public class SysConfigQuery extends PageQuery {

    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 参数分类（如数据库配置/安全策略）
     */
    private String paramCateg;
    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 参数值
     */
    private String paramValue;
    /**
     * 所属环境编码
     */
    private String envCode;
    /**
     * 数据类型（字符串/整数/布尔值等）
     */
    private String dataType;
    /**
     * 是否必填（1=是，0=否）
     */
    private Integer isRequired;
    /**
     * 状态（1=启用，0=禁用）
     */
    private Integer status;

}