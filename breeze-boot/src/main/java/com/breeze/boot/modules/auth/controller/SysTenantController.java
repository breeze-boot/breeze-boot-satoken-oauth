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
import com.breeze.boot.modules.auth.model.entity.SysTenant;
import com.breeze.boot.modules.auth.model.form.TenantForm;
import com.breeze.boot.modules.auth.model.query.TenantQuery;
import com.breeze.boot.modules.auth.model.vo.TenantVO;
import com.breeze.boot.modules.auth.service.SysTenantService;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 系统岗位控制器
 *
 * @author gaoweixuan
 * @since 2022-11-06
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/tenant")
@Tag(name = "系统租户管理模块", description = "SysTenantController")
public class SysTenantController {

    /**
     * 系统租户服务
     */
    private final SysTenantService sysTenantService;

    /**
     * 列表
     *
     * @param tenantQuery 租户查询
     * @return {@link Result}<{@link Page}<{@link TenantVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("auth:tenant:list")
    public Result<Page<TenantVO>> list(@RequestBody TenantQuery tenantQuery) {
        return Result.ok(this.sysTenantService.listPage(tenantQuery));
    }

    /**
     * 详情
     *
     * @param tenantId 租户id
     * @return {@link Result}<{@link TenantVO}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{tenantId}")
    @SaCheckPermission("auth:tenant:info")
    public Result<TenantVO> info(@Parameter(description = "租户ID") @NotNull(message = "租户ID不能为空") @PathVariable("tenantId") Long tenantId) {
        return Result.ok(this.sysTenantService.getInfoById(tenantId));
    }

    /**
     * 创建
     *
     * @param tenant 租户表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("auth:tenant:create")
    @BreezeSysLog(description = "租户信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysTenant tenant) {
        return Result.ok(this.sysTenantService.save(tenant));
    }

    /**
     * 校验租户编码是否重复
     *
     * @param tenantCode 租户编码
     * @param tenantId   租户ID
     * @return {@link Result }<{@link Boolean }>
     */
    @Operation(summary = "校验租户编码是否重复")
    @GetMapping("/checkTenantCode")
    @SaCheckPermission("auth:tenant:list")
    public Result<Boolean> checkTenantCode(@Parameter(description = "租户编码") @NotBlank(message = "租户编码不能为空") @RequestParam("tenantCode") String tenantCode,
                                           @Parameter(description = "租户ID") @RequestParam(value = "tenantId", required = false) Long tenantId) {
        // @formatter:off
        return Result.ok(Objects.isNull(this.sysTenantService.getOne(Wrappers.<SysTenant>lambdaQuery()
                .ne(Objects.nonNull(tenantId), SysTenant::getId, tenantId)
                .eq(SysTenant::getTenantCode, tenantCode))));
        // @formatter:on
    }

    /**
     * 修改
     *
     * @param id         ID
     * @param tenantForm 租户表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("auth:tenant:modify")
    @BreezeSysLog(description = "租户信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "租户ID") @NotNull(message = "租户ID不能为空") @PathVariable Long id,
                                  @Valid @RequestBody TenantForm tenantForm) {
        return Result.ok(this.sysTenantService.modifyTenant(id,tenantForm));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("auth:tenant:delete")
    @BreezeSysLog(description = "租户信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "租户IDS")
                                  @NotEmpty(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.sysTenantService.removeTenantByIds(ids);
    }

    /**
     * 租户下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "租户下拉框", description = "下拉框接口")
    @GetMapping("/selectTenant")
    public Result<List<Map<String, Object>>> selectTenant() {
        return this.sysTenantService.selectTenant();
    }

}
