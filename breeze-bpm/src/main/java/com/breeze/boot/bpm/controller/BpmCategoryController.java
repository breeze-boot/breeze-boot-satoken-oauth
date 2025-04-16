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

package com.breeze.boot.bpm.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.bpm.model.form.BpmCategoryForm;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.bpm.model.entity.BpmCategory;
import com.breeze.boot.bpm.model.query.BpmCategoryQuery;
import com.breeze.boot.bpm.model.vo.BpmCategoryVO;
import com.breeze.boot.bpm.service.IBpmCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * 流程资源管理控制器
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bpm/v1/category")
@Tag(name = "流程分类管理模块", description = "BpmCategoryController")
public class BpmCategoryController {

    /**
     * 流程分类服务
     */
    private final IBpmCategoryService bpmCategoryService;

    /**
     * 列表
     *
     * @param processCategory 流程类别
     * @return {@link Result}<{@link IPage}<{@link BpmCategoryVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("bpm:category:list")
    public Result<IPage<BpmCategoryVO>> list(@RequestBody BpmCategoryQuery processCategory) {
        return Result.ok(this.bpmCategoryService.listPage(processCategory));
    }

    /**
     * 详情
     *
     * @param categoryId 流程分类ID
     * @return {@link Result}<{@link BpmCategory}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{categoryId}")
    @SaCheckPermission("bpm:category:info")
    public Result<BpmCategoryVO> info(@Parameter(description = "流程分类ID") @NotNull(message = "流程分类ID不能为空") @PathVariable("categoryId") Long categoryId) {
        return Result.ok(this.bpmCategoryService.getInfoById(categoryId));
    }

    /**
     * 校验流程分类编码是否重复
     *
     * @param categoryCode 流程分类编码
     * @param categoryId   流程分类ID
     * @return {@link Result }<{@link Boolean }>
     */
    @Operation(summary = "校验流程分类编码是否重复")
    @GetMapping("/checkCategoryCode")
    @SaCheckPermission("bpm:category:list")
    public Result<Boolean> checkCategoryCode(@Parameter(description = "流程分类编码") @NotBlank(message = "流程分类编码不能为空") @RequestParam("categoryCode") String categoryCode,
                                             @Parameter(description = "流程分类ID") @RequestParam(value = "categoryId", required = false) Long categoryId) {
        // @formatter:off
        return Result.ok(Objects.isNull(this.bpmCategoryService.getOne(Wrappers.<BpmCategory>lambdaQuery()
                .ne(Objects.nonNull(categoryId), BpmCategory::getId, categoryId)
                .eq(BpmCategory::getCategoryCode, categoryCode))));
        // @formatter:on
    }

    /**
     * 创建
     *
     * @param bpmCategoryForm 流程分类表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("bpm:category:create")
    @BreezeSysLog(description = "流程分类信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody BpmCategoryForm bpmCategoryForm) {
        return Result.ok(this.bpmCategoryService.saveFlowCategory(bpmCategoryForm));
    }

    /**
     * 修改
     *
     * @param bpmCategoryForm 流程分类表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("bpm:category:modify")
    @BreezeSysLog(description = "流程分类信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@PathVariable @Parameter(description = "流程分类ID") @NotNull(message = "流程分类ID不能为空") Long id,
                                  @Valid @RequestBody BpmCategoryForm bpmCategoryForm) {
        return Result.ok(this.bpmCategoryService.modifyFlowCategory(id, bpmCategoryForm));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("bpm:category:delete")
    @BreezeSysLog(description = "流程分类信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "流程分类IDS") @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.bpmCategoryService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 流程分类下拉框
     *
     * @return {@link Result }<{@link List }<{@link Map }<{@link String }, {@link Object }>>>
     */
    @Operation(summary = "流程分类下拉框", description = "下拉框接口")
    @GetMapping("/selectCategory")
    public Result<List<Map<String, Object>>> selectCategory() {
        return this.bpmCategoryService.selectCategory();
    }
}
