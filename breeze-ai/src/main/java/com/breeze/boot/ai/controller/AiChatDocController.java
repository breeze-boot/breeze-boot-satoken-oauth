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
import com.breeze.boot.ai.model.form.AiChatDocForm;
import com.breeze.boot.ai.model.query.AiChatDocQuery;
import com.breeze.boot.ai.model.vo.AiChatDocVO;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.ai.service.AiChatDocService;
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
 * 聊天文档 控制器
 *
 * @author gaoweixuan
 * @since 2025-04-22
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/ai/v1/aiChatDoc")
@Tag(name = "聊天文档管理模块", description = "AiChatDocController")
public class AiChatDocController {

    /**
     * 聊天文档服务
     */
    private final AiChatDocService aiChatDocService;

    /**
     * 列表
     *
     * @param query  聊天文档查询
     * @return {@link Result}<{@link Page}<{@link AiChatDocVO }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("ai:chatDoc:list")
    public Result<Page<AiChatDocVO>> list(@RequestBody AiChatDocQuery query) {
        return Result.ok(this.aiChatDocService.listPage(query));
    }

    /**
     * 详情
     *
     * @param aiChatDocId 聊天文档id
     * @return {@link Result}<{@link AiChatDocVO }>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{aiChatDocId}")
    @SaCheckPermission("ai:chatDoc:info")
    public Result<AiChatDocVO> info(
            @Parameter(description = "聊天文档 ID") @NotNull(message = "聊天文档ID不能为空")
            @PathVariable("aiChatDocId") Long aiChatDocId) {
        return Result.ok(this.aiChatDocService.getInfoById(aiChatDocId));
    }

    /**
     * 创建
     *
     * @param form     聊天文档表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("ai:chatDoc:create")
    @BreezeSysLog(description = "聊天文档信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody AiChatDocForm form) {
        return Result.ok(this.aiChatDocService.saveAiChatDoc(form));
    }

    /**
     * 修改
     *
     * @param aiChatDocId 聊天文档ID
     * @param form     聊天文档表单
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "修改")
    @PutMapping("/{aiChatDocId}")
    @SaCheckPermission("ai:chatDoc:modify")
    @BreezeSysLog(description = "聊天文档信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(
            @Parameter(description = "AiChatDoc ID") @NotNull(message = "聊天文档ID不能为空") @PathVariable Long aiChatDocId,
            @Valid @RequestBody AiChatDocForm form) {
        return Result.ok(this.aiChatDocService.modifyAiChatDoc(aiChatDocId, form));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("ai:chatDoc:delete")
    @BreezeSysLog(description = "聊天文档信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "AiChatDoc ids")
                                  @NotEmpty(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.aiChatDocService.removeAiChatDocByIds(ids);
    }

}