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

package com.breeze.boot.ai.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.ai.model.form.AiModelForm;
import com.breeze.boot.ai.model.query.AiModelQuery;
import com.breeze.boot.ai.model.vo.AiModelVO;
import com.breeze.boot.ai.service.AiModelService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
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
 * AI模型 控制器
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/ai/v1/aiModel")
@Tag(name = "AI模型管理模块", description = "AiModelController")
public class AiModelController {

    /**
     * AI模型服务
     */
    private final AiModelService aiModelService;

    /**
     * 列表
     *
     * @param query AI模型查询
     * @return {@link Result}<{@link Page}<{@link AiModelVO }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("ai:model:list")
    public Result<Page<AiModelVO>> list(@RequestBody AiModelQuery query) {
        return Result.ok(this.aiModelService.listPage(query));
    }

    /**
     * 详情
     *
     * @param modelId AI模型id
     * @return {@link Result}<{@link AiModelVO }>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{modelId}")
    @SaCheckPermission("ai:model:info")
    public Result<AiModelVO> info(
            @Parameter(description = "AI模型 ID") @NotNull(message = "AI模型ID不能为空")
            @PathVariable("modelId") Long modelId) {
        return Result.ok(this.aiModelService.getInfoById(modelId));
    }

    /**
     * 创建
     *
     * @param form AI模型表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("ai:model:create")
    @BreezeSysLog(description = "AI模型信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody AiModelForm form) {
        return Result.ok(this.aiModelService.saveAiModel(form));
    }

    /**
     * 修改
     *
     * @param aiModelId AI模型ID
     * @param form      AI模型表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "修改")
    @PutMapping("/{aiModelId}")
    @SaCheckPermission("ai:model:modify")
    @BreezeSysLog(description = "AI模型信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(
            @Parameter(description = "AiModel ID") @NotNull(message = "AI模型ID不能为空") @PathVariable Long aiModelId,
            @Valid @RequestBody AiModelForm form) {
        return Result.ok(this.aiModelService.modifyAiModel(aiModelId, form));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("ai:model:delete")
    @BreezeSysLog(description = "AI模型信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "AI模型 ids")
                                  @NotEmpty(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.aiModelService.removeAiModelByIds(ids);
    }

}