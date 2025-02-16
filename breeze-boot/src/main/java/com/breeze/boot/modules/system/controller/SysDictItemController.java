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
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.system.model.form.DictItemForm;
import com.breeze.boot.modules.system.model.query.DictItemQuery;
import com.breeze.boot.modules.system.model.vo.DictItemVO;
import com.breeze.boot.modules.system.service.SysDictItemService;
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
import java.util.List;

/**
 * 系统字典控制器
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/dictItem")
@Tag(name = "系统字典项管理模块", description = "SysDictItemController")
public class SysDictItemController {

    /**
     * 系统字典服务
     */
    private final SysDictItemService sysDictItemService;

    /**
     * 列表
     *
     * @param dictItemQuery 字典项查询
     * @return {@link Result}<{@link List}<{@link DictItemVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/list")
    @SaCheckPermission("sys:dict:list")
    public Result<List<DictItemVO>> list(@RequestBody DictItemQuery dictItemQuery) {
        return Result.ok(this.sysDictItemService.listDictItem(dictItemQuery));
    }

    /**
     * 详情
     *
     * @param dictItemId 字典项id
     * @return {@link Result}<{@link DictItemVO}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{dictItemId}")
    @SaCheckPermission("auth:dict:info")
    public Result<DictItemVO> info(@Parameter(description = "字典项ID") @NotNull(message = "字典项ID") @PathVariable("dictItemId") Long dictItemId) {
        return Result.ok(this.sysDictItemService.getInfoById(dictItemId));
    }

    /**
     * 创建
     *
     * @param dictItemForm 字典项表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("sys:dict:create")
    @BreezeSysLog(description = "字典项信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody DictItemForm dictItemForm) {
        return Result.ok(sysDictItemService.saveDictItem(dictItemForm));
    }

    /**
     * 修改
     *
     * @param id           ID
     * @param dictItemForm 字典项表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("sys:dict:modify")
    @BreezeSysLog(description = "字典项信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "字典项ID") @NotNull(message = "字典项ID不能为空") @PathVariable Long id,
                                  @Valid @RequestBody DictItemForm dictItemForm) {
        return Result.ok(this.sysDictItemService.modifyDictItem(id, dictItemForm));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:dict:delete")
    @BreezeSysLog(description = "字典项信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "字典项ID")
                                  @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysDictItemService.removeByIds(Arrays.asList(ids)));
    }

}
