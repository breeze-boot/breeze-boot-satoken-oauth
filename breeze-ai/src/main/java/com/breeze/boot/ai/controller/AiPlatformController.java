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
import com.breeze.boot.ai.model.form.AiPlatformForm;
import com.breeze.boot.ai.model.query.AiPlatformQuery;
import com.breeze.boot.ai.model.vo.AiPlatformVO;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.ai.service.AiPlatformService;
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
 * AI平台 控制器
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/ai/v1/aiPlatform")
@Tag(name = "AI平台管理模块", description = "AiPlatformController")
public class AiPlatformController {

    /**
     * AI平台服务
     */
    private final AiPlatformService aiPlatformService;

    /**
     * 列表
     *
     * @param query  AI平台查询
     * @return {@link Result}<{@link Page}<{@link AiPlatformVO }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("ai:platform:list")
    public Result<Page<AiPlatformVO>> list(@RequestBody AiPlatformQuery query) {
        return Result.ok(this.aiPlatformService.listPage(query));
    }

    /**
     * 详情
     *
     * @param aiPlatformId AI平台id
     * @return {@link Result}<{@link AiPlatformVO }>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{aiPlatformId}")
    @SaCheckPermission("ai:platform:info")
    public Result<AiPlatformVO> info(
            @Parameter(description = "AI平台 ID") @NotNull(message = "AI平台ID不能为空")
            @PathVariable("aiPlatformId") Long aiPlatformId) {
        return Result.ok(this.aiPlatformService.getInfoById(aiPlatformId));
    }

    /**
     * 创建
     *
     * @param form     AI平台表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("ai:platform:create")
    @BreezeSysLog(description = "AI平台信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody AiPlatformForm form) {
        return Result.ok(this.aiPlatformService.saveAiPlatform(form));
    }

    /**
     * 修改
     *
     * @param aiPlatformId AI平台ID
     * @param form     AI平台表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "修改")
    @PutMapping("/{aiPlatformId}")
    @SaCheckPermission("ai:platform:modify")
    @BreezeSysLog(description = "AI平台信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(
            @Parameter(description = "AiPlatform ID") @NotNull(message = "AI平台ID不能为空") @PathVariable Long aiPlatformId,
            @Valid @RequestBody AiPlatformForm form) {
        return Result.ok(this.aiPlatformService.modifyAiPlatform(aiPlatformId, form));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("ai:platform:delete")
    @BreezeSysLog(description = "AI平台信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "AiPlatform ids")
                                  @NotEmpty(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.aiPlatformService.removeAiPlatformByIds(ids);
    }

    /**
     * AI平台下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "AI平台下拉框", description = "下拉框接口")
    @GetMapping("/selectAIPlatform")
    public Result<List<Map<String, Object>>> selectAIPlatform() {
        return this.aiPlatformService.selectAIPlatform();
    }
}