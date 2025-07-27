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
import com.breeze.boot.system.model.form.SysConfigForm;
import com.breeze.boot.system.model.query.SysConfigQuery;
import com.breeze.boot.system.model.vo.SysConfigVO;
import com.breeze.boot.system.service.SysConfigService;
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
 * 系统配置 控制器
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/config")
@Tag(name = "系统配置管理模块", description = "SysConfigController")
public class SysConfigController {

    /**
     * 系统配置服务
     */
    private final SysConfigService sysConfigService;

    /**
     * 列表
     *
     * @param query 系统配置查询
     * @return {@link Result}<{@link Page}<{@link SysConfigVO }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("sys:config:list")
    public Result<Page<SysConfigVO>> list(@RequestBody SysConfigQuery query) {
        return Result.ok(this.sysConfigService.listPage(query));
    }

    /**
     * 详情
     *
     * @param sysConfigId 系统配置id
     * @return {@link Result}<{@link SysConfigVO }>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{sysConfigId}")
    @SaCheckPermission("sys:config:info")
    public Result<SysConfigVO> info(
            @Parameter(description = "系统配置 ID") @NotNull(message = "系统配置ID不能为空")
            @PathVariable("sysConfigId") Long sysConfigId) {
        return Result.ok(this.sysConfigService.getInfoById(sysConfigId));
    }

    /**
     * 创建
     *
     * @param form 系统配置表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("sys:config:create")
    @BreezeSysLog(description = "系统配置信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysConfigForm form) {
        return Result.ok(this.sysConfigService.saveSysConfig(form));
    }

    /**
     * 修改
     *
     * @param sysConfigId 系统配置ID
     * @param form        系统配置表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "修改")
    @PutMapping("/{sysConfigId}")
    @SaCheckPermission("sys:config:modify")
    @BreezeSysLog(description = "系统配置信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(
            @Parameter(description = "系统配置 ID") @NotNull(message = "系统配置ID不能为空") @PathVariable Long sysConfigId,
            @Valid @RequestBody SysConfigForm form) {
        return Result.ok(this.sysConfigService.modifySysConfig(sysConfigId, form));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:config:delete")
    @BreezeSysLog(description = "系统配置信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "系统配置 ids")
                                  @NotEmpty(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.sysConfigService.removeSysConfigByIds(ids);
    }

}