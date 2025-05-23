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

package com.breeze.boot.auth.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.auth.model.entity.SysRowPermission;
import com.breeze.boot.auth.model.form.RowPermissionForm;
import com.breeze.boot.auth.model.query.RowPermissionQuery;
import com.breeze.boot.auth.model.vo.RowPermissionVO;
import com.breeze.boot.auth.service.SysRowPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统行数据权限控制器
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/rowPermission")
@Tag(name = "系统行数据权限管理模块", description = "SysRowPermissionController")
public class SysRowPermissionController {

    /**
     * 系统数据权限服务
     */
    private final SysRowPermissionService sysRowPermissionService;

    @Operation(summary = "初始化")
    @GetMapping("/init")
    public void init() {
        this.sysRowPermissionService.init();
    }

    /**
     * 列表
     *
     * @param query 数据权限查询
     * @return {@link Result}<{@link Page}<{@link RowPermissionVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("auth:rowPermission:list")
    public Result<Page<RowPermissionVO>> list(@RequestBody RowPermissionQuery query) {
        return Result.ok(this.sysRowPermissionService.listPage(query));
    }

    /**
     * 详情
     *
     * @param permissionId 权限ID
     * @return {@link Result}<{@link RowPermissionVO}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{permissionId}")
    @SaCheckPermission("auth:rowPermission:info")
    public Result<RowPermissionVO> info(@Parameter(description = "权限ID") @NotNull(message = "参数不能为空") @PathVariable("permissionId") Long permissionId) {
        return Result.ok(this.sysRowPermissionService.getInfoById(permissionId));
    }


    /**
     * 校验权限编码是否重复
     *
     * @param permissionCode 权限编码
     * @param permissionId   权限ID
     * @return {@link Result }<{@link Boolean }>
     */
    @Operation(summary = "校验权限编码是否重复")
    @GetMapping("/checkRowPermissionCode")
    @SaCheckPermission("auth:rowPermission:list")
    public Result<Boolean> checkRowPermission(
            // @formatter:off
            @Parameter(description = "权限编码") @NotBlank(message = "权限编码不能为空") @RequestParam("permissionCode") String permissionCode,
            @Parameter(description = "权限ID") @RequestParam(value = "permissionId", required = false) Long permissionId) {
            // @formatter:on
        // @formatter:off
        return Result.ok(Objects.isNull(this.sysRowPermissionService.getOne(Wrappers.<SysRowPermission>lambdaQuery()
                .ne(Objects.nonNull(permissionId), SysRowPermission::getId, permissionId)
                .eq(SysRowPermission::getPermissionCode, permissionCode))));
        // @formatter:on
    }

    /**
     * 创建
     *
     * @param form 行权限表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("auth:rowPermission:create")
    @BreezeSysLog(description = "数据权限信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody RowPermissionForm form) {
        return this.sysRowPermissionService.saveRowPermission(form);
    }

    /**
     * 修改
     *
     * @param id   ID
     * @param form 数据权限表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("auth:rowPermission:modify")
    @BreezeSysLog(description = "数据权限信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "权限ID") @NotNull(message = "权限ID不能为空") @PathVariable Long id,
                                  @Valid @RequestBody RowPermissionForm form) {
        return this.sysRowPermissionService.modifyRowPermission(id, form);
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("auth:rowPermission:delete")
    @BreezeSysLog(description = "数据权限信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "权限IDS") @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return this.sysRowPermissionService.removeRowPermissionByIds(Arrays.asList(ids));
    }

    /**
     * 行数据权限类型下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "行数据权限类型下拉框", description = "下拉框接口")
    @GetMapping("/selectPermissionType")
    public Result<List<Map<String, Object>>> selectPermissionType() {
        return this.sysRowPermissionService.selectPermissionType();
    }

    /**
     * 数据权限下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "数据权限下拉框", description = "下拉框接口")
    @GetMapping("/selectCustomizePermission")
    public Result<List<Map<String, Object>>> selectCustomizePermission() {
        return this.sysRowPermissionService.selectCustomizePermission();
    }
}
