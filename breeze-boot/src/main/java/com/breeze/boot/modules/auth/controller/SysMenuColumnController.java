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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.auth.model.form.MenuColumnForm;
import com.breeze.boot.modules.auth.model.query.MenuColumnQuery;
import com.breeze.boot.modules.auth.model.vo.MenuColumnVO;
import com.breeze.boot.modules.auth.model.vo.RolesMenuColumnVO;
import com.breeze.boot.modules.auth.service.SysMenuColumnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统菜单列权限控制器
 *
 * @author gaoweixuan
 * @since 2024-07-17
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/menuColumn")
@Tag(name = "系统菜单列权限管理模块", description = "SysMenuColumnController")
public class SysMenuColumnController {

    /**
     * 系统菜单列权限服务
     */
    private final SysMenuColumnService sysMenuColumnService;

    /**
     * 获取当前用户角色下菜单列  初始化使用
     *
     * @return {@link Result }<{@link List }<{@link RolesMenuColumnVO }>>
     */
    @Operation(summary = "获取当前用户角色下菜单列")
    @GetMapping("/getRolesMenuColumns")
    public Result<List<RolesMenuColumnVO>> getRolesMenuColumns() {
        return Result.ok(this.sysMenuColumnService.getRolesMenuColumns());
    }

    /**
     * 列表
     *
     * @param permissionQuery 数据权限查询
     * @return {@link Result }<{@link Page }<{@link MenuColumnVO }>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @SaCheckPermission("auth:menuColumn:list")
    public Result<Page<MenuColumnVO>> list(MenuColumnQuery permissionQuery) {
        return Result.ok(this.sysMenuColumnService.listPage(permissionQuery));
    }

    /**
     * 详情
     *
     * @param menuColumnId 菜单列ID
     * @return {@link Result }<{@link MenuColumnVO }>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{menuColumnId}")
    @SaCheckPermission("auth:menuColumn:info")
    public Result<MenuColumnVO> info(@Parameter(description = "权限ID") @PathVariable("menuColumnId") Long menuColumnId) {
        return Result.ok(this.sysMenuColumnService.getInfoById(menuColumnId));
    }

    /**
     * 创建
     *
     * @param menuColumnForm 菜单列表单
     * @return {@link Result }<{@link Boolean }>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("auth:menu:create")
    @BreezeSysLog(description = "菜单列保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody MenuColumnForm menuColumnForm) {
        return this.sysMenuColumnService.saveMenuColumn(menuColumnForm);
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("auth:menuColumn:delete")
    @BreezeSysLog(description = "数据权限信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "权限IDS")
                                  @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return this.sysMenuColumnService.removeMenuColumnByIds(Arrays.asList(ids));
    }

}
