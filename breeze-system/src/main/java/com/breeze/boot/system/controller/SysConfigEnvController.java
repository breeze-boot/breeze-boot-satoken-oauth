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
import com.breeze.boot.system.model.form.SysConfigEnvForm;
import com.breeze.boot.system.model.query.SysConfigEnvQuery;
import com.breeze.boot.system.model.vo.SysConfigEnvVO;
import com.breeze.boot.system.service.SysConfigEnvService;
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
import java.util.Map;

/**
 * 系统环境配置 控制器
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/configEnv")
@Tag(name = "系统环境配置管理模块", description = "SysConfigEnvController")
public class SysConfigEnvController {

    /**
     * 系统环境配置服务
     */
    private final SysConfigEnvService sysConfigEnvService;

    /**
     * 列表
     *
     * @param query 系统环境配置查询
     * @return {@link Result}<{@link Page}<{@link SysConfigEnvVO }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("sys:configEnv:list")
    public Result<Page<SysConfigEnvVO>> list(@RequestBody SysConfigEnvQuery query) {
        return Result.ok(this.sysConfigEnvService.listPage(query));
    }

    /**
     * 详情
     *
     * @param sysConfigEnvId 系统环境配置id
     * @return {@link Result}<{@link SysConfigEnvVO }>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{sysConfigEnvId}")
    @SaCheckPermission("sys:configEnv:info")
    public Result<SysConfigEnvVO> info(
            @Parameter(description = "系统环境配置 ID") @NotNull(message = "系统环境配置ID不能为空")
            @PathVariable("sysConfigEnvId") Long sysConfigEnvId) {
        return Result.ok(this.sysConfigEnvService.getInfoById(sysConfigEnvId));
    }

    /**
     * 创建
     *
     * @param form 系统环境配置表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("sys:configEnv:create")
    @BreezeSysLog(description = "系统环境配置信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysConfigEnvForm form) {
        return Result.ok(this.sysConfigEnvService.saveSysConfigEnv(form));
    }

    /**
     * 修改
     *
     * @param sysConfigEnvId 系统环境配置ID
     * @param form           系统环境配置表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "修改")
    @PutMapping("/{sysConfigEnvId}")
    @SaCheckPermission("sys:configEnv:modify")
    @BreezeSysLog(description = "系统环境配置信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(
            @Parameter(description = "SysConfigEnv ID") @NotNull(message = "系统环境配置ID不能为空") @PathVariable Long sysConfigEnvId,
            @Valid @RequestBody SysConfigEnvForm form) {
        return Result.ok(this.sysConfigEnvService.modifySysConfigEnv(sysConfigEnvId, form));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:configEnv:delete")
    @BreezeSysLog(description = "系统环境配置信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "系统环境配置 ids")
                                  @NotEmpty(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.sysConfigEnvService.removeSysConfigEnvByIds(ids);
    }

    /**
     * 系统配置环境下拉框
     *
     * @return {@link Result }<{@link List }<{@link Map }<{@link String }, {@link Object }>>>
     */
    @Operation(summary = "系统配置环境下拉框")
    @PostMapping("/list")
    public Result<List<Map<String, Object>>> list() {
        return Result.ok(this.sysConfigEnvService.selectConfigEnv());
    }
}