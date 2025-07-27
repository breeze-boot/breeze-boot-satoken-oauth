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

package com.breeze.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.system.model.form.SysConfigCategForm;
import com.breeze.boot.system.model.query.SysConfigCategQuery;
import com.breeze.boot.system.model.vo.SysConfigCategVO;
import com.breeze.boot.system.service.SysConfigCategService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统参数分类表 控制器
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/configCateg")
@Tag(name = "系统参数分类表管理模块", description = "SysConfigCategController")
public class SysConfigCategController {

    /**
     * 系统参数分类表服务
     */
    private final SysConfigCategService sysConfigCategService;

    /**
     * 列表
     *
     * @param query 系统参数分类表查询
     * @return {@link Result}<{@link Page}<{@link SysConfigCategVO }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("sys:configCateg:list")
    public Result<Page<SysConfigCategVO>> list(@RequestBody SysConfigCategQuery query) {
        return Result.ok(this.sysConfigCategService.listPage(query));
    }

    /**
     * 列表
     *
     * @return {@link Result}<{@link Page}<{@link SysConfigCategVO }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    public Result<List<SysConfigCategVO>> listConfigCateg() {
        return Result.ok(this.sysConfigCategService.listConfigCateg());
    }

    /**
     * 详情
     *
     * @param sysConfigCategId 系统参数分类表id
     * @return {@link Result}<{@link SysConfigCategVO }>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{sysConfigCategId}")
    @SaCheckPermission("sys:configCateg:info")
    public Result<SysConfigCategVO> info(
            @Parameter(description = "系统参数分类表 ID") @NotNull(message = "系统参数分类表ID不能为空")
            @PathVariable("sysConfigCategId") Long sysConfigCategId) {
        return Result.ok(this.sysConfigCategService.getInfoById(sysConfigCategId));
    }

    /**
     * 创建
     *
     * @param form 系统参数分类表表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("sys:configCateg:create")
    @BreezeSysLog(description = "系统参数分类表信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysConfigCategForm form) {
        return Result.ok(this.sysConfigCategService.saveSysConfigCateg(form));
    }

    /**
     * 修改
     *
     * @param sysConfigCategId 系统参数分类表ID
     * @param form             系统参数分类表表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "修改")
    @PutMapping("/{sysConfigCategId}")
    @SaCheckPermission("sys:configCateg:modify")
    @BreezeSysLog(description = "系统参数分类表信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(
            @Parameter(description = "SysConfigCateg ID") @NotNull(message = "系统参数分类表ID不能为空") @PathVariable Long sysConfigCategId,
            @Valid @RequestBody SysConfigCategForm form) {
        return Result.ok(this.sysConfigCategService.modifySysConfigCateg(sysConfigCategId, form));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:configCateg:delete")
    @BreezeSysLog(description = "系统参数分类表信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "系统参数分类表 ids")
                                  @NotEmpty(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.sysConfigCategService.removeSysConfigCategByIds(ids);
    }

}