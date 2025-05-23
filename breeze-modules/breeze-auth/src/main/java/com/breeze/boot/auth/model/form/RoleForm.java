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

package com.breeze.boot.auth.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

/**
 * 系统角色表单
 *
 * @author gaoweixuan
 * @since 2021-12-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统角色表单")
public class RoleForm {

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不可为空")
    @Schema(description = "角色编码")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不可为空")
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 行数据权限类型
     */
    @Schema(description = "行数据权限类型")
    private String rowPermissionType;

    /**
     * 行数据权限IDs
     */
    @Schema(description = "行数据权限IDs")
    private Set<Long> rowPermissionIds;

    /**
     * 列数据权限IDs
     */
    @Schema(description = "列数据权限IDs")
    private Set<Long> columnPermissionIds;

}
