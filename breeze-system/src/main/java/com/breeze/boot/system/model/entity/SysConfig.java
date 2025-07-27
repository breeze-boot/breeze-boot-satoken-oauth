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

package com.breeze.boot.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.model.IdBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统参数配置表 实体
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_config")
@Schema(description = "系统参数配置表 实体")
public class SysConfig extends IdBaseModel<SysConfig> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数分类
     */
    private Long categId;
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
     * 参数说明
     */
    private String description;
    /**
     * 状态（1=启用，0=禁用）
     */
    private Integer status;

}