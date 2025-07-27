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
import com.breeze.boot.system.model.form.SysDictGroupForm;
import com.breeze.boot.system.model.query.SysDictGroupQuery;
import com.breeze.boot.system.model.vo.SysDictGroupVO;
import com.breeze.boot.system.service.SysDictGroupService;
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
 * 字典分组 控制器
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/dictGroup")
@Tag(name = "字典分组管理模块", description = "SysDictGroupController")
public class SysDictGroupController {

    /**
     * 字典分组服务
     */
    private final SysDictGroupService sysDictGroupService;

    /**
     * 列表
     *
     * @param query 字典分组查询
     * @return {@link Result}<{@link Page}<{@link SysDictGroupVO }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("sys:dictGroup:list")
    public Result<Page<SysDictGroupVO>> list(@RequestBody SysDictGroupQuery query) {
        return Result.ok(this.sysDictGroupService.listPage(query));
    }

    /**
     * 列表
     *
     * @return {@link Result}<{@link Page}<{@link SysDictGroupVO }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    public Result<List<SysDictGroupVO>> listDictGroup() {
        return Result.ok(this.sysDictGroupService.listDictGroup());
    }

    /**
     * 详情
     *
     * @param sysDictGroupId 字典分组id
     * @return {@link Result}<{@link SysDictGroupVO }>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{sysDictGroupId}")
    @SaCheckPermission("sys:dictGroup:info")
    public Result<SysDictGroupVO> info(
            @Parameter(description = "字典分组 ID") @NotNull(message = "字典分组ID不能为空")
            @PathVariable("sysDictGroupId") Long sysDictGroupId) {
        return Result.ok(this.sysDictGroupService.getInfoById(sysDictGroupId));
    }

    /**
     * 创建
     *
     * @param form 字典分组表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("sys:dictGroup:create")
    @BreezeSysLog(description = "字典分组信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody SysDictGroupForm form) {
        return Result.ok(this.sysDictGroupService.saveSysDictGroup(form));
    }

    /**
     * 修改
     *
     * @param sysDictGroupId 字典分组ID
     * @param form           字典分组表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "修改")
    @PutMapping("/{sysDictGroupId}")
    @SaCheckPermission("sys:dictGroup:modify")
    @BreezeSysLog(description = "字典分组信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(
            @Parameter(description = "SysDictGroup ID") @NotNull(message = "字典分组ID不能为空") @PathVariable Long sysDictGroupId,
            @Valid @RequestBody SysDictGroupForm form) {
        return Result.ok(this.sysDictGroupService.modifySysDictGroup(sysDictGroupId, form));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:dictGroup:delete")
    @BreezeSysLog(description = "字典分组信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "字典分组 ids")
                                  @NotEmpty(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.sysDictGroupService.removeSysDictGroupByIds(ids);
    }

}