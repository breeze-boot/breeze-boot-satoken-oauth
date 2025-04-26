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

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.breeze.boot.core.model.IdBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统行数据权限规则实体
 *
 * @author gaoweixuan
 * @since 2022-10-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_row_permission", autoResultMap = true)
@Schema(description = "系统行数据权限规则实体")
public class SysRowPermission extends IdBaseModel<SysRowPermission> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 数据权限名称
     */
    @Schema(description = "数据权限名称")
    private String permissionName;

    /**
     * 数据权限编码
     */
    @Schema(description = "数据权限编码")
    private String permissionCode;

    /**
     * 数据权限自定义类型
     */
    @Schema(description = "数据权限自定义类型")
    private String customizesType;

    /**
     * 权限
     */
    @Schema(description = "权限")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONArray permissions;

}
