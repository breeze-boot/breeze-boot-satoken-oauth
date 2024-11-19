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

package com.breeze.boot.modules.auth.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.auth.model.entity.SysRoleMenu;
import com.breeze.boot.modules.auth.model.entity.SysRoleMenuColumn;
import com.breeze.boot.modules.auth.model.form.RoleMenuColumnForm;
import com.breeze.boot.modules.auth.model.vo.RoleMenuColumnVO;
import com.breeze.boot.modules.auth.service.SysRoleMenuColumnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色菜单字段数据权限管理模块
 *
 * @author gaoweixuan
 * @since 2024-03-18
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/roleMenuColumn")
@Tag(name = "系统角色菜单字段数据权限管理模块", description = "SysRoleMenuColumnController")
public class SysRoleMenuColumnController {

    /**
     * 系统角色菜单字段数据权限服务
     */
    private final SysRoleMenuColumnService sysRoleMenuColumnService;

    /**
     * 保存角色列权限
     *
     * @param roleMenuColumnForm 角色菜单栏表单
     * @return {@link Result }<{@link Boolean }>
     */
    @Operation(summary = "保存角色列权限")
    @PostMapping
    @SaCheckPermission("auth:menu:create")
    @BreezeSysLog(description = "保存角色列权限", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody RoleMenuColumnForm roleMenuColumnForm) {
        return this.sysRoleMenuColumnService.saveRoleMenuColumn(roleMenuColumnForm);
    }
    /**
     * 获取角色菜单列权限列表回显
     * <p>
     * 不加权限标识
     *
     * @param roleId 角色id
     * @return {@link Result}<{@link List}<{@link SysRoleMenu}>>
     */
    @Operation(summary = "获取角色菜单列权限列表回显", description = "选中的菜单列表回显")
    @GetMapping("/listRolesMenuColumnPermission")
    public Result<List<RoleMenuColumnVO>> listRolesMenuColumnPermission(@Parameter(description = "角色id") @RequestParam Long roleId) {
        // @formatter:off
        return Result.ok(this.sysRoleMenuColumnService
                .list(Wrappers.<SysRoleMenuColumn>lambdaQuery().eq(SysRoleMenuColumn::getRoleId, roleId))
                .stream().map(sysRoleMenuColumn ->
                        RoleMenuColumnVO.builder()
                                .menu(sysRoleMenuColumn.getMenu()).roleId(sysRoleMenuColumn.getRoleId())
                                .build())
                .collect(Collectors.toList()));
        // @formatter:on
    }
}
