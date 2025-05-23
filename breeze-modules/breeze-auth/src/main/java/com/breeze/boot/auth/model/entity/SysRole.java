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

package com.breeze.boot.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.model.IdBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统角色实体
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
@Schema(description = "系统角色实体")
public class SysRole extends IdBaseModel<SysRole> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 行数据权限类型
     */
    @Schema(description = "行数据权限类型")
    private String rowPermissionType;

}
