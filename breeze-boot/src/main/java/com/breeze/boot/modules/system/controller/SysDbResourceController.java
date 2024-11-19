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

package com.breeze.boot.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.system.model.entity.SysDbResource;
import com.breeze.boot.modules.system.model.form.DbResourceForm;
import com.breeze.boot.modules.system.model.query.DbResourceQuery;
import com.breeze.boot.modules.system.model.vo.DbResourceVO;
import com.breeze.boot.modules.system.service.SysDbResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 系统数据源管理模块
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/dbResource")
@Tag(name = "系统数据源管理模块", description = "SysDbResourceController")
public class SysDbResourceController {

    /**
     * 系统数据源服务
     */
    private final SysDbResourceService sysDbResourceService;

    /**
     * 列表
     *
     * @param dbResourceQuery 数据源查询
     * @return {@link Result}<{@link Page}<{@link DbResourceVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @SaCheckPermission("sys:dbResource:list")
    public Result<Page<DbResourceVO>> list(DbResourceQuery dbResourceQuery) {
        return Result.ok(this.sysDbResourceService.listPage(dbResourceQuery));
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link Result}<{@link SysDbResource}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @SaCheckPermission("sys:dbResource:info")
    public Result<DbResourceVO> info(@NotNull(message = "不能为空") @PathVariable Long id) {
        return Result.ok(this.sysDbResourceService.getDbResourceById(id));
    }

    /**
     * 创建
     *
     * @param dbResourceForm 数据源
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("sys:dbResource:create")
    @BreezeSysLog(description = "数据源信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody DbResourceForm dbResourceForm) {
        return Result.ok(this.sysDbResourceService.saveDbResource(dbResourceForm));
    }

    /**
     * 修改
     *
     * @param id     ID
     * @param dbResourceForm 数据源表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("sys:dbResource:modify")
    @BreezeSysLog(description = "数据源信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "数据源ID") @NotNull(message = "数据源ID") @PathVariable Long id,
                                  @Valid @RequestBody DbResourceForm dbResourceForm) {
        return Result.ok(this.sysDbResourceService.modifyDbResource(id, dbResourceForm));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:dbResource:delete")
    @BreezeSysLog(description = "数据源信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "数据源IDS")
                                  @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysDbResourceService.removeDbResourceByIds(Arrays.asList(ids)));
    }

}
