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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.auth.model.entity.SysPost;
import com.breeze.boot.modules.auth.model.entity.SysRole;
import com.breeze.boot.modules.auth.model.entity.SysRoleMenu;
import com.breeze.boot.modules.auth.model.form.MenuPermissionForm;
import com.breeze.boot.modules.auth.model.form.RoleForm;
import com.breeze.boot.modules.auth.model.query.RoleQuery;
import com.breeze.boot.modules.auth.model.vo.RoleVO;
import com.breeze.boot.modules.auth.service.SysRoleMenuService;
import com.breeze.boot.modules.auth.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 系统角色控制器
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/role")
@Tag(name = "系统角色管理模块", description = "SysRoleController")
public class SysRoleController {

    /**
     * 系统角色服务
     */
    private final SysRoleService sysRoleService;

    /**
     * 系统角色菜单服务
     */
    private final SysRoleMenuService sysRoleMenuService;

    /**
     * 列表
     *
     * @param roleQuery 角色查询
     * @return {@link Result}<{@link Page}<{@link RoleVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @SaCheckPermission("auth:role:list")
    public Result<Page<RoleVO>> list(RoleQuery roleQuery) {
        return Result.ok(this.sysRoleService.listPage(roleQuery));
    }

    /**
     * 详情
     *
     * @param roleId 角色id
     * @return {@link Result}<{@link SysRole}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{roleId}")
    @SaCheckPermission("auth:role:info")
    public Result<RoleVO> info(@Parameter(description = "角色ID") @NotNull(message = "角色ID不能为空") @PathVariable("roleId") Long roleId) {
        return Result.ok(this.sysRoleService.getInfoById(roleId));
    }

    /**
     * 校验角色编码是否重复
     *
     * @param roleCode 角色编码
     * @param roleId   角色ID
     * @return {@link Result}<{@link SysPost}>
     */
    @Operation(summary = "校验角色编码是否重复")
    @GetMapping("/checkRoleCode")
    @SaCheckPermission("auth:role:list")
    public Result<Boolean> checkRoleCode(@Parameter(description = "角色编码") @NotBlank(message = "角色编码不能为空") @RequestParam("roleCode") String roleCode,
                                         @Parameter(description = "角色ID") @RequestParam(value = "roleId", required = false) Long roleId) {
        return Result.ok(Objects.isNull(this.sysRoleService.getOne(Wrappers.<SysRole>lambdaQuery()
                .ne(Objects.nonNull(roleId), SysRole::getId, roleId)
                .eq(SysRole::getRoleCode, roleCode))));
    }

    /**
     * 创建
     *
     * @param roleForm 角色参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("auth:role:create")
    @BreezeSysLog(description = "角色信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody RoleForm roleForm) {
        return sysRoleService.saveRole(roleForm);
    }

    /**
     * 修改
     *
     * @param id       ID
     * @param roleForm 角色表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("auth:role:modify")
    @BreezeSysLog(description = "角色信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "角色ID") @PathVariable Long id,
                                  @Valid @RequestBody RoleForm roleForm) {
        return sysRoleService.modifyRole(id, roleForm);
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("auth:role:delete")
    @BreezeSysLog(description = "角色信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "角色IDS") @NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return sysRoleService.deleteByIds(Arrays.asList(ids));
    }

    /**
     * 获取用户角色列表回显
     * <p>
     * 不加权限标识
     *
     * @param userId 用户Id
     * @return {@link Result}<{@link List}<{@link Long}>>
     */
    @Operation(summary = "获取用户角色列表回显", description = "选中的用户角色列表回显")
    @GetMapping("/listUserRoles")
    public Result<List<Long>> listUserRoles(@Parameter(description = "用户Id") @RequestParam("id") Long userId) {
        return Result.ok(this.sysRoleService.listUserRoles(userId));
    }

    /**
     * 获取树菜单权限列表回显
     * <p>
     * 不加权限标识
     *
     * @param roleId 角色id
     * @return {@link Result}<{@link List}<{@link SysRoleMenu}>>
     */
    @Operation(summary = "获取树菜单权限", description = "选中的菜单列表回显")
    @GetMapping("/listRolesPermission")
    public Result<List<SysRoleMenu>> listRolesPermission(@Parameter(description = "角色id") @RequestParam Long roleId) {
        return Result.ok(this.sysRoleMenuService.list(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, roleId)));
    }

    /**
     * 编辑菜单权限
     * <p>
     * /listTreePermission /listRolesPermission 不使用权限标识，直接使用这个
     *
     * @param menuPermissionForm 菜单权限参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "编辑菜单权限")
    @PutMapping("/modifyPermission")
    @SaCheckPermission(value = "auth:menu:permission:modify", orRole = "ROLE_ADMIN")
    @BreezeSysLog(description = "编辑菜单权限", type = LogType.EDIT)
    public Result<Boolean> modifyMenuPermission(@Valid @RequestBody MenuPermissionForm menuPermissionForm) {
        return this.sysRoleMenuService.modifyMenuPermission(menuPermissionForm);
    }
}
