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
import com.breeze.boot.ai.model.query.AiChatKnowledgeQuery;
import com.breeze.boot.ai.model.vo.AiChatKnowledgeVO;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.ai.service.AiChatKnowledgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/ai/v1/aiChatKnowledge")
@Tag(name = "聊天文档管理模块", description = "AiChatKnowledgeController")
public class AiChatKnowledgeController {

    /**
     * 聊天文档服务
     */
    private final AiChatKnowledgeService aiChatKnowledgeService;

    /**
     * 列表
     *
     * @param query  聊天文档查询
     * @return {@link Result}<{@link Page}<{@link AiChatKnowledgeVO }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("ai:chatKnowledge:list")
    public Result<Page<AiChatKnowledgeVO>> list(@RequestBody AiChatKnowledgeQuery query) {
        return Result.ok(this.aiChatKnowledgeService.listPage(query));
    }

    /**
     * 详情
     *
     * @param aiChatKnowledgeId 聊天文档id
     * @return {@link Result}<{@link AiChatKnowledgeVO }>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{aiChatKnowledgeId}")
    @SaCheckPermission("ai:chatKnowledge:info")
    public Result<AiChatKnowledgeVO> info(
            @Parameter(description = "聊天文档 ID") @NotNull(message = "聊天文档ID不能为空")
            @PathVariable("aiChatKnowledgeId") Long aiChatKnowledgeId) {
        return Result.ok(this.aiChatKnowledgeService.getInfoById(aiChatKnowledgeId));
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("ai:chatKnowledge:delete")
    @BreezeSysLog(description = "聊天文档信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "AiChatKnowledge ids")
                                  @NotEmpty(message = "参数不能为空") @RequestBody List<Long> ids) {
        return this.aiChatKnowledgeService.removeAiChatKnowledgeByIds(ids);
    }

}